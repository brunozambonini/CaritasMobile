package com.example.caritaspiapplication.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Instrutor;
import com.example.caritaspiapplication.model.Instrutor;
import com.example.caritaspiapplication.model.Responsavel;

import java.util.ArrayList;
import java.util.List;

public class InstrutorDAO {

    private final ConexaoSQLite conexaoSQLite;

    public InstrutorDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvarInstrutorDAO(Instrutor instrutor) {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nome", instrutor.getNome());
            values.put("rg", instrutor.getRg());

            long idinstrutorInserido = db.insert("instrutor", null, values);

            return idinstrutorInserido;

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public List<Instrutor> getListaInstrutor(){

        List<Instrutor> listaInstrutor = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM instrutor ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Instrutor instrutorTemporario = null;
                do {

                    instrutorTemporario = new Instrutor();

                    instrutorTemporario.setCod_inst(cursor.getLong(0));
                    instrutorTemporario.setNome(cursor.getString(1));
                    instrutorTemporario.setRg(cursor.getString(2));

                    listaInstrutor.add(instrutorTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE instrutor", "Erro ao retornar lista de instrutores");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaInstrutor;
    }

    public boolean excluirInstrutorDAO(long idInstrutor){
        SQLiteDatabase db = null;

        try{

            db = this.conexaoSQLite.getWritableDatabase();
            db.delete(
                    "instrutor",
                    "cod_inst = ?",
                    new String[]{String.valueOf(idInstrutor)}

            );
        }catch(Exception e){
            Log.d("INSTRUTOR DAO","Não foi possível deletar instrutor");
            return false;
        }finally {
            if (db != null) {
                db.close();
            }
        }
        return true;
    }

    public boolean atualizarInstrutorDAO(Instrutor instrutor){
        SQLiteDatabase db = null;

        try{
            db = this.conexaoSQLite.getWritableDatabase();

            System.out.println("RESULTADOOOOO " + instrutor.getCod_inst());

            ContentValues instrutorAtributos = new ContentValues();
            instrutorAtributos.put("nome", instrutor.getNome());
            instrutorAtributos.put("rg", instrutor.getRg());

            int atualizou = db.update(
                    "instrutor",
                    instrutorAtributos,
                    "cod_inst = ?",
                    new String[]{String.valueOf(instrutor.getCod_inst())}
            );

            if(atualizou > 0){
                return true;
            }

        }catch(Exception e){
            Log.d("instrutor DAO", "Não foi possível atualizar o instrutor");
            return false;
        }finally {
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public List<Instrutor> getInstrutor(long id_instrutor){

        List<Instrutor> listaInstrutor = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM instrutor WHERE cod_inst = " + id_instrutor + " ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Instrutor instrutorTemporario = null;
                do {

                    instrutorTemporario = new Instrutor();

                    instrutorTemporario.setCod_inst(cursor.getLong(0));
                    instrutorTemporario.setNome(cursor.getString(1));
                    instrutorTemporario.setRg(cursor.getString(2));

                    listaInstrutor.add(instrutorTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE instrutor", "Erro ao retornar lista de instrutores");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaInstrutor;
    }

    public List<Instrutor> getListaOrdenadaInstrutor(long id_instrutor) {

        List<Instrutor> listaInstrutor = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM instrutor WHERE cod_inst = " + id_instrutor + " ORDER BY nome ASC;";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Instrutor instrutorTemporario = null;
                do {

                    instrutorTemporario = new Instrutor();

                    instrutorTemporario.setCod_inst(cursor.getLong(0));
                    instrutorTemporario.setNome(cursor.getString(1));
                    instrutorTemporario.setRg(cursor.getString(2));

                    listaInstrutor.add(instrutorTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE instrutor", "Erro ao retornar lista de instrutores");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        SQLiteDatabase db2 = null;
        Cursor cursor2;
        String query2 = "SELECT * FROM instrutor ORDER BY nome ASC;";

        try {
            db2 = this.conexaoSQLite.getReadableDatabase();


            cursor2 = db2.rawQuery(query2, null);

            if (cursor2.moveToFirst()) {

                Instrutor instrutorTemporario = null;
                do {

                    instrutorTemporario = new Instrutor();

                    instrutorTemporario.setCod_inst(cursor2.getLong(0));
                    instrutorTemporario.setNome(cursor2.getString(1));
                    instrutorTemporario.setRg(cursor2.getString(2));

                    listaInstrutor.add(instrutorTemporario);

                } while (cursor2.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE instrutor", "Erro ao retornar lista de instrutores");
            return null;
        } finally {
            if (db2 != null) {
                db2.close();
            }

            return listaInstrutor;
        }
    }

    public List<Instrutor> getListaInstrutorFiltroDAO(String filtro){

        List<Instrutor> listaInstrutor = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM instrutor WHERE nome LIKE '%" + filtro + "%' ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Instrutor instrutorTemporario = null;
                do {

                    instrutorTemporario = new Instrutor();

                    instrutorTemporario.setCod_inst(cursor.getLong(0));
                    instrutorTemporario.setNome(cursor.getString(1));
                    instrutorTemporario.setRg(cursor.getString(2));

                    listaInstrutor.add(instrutorTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE ATENDIDO", "Erro ao retornar lista de instrutores");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaInstrutor;
    }
}
