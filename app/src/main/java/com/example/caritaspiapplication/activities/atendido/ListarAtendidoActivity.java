package com.example.caritaspiapplication.activities.atendido;

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
import com.example.caritaspiapplication.adapter.AdapterListaAtendido;
import com.example.caritaspiapplication.adapter.AdapterListaResponsavel;
import com.example.caritaspiapplication.controller.AtendidoController;
import com.example.caritaspiapplication.controller.ResponsavelController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Responsavel;

import java.util.List;


public class ListarAtendidoActivity extends AppCompatActivity {

    private ListView lsvAtendido;
    private List<Atendido> atendidoList;
    private AdapterListaAtendido adapterListaAtendido;
    private Button btnCadastrarAtendido;
    private AtendidoController atendidoController;

    private EditText edt_pesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_atendido);

        this.edt_pesquisa = (EditText) findViewById(R.id.edt_pesquisa);

        this.btnCadastrarAtendido = (Button) findViewById(R.id.btn_cadastroAtendido);
        this.btnCadastrarAtendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarAtendidoActivity.this, AtendidoActivity.class);
                startActivity(intent);
            }
        });

        this.atendidoController = new AtendidoController(ConexaoSQLite.getInstance(ListarAtendidoActivity.this));
        atendidoList = atendidoController.getListaatendidoController();

        this.lsvAtendido = (ListView) findViewById(R.id.lsvAtendido);
        this.adapterListaAtendido = new AdapterListaAtendido(ListarAtendidoActivity.this, this.atendidoList);
        this.lsvAtendido.setAdapter(this.adapterListaAtendido);

        //Janela de dialogo para optar entre excluir e visualizar um registro
        this.lsvAtendido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Atendido atendidoSelecionado = (Atendido) adapterListaAtendido.getItem(position);

                AlertDialog.Builder janelaDeEsolha = new AlertDialog.Builder(ListarAtendidoActivity.this);

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
                        boolean excluiu = atendidoController.excluiratendidoController(atendidoSelecionado.getCod_atend());

                        dialog.cancel();

                        if (excluiu) {
                            adapterListaAtendido.removeratendido(position);
                            Toast.makeText(ListarAtendidoActivity.this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ListarAtendidoActivity.this, "Erro ao excluir registro", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                janelaDeEsolha.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundleDadosatendido = new Bundle();

                        bundleDadosatendido.putLong("cod_atend", atendidoSelecionado.getCod_atend());
                        bundleDadosatendido.putString("nome", atendidoSelecionado.getNome());
                        bundleDadosatendido.putString("ra", atendidoSelecionado.getRa());
                        bundleDadosatendido.putString("nis", atendidoSelecionado.getNis());
                        bundleDadosatendido.putString("data_nasci", atendidoSelecionado.getData_nasci());
                        bundleDadosatendido.putString("rua", atendidoSelecionado.getRua());
                        bundleDadosatendido.putString("bairro", atendidoSelecionado.getBairro());
                        bundleDadosatendido.putString("n_casa", atendidoSelecionado.getN_casa());
                        bundleDadosatendido.putLong("responsavel", atendidoSelecionado.getCod_res());
                        bundleDadosatendido.putString("matriculas", atendidoSelecionado.getMatriculas()); // NOVO --------


                        Intent intentEditaratendido = new Intent(ListarAtendidoActivity.this, EditarAtendidoActivity.class);
                        intentEditaratendido.putExtras(bundleDadosatendido);
                        startActivity(intentEditaratendido);
                    }
                });

                janelaDeEsolha.create().show();
            }
        });
}

    //procurar
    public void eventSearch(View view){
        String filtro = this.edt_pesquisa.getText().toString();

        this.adapterListaAtendido.refresh(this.atendidoController.getListaatendidoFiltroController(filtro));

        Toast.makeText(this, "Busca realizada", Toast.LENGTH_SHORT).show();
    }

    //Atualizar tela
    public void eventRefresh(View view){
        this.adapterListaAtendido.refresh(this.atendidoController.getListaatendidoController());
        Toast.makeText(this, "Atualização Feita com Sucesso.", Toast.LENGTH_SHORT).show();
    }
}