package com.example.caritaspiapplication.activities.atendido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.activities.matricula.ListarMatriculaActivity;
import com.example.caritaspiapplication.controller.AtendidoController;
import com.example.caritaspiapplication.controller.ResponsavelController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Responsavel;

import org.w3c.dom.Text;

import java.util.List;

public class EditarAtendidoActivity extends AppCompatActivity {

    private EditText edt_id;
    private EditText edt_nome;
    private EditText edt_ra;
    private EditText edt_nis;
    private EditText edt_data_nasci;
    private EditText edt_rua;
    private EditText edt_bairro;
    private EditText edt_numero;

    private TextView txt_num_matriculas;
    private Button btn_verMatriculas;

    //Spiner
    private Spinner spnResponsavel;
    private List<Responsavel> listaResponsavel;
    private ResponsavelController responsavelController;

    private Button btn_salvar;

    private Atendido atendido;

    private long cod_atendido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_atendido);

        //this.edt_id = (EditText) findViewById(R.id.edt_cod_atend);
        this.edt_nome = (EditText)findViewById(R.id.edt_nome);
        this.edt_ra = (EditText)findViewById(R.id.edt_ra);
        this.edt_nis = (EditText)findViewById(R.id.edt_nis);
        this.edt_data_nasci = (EditText)findViewById(R.id.edt_data_nasci);
        this.edt_rua = (EditText)findViewById(R.id.edt_rua);
        this.edt_bairro = (EditText)findViewById(R.id.edt_bairro);
        this.edt_numero = (EditText)findViewById(R.id.edt_numero);

        this.txt_num_matriculas = (TextView) findViewById(R.id.txt_num_matriculas);


        Bundle bundleDadosAtendido = getIntent().getExtras();

        Atendido atendido = new Atendido();

        atendido.setCod_atend(bundleDadosAtendido.getLong("cod_atend"));
        atendido.setNome(bundleDadosAtendido.getString("nome"));
        atendido.setRa(bundleDadosAtendido.getString("ra"));
        atendido.setNis(bundleDadosAtendido.getString("nis"));
        atendido.setData_nasci(bundleDadosAtendido.getString("data_nasci"));
        atendido.setRua(bundleDadosAtendido.getString("rua"));
        atendido.setBairro(bundleDadosAtendido.getString("bairro"));
        atendido.setN_casa(bundleDadosAtendido.getString("n_casa"));
        atendido.setCod_res(bundleDadosAtendido.getLong("responsavel"));

        atendido.setMatriculas(bundleDadosAtendido.getString("matriculas")); // NOVO --------

        //Spinner
        this.responsavelController = new ResponsavelController(ConexaoSQLite.getInstance(this));
        this.listaResponsavel = this.responsavelController.getListaOrdenadaResponsavel(atendido.getCod_res());

        this.spnResponsavel = (Spinner) this.findViewById(R.id.spnResponsavel);
        ArrayAdapter<Responsavel> spnResponsavelAdapter = new ArrayAdapter<Responsavel>(
                this,
                android.R.layout.simple_spinner_item,
                this.listaResponsavel
        );
        this.spnResponsavel.setAdapter(spnResponsavelAdapter);
        //End Spinner

        this.cod_atendido = bundleDadosAtendido.getLong("cod_atend");

        this.setDadosAtendido(atendido);

        this.btn_verMatriculas = (Button) findViewById(R.id.btn_verMatriculas);
        this.verMatriculaListiner(atendido.getNome());

        this.btn_salvar = (Button) findViewById(R.id.btn_atualizarAtendido);
        this.salvarListener();

    }

    private void setDadosAtendido(Atendido atendido){

        //this.edt_id.setText(String.valueOf(atendido.getCod_res()));
        this.edt_nome.setText(atendido.getNome());
        this.edt_ra.setText(atendido.getRa());
        this.edt_nis.setText(atendido.getNis());
        this.edt_data_nasci.setText(atendido.getData_nasci());
        this.edt_rua.setText(atendido.getRua());
        this.edt_bairro.setText(atendido.getBairro());
        this.edt_numero.setText(atendido.getN_casa());

        if (atendido.getMatriculas().equals("0")) {
            this.txt_num_matriculas.setText("Atendido matriculado em nenhuma turma");
        } else{
            this.txt_num_matriculas.setText("Atendido matriculado em " + atendido.getMatriculas() + " turma(s)");
        }
        System.out.println("Matriculas" + atendido.getMatriculas());
    }

    private void salvarListener() {
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Atendido atendidoAAtualizar = getDadosAtendido();

                if (atendidoAAtualizar != null) {
                    AtendidoController atendidoController = new AtendidoController(ConexaoSQLite.getInstance(EditarAtendidoActivity.this));
                    boolean atualizou = atendidoController.atualizaratendidoController(atendidoAAtualizar);

                    if (atualizou) {
                        Toast.makeText(EditarAtendidoActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EditarAtendidoActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarAtendidoActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Atendido getDadosAtendido(){
        this.atendido = new Atendido();
        Responsavel responsavelSelecionado = (Responsavel) this.spnResponsavel.getSelectedItem();

        this.atendido.setCod_atend(this.cod_atendido);

        if(this.edt_nome.getText().toString().isEmpty() == false){
            this.atendido.setNome(this.edt_nome.getText().toString());
        }else {
            return null;
        }
        if (this.edt_ra.getText().toString().isEmpty() == false){
            this.atendido.setRa(this.edt_ra.getText().toString());
        }else {
            return null;
        }
        if (this.edt_nis.getText().toString().isEmpty() == false){
            this.atendido.setNis(this.edt_nis.getText().toString());
        }else {
            return null;
        }
        if (this.edt_data_nasci.getText().toString().isEmpty() == false){
            this.atendido.setData_nasci(this.edt_data_nasci.getText().toString());
        }else {
            return null;
        }
        if (this.edt_rua.getText().toString().isEmpty() == false){
            this.atendido.setRua(this.edt_rua.getText().toString());
        }else{
            return null;
        }
        if (this.edt_bairro.getText().toString().isEmpty() == false){
            this.atendido.setBairro(this.edt_bairro.getText().toString());
        }else{
            return null;
        }
        if (this.edt_numero.getText().toString().isEmpty() == false){
            this.atendido.setN_casa(this.edt_numero.getText().toString());
        }else{
            return null;
        }
        this.atendido.setCod_res(responsavelSelecionado.getCod_res());

        return atendido;
    }

    private void verMatriculaListiner(String nome_atendido) {
        this.btn_verMatriculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleNomeAtendido = new Bundle();

                bundleNomeAtendido.putString("nome_atendido", nome_atendido);

                System.out.println("nome atendido a ser passado" + nome_atendido);

                Intent intentListarMatriculasAtendido = new Intent(EditarAtendidoActivity.this, ListarMatriculaActivity.class);
                intentListarMatriculasAtendido.putExtras(bundleNomeAtendido);
                startActivity(intentListarMatriculasAtendido);
            }
        });
    }

}