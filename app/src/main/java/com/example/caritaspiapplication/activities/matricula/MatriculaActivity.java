package com.example.caritaspiapplication.activities.matricula;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.activities.atendido.AtendidoActivity;
import com.example.caritaspiapplication.controller.AtendidoController;
import com.example.caritaspiapplication.controller.MatriculaController;
import com.example.caritaspiapplication.controller.TurmaController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Matricula;
import com.example.caritaspiapplication.model.Turma;

import java.util.List;

public class MatriculaActivity extends AppCompatActivity {

    //Spinner Atendido
    private Spinner spnAtendido;
    private List<Atendido> listaAtendido;
    private AtendidoController atendidoController;

    //Spinner Atendido
    private Spinner spnTurma;
    private List<Turma> listaTurma;
    private TurmaController turmaController;

    private Button btn_salvar;

    private Matricula matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);

    //Spinner atendido
        this.atendidoController = new AtendidoController(ConexaoSQLite.getInstance(this));
        this.listaAtendido = this.atendidoController.getListaatendidoController();

        this.spnAtendido = (Spinner) this.findViewById(R.id.spnAtendido);
    ArrayAdapter<Atendido> spnAtendidoAdapter = new ArrayAdapter<Atendido>(
            this,
            android.R.layout.simple_spinner_item,
            this.listaAtendido
    );

        this.spnAtendido.setAdapter(spnAtendidoAdapter);
    //End Spinner

    //Spinner turma
        this.turmaController = new TurmaController(ConexaoSQLite.getInstance(this));
        this.listaTurma = this.turmaController.getListaturmaController();

        this.spnTurma = (Spinner) this.findViewById(R.id.spnTurma);
    ArrayAdapter<Turma> spnTurmaAdapter = new ArrayAdapter<Turma>(
            this,
            android.R.layout.simple_spinner_item,
            this.listaTurma
    );

        this.spnTurma.setAdapter(spnTurmaAdapter);
    //End Spinner

    btn_salvar = (Button) findViewById(R.id.btn_salvarMatricula);

    this.salvarListener();
}

    private void salvarListener(){
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matricula matriculaACadastrar = getDadosMatricula();

                if(matriculaACadastrar != null){
                    MatriculaController matriculaController = new MatriculaController(ConexaoSQLite.getInstance(MatriculaActivity.this));
                    long idAtendidoTurma = matriculaController.salvarMatriculaController(matriculaACadastrar);

                    if(idAtendidoTurma == -1) {
                        Toast.makeText(MatriculaActivity.this, "Atendido já matriculado!", Toast.LENGTH_LONG).show();
                    }
                    else if (idAtendidoTurma > 0){
                        Toast.makeText(MatriculaActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(MatriculaActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(MatriculaActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Matricula getDadosMatricula(){

        Atendido atendidoSelecionado = (Atendido) this.spnAtendido.getSelectedItem();
        Turma turmaSelecionado = (Turma) this.spnTurma.getSelectedItem();

        this.matricula = new Matricula();

        this.matricula.setNome_turma(turmaSelecionado.getNome());
        this.matricula.setNome_atend(atendidoSelecionado.getNome());

        this.matricula.setCod_turma(turmaSelecionado.getCod_turma());
        this.matricula.setCod_atend(atendidoSelecionado.getCod_atend());

        return matricula;
    }
}