package com.example.caritaspiapplication.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Atendido;
import com.example.caritaspiapplication.model.Atendido;

import java.util.ArrayList;
import java.util.List;

public class AtendidoDAO {
    private final ConexaoSQLite conexaoSQLite;

    public AtendidoDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvaratendidoDAO(Atendido atendido) {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nome", atendido.getNome());
            values.put("ra", atendido.getRa());
            values.put("nis", atendido.getNis());
            values.put("data_nasci", atendido.getData_nasci());
            values.put("rua", atendido.getRua());
            values.put("bairro", atendido.getBairro());
            values.put("n_casa", atendido.getN_casa());
            values.put("matriculas", atendido.getMatriculas());
            values.put("cod_res", atendido.getCod_res());

            long idAtendidoInserido = db.insert("atendido", null, values);

            return idAtendidoInserido;

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public List<Atendido> getListaAtendido(){

        List<Atendido> listaAtendido = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM atendido ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Atendido atendidoTemporario = null;
                do {

                    atendidoTemporario = new Atendido();

                    atendidoTemporario.setCod_atend(cursor.getLong(0));
                    atendidoTemporario.setNome(cursor.getString(1));
                    atendidoTemporario.setRa(cursor.getString(2));
                    atendidoTemporario.setNis(cursor.getString(3));
                    atendidoTemporario.setData_nasci(cursor.getString(4));
                    atendidoTemporario.setRua(cursor.getString(5));
                    atendidoTemporario.setBairro(cursor.getString(6));
                    atendidoTemporario.setN_casa(cursor.getString(7));
                    atendidoTemporario.setMatriculas(cursor.getString(8));
                    atendidoTemporario.setCod_res(cursor.getLong(9));

                    listaAtendido.add(atendidoTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE ATENDIDO", "Erro ao retornar lista de atendidoes");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaAtendido;
    }

    public boolean excluirAtendidoDAO(long idAtendido){
        SQLiteDatabase db = null;

        System.out.println("id = " +idAtendido);

        try{

            db = this.conexaoSQLite.getWritableDatabase();
            db.delete(
                    "atendido",
                    "cod_atend = ?",
                    new String[]{String.valueOf(idAtendido)}

            );
        }catch(Exception e){
            Log.d("ATENDIDO DAO","Não foi possível deletar atendido");
            return false;
        }finally {
            if (db != null) {
                db.close();
            }
        }
        return true;
    }

    public boolean atualizarAtendidoDAO(Atendido atendido){
        SQLiteDatabase db = null;

        try{
            db = this.conexaoSQLite.getWritableDatabase();

            ContentValues atendidoAtributos = new ContentValues();
            atendidoAtributos.put("nome", atendido.getNome());
            atendidoAtributos.put("ra", atendido.getRa());
            atendidoAtributos.put("nis", atendido.getNis());
            atendidoAtributos.put("data_nasci", atendido.getData_nasci());
            atendidoAtributos.put("rua", atendido.getRua());
            atendidoAtributos.put("bairro", atendido.getBairro());
            atendidoAtributos.put("n_casa", atendido.getN_casa());
            atendidoAtributos.put("cod_res", atendido.getCod_res());

            int atualizou = db.update(
                    "atendido",
                    atendidoAtributos,
                    "cod_atend = ?",
                    new String[]{String.valueOf(atendido.getCod_atend())}
            );

            if(atualizou > 0){
                return true;
            }

        }catch(Exception e){
            Log.d("atendido DAO", "Não foi possível atualizar o atendido");
            return false;
        }finally {
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public List<Atendido> getListaOrdenadaAtendido(long id_atendido) {

        List<Atendido> listaAtendido = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM atendido WHERE cod_atend = " + id_atendido + ";";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Atendido atendidoTemporario = null;
                do {

                    atendidoTemporario = new Atendido();

                    atendidoTemporario.setCod_atend(cursor.getLong(0));
                    atendidoTemporario.setNome(cursor.getString(1));
                    atendidoTemporario.setRa(cursor.getString(2));
                    atendidoTemporario.setNis(cursor.getString(3));
                    atendidoTemporario.setData_nasci(cursor.getString(4));
                    atendidoTemporario.setRua(cursor.getString(5));
                    atendidoTemporario.setBairro(cursor.getString(6));
                    atendidoTemporario.setN_casa(cursor.getString(7));
                    atendidoTemporario.setMatriculas(cursor.getString(8));
                    atendidoTemporario.setCod_res(cursor.getLong(9));

                    listaAtendido.add(atendidoTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE atendido", "Erro ao retornar lista de atendidos");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        SQLiteDatabase db2 = null;
        Cursor cursor2;
        String query2 = "SELECT * FROM atendido ORDER BY nome ASC;";

        try {
            db2 = this.conexaoSQLite.getReadableDatabase();

            cursor2 = db2.rawQuery(query2, null);

            if (cursor2.moveToFirst()) {

                Atendido atendidoTemporario = null;
                do {

                    atendidoTemporario = new Atendido();

                    atendidoTemporario.setCod_atend(cursor2.getLong(0));
                    atendidoTemporario.setNome(cursor2.getString(1));
                    atendidoTemporario.setRa(cursor2.getString(2));
                    atendidoTemporario.setNis(cursor2.getString(3));
                    atendidoTemporario.setData_nasci(cursor2.getString(4));
                    atendidoTemporario.setRua(cursor2.getString(5));
                    atendidoTemporario.setBairro(cursor2.getString(6));
                    atendidoTemporario.setN_casa(cursor2.getString(7));
                    atendidoTemporario.setMatriculas(cursor2.getString(8));
                    atendidoTemporario.setCod_res(cursor2.getLong(9));

                    listaAtendido.add(atendidoTemporario);

                } while (cursor2.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE atendido", "Erro ao retornar lista de atendidoes");
            return null;
        } finally {
            if (db2 != null) {
                db2.close();
            }

            return listaAtendido;
        }
    }

    public List<Atendido> getListaAtendidFiltroDAO(String filtro){

        List<Atendido> listaAtendido = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM atendido WHERE nome LIKE '%" + filtro + "%' ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Atendido atendidoTemporario = null;
                do {

                    atendidoTemporario = new Atendido();

                    atendidoTemporario.setCod_atend(cursor.getLong(0));
                    atendidoTemporario.setNome(cursor.getString(1));
                    atendidoTemporario.setRa(cursor.getString(2));
                    atendidoTemporario.setNis(cursor.getString(3));
                    atendidoTemporario.setData_nasci(cursor.getString(4));
                    atendidoTemporario.setRua(cursor.getString(5));
                    atendidoTemporario.setBairro(cursor.getString(6));
                    atendidoTemporario.setN_casa(cursor.getString(7));
                    atendidoTemporario.setMatriculas(cursor.getString(8));
                    atendidoTemporario.setCod_res(cursor.getLong(9));

                    listaAtendido.add(atendidoTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE ATENDIDO", "Erro ao retornar lista de atendidoes");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaAtendido;
    }

}
