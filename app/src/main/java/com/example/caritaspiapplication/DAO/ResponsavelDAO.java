package com.example.caritaspiapplication.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Responsavel;
import com.example.caritaspiapplication.model.Responsavel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ResponsavelDAO {

    private final ConexaoSQLite conexaoSQLite;

    public ResponsavelDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvarResponsavelDAO(Responsavel responsavel) {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nome", responsavel.getNome());
            values.put("rg", responsavel.getRg());
            values.put("nis", responsavel.getNis());
            values.put("cpf", responsavel.getCpf());
            values.put("data_nasci", responsavel.getData_nasci());
            values.put("rua", responsavel.getRua());
            values.put("bairro", responsavel.getBairro());
            values.put("n_casa", responsavel.getN_casa());
            values.put("telefone", responsavel.getTelefone());

            long idResponsavelInserido = db.insert("responsavel", null, values);

            return idResponsavelInserido;

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public List<Responsavel> getListaResponsavel(){

        List<Responsavel> listaResponsavel = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM responsavel ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Responsavel responsavelTemporario = null;
                do {

                    responsavelTemporario = new Responsavel();

                    responsavelTemporario.setCod_res(cursor.getLong(0));
                    responsavelTemporario.setNome(cursor.getString(1));
                    responsavelTemporario.setRg(cursor.getString(2));
                    responsavelTemporario.setNis(cursor.getString(3));
                    responsavelTemporario.setCpf(cursor.getString(4));
                    responsavelTemporario.setData_nasci(cursor.getString(5));
                    responsavelTemporario.setRua(cursor.getString(6));
                    responsavelTemporario.setBairro(cursor.getString(7));
                    responsavelTemporario.setN_casa(cursor.getString(8));
                    responsavelTemporario.setTelefone(cursor.getString(9));

                    listaResponsavel.add(responsavelTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE RESPONSAVEL", "Erro ao retornar lista de responsáveis");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaResponsavel;
    }

    public boolean excluirResponsavelDAO(long idResponsavel){
        SQLiteDatabase db = null;

        System.out.println("id = " +idResponsavel);

        try{

            db = this.conexaoSQLite.getWritableDatabase();
            db.delete(
                    "responsavel",
                    "cod_res = ?",
                    new String[]{String.valueOf(idResponsavel)}

            );
        }catch(Exception e){
            Log.d("RESPONSAVEL DAO","Não foi possível deletar responsável");
            return false;
        }finally {
            if (db != null) {
                db.close();
            }
        }
        return true;
    }

    public boolean atualizarResponsavelDAO(Responsavel responsavel){
        SQLiteDatabase db = null;

        try{
            db = this.conexaoSQLite.getWritableDatabase();

            System.out.println("RESULTADOOOOO " + responsavel.getCod_res());

            ContentValues responsavelAtributos = new ContentValues();
            responsavelAtributos.put("nome", responsavel.getNome());
            responsavelAtributos.put("rg", responsavel.getRg());
            responsavelAtributos.put("nis", responsavel.getNis());
            responsavelAtributos.put("cpf", responsavel.getCpf());
            responsavelAtributos.put("data_nasci", responsavel.getData_nasci());
            responsavelAtributos.put("rua", responsavel.getRua());
            responsavelAtributos.put("bairro", responsavel.getBairro());
            responsavelAtributos.put("n_casa", responsavel.getN_casa());
            responsavelAtributos.put("telefone", responsavel.getTelefone());

            int atualizou = db.update(
                    "responsavel",
                    responsavelAtributos,
                    "cod_res = ?",
                    new String[]{String.valueOf(responsavel.getCod_res())}
            );

            if(atualizou > 0){
                return true;
            }

        }catch(Exception e){
            Log.d("RESPONSAVEL DAO", "Não foi possível atualizar o responsavel");
            return false;
        }finally {
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public List<Responsavel> getResponsavel(long id_Responsavel){

        List<Responsavel> listaResponsavel = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM responsavel WHERE cod_res = " + id_Responsavel + " ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Responsavel responsavelTemporario = null;
                do {

                    responsavelTemporario = new Responsavel();

                    responsavelTemporario.setCod_res(cursor.getLong(0));
                    responsavelTemporario.setNome(cursor.getString(1));
                    responsavelTemporario.setRg(cursor.getString(2));
                    responsavelTemporario.setNis(cursor.getString(3));
                    responsavelTemporario.setCpf(cursor.getString(4));
                    responsavelTemporario.setData_nasci(cursor.getString(5));
                    responsavelTemporario.setRua(cursor.getString(6));
                    responsavelTemporario.setBairro(cursor.getString(7));
                    responsavelTemporario.setN_casa(cursor.getString(8));
                    responsavelTemporario.setTelefone(cursor.getString(9));

                    listaResponsavel.add(responsavelTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE RESPONSAVEL", "Erro ao retornar lista de responsáveis");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaResponsavel;
    }

    public List<Responsavel> getListaOrdenadaResponsavel(long id_responsavel) {

        List<Responsavel> listaResponsavel = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM responsavel WHERE cod_res = " + id_responsavel + " ORDER BY nome ASC;";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Responsavel responsavelTemporario = null;
                do {

                    responsavelTemporario = new Responsavel();

                    responsavelTemporario.setCod_res(cursor.getLong(0));
                    responsavelTemporario.setNome(cursor.getString(1));
                    responsavelTemporario.setRg(cursor.getString(2));
                    responsavelTemporario.setNis(cursor.getString(3));
                    responsavelTemporario.setCpf(cursor.getString(4));
                    responsavelTemporario.setData_nasci(cursor.getString(5));
                    responsavelTemporario.setRua(cursor.getString(6));
                    responsavelTemporario.setBairro(cursor.getString(7));
                    responsavelTemporario.setN_casa(cursor.getString(8));
                    responsavelTemporario.setTelefone(cursor.getString(9));

                    listaResponsavel.add(responsavelTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE RESPONSAVEL", "Erro ao retornar lista de responsáveis");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        SQLiteDatabase db2 = null;
        Cursor cursor2;
        String query2 = "SELECT * FROM responsavel ORDER BY nome ASC;";

        try {
            db2 = this.conexaoSQLite.getReadableDatabase();


            cursor2 = db2.rawQuery(query2, null);

            if (cursor2.moveToFirst()) {

                Responsavel responsavelTemporario = null;
                do {

                    responsavelTemporario = new Responsavel();

                    responsavelTemporario.setCod_res(cursor2.getLong(0));
                    responsavelTemporario.setNome(cursor2.getString(1));
                    responsavelTemporario.setRg(cursor2.getString(2));
                    responsavelTemporario.setNis(cursor2.getString(3));
                    responsavelTemporario.setCpf(cursor2.getString(4));
                    responsavelTemporario.setData_nasci(cursor2.getString(5));
                    responsavelTemporario.setRua(cursor2.getString(6));
                    responsavelTemporario.setBairro(cursor2.getString(7));
                    responsavelTemporario.setN_casa(cursor2.getString(8));
                    responsavelTemporario.setTelefone(cursor2.getString(9));

                    listaResponsavel.add(responsavelTemporario);

                } while (cursor2.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE Responsavel", "Erro ao retornar lista de Responsaveis");
            return null;
        } finally {
            if (db2 != null) {
                db2.close();
            }


            return listaResponsavel;
        }
    }

    public List<Responsavel> getListaResponsavelFiltroDAO(String filtro){

        List<Responsavel> listaResponsavel = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM responsavel WHERE nome LIKE '%" + filtro + "%' ORDER BY nome ASC;";

        System.out.println(query + "---------------------------");

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Responsavel responsavelTemporario = null;
                do {

                    responsavelTemporario = new Responsavel();

                    responsavelTemporario.setCod_res(cursor.getLong(0));
                    responsavelTemporario.setNome(cursor.getString(1));
                    responsavelTemporario.setRg(cursor.getString(2));
                    responsavelTemporario.setNis(cursor.getString(3));
                    responsavelTemporario.setCpf(cursor.getString(4));
                    responsavelTemporario.setData_nasci(cursor.getString(5));
                    responsavelTemporario.setRua(cursor.getString(6));
                    responsavelTemporario.setBairro(cursor.getString(7));
                    responsavelTemporario.setN_casa(cursor.getString(8));
                    responsavelTemporario.setTelefone(cursor.getString(9));

                    listaResponsavel.add(responsavelTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE ATENDIDO", "Erro ao retornar lista de responsaveles");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaResponsavel;
    }

}
