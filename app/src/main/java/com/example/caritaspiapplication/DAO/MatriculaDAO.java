package com.example.caritaspiapplication.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Matricula;
import com.example.caritaspiapplication.model.Matricula;

import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    private final ConexaoSQLite conexaoSQLite;

    public MatriculaDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvarMatriculaDAO(Matricula matricula) {

        int resultado;

        resultado = verificarMatriculaExistente(matricula);

        System.out.println("dados matricula" +  matricula);

        SQLiteDatabase db = null;
        db = conexaoSQLite.getWritableDatabase();

        if (resultado == 0) {
            try {
                ContentValues values = new ContentValues();
                values.put("nome_atend", matricula.getNome_atend());
                values.put("nome_turma", matricula.getNome_turma());
                values.put("cod_atend", matricula.getCod_atend());
                values.put("cod_turma", matricula.getCod_turma());

                long idMatriculaInserido = db.insert("matricula", null, values);

                return idMatriculaInserido;

            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                if (db != null) {
                    db.close();
                }
            }
        }else if(resultado > 0){
            return -1;
        }
        return 0;
    }

    public List<Matricula> getListaMatricula() {

        List<Matricula> listaMatricula = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM matricula ORDER BY nome_turma ASC;";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Matricula matriculaTemporario = null;
                do {

                    matriculaTemporario = new Matricula();

                    matriculaTemporario.setId(cursor.getLong(0));
                    matriculaTemporario.setCod_turma(cursor.getLong(1));
                    matriculaTemporario.setCod_atend(cursor.getLong(2));
                    matriculaTemporario.setNome_turma(cursor.getString(3));
                    matriculaTemporario.setNome_atend(cursor.getString(4));

                    listaMatricula.add(matriculaTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE matricula", "Erro ao retornar lista de matriculas");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return listaMatricula;
    }

    public boolean excluirMatriculaDAO(long idMatricula) {
        SQLiteDatabase db = null;

        try {

            db = this.conexaoSQLite.getWritableDatabase();
            db.delete(
                    "matricula",
                    "id = ?",
                    new String[]{String.valueOf(idMatricula)}

            );
        } catch (Exception e) {
            Log.d("matricula DAO", "Não foi possível deletar matricula");
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return true;
    }

    public long atualizarMatriculaDAO(Matricula matricula) {

        int resultado;
        resultado = verificarMatriculaExistente(matricula);

        SQLiteDatabase db = null;

        if (resultado == 0) {
            try {
                db = this.conexaoSQLite.getWritableDatabase();

                ContentValues matriculaAtributos = new ContentValues();
                matriculaAtributos.put("cod_turma", matricula.getCod_turma());
                matriculaAtributos.put("cod_atend", matricula.getCod_atend());
                matriculaAtributos.put("nome_turma", matricula.getNome_turma());
                matriculaAtributos.put("nome_atend", matricula.getNome_atend());

                int atualizou = db.update(
                        "matricula",
                        matriculaAtributos,
                        "id = ?",
                        new String[]{String.valueOf(matricula.getId())}
                );

                if (atualizou > 0) {
                    return 1;
                }

            } catch (Exception e) {
                Log.d("matricula DAO", "Não foi possível atualizar o matricula");
                return 0;
            } finally {
                if (db != null) {
                    db.close();
                }
            }
        }
        return -1;
    }

    public List<Matricula> getMatricula(long id_matricula) {

        List<Matricula> listaMatricula = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM matricula WHERE id = " + id_matricula + " ORDER BY nome_turma ASC;";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Matricula matriculaTemporario = null;
                do {

                    matriculaTemporario = new Matricula();

                    matriculaTemporario.setId(cursor.getLong(0));
                    matriculaTemporario.setCod_turma(cursor.getLong(1));
                    matriculaTemporario.setCod_atend(cursor.getLong(2));
                    matriculaTemporario.setNome_turma(cursor.getString(3));
                    matriculaTemporario.setNome_atend(cursor.getString(4));

                    listaMatricula.add(matriculaTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE matricula", "Erro ao retornar lista de matriculaes");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return listaMatricula;
    }

    private int verificarMatriculaExistente(Matricula matricula){
        SQLiteDatabase db3 = null;

        Cursor cursor;

        String cod_atend;
        String cod_turma;
        int quantidade;

        cod_atend = String.valueOf(matricula.getCod_atend());
        cod_turma = String.valueOf(matricula.getCod_turma());

        //String query = "SELECT * FROM matricula WHERE cod_atend = " + cod_atend + " AND cod_turma = " + cod_turma + "";

        try {
            db3 = this.conexaoSQLite.getReadableDatabase();

            //cursor = db.rawQuery(query, null);
            cursor = db3.rawQuery("SELECT * FROM matricula WHERE cod_atend=? AND cod_turma=?", new String[]{cod_atend,cod_turma});
            quantidade = cursor.getCount();

            System.out.println(quantidade);

        } catch (Exception e) {
            Log.d("ERRO LISTA DE matricula", "Erro ao retornar lista de matriculaes");
            return -1;
        } finally {
            if (db3 != null) {
                db3.close();
            }
        }

        return quantidade;
    }

    public List<Matricula> getListaMatriculaFiltroDAO(String filtroTurma, String filtroAtend){

        List<Matricula> listaMatricula = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "";

        if (filtroTurma.isEmpty()){
            query = "SELECT * FROM matricula WHERE " +
                    "nome_atend LIKE '%" + filtroAtend + "%' ORDER BY nome_turma ASC;";
        } else if (filtroAtend.isEmpty()){
            query = "SELECT * FROM matricula WHERE " +
                    "nome_turma LIKE '%" + filtroTurma + "%' " +
                    "ORDER BY nome_turma ASC;";
        } else {
            query = "SELECT * FROM matricula WHERE " +
                    "nome_turma LIKE '%" + filtroTurma + "%' AND " +
                    "nome_atend LIKE '%" + filtroAtend + "%' ORDER BY nome_turma ASC;";
        }

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            System.out.println(cursor.getCount());

            if(cursor.moveToFirst()){

                Matricula matriculaTemporario = null;
                do {

                    matriculaTemporario = new Matricula();

                    matriculaTemporario.setId(cursor.getLong(0));
                    matriculaTemporario.setCod_turma(cursor.getLong(1));
                    matriculaTemporario.setCod_atend(cursor.getLong(2));
                    matriculaTemporario.setNome_turma(cursor.getString(3));
                    matriculaTemporario.setNome_atend(cursor.getString(4));

                    listaMatricula.add(matriculaTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE ATENDIDO", "Erro ao retornar lista de matriculaes");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaMatricula;
    }
}
