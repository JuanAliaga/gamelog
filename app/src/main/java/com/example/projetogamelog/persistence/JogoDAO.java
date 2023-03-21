package com.example.projetogamelog.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetogamelog.Jogo;
import com.example.projetogamelog.PlataformaJogo;
import com.example.projetogamelog.StatusJogo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JogoDAO {

    public static final String TABELA  = "JOGOS";
    public static final String ID = "ID";
    public static final String NOME = "NOME";
    public static final String PLATAFORMA = "PLATAFORMA";
    public static final String STATUS = "STATUS";
    public static final String DATAINICIO = "DATAINICIO";
    public static final String DATAFIM = "DATAFIM";
    public static final String CONQUISTAS100 = "CONQUISTAS100";

    public ArrayList<Jogo> jogosList;
    private JogoDatabase conexaoDB;

    public JogoDAO(JogoDatabase jogoDatabase){
        conexaoDB = jogoDatabase;
        jogosList = new ArrayList<>();
    }

    public void createTable(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TABELA + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    NOME  + " TEXT NOT NULL, "  +
                    PLATAFORMA  + " TEXT NOT NULL, "  +
                    STATUS  + " TEXT NOT NULL, "  +
                    DATAINICIO  + " TEXT, "  +
                    DATAFIM  + " TEXT, "  +
                    CONQUISTAS100  + " INTEGER)";

        sqLiteDatabase.execSQL(sql);
    }

    public void deleteTable(SQLiteDatabase sqLiteDatabase) {

        String sql = "DROP TABLE IF EXISTS "+ TABELA;

        sqLiteDatabase.execSQL(sql);
    }

    public boolean insertJogo(Jogo jogo){
        ContentValues values = new ContentValues();

        values.put(NOME,jogo.getNome());
        values.put(PLATAFORMA,String.valueOf(jogo.getPlataforma()));
        values.put(STATUS,String.valueOf(jogo.getStatus()));
        values.put(DATAINICIO,String.valueOf(jogo.getDataInicio()));
        values.put(DATAFIM,String.valueOf(jogo.getDataFim()));
        values.put(CONQUISTAS100, (jogo.isConcluidoTodasConquistas() ? "TRUE" : "FALSE"));

        long id = conexaoDB.getWritableDatabase().insert(TABELA,
                null,
                values);

        jogo.setId(id);

        jogosList.add(jogo);

        return true;
    }

    public void loadList(){
        String sql = "SELECT * FROM " + TABELA;
        jogosList.clear();

        Cursor cursor =conexaoDB.getReadableDatabase().rawQuery(sql,null);

        int columnId = cursor.getColumnIndex(ID);
        int columnNome = cursor.getColumnIndex(NOME);
        int columnPlataforma = cursor.getColumnIndex(PLATAFORMA);
        int columnStatus = cursor.getColumnIndex(STATUS);
        int columnDataInicio = cursor.getColumnIndex(DATAINICIO);
        int columnDataFim = cursor.getColumnIndex(DATAFIM);
        int columnConquistas100 = cursor.getColumnIndex(CONQUISTAS100);

        while(cursor.moveToNext()){

            Jogo jogo = new Jogo(cursor.getString(columnNome), PlataformaJogo.valueOf( cursor.getString(columnPlataforma)));
            jogo.setStatus(StatusJogo.valueOf(cursor.getString(columnStatus)));
            jogo.setConcluidoTodasConquistas(Boolean.valueOf(cursor.getString(columnConquistas100)));
            jogo.setDataInicio(null);
            jogo.setDataFim(null);
            jogo.setId(cursor.getLong(columnId));

            jogosList.add(jogo);
        }
        cursor.close();
    }

    public boolean updateJogo(Jogo jogo){

        ContentValues values = new ContentValues();

        values.put(NOME,jogo.getNome());
        values.put(PLATAFORMA,String.valueOf(jogo.getPlataforma()));
        values.put(STATUS,String.valueOf(jogo.getStatus()));
        values.put(DATAINICIO,String.valueOf(jogo.getDataInicio()));
        values.put(DATAFIM,String.valueOf(jogo.getDataFim()));
        values.put(CONQUISTAS100, (jogo.isConcluidoTodasConquistas() ? "TRUE" : "FALSE"));

        String[] args = {String.valueOf(jogo.getId())};
        conexaoDB.getWritableDatabase().update(TABELA,
                values,ID + " = ?",
                args);
        return true;
    }

    public boolean deleteJogo(Jogo jogo) {

        String[] args = {String.valueOf(jogo.getId())};
        conexaoDB.getWritableDatabase().delete(TABELA,
             ID + " = ?",
                args);

        jogosList.remove(jogo);
        return true;
    }
}
