package com.example.caritaspiapplication.activities.responsavel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.controller.ResponsavelController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Responsavel;

public class EditarResponsavelActivity extends AppCompatActivity {

    private EditText edt_id;
    private EditText edt_nome;
    private EditText edt_rg;
    private EditText edt_nis;
    private EditText edt_cpf;
    private EditText edt_data_nasci;
    private EditText edt_rua;
    private EditText edt_bairro;
    private EditText edt_numero;
    private EditText edt_telefone;

    private Button btn_salvar;

    private Responsavel responsavel;

    private long cod_responsavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_responsavel);

        //this.edt_id = (EditText) findViewById(R.id.edt_cod_res);
        this.edt_nome = (EditText)findViewById(R.id.edt_nome);
        this.edt_rg = (EditText)findViewById(R.id.edt_rg);
        this.edt_nis = (EditText)findViewById(R.id.edt_nis);
        this.edt_cpf = (EditText)findViewById(R.id.edt_cpf);
        this.edt_data_nasci = (EditText)findViewById(R.id.edt_data_nasci);
        this.edt_rua = (EditText)findViewById(R.id.edt_rua);
        this.edt_bairro = (EditText)findViewById(R.id.edt_bairro);
        this.edt_numero = (EditText)findViewById(R.id.edt_numero);
        this.edt_telefone = (EditText)findViewById(R.id.edt_telefone);

        Bundle bundleDadosResponsavel = getIntent().getExtras();

        Responsavel responsavel = new Responsavel();

        responsavel.setCod_res(bundleDadosResponsavel.getLong("cod_res"));
        responsavel.setNome(bundleDadosResponsavel.getString("nome"));
        responsavel.setRg(bundleDadosResponsavel.getString("rg"));
        responsavel.setNis(bundleDadosResponsavel.getString("nis"));
        responsavel.setCpf(bundleDadosResponsavel.getString("cpf"));
        responsavel.setData_nasci(bundleDadosResponsavel.getString("data_nasci"));
        responsavel.setRua(bundleDadosResponsavel.getString("rua"));
        responsavel.setBairro(bundleDadosResponsavel.getString("bairro"));
        responsavel.setN_casa(bundleDadosResponsavel.getString("n_casa"));
        responsavel.setTelefone(bundleDadosResponsavel.getString("telefone"));

        this.cod_responsavel = bundleDadosResponsavel.getLong("cod_res");

        this.setDadosResponsavel(responsavel);

        this.btn_salvar = (Button) findViewById(R.id.btn_salvar);
        this.salvarListener();

    }
    private void setDadosResponsavel(Responsavel responsavel){

        //this.edt_id.setText(String.valueOf(responsavel.getCod_res()));
        this.edt_nome.setText(responsavel.getNome());
        this.edt_rg.setText(responsavel.getRg());
        this.edt_nis.setText(responsavel.getNis());
        this.edt_cpf.setText(responsavel.getCpf());
        this.edt_data_nasci.setText(responsavel.getData_nasci());
        this.edt_rua.setText(responsavel.getRua());
        this.edt_bairro.setText(responsavel.getBairro());
        this.edt_numero.setText(responsavel.getN_casa());
        this.edt_telefone.setText(responsavel.getTelefone());

    }

    private void salvarListener() {
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Responsavel responsavelAAtualizar = getDadosResponsavel();

                if (responsavelAAtualizar != null) {
                    ResponsavelController responsavelController = new ResponsavelController(ConexaoSQLite.getInstance(EditarResponsavelActivity.this));
                    boolean atualizou = responsavelController.atualizarResponsavelController(responsavelAAtualizar);

                    if (atualizou) {
                        Toast.makeText(EditarResponsavelActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EditarResponsavelActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarResponsavelActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Responsavel getDadosResponsavel(){
        this.responsavel = new Responsavel();

        this.responsavel.setCod_res(this.cod_responsavel);

        if(this.edt_nome.getText().toString().isEmpty() == false){
            this.responsavel.setNome(this.edt_nome.getText().toString());
        }else {
            return null;
        }
        if (this.edt_rg.getText().toString().isEmpty() == false){
            this.responsavel.setRg(this.edt_rg.getText().toString());
        }else {
            return null;
        }
        if (this.edt_nis.getText().toString().isEmpty() == false){
            this.responsavel.setNis(this.edt_nis.getText().toString());
        }else {
            return null;
        }
        if (this.edt_cpf.getText().toString().isEmpty() == false){
            this.responsavel.setCpf(this.edt_cpf.getText().toString());
        }else {
            return null;
        }
        if (this.edt_data_nasci.getText().toString().isEmpty() == false){
            this.responsavel.setData_nasci(this.edt_data_nasci.getText().toString());
        }else {
            return null;
        }
        if (this.edt_rua.getText().toString().isEmpty() == false){
            this.responsavel.setRua(this.edt_rua.getText().toString());
        }else{
            return null;
        }
        if (this.edt_bairro.getText().toString().isEmpty() == false){
            this.responsavel.setBairro(this.edt_bairro.getText().toString());
        }else{
            return null;
        }
        if (this.edt_numero.getText().toString().isEmpty() == false){
            this.responsavel.setN_casa(this.edt_numero.getText().toString());
        }else{
            return null;
        }
        if (this.edt_telefone.getText().toString().isEmpty() == false) {
            this.responsavel.setTelefone(this.edt_telefone.getText().toString());
        }else {
            return null;
        }

        return responsavel;
    }
}