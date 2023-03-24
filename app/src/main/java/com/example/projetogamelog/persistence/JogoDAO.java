package com.example.projetogamelog.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projetogamelog.Jogo;
import com.example.projetogamelog.PlataformaJogo;
import com.example.projetogamelog.StatusJogo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Dao
public interface JogoDAO {

    @Insert
    long insert (Jogo jogo);

    @Delete
    void delete (Jogo jogo);

    @Update
    void update (Jogo jogo);

    @Query("SELECT * FROM jogos" )
    List<Jogo> jogosAll();

}
