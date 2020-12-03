package com.example.caritaspiapplication.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final ConexaoSQLite conexaoSQLite;

    public UsuarioDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public String salvarUsuarioDAO(Usuario usuario, String senhaAdmin) {
        SQLiteDatabase db = conexaoSQLite.getReadableDatabase();

        //verifica se já existe um usuario com mesmo nome
        Cursor c = db.rawQuery("SELECT * FROM usuario WHERE user=?", new String[]{usuario.getUser()});
        if (c.getCount() > 0){
            return "Existente";
        }

        //Verifica se senha do admin está correta
        SQLiteDatabase db2 = conexaoSQLite.getReadableDatabase();
        Cursor c2 = db2.rawQuery("SELECT * FROM usuario WHERE user=? AND senha=?", new String[]{"admin", senhaAdmin});
        if (c2.getCount() == 0){
            return "ErroAdmin";
        }

        SQLiteDatabase db3 = conexaoSQLite.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("user", usuario.getUser());
            values.put("senha", usuario.getSenha());

            db3.insert("usuario", null, values);

            return "Sucesso";

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db3 != null) {
                db3.close();
            }
        }
        return "ERRO";
    }


    public String atualizarUsuarioDAO(Usuario usuario, String novaSenha){

        //verifica se existe o usuario
        SQLiteDatabase db = conexaoSQLite.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM usuario WHERE user=?", new String[]{usuario.getUser()});
        if (c.getCount() == 0){
            return "ErroUsuario";
        }

        //Verifica se usuario e senha estão corretos
        SQLiteDatabase db2 = conexaoSQLite.getReadableDatabase();
        Cursor c2 = db2.rawQuery("SELECT * FROM usuario WHERE user=? AND senha=?", new String[]{usuario.getUser(), usuario.getSenha()});
        if (c2.getCount() == 0){
            return "ErroSenha";
        }

        SQLiteDatabase db3 = conexaoSQLite.getWritableDatabase();
        try{

            ContentValues usuarioAtributos = new ContentValues();
            usuarioAtributos.put("senha", novaSenha);

            int atualizou = db3.update(
                    "usuario",
                    usuarioAtributos,
                    "user = ?",
                    new String[]{String.valueOf(usuario.getUser())}
            );

            if(atualizou > 0){
                return "Atualizou";
            }

        }catch(Exception e){
            Log.d("usuario DAO", "Não foi possível atualizar o usuario");
            return "ERRO";
        }finally {
            if (db3 != null) {
                db3.close();
            }
        }
        return "ERRO";
    }

    public boolean loginUsuarioDAO(Usuario usuario) {

        SQLiteDatabase db = conexaoSQLite.getReadableDatabase();
        try {
            Cursor c = db.rawQuery("SELECT * FROM usuario WHERE user=? AND senha=?", new String[]{usuario.getUser(), usuario.getSenha()});
            if (c.getCount() == 0){
                return false;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return true;
    }

}
