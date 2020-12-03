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
import com.example.caritaspiapplication.activities.turma.TurmaActivity;
import com.example.caritaspiapplication.controller.TurmaController;
import com.example.caritaspiapplication.controller.InstrutorController;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Turma;
import com.example.caritaspiapplication.model.Instrutor;

import java.util.List;

public class TurmaActivity extends AppCompatActivity {

    private Spinner spnInstrutor;
    private List<Instrutor> listaInstrutor;
    private InstrutorController instrutorController;

    private Spinner spnPeriodo;

    private EditText edt_nome;
    private EditText edt_periodo;

    private Button btn_salvar;

    private Turma turma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turma);


        //Spinner Instrutor
        this.instrutorController = new InstrutorController(ConexaoSQLite.getInstance(this));
        this.listaInstrutor = this.instrutorController.getListaInstrutorController();

        this.spnInstrutor = (Spinner) this.findViewById(R.id.spnInstrutor);
        ArrayAdapter<Instrutor> spnInstrutorAdapter = new ArrayAdapter<Instrutor>(
                this,
                android.R.layout.simple_spinner_item,
                this.listaInstrutor
        );

        this.spnInstrutor.setAdapter(spnInstrutorAdapter);
        //End Spinner

        //Spinner spnPeriodo
        this.spnPeriodo = (Spinner) this.findViewById(R.id.spnPeriodo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.periodo_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPeriodo.setAdapter(adapter);
        //Spinner

        edt_nome = (EditText)findViewById(R.id.edt_nome);

        btn_salvar = (Button) findViewById(R.id.btn_salvarTurma);

        this.salvarListener();
    }

    private void salvarListener(){
        this.btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Turma turmaACadastrar = getDadosTurma();

                if(turmaACadastrar != null){
                    TurmaController turmaController = new TurmaController(ConexaoSQLite.getInstance(TurmaActivity.this));
                    long idTurma = turmaController.salvarturmaController(turmaACadastrar);

                    if(idTurma > 0){
                        Toast.makeText(TurmaActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(TurmaActivity.this, "Ops! Não foi possível salvar.", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(TurmaActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Turma getDadosTurma(){

        Instrutor instrutorSelecionado = (Instrutor) this.spnInstrutor.getSelectedItem();

        this.turma = new Turma();

        if(this.edt_nome.getText().toString().isEmpty() == false){
            this.turma.setNome(this.edt_nome.getText().toString());
        }else {
            return null;
        }

        this.turma.setPeriodo(this.spnPeriodo.getSelectedItem().toString());

        this.turma.setCod_inst(instrutorSelecionado.getCod_inst());

        return turma;
    }
}