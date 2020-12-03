package com.example.caritaspiapplication.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexaoSQLite extends SQLiteOpenHelper {

    private static ConexaoSQLite INSTANCIA_CONEXAO;
    private static final int VERSAO_DB = 1;
    private static final String NOME_DB = "caritas_app";

    public ConexaoSQLite(@Nullable Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
    }

    //Instancia a conexão caso nao esteja conectado
    public static ConexaoSQLite getInstance(Context context){
        if(INSTANCIA_CONEXAO == null){
            INSTANCIA_CONEXAO = new ConexaoSQLite(context);
        }
        return INSTANCIA_CONEXAO;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTabelaResponsavel =
                "create table if not exists responsavel" +
                        "( cod_res INTEGER  PRIMARY KEY," +
                        "nome varchar(30) not null," +
                        "rg varchar (10) not null," +
                        "nis varchar (11) not null," +
                        "cpf varchar (11) not null," +
                        "data_nasci varchar not null," +
                        "rua varchar (20) not null," +
                        "bairro varchar (20) not null," +
                        "n_casa varchar (4) not null," +
                        "telefone varchar (13)" +
                        ")";

        db.execSQL(sqlTabelaResponsavel);


        String sqlTabelaAtendido =
                "create table if not exists atendido" +
                        "( cod_atend INTEGER  PRIMARY KEY," +
                        "nome varchar(30) not null," +
                        "ra varchar (10) not null," +
                        "nis varchar (11) not null," +
                        "data_nasci varchar not null," +
                        "rua varchar (20) not null," +
                        "bairro varchar (20) not null," +
                        "n_casa varchar (4) not null," +
                        "matriculas varchar(3), " +
                        "cod_res INTEGER REFERENCES responsavel ON DELETE CASCADE" +
                        ")";

        db.execSQL(sqlTabelaAtendido);

        String sqlTabelaInstrutor =
                "create table if not exists instrutor" +
                        "( cod_inst INTEGER  PRIMARY KEY," +
                        "nome varchar(30) not null," +
                        "rg varchar (10) not null" +
                        ")";
        db.execSQL(sqlTabelaInstrutor);

        String sqlTabelaTurma =
                "create table if not exists turma" +
                        "( cod_turma INTEGER  PRIMARY KEY," +
                        "nome varchar(30) not null," +
                        "periodo varchar(10) not null," +
                        "cod_inst INTEGER REFERENCES instrutor ON DELETE CASCADE" +
                        ")";
        db.execSQL(sqlTabelaTurma);

        String sqlTabelaMatricula =
                "create table if not exists matricula" +
                        "( id INTEGER  PRIMARY KEY," +
                        "cod_turma INTEGER REFERENCES turma ON DELETE CASCADE," +
                        "cod_atend INTEGER REFERENCES atendido ON DELETE CASCADE," +
                        "nome_turma varchar(50)," +
                        "nome_atend varchar(50)" +
                        ")";
        db.execSQL(sqlTabelaMatricula);

        String sqlTabelaUsuario =
                "create table if not exists usuario" +
                        "(user varchar(30)  PRIMARY KEY," +
                        "senha varchar(30)" +
                        ")";
        db.execSQL(sqlTabelaUsuario);

        String sqlTriggerUpdateMatricula =
                ("create trigger if not exists aumenta_matricula " +
                        "AFTER INSERT ON matricula " +
                        "BEGIN " +
                        "UPDATE atendido SET matriculas = (Select COUNT(*) FROM matricula WHERE cod_atend = NEW.cod_atend) WHERE cod_atend = NEW.cod_atend; "+
                        "END;");

        db.execSQL(sqlTriggerUpdateMatricula);

        String sqlTriggerDeleteMatricula =
                ("create trigger if not exists diminui_matricula " +
                        "AFTER DELETE ON matricula " +
                        "BEGIN " +
                        //"UPDATE atendido SET matriculas = 0 WHERE cod_atend = 1; "+
                        "UPDATE atendido SET matriculas = (Select COUNT(*) FROM matricula WHERE cod_atend = OLD.cod_atend) WHERE cod_atend = OLD.cod_atend; "+
                        "END;");

        db.execSQL(sqlTriggerDeleteMatricula);


        String sqlTriggerCreateAdmin =
                ("INSERT INTO usuario(user, senha) " +
                        "VALUES('admin', 'admin123');");

        db.execSQL(sqlTriggerCreateAdmin);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
