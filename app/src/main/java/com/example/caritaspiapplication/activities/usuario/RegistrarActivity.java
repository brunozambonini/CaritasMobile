package com.example.caritaspiapplication.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caritaspiapplication.MainActivity;
import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.controller.UsuarioController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Usuario;

public class RegistrarActivity extends AppCompatActivity {

    private EditText edt_usuario;
    private EditText edt_senha;
    private EditText edt_confirmaSenha;
    private EditText edt_senhaAdm;

    private Button btn_login;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        edt_usuario = (EditText)findViewById(R.id.edt_usuario);
        edt_senha = (EditText)findViewById(R.id.edt_senha);
        edt_confirmaSenha = (EditText)findViewById(R.id.edt_confirmaSenha);
        edt_senhaAdm = (EditText)findViewById(R.id.edt_senhaAdm);

        btn_login = (Button) findViewById(R.id.btn_alterar);

        this.salvarListener();
    }

    private void salvarListener(){
        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String senhaAdmin;

                Usuario usuarioACadastrar = getDadosUsuario();
                if (edt_senha.getText().toString().equals(edt_confirmaSenha.getText().toString())) {
                    senhaAdmin = edt_senhaAdm.getText().toString();
                    if (usuarioACadastrar != null) {
                        UsuarioController usuarioController = new UsuarioController(ConexaoSQLite.getInstance(RegistrarActivity.this));
                        String resultado = usuarioController.salvarUsuarioController(usuarioACadastrar, senhaAdmin);

                        if (resultado == "Sucesso") {
                            Toast.makeText(RegistrarActivity.this, "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else if(resultado == "ErroAdmin") {
                            Toast.makeText(RegistrarActivity.this, "Ops! Senha do admistrador está errado.", Toast.LENGTH_LONG).show();
                        } else if(resultado == "Existente") {
                            Toast.makeText(RegistrarActivity.this, "Ops! Usuário já cadastrado", Toast.LENGTH_LONG).show();
                        }
                        else if(resultado == "ERRO") {
                            Toast.makeText(RegistrarActivity.this, "Ops! Algo deu errado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RegistrarActivity.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(RegistrarActivity.this, "As senhas não são iguais.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Usuario getDadosUsuario(){

        this.usuario = new Usuario();

        if(this.edt_usuario.getText().toString().isEmpty() == false){
            this.usuario.setUser(this.edt_usuario.getText().toString().toLowerCase());
        }else {
            return null;
        }
        if (this.edt_senha.getText().toString().isEmpty() == false){
            this.usuario.setSenha(this.edt_senha.getText().toString());
        }else {
            return null;
        }
        if (this.edt_senhaAdm.getText().toString().isEmpty() || this.edt_senhaAdm.getText().toString().equals("")){
            return null;
        }
        return usuario;
    }

}