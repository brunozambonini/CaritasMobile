package com.example.caritaspiapplication.activities.turma;

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
import com.example.caritaspiapplication.activities.turma.TurmaActivity;
import com.example.caritaspiapplication.activities.turma.EditarTurmaActivity;
import com.example.caritaspiapplication.activities.turma.ListarTurmaActivity;
import com.example.caritaspiapplication.adapter.AdapterListaTurma;
import com.example.caritaspiapplication.controller.TurmaController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Turma;

import java.util.List;

public class ListarTurmaActivity extends AppCompatActivity {

    private ListView lsvTurma;
    private List<Turma> turmaList;
    private AdapterListaTurma adapterListaTurma;
    private Button btnCadastrarTurma;
    private TurmaController turmaController;

    private EditText edt_pesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_turma);

        this.edt_pesquisa = (EditText) findViewById(R.id.edt_pesquisa);

        this.btnCadastrarTurma = (Button) findViewById(R.id.btn_cadastroTurma);
        this.btnCadastrarTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarTurmaActivity.this, TurmaActivity.class);
                startActivity(intent);
            }
        });

        this.turmaController = new TurmaController(ConexaoSQLite.getInstance(ListarTurmaActivity.this));
        turmaList = turmaController.getListaturmaController();


        this.lsvTurma = (ListView) findViewById(R.id.lsvTurma);
        this.adapterListaTurma = new AdapterListaTurma(ListarTurmaActivity.this, this.turmaList);
        this.lsvTurma.setAdapter(this.adapterListaTurma);

        //Janela de dialogo para optar entre excluir e visualizar um registro
        this.lsvTurma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Turma turmaSelecionado = (Turma) adapterListaTurma.getItem(position);

                AlertDialog.Builder janelaDeEsolha = new AlertDialog.Builder(ListarTurmaActivity.this);

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
                        boolean excluiu = turmaController.excluirturmaController(turmaSelecionado.getCod_turma());

                        dialog.cancel();

                        if (excluiu) {
                            adapterListaTurma.removerturma(position);
                            Toast.makeText(ListarTurmaActivity.this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ListarTurmaActivity.this, "Erro ao excluir registro", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEsolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundleDadosturma = new Bundle();

                        bundleDadosturma.putLong("cod_turma", turmaSelecionado.getCod_turma());
                        bundleDadosturma.putString("nome", turmaSelecionado.getNome());
                        bundleDadosturma.putString("periodo", turmaSelecionado.getPeriodo());
                        bundleDadosturma.putLong("instrutor", turmaSelecionado.getCod_inst());

                        Intent intentEditarturma = new Intent(ListarTurmaActivity.this, EditarTurmaActivity.class);
                        intentEditarturma.putExtras(bundleDadosturma);
                        startActivity(intentEditarturma);
                    }
                });

                janelaDeEsolha.create().show();
            }
        });
    }

    //procurar
    public void eventSearch(View view){
        String filtro = this.edt_pesquisa.getText().toString();

        this.adapterListaTurma.refresh(this.turmaController.getListaTurmaFiltroController(filtro));

        Toast.makeText(this, "Busca realizada", Toast.LENGTH_SHORT).show();
    }

    //Atualizar tela
    public void eventRefresh(View view){
        this.adapterListaTurma.refresh(this.turmaController.getListaturmaController());
        Toast.makeText(this, "Atualização Feita com Sucesso.", Toast.LENGTH_SHORT).show();
    }
}