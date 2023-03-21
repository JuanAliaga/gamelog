package com.example.projetogamelog.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JogoDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "jogos.db";
    private static final int DB_VERSION = 1;
    private static JogoDatabase instance;

    private Context context;
    public JogoDAO jogoDAO;

    public JogoDatabase(Context context) {
        super(context,DB_NAME,null,DB_VERSION);

        this.context = context;

        jogoDAO = new JogoDAO(this);
    }

    public static JogoDatabase getInstance(Context context){
        if(instance== null){
            instance = new JogoDatabase(context);
        }
        return instance;
    };



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        jogoDAO.createTable(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int version, int newVersion) {
        jogoDAO.deleteTable(sqLiteDatabase);

        onCreate(sqLiteDatabase);
    }
}
