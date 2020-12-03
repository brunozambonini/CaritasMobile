package com.example.caritaspiapplication.activities.responsavel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.controller.ResponsavelController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.CEP;
import com.example.caritaspiapplication.model.Responsavel;
import com.example.caritaspiapplication.service.HTTPService;

import java.util.concurrent.ExecutionException;

public class ResponsavelActivity extends AppCompatActivity {

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

    private Button btn_buscaCep;
    private EditText edt_cep;

    private Responsavel responsavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsavel);

        edt_nome = (EditText)findViewById(R.id.edt_nome);
        edt_rg = (EditText)findViewById(R.id.edt_rg);
        edt_nis = (EditText)findViewById(R.id.edt_nis);
        edt_cpf = (EditText)findViewById(R.id.edt_cpf);
        edt_data_nasci = (EditText)findViewById(R.id.edt_data_nasci);
        edt_rua = (EditText)findViewById(R.id.edt_rua);
        edt_bairro = (EditText)findViewById(R.id.edt_bairro);
        edt_numero = (EditText)findViewById(R.id.edt_numero);
        edt_telefone = (EditText)findViewById(R.id.edt_telefone);

        btn_salvar = (Button) findViewById(R.id.btn_salvar);

        edt_cep = (EditText) findViewById(R.id.edt_cep);
        btn_buscaCep = (Button) findViewById(R.id.btn_buscarCep);
        this.buscaCep();

        this.salvarListener();
    }

    private void salvarListener(){
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Responsavel responsavelACadastrar = getDadosResponsavel();

                if(responsavelACadastrar != null){
                    ResponsavelController responsavelController = new ResponsavelController(ConexaoSQLite.getInstance(ResponsavelActivity.this));
                    long idResponsavel = responsavelController.salvarResponsavelController(responsavelACadastrar);

                    if(idResponsavel > 0){
                        Toast.makeText(ResponsavelActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ResponsavelActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(ResponsavelActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Responsavel getDadosResponsavel(){
        this.responsavel = new Responsavel();

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

    private void buscaCep(){
        this.btn_buscaCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_cep.getText().toString().length() > 0 && !edt_cep.getText().toString().equals("") && edt_cep.getText().toString().length() == 8){

                    HTTPService service = new HTTPService(edt_cep.getText().toString());
                    System.out.println(edt_cep.getText().toString());
                    try {
                        CEP cep = service.execute().get();
                        edt_rua.setText(cep.toString());
                        setDadosCEP(cep);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(ResponsavelActivity.this, "Verifique se o CEP digitado é valido", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setDadosCEP(CEP cep){

        this.edt_rua.setText(cep.getLogradouro());
        this.edt_bairro.setText(cep.getBairro());
        this.edt_telefone.setText("(" + cep.getDdd() + ")");

    }
}