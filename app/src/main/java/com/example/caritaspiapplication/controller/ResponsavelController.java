package com.example.caritaspiapplication.controller;

import com.example.caritaspiapplication.DAO.ResponsavelDAO;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Responsavel;

import java.util.List;

public class ResponsavelController {

    private final ResponsavelDAO responsavelDAO;

    public ResponsavelController(ConexaoSQLite conexaoSQLite){
        responsavelDAO = new ResponsavelDAO(conexaoSQLite);
    }

    public long salvarResponsavelController(Responsavel responsavel){
        return this.responsavelDAO.salvarResponsavelDAO(responsavel);
    }

    public List<Responsavel> getListaResponsavelController(){
        return this.responsavelDAO.getListaResponsavel();
    }

    public boolean excluirResponsavelController(long idResponsavel){
        return this.responsavelDAO.excluirResponsavelDAO(idResponsavel);
    }

    public boolean atualizarResponsavelController(Responsavel responsavel){
        return this.responsavelDAO.atualizarResponsavelDAO(responsavel);
    }

    public List<Responsavel> getResponsavelController(long id_Responsavel){
        return this.responsavelDAO.getResponsavel(id_Responsavel);
    }

    public List<Responsavel> getListaOrdenadaResponsavel(long id_Responsavel){
        return this.responsavelDAO.getListaOrdenadaResponsavel(id_Responsavel);
    }

    public List<Responsavel> getListaResponsavelFiltroController(String filtro){
        return this.responsavelDAO.getListaResponsavelFiltroDAO(filtro);
    }
}
