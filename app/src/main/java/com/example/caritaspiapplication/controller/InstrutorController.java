package com.example.caritaspiapplication.controller;

import com.example.caritaspiapplication.DAO.InstrutorDAO;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Instrutor;

import java.util.List;

public class InstrutorController {

    private final InstrutorDAO instrutorDAO;

    public InstrutorController(ConexaoSQLite conexaoSQLite){
        instrutorDAO = new InstrutorDAO(conexaoSQLite);
    }

    public long salvarInstrutorController(Instrutor instrutor){
        return this.instrutorDAO.salvarInstrutorDAO(instrutor);
    }

    public List<Instrutor> getListaInstrutorController(){
        return this.instrutorDAO.getListaInstrutor();
    }

    public boolean excluirInstrutorController(long idInstrutor){
        return this.instrutorDAO.excluirInstrutorDAO(idInstrutor);
    }

    public boolean atualizarInstrutorController(Instrutor instrutor){
        return this.instrutorDAO.atualizarInstrutorDAO(instrutor);
    }

    public List<Instrutor> getInstrutorController(long id_instrutor){
        return this.instrutorDAO.getInstrutor(id_instrutor);
    }
    public List<Instrutor> getListaOrdenadoInstrutor(long id_instrutor){
        return this.instrutorDAO.getListaOrdenadaInstrutor(id_instrutor);
    }
    public List<Instrutor> getListaInstrutorFiltroController(String filtro){
        return this.instrutorDAO.getListaInstrutorFiltroDAO(filtro);
    }
}
