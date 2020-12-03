package com.example.caritaspiapplication.controller;

import com.example.caritaspiapplication.DAO.UsuarioDAO;
import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Usuario;

import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController(ConexaoSQLite conexaoSQLite){
        usuarioDAO = new UsuarioDAO(conexaoSQLite);
    }

    public String salvarUsuarioController(Usuario usuario, String senhaAdmin){
        return this.usuarioDAO.salvarUsuarioDAO(usuario, senhaAdmin);
    }

    public boolean loginController(Usuario usuario){
        return this.usuarioDAO.loginUsuarioDAO(usuario);
    }

    public String atualizarUsuarioController(Usuario usuario, String novaSenha){
        return this.usuarioDAO.atualizarUsuarioDAO(usuario, novaSenha);
    }

}
