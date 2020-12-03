package com.example.caritaspiapplication.activities.instrutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.controller.InstrutorController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Instrutor;

public class EditarInstrutorActivity extends AppCompatActivity {

    private EditText edt_id;
    private EditText edt_nome;
    private EditText edt_rg;

    private Button btn_salvar;

    private Instrutor instrutor;

    private long cod_instrutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_instrutor);

        this.edt_nome = (EditText)findViewById(R.id.edt_nome);
        this.edt_rg = (EditText)findViewById(R.id.edt_rg);

        Bundle bundleDadosinstrutor = getIntent().getExtras();

        Instrutor instrutor = new Instrutor();

        instrutor.setCod_inst(bundleDadosinstrutor.getLong("cod_inst"));
        instrutor.setNome(bundleDadosinstrutor.getString("nome"));
        instrutor.setRg(bundleDadosinstrutor.getString("rg"));

        this.cod_instrutor = bundleDadosinstrutor.getLong("cod_inst");

        this.setDadosInstrutor(instrutor);

        this.btn_salvar = (Button) findViewById(R.id.btn_atualizarInstrutor);
        this.salvarListener();

    }
    private void setDadosInstrutor(Instrutor instrutor){

        this.edt_nome.setText(instrutor.getNome());
        this.edt_rg.setText(instrutor.getRg());

    }

    private void salvarListener() {
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instrutor instrutorAAtualizar = getDadosInstrutor();

                if (instrutorAAtualizar != null) {
                    InstrutorController instrutorController = new InstrutorController(ConexaoSQLite.getInstance(EditarInstrutorActivity.this));
                    boolean atualizou = instrutorController.atualizarInstrutorController(instrutorAAtualizar);

                    if (atualizou) {
                        Toast.makeText(EditarInstrutorActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EditarInstrutorActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarInstrutorActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Instrutor getDadosInstrutor(){
        this.instrutor = new Instrutor();

        this.instrutor.setCod_inst(this.cod_instrutor);

        if(this.edt_nome.getText().toString().isEmpty() == false){
            this.instrutor.setNome(this.edt_nome.getText().toString());
        }else {
            return null;
        }
        if (this.edt_rg.getText().toString().isEmpty() == false){
            this.instrutor.setRg(this.edt_rg.getText().toString());
        }else {
            return null;
        }

        return instrutor;
    }
}