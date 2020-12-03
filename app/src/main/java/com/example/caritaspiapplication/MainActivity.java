package com.example.caritaspiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.caritaspiapplication.activities.atendido.AtendidoActivity;
import com.example.caritaspiapplication.activities.atendido.ListarAtendidoActivity;
import com.example.caritaspiapplication.activities.matricula.ListarMatriculaActivity;
import com.example.caritaspiapplication.activities.matricula.MatriculaActivity;
import com.example.caritaspiapplication.activities.instrutor.InstrutorActivity;
import com.example.caritaspiapplication.activities.instrutor.ListarInstrutorActivity;
import com.example.caritaspiapplication.activities.turma.ListarTurmaActivity;
import com.example.caritaspiapplication.activities.turma.TurmaActivity;
import com.example.caritaspiapplication.activities.usuario.LoginActivity;
import com.example.caritaspiapplication.activities.usuario.RegistrarActivity;
import com.example.caritaspiapplication.activities.usuario.UsuarioAttActivity;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.activities.responsavel.ListarResponsavelActivity;
import com.example.caritaspiapplication.activities.responsavel.ResponsavelActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnListarResponsavel;
    private Button btnListarAtendido;
    private Button btnListarInstrutor;
    private Button btnListarTurma;
    private Button btnListarMatricula;

    private Button btnSair;
    private Button btnAtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstance(this);

        this.btnListarResponsavel = (Button) findViewById(R.id.btn_listarResponsavel);
        this.btnListarAtendido = (Button) findViewById(R.id.btn_listarAtendido);
        this.btnListarInstrutor = (Button) findViewById(R.id.btn_listarInstrutor);
        this.btnListarTurma = (Button) findViewById(R.id.btn_listarTurma);
        this.btnListarMatricula = (Button) findViewById(R.id.btn_listarMatricula);

        this.btnAtt = (Button) findViewById(R.id.btn_att);
        this.btnSair = (Button) findViewById(R.id.btn_sair);

        this.btnListarResponsavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarResponsavelActivity.class);
                startActivity(intent);
            }
        });

        this.btnListarAtendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarAtendidoActivity.class);
                startActivity(intent);
            }
        });


        this.btnListarInstrutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarInstrutorActivity.class);
                startActivity(intent);
            }
        });


        this.btnListarTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarTurmaActivity.class);
                startActivity(intent);
            }
        });

        this.btnListarMatricula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarMatriculaActivity.class);
                startActivity(intent);
            }
        });

        this.btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        this.btnAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UsuarioAttActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
}