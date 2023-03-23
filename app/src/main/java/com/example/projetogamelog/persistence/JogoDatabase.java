package com.example.projetogamelog.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.projetogamelog.Jogo;

@Database(entities = {Jogo.class},version=1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class JogoDatabase extends RoomDatabase {

    public abstract JogoDAO JogoDao();

    public static JogoDatabase getDatabase(Context context){
        return Room.databaseBuilder(context,JogoDatabase.class,"jogos.db")
                .allowMainThreadQueries()
                .build();
    }
    /*private static final String DB_NAME = "jogos.db";
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
    }*/
}
