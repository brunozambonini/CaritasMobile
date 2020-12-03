package com.example.caritaspiapplication.activities.matricula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.adapter.AdapterListaMatricula;
import com.example.caritaspiapplication.controller.MatriculaController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Matricula;


import java.util.List;

public class ListarMatriculaActivity extends AppCompatActivity {

    private ListView lsvMatricula;
    private List<Matricula> matriculaList;
    private AdapterListaMatricula adapterListaMatricula;
    private Button btnCadastrarMatricula;
    private MatriculaController matriculaController;

    private EditText edt_pesquisaTurma;
    private EditText edt_pesquisaAtend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_matricula);

        this.edt_pesquisaTurma = (EditText) findViewById(R.id.edt_pesquisaTurma);
        this.edt_pesquisaAtend = (EditText) findViewById(R.id.edt_pesquisaAtend);

        this.btnCadastrarMatricula = (Button) findViewById(R.id.btn_novaMatricula);
        this.btnCadastrarMatricula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarMatriculaActivity.this, MatriculaActivity.class);
                startActivity(intent);
            }
        });

        this.matriculaController = new MatriculaController(ConexaoSQLite.getInstance(ListarMatriculaActivity.this));
        matriculaList = matriculaController.getListaMatriculaController();

        this.lsvMatricula = (ListView) findViewById(R.id.lsvMatricula);
        this.adapterListaMatricula = new AdapterListaMatricula(ListarMatriculaActivity.this, this.matriculaList);
        this.lsvMatricula.setAdapter(this.adapterListaMatricula);

        if (getIntent().getExtras() != null) {
            Bundle bundleNomeAtendido = getIntent().getExtras();
            if (!bundleNomeAtendido.getString("nome_atendido").isEmpty()) {
                this.edt_pesquisaAtend.setText(bundleNomeAtendido.getString("nome_atendido"));
                filterList();
            }
        }

        //Janela de dialogo para optar entre excluir e visualizar um registro
        this.lsvMatricula.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Matricula matriculaSelecionado = (Matricula) adapterListaMatricula.getItem(position);

                AlertDialog.Builder janelaDeEsolha = new AlertDialog.Builder(ListarMatriculaActivity.this);

                janelaDeEsolha.setTitle("Escolha: ");
                janelaDeEsolha.setMessage("O que deseja fazer?");

                janelaDeEsolha.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                janelaDeEsolha.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean excluiu = matriculaController.excluirMatriculaController(matriculaSelecionado.getId());

                        dialog.cancel();

                        if (excluiu) {
                            adapterListaMatricula.removerMatricula(position);
                            Toast.makeText(ListarMatriculaActivity.this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ListarMatriculaActivity.this, "Erro ao excluir registro", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEsolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundleDadosMatricula = new Bundle();

                        bundleDadosMatricula.putLong("id", matriculaSelecionado.getId());
                        bundleDadosMatricula.putString("nome_atend", matriculaSelecionado.getNome_atend());
                        bundleDadosMatricula.putString("nome_turma", matriculaSelecionado.getNome_turma());
                        bundleDadosMatricula.putLong("cod_atend", matriculaSelecionado.getCod_atend());
                        bundleDadosMatricula.putLong("cod_turma", matriculaSelecionado.getCod_turma());

                        Intent intentEditarMatricula = new Intent(ListarMatriculaActivity.this, EditarMatriculaActivity.class);
                        intentEditarMatricula.putExtras(bundleDadosMatricula);
                        startActivity(intentEditarMatricula);
                    }
                });

                janelaDeEsolha.create().show();
            }
        });

    }

    //procurar
    public void eventSearch(View view){
        String filtroTurma;
        filtroTurma = this.edt_pesquisaTurma.getText().toString();

        String filtroAtend;
        filtroAtend = this.edt_pesquisaAtend.getText().toString();


        this.adapterListaMatricula.refresh(this.matriculaController.getListaMatriculaFiltroController(filtroTurma, filtroAtend));

        Toast.makeText(this, "Busca realizada", Toast.LENGTH_SHORT).show();
    }

    public void filterList(){
        String filtroTurma;
        filtroTurma = this.edt_pesquisaTurma.getText().toString();

        String filtroAtend;
        filtroAtend = this.edt_pesquisaAtend.getText().toString();


        this.adapterListaMatricula.refresh(this.matriculaController.getListaMatriculaFiltroController(filtroTurma, filtroAtend));

        Toast.makeText(this, "Busca realizada", Toast.LENGTH_SHORT).show();
    }


    //Atualizar tela
    public void eventRefresh(View view){
        this.adapterListaMatricula.refresh(this.matriculaController.getListaMatriculaController());
        Toast.makeText(this, "Atualização Feita com Sucesso.", Toast.LENGTH_SHORT).show();
    }
}