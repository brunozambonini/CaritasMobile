package com.example.caritaspiapplication.activities.atendido;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.activities.responsavel.ResponsavelActivity;
import com.example.caritaspiapplication.controller.AtendidoController;
import com.example.caritaspiapplication.controller.ResponsavelController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.CEP;
import com.example.caritaspiapplication.model.Responsavel;
import com.example.caritaspiapplication.service.HTTPService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AtendidoActivity extends AppCompatActivity {

    private Spinner spnResponsavel;
    private List<Responsavel> listaResponsavel;
    private ResponsavelController responsavelController;

    private EditText edt_nome;
    private EditText edt_ra;
    private EditText edt_nis;
    private EditText edt_data_nasci;
    private EditText edt_rua;
    private EditText edt_bairro;
    private EditText edt_numero;

    private Button btn_salvar;

    private Atendido atendido;

    private Button btn_buscaCep;
    private EditText edt_cep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atendido);

        //buscar CEP
        edt_cep = (EditText) findViewById(R.id.edt_cep);
        btn_buscaCep = (Button) findViewById(R.id.btn_buscarCep);
        this.buscaCep();

        //Spinner
        this.responsavelController = new ResponsavelController(ConexaoSQLite.getInstance(this));
        this.listaResponsavel = this.responsavelController.getListaResponsavelController();

        this.spnResponsavel = (Spinner) this.findViewById(R.id.spnResponsavel);
        ArrayAdapter<Responsavel> spnResponsavelAdapter = new ArrayAdapter<Responsavel>(
                this,
                android.R.layout.simple_spinner_item,
                this.listaResponsavel
        );

        this.spnResponsavel.setAdapter(spnResponsavelAdapter);
        //End Spinner

        edt_nome = (EditText)findViewById(R.id.edt_nome);
        edt_ra = (EditText)findViewById(R.id.edt_ra);
        edt_nis = (EditText)findViewById(R.id.edt_nis);
        edt_data_nasci = (EditText)findViewById(R.id.edt_data_nasci);
        edt_rua = (EditText)findViewById(R.id.edt_rua);
        edt_bairro = (EditText)findViewById(R.id.edt_bairro);
        edt_numero = (EditText)findViewById(R.id.edt_numero);

        btn_salvar = (Button) findViewById(R.id.btn_salvarAtendido);

        this.salvarListener();
    }

    private void salvarListener(){
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Atendido atendidoACadastrar = getDadosAtendido();

                if(atendidoACadastrar != null){
                    AtendidoController atendidoController = new AtendidoController(ConexaoSQLite.getInstance(AtendidoActivity.this));
                    long idAtendido = atendidoController.salvaratendidoController(atendidoACadastrar);

                    if(idAtendido > 0){
                        Toast.makeText(AtendidoActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(AtendidoActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(AtendidoActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Atendido getDadosAtendido(){

        Responsavel responsavelSelecionado = (Responsavel) this.spnResponsavel.getSelectedItem();

        this.atendido = new Atendido();

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

        this.atendido.setMatriculas("0");

        this.atendido.setCod_res(responsavelSelecionado.getCod_res());

        return atendido;
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
                    Toast.makeText(AtendidoActivity.this, "Verifique se o CEP digitado é valido", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setDadosCEP(CEP cep){

        this.edt_rua.setText(cep.getLogradouro());
        this.edt_bairro.setText(cep.getBairro());

    }

}