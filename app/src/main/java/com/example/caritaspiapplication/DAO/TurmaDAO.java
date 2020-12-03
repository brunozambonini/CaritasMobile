package com.example.caritaspiapplication.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.caritaspiapplication.dbHelper.ConexaoSQLite;
import com.example.caritaspiapplication.model.Turma;
import com.example.caritaspiapplication.model.Turma;
import com.example.caritaspiapplication.model.Responsavel;
import com.example.caritaspiapplication.model.Turma;

import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    private final ConexaoSQLite conexaoSQLite;

    public TurmaDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvarTurmaDAO(Turma turma) {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nome", turma.getNome());
            values.put("periodo", turma.getPeriodo());
            values.put("cod_inst", turma.getCod_inst());

            long idTurmaInserido = db.insert("turma", null, values);

            return idTurmaInserido;

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public List<Turma> getListaTurma() {

        List<Turma> listaTurma = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM turma ORDER BY nome ASC;";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Turma turmaTemporario = null;
                do {

                    turmaTemporario = new Turma();

                    turmaTemporario.setCod_turma(cursor.getLong(0));
                    turmaTemporario.setNome(cursor.getString(1));
                    turmaTemporario.setPeriodo(cursor.getString(2));
                    turmaTemporario.setCod_inst(cursor.getLong(3));

                    listaTurma.add(turmaTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE turma", "Erro ao retornar lista de turmas");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return listaTurma;
    }

    public boolean excluirTurmaDAO(long idTurma) {
        SQLiteDatabase db = null;

        try {

            db = this.conexaoSQLite.getWritableDatabase();
            db.delete(
                    "turma",
                    "cod_turma = ?",
                    new String[]{String.valueOf(idTurma)}

            );
        } catch (Exception e) {
            Log.d("TURMA DAO", "Não foi possível deletar turma");
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return true;
    }

    public boolean atualizarTurmaDAO(Turma turma) {
        SQLiteDatabase db = null;

        try {
            db = this.conexaoSQLite.getWritableDatabase();

            System.out.println("RESULTADOOOOO " + turma.getCod_turma());

            ContentValues turmaAtributos = new ContentValues();
            turmaAtributos.put("nome", turma.getNome());
            turmaAtributos.put("periodo", turma.getPeriodo());
            turmaAtributos.put("cod_inst", turma.getCod_inst());

            int atualizou = db.update(
                    "turma",
                    turmaAtributos,
                    "cod_turma = ?",
                    new String[]{String.valueOf(turma.getCod_turma())}
            );

            if (atualizou > 0) {
                return true;
            }

        } catch (Exception e) {
            Log.d("turma DAO", "Não foi possível atualizar o turma");
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public List<Turma> getTurma(long id_turma) {

        List<Turma> listaTurma = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM turma WHERE cod_turma = " + id_turma + " ORDER BY nome ASC;";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Turma turmaTemporario = null;
                do {

                    turmaTemporario = new Turma();

                    turmaTemporario.setCod_turma(cursor.getLong(0));
                    turmaTemporario.setNome(cursor.getString(1));
                    turmaTemporario.setPeriodo(cursor.getString(2));
                    turmaTemporario.setCod_inst(cursor.getLong(3));

                    listaTurma.add(turmaTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE turma", "Erro ao retornar lista de turmaes");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return listaTurma;
    }

    public List<Turma> getListaOrdenadaTurmaDAO(long id_turma) {

        List<Turma> listaTurma = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM turma WHERE cod_turma = " + id_turma + " ORDER BY nome ASC;";

        try {
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                Turma turmaTemporario = null;
                do {

                    turmaTemporario = new Turma();

                    turmaTemporario.setCod_turma(cursor.getLong(0));
                    turmaTemporario.setNome(cursor.getString(1));
                    turmaTemporario.setPeriodo(cursor.getString(2));
                    turmaTemporario.setCod_inst(cursor.getLong(3));

                    listaTurma.add(turmaTemporario);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE turma", "Erro ao retornar lista de turmaes");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        SQLiteDatabase db2 = null;
        Cursor cursor2;
        String query2 = "SELECT * FROM turma ORDER BY nome ASC;";

        try {
            db2 = this.conexaoSQLite.getReadableDatabase();


            cursor2 = db2.rawQuery(query2, null);

            if (cursor2.moveToFirst()) {

                Turma turmaTemporario = null;
                do {

                    turmaTemporario = new Turma();

                    turmaTemporario.setCod_turma(cursor2.getLong(0));
                    turmaTemporario.setNome(cursor2.getString(1));
                    turmaTemporario.setPeriodo(cursor2.getString(2));
                    turmaTemporario.setCod_inst(cursor2.getLong(3));

                    listaTurma.add(turmaTemporario);

                } while (cursor2.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTA DE turma", "Erro ao retornar lista de turmaes");
            return null;
        } finally {
            if (db2 != null) {
                db2.close();
            }


            return listaTurma;
        }
    }

    public List<Turma> getListaTurmaFiltroDAO(String filtro){

        List<Turma> listaTurma = new ArrayList<>();

        SQLiteDatabase db = null;

        Cursor cursor;

        String query = "SELECT * FROM turma WHERE nome LIKE '%" + filtro + "%' ORDER BY nome ASC;";

        try{
            db = this.conexaoSQLite.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){

                Turma turmaTemporario = null;
                do {

                    turmaTemporario = new Turma();

                    turmaTemporario.setCod_turma(cursor.getLong(0));
                    turmaTemporario.setNome(cursor.getString(1));
                    turmaTemporario.setPeriodo(cursor.getString(2));
                    turmaTemporario.setCod_inst(cursor.getLong(3));

                    listaTurma.add(turmaTemporario);

                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("ERRO LISTA DE ATENDIDO", "Erro ao retornar lista de turmaes");
            return null;
        }finally {
            if (db != null) {
                db.close();
            }
        }

        return listaTurma;
    }
}
