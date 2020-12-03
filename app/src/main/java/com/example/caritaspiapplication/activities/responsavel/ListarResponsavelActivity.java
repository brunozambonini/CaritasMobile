package com.example.caritaspiapplication.activities.responsavel;

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
import com.example.caritaspiapplication.adapter.AdapterListaResponsavel;
import com.example.caritaspiapplication.controller.ResponsavelController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Responsavel;

import java.util.List;

public class ListarResponsavelActivity extends AppCompatActivity {

    private ListView lsvResponsavel;
    private List<Responsavel> responsavelList;
    private AdapterListaResponsavel adapterListaResponsavel;
    private Button btnCadastrarResponsavel;
    private ResponsavelController responsavelController;

    private EditText edt_pesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_responsavel);

        this.edt_pesquisa = (EditText) findViewById(R.id.edt_pesquisa);

        this.btnCadastrarResponsavel = (Button) findViewById(R.id.btn_cadastroResponsavel);
        this.btnCadastrarResponsavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarResponsavelActivity.this, ResponsavelActivity.class);
                startActivity(intent);
            }
        });

        this.responsavelController = new ResponsavelController(ConexaoSQLite.getInstance(ListarResponsavelActivity.this));
        responsavelList = responsavelController.getListaResponsavelController();


        this.lsvResponsavel = (ListView) findViewById(R.id.lsvResponsavel);
        this.adapterListaResponsavel = new AdapterListaResponsavel(ListarResponsavelActivity.this, this.responsavelList);
        this.lsvResponsavel.setAdapter(this.adapterListaResponsavel);

        //Janela de dialogo para optar entre excluir e visualizar um registro
        this.lsvResponsavel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Responsavel responsavelSelecionado = (Responsavel) adapterListaResponsavel.getItem(position);

                AlertDialog.Builder janelaDeEsolha = new AlertDialog.Builder(ListarResponsavelActivity.this);

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
                        boolean excluiu = responsavelController.excluirResponsavelController(responsavelSelecionado.getCod_res());

                        dialog.cancel();

                        if (excluiu) {
                            adapterListaResponsavel.removerResponsavel(position);
                            Toast.makeText(ListarResponsavelActivity.this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ListarResponsavelActivity.this, "Erro ao excluir registro", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEsolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundleDadosResponsavel = new Bundle();

                        bundleDadosResponsavel.putLong("cod_res", responsavelSelecionado.getCod_res());
                        bundleDadosResponsavel.putString("nome", responsavelSelecionado.getNome());
                        bundleDadosResponsavel.putString("rg", responsavelSelecionado.getRg());
                        bundleDadosResponsavel.putString("nis", responsavelSelecionado.getNis());
                        bundleDadosResponsavel.putString("cpf", responsavelSelecionado.getCpf());
                        bundleDadosResponsavel.putString("data_nasci", responsavelSelecionado.getData_nasci());
                        bundleDadosResponsavel.putString("rua", responsavelSelecionado.getRua());
                        bundleDadosResponsavel.putString("bairro", responsavelSelecionado.getBairro());
                        bundleDadosResponsavel.putString("n_casa", responsavelSelecionado.getN_casa());
                        bundleDadosResponsavel.putString("telefone", responsavelSelecionado.getTelefone());

                        Intent intentEditarResponsavel = new Intent(ListarResponsavelActivity.this, EditarResponsavelActivity.class);
                        intentEditarResponsavel.putExtras(bundleDadosResponsavel);
                        startActivity(intentEditarResponsavel);
                    }
                });

                janelaDeEsolha.create().show();
            }
        });

    }

    //procurar
    public void eventSearch(View view){
        String filtro = this.edt_pesquisa.getText().toString();

        this.adapterListaResponsavel.refresh(this.responsavelController.getListaResponsavelFiltroController(filtro));

        Toast.makeText(this, "Busca realizada", Toast.LENGTH_SHORT).show();
    }

    //Atualizar tela
    public void eventRefresh(View view){
        this.adapterListaResponsavel.refresh(this.responsavelController.getListaResponsavelController());
        Toast.makeText(this, "Atualização Feita com Sucesso.", Toast.LENGTH_SHORT).show();
    }
}