package com.example.caritaspiapplication.activities.turma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.activities.turma.EditarTurmaActivity;
import com.example.caritaspiapplication.controller.TurmaController;
import com.example.caritaspiapplication.controller.InstrutorController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Turma;
import com.example.caritaspiapplication.model.Turma;
import com.example.caritaspiapplication.model.Instrutor;

import java.util.List;

public class EditarTurmaActivity extends AppCompatActivity {

    private EditText edt_id;
    private EditText edt_nome;
    private EditText edt_periodo;


    //Spiner
    private Spinner spnInstrutor;
    private List<Instrutor> listaInstrutor;
    private InstrutorController instrutorController;

    private Button btn_salvar;

    private Turma turma;

    private long cod_turma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_turma);

        //this.edt_id = (EditText) findViewById(R.id.edt_cod_atend);
        this.edt_nome = (EditText)findViewById(R.id.edt_nome);
        this.edt_periodo = (EditText)findViewById(R.id.edt_periodo);

        Bundle bundleDadosTurma = getIntent().getExtras();

        Turma turma = new Turma();

        turma.setCod_turma(bundleDadosTurma.getLong("cod_turma"));
        turma.setNome(bundleDadosTurma.getString("nome"));
        turma.setPeriodo(bundleDadosTurma.getString("periodo"));
        turma.setCod_inst(bundleDadosTurma.getLong("instrutor"));

        //Spinner
        this.instrutorController = new InstrutorController(ConexaoSQLite.getInstance(this));
        this.listaInstrutor = this.instrutorController.getListaOrdenadoInstrutor(turma.getCod_inst());

        this.spnInstrutor = (Spinner) this.findViewById(R.id.spnInstrutor);
        ArrayAdapter<Instrutor> spnInstrutorAdapter = new ArrayAdapter<Instrutor>(
                this,
                android.R.layout.simple_spinner_item,
                this.listaInstrutor
        );
        this.spnInstrutor.setAdapter(spnInstrutorAdapter);
        //End Spinner

        this.cod_turma = bundleDadosTurma.getLong("cod_turma"); // ----

        this.setDadosTurma(turma);

        this.btn_salvar = (Button) findViewById(R.id.btn_atualizarTurma);
        this.salvarListener();

    }
    private void setDadosTurma(Turma turma){

        //this.edt_id.setText(String.valueOf(turma.getCod_res()));
        this.edt_nome.setText(turma.getNome());
        this.edt_periodo.setText(turma.getPeriodo());

    }

    private void salvarListener() {
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Turma turmaAAtualizar = getDadosTurma();

                if (turmaAAtualizar != null) {
                    TurmaController turmaController = new TurmaController(ConexaoSQLite.getInstance(EditarTurmaActivity.this));
                    boolean atualizou = turmaController.atualizarturmaController(turmaAAtualizar);

                    if (atualizou) {
                        Toast.makeText(EditarTurmaActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EditarTurmaActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarTurmaActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Turma getDadosTurma(){
        this.turma = new Turma();
        Instrutor instrutorSelecionado = (Instrutor) this.spnInstrutor.getSelectedItem();

        System.out.println(this.cod_turma);

        this.turma.setCod_turma(this.cod_turma);

        if(this.edt_nome.getText().toString().isEmpty() == false){
            this.turma.setNome(this.edt_nome.getText().toString());
        }else {
            return null;
        }
        if (this.edt_periodo.getText().toString().isEmpty() == false){
            this.turma.setPeriodo(this.edt_periodo.getText().toString());
        }else {
            return null;
        }

        this.turma.setCod_inst(instrutorSelecionado.getCod_inst());

        return turma;
    }
}