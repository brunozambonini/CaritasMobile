package com.example.caritaspiapplication.controller;

import com.example.caritaspiapplication.DAO.AtendidoDAO;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Instrutor;

import java.util.List;

public class AtendidoController {

    private final AtendidoDAO atendidoDAO;

    public AtendidoController(ConexaoSQLite conexaoSQLite){
        atendidoDAO = new AtendidoDAO(conexaoSQLite);
    }

    public long salvaratendidoController(Atendido atendido){
        return this.atendidoDAO.salvaratendidoDAO(atendido);
    }

    public List<Atendido> getListaatendidoController(){
        return this.atendidoDAO.getListaAtendido();
    }

    public boolean excluiratendidoController(long idatendido){
        return this.atendidoDAO.excluirAtendidoDAO(idatendido);
    }

    public boolean atualizaratendidoController(Atendido atendido){
        return this.atendidoDAO.atualizarAtendidoDAO(atendido);
    }

    public List<Atendido> getListaOrdenadaAtendidoController(long id_atend){
        return this.atendidoDAO.getListaOrdenadaAtendido(id_atend);
    }

    public List<Atendido> getListaatendidoFiltroController(String filtro){
        return this.atendidoDAO.getListaAtendidFiltroDAO(filtro);
    }
}
