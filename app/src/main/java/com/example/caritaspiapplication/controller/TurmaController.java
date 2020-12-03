package com.example.caritaspiapplication.controller;

import com.example.caritaspiapplication.DAO.TurmaDAO;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Turma;

import java.util.List;

public class TurmaController {

    private final TurmaDAO turmaDAO;

    public TurmaController(ConexaoSQLite conexaoSQLite){
        turmaDAO = new TurmaDAO(conexaoSQLite);
    }

    public long salvarturmaController(Turma turma){
        return this.turmaDAO.salvarTurmaDAO(turma);
    }

    public List<Turma> getListaturmaController(){
        return this.turmaDAO.getListaTurma();
    }

    public boolean excluirturmaController(long idTurma){
        return this.turmaDAO.excluirTurmaDAO(idTurma);
    }

    public boolean atualizarturmaController(Turma turma){
        return this.turmaDAO.atualizarTurmaDAO(turma);
    }

    public List<Turma> getListaOrdenadaTurmaController(long id_turma){
        return this.turmaDAO.getListaOrdenadaTurmaDAO(id_turma);
    }

    public List<Turma> getListaTurmaFiltroController(String filtro){
        return this.turmaDAO.getListaTurmaFiltroDAO(filtro);
    }
}
