package com.example.projetogamelog.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.projetogamelog.Jogo;

@Database(entities = {Jogo.class},version=1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class JogoDatabase extends RoomDatabase {

    public abstract JogoDAO JogoDao();

    private static JogoDatabase instance;

    public static JogoDatabase getDatabase(final Context context){

        if(instance == null){

            synchronized (JogoDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context,JogoDatabase.class,"jogos.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}
