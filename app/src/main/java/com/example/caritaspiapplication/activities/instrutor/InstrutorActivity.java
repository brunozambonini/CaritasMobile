package com.example.caritaspiapplication.activities.instrutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.activities.instrutor.InstrutorActivity;
import com.example.caritaspiapplication.controller.InstrutorController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Instrutor;

public class InstrutorActivity extends AppCompatActivity {

    private EditText edt_nome;
    private EditText edt_rg;

    private Button btn_salvar;

    private Instrutor instrutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrutor);
        edt_nome = (EditText)findViewById(R.id.edt_nome);
        edt_rg = (EditText)findViewById(R.id.edt_rg);

        btn_salvar = (Button) findViewById(R.id.btn_salvarInstrutor);

        this.salvarListener();
    }

    private void salvarListener(){
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instrutor instrutorACadastrar = getDadosInstrutor();

                if(instrutorACadastrar != null){
                    InstrutorController instrutorController = new InstrutorController(ConexaoSQLite.getInstance(InstrutorActivity.this));
                    long idInstrutor = instrutorController.salvarInstrutorController(instrutorACadastrar);

                    if(idInstrutor > 0){
                        Toast.makeText(InstrutorActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(InstrutorActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(InstrutorActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Instrutor getDadosInstrutor(){
        this.instrutor = new Instrutor();

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