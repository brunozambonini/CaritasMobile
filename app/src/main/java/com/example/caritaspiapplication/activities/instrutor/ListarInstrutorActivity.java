package com.example.caritaspiapplication.activities.instrutor;

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
import com.example.caritaspiapplication.activities.instrutor.EditarInstrutorActivity;
import com.example.caritaspiapplication.activities.instrutor.ListarInstrutorActivity;
import com.example.caritaspiapplication.activities.instrutor.InstrutorActivity;
import com.example.caritaspiapplication.adapter.AdapterListaInstrutor;
import com.example.caritaspiapplication.controller.InstrutorController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Instrutor;

import java.util.List;

public class ListarInstrutorActivity extends AppCompatActivity {

    private ListView lsvInstrutor;
    private List<Instrutor> instrutorList;
    private AdapterListaInstrutor adapterListaInstrutor;
    private Button btnCadastrarInstrutor;
    private InstrutorController instrutorController;
    private EditText edt_pesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_instrutor);

        this.edt_pesquisa = (EditText) findViewById(R.id.edt_pesquisa);

        this.btnCadastrarInstrutor = (Button) findViewById(R.id.btn_cadastroInstrutor);
        this.btnCadastrarInstrutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarInstrutorActivity.this, InstrutorActivity.class);
                startActivity(intent);
            }
        });

        this.instrutorController = new InstrutorController(ConexaoSQLite.getInstance(ListarInstrutorActivity.this));
        instrutorList = instrutorController.getListaInstrutorController();


        this.lsvInstrutor = (ListView) findViewById(R.id.lsvInstrutor);
        this.adapterListaInstrutor = new AdapterListaInstrutor(ListarInstrutorActivity.this, this.instrutorList);
        this.lsvInstrutor.setAdapter(this.adapterListaInstrutor);

        //Janela de dialogo para optar entre excluir e visualizar um registro
        this.lsvInstrutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Instrutor instrutorSelecionado = (Instrutor) adapterListaInstrutor.getItem(position);

                AlertDialog.Builder janelaDeEsolha = new AlertDialog.Builder(ListarInstrutorActivity.this);

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
                        boolean excluiu = instrutorController.excluirInstrutorController(instrutorSelecionado.getCod_inst());

                        dialog.cancel();

                        if (excluiu) {
                            adapterListaInstrutor.removerInstrutor(position);
                            Toast.makeText(ListarInstrutorActivity.this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ListarInstrutorActivity.this, "Erro ao excluir registro", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEsolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundleDadosInstrutor = new Bundle();

                        bundleDadosInstrutor.putLong("cod_inst", instrutorSelecionado.getCod_inst());
                        bundleDadosInstrutor.putString("nome", instrutorSelecionado.getNome());
                        bundleDadosInstrutor.putString("rg", instrutorSelecionado.getRg());

                        Intent intentEditarInstrutor = new Intent(ListarInstrutorActivity.this, EditarInstrutorActivity.class);
                        intentEditarInstrutor.putExtras(bundleDadosInstrutor);
                        startActivity(intentEditarInstrutor);
                    }
                });

                janelaDeEsolha.create().show();
            }
        });

    }

    //procurar
    public void eventSearch(View view){
        String filtro = this.edt_pesquisa.getText().toString();

        this.adapterListaInstrutor.refresh(this.instrutorController.getListaInstrutorFiltroController(filtro));

        Toast.makeText(this, "Busca realizada", Toast.LENGTH_SHORT).show();
    }

    //Atualizar tela
    public void eventRefresh(View view){
        this.adapterListaInstrutor.refresh(this.instrutorController.getListaInstrutorController());
        Toast.makeText(this, "Atualização Feita com Sucesso.", Toast.LENGTH_SHORT).show();
    }
}