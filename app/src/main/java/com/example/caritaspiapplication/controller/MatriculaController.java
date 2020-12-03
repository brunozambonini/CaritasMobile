package com.example.caritaspiapplication.controller;

import com.example.caritaspiapplication.DAO.MatriculaDAO;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Instrutor;
import com.example.caritaspiapplication.model.Matricula;

import java.util.List;

public class MatriculaController {

    private final MatriculaDAO matriculaDAO;

    public MatriculaController(ConexaoSQLite conexaoSQLite){
        matriculaDAO = new MatriculaDAO(conexaoSQLite);
    }

    public long salvarMatriculaController(Matricula matricula){
        return this.matriculaDAO.salvarMatriculaDAO(matricula);
    }

    public List<Matricula> getListaMatriculaController(){
        return this.matriculaDAO.getListaMatricula();
    }

    public boolean excluirMatriculaController(long idMatricula){
        return this.matriculaDAO.excluirMatriculaDAO(idMatricula);
    }

    public long atualizarMatriculaController(Matricula matricula){
        return this.matriculaDAO.atualizarMatriculaDAO(matricula);
    }

    public List<Matricula> getMatriculaController(long id_Matricula){
        return this.matriculaDAO.getMatricula(id_Matricula);
    }

    public List<Matricula> getListaMatriculaFiltroController(String filtroTurma, String filtroAtend){
        return this.matriculaDAO.getListaMatriculaFiltroDAO(filtroTurma, filtroAtend);
    }


}
