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

public class UsuarioAttActivity extends AppCompatActivity {

    private EditText edt_usuario;
    private EditText edt_senhaAtual;
    private EditText edt_novaSenha;
    private EditText edt_confirmaSenha;

    private Button btn_alterar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_att);

        edt_usuario = (EditText)findViewById(R.id.edt_usuario);
        edt_senhaAtual = (EditText)findViewById(R.id.edt_senha);
        edt_novaSenha = (EditText)findViewById(R.id.edt_novaSenha);
        edt_confirmaSenha = (EditText)findViewById(R.id.edt_confirmaSenha);

        btn_alterar = (Button) findViewById(R.id.btn_alterar);

        this.alterarListener();
    }

    private void alterarListener(){
        this.btn_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String novaSenha;

                Usuario usuarioAAtualizar = getDadosUsuario();
                if (edt_novaSenha.getText().toString().equals(edt_confirmaSenha.getText().toString())) {
                    novaSenha = edt_novaSenha.getText().toString();
                    if (usuarioAAtualizar != null) {
                        UsuarioController usuarioController = new UsuarioController(ConexaoSQLite.getInstance(UsuarioAttActivity.this));
                        String resultado = usuarioController.atualizarUsuarioController(usuarioAAtualizar, novaSenha);

                        if (resultado == "Atualizou") {
                            Toast.makeText(UsuarioAttActivity.this, "Senha alterada com sucesso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UsuarioAttActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else if(resultado == "ErroUsuario") {
                            Toast.makeText(UsuarioAttActivity.this, "Ops! Usuário não existe.", Toast.LENGTH_LONG).show();
                        } else if(resultado == "ErroSenha") {
                            Toast.makeText(UsuarioAttActivity.this, "Ops! Senha atual incorreta", Toast.LENGTH_LONG).show();
                        }
                        else if(resultado == "ERRO") {
                            Toast.makeText(UsuarioAttActivity.this, "Ops! Algo deu errado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(UsuarioAttActivity.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(UsuarioAttActivity.this, "As senhas não são iguais.", Toast.LENGTH_LONG).show();
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
        if (this.edt_senhaAtual.getText().toString().isEmpty() == false){
            this.usuario.setSenha(this.edt_senhaAtual.getText().toString());
        }else {
            return null;
        }
        if (this.edt_novaSenha.getText().toString().isEmpty() || this.edt_novaSenha.getText().toString().equals("")){
            return null;
        }
        return usuario;
    }
}