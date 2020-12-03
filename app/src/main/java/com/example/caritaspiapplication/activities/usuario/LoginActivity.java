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

public class LoginActivity extends AppCompatActivity {

    private EditText edt_usuario;
    private EditText edt_senha;

    private Button btn_login;
    private Button btn_registrar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_usuario = (EditText)findViewById(R.id.edt_usuario);
        edt_senha = (EditText)findViewById(R.id.edt_senha);

        btn_login = (Button) findViewById(R.id.btn_alterar);
        btn_registrar = (Button) findViewById(R.id.btn_registrar);

        this.salvarListener();

        this.btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent);
            }
        });
    }

    private void salvarListener(){
        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuarioALogar = getDadosUsuario();
                    if (usuarioALogar != null) {
                        UsuarioController usuarioController = new UsuarioController(ConexaoSQLite.getInstance(LoginActivity.this));
                        boolean resultado = usuarioController.loginController(usuarioALogar);

                        if (resultado) {
                            Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(resultado == false) {
                            Toast.makeText(LoginActivity.this, "Ops! Senha ou usuário estão incorretos", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
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

        return usuario;
    }


}