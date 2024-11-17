package com.fraga.timesgestao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author: Bruno Fraga
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sports.db";
    private static final int DATABASE_VERSION = 1;

    // Scripts de criação das tabelas
    private static final String CREATE_TABLE_TIME =
            "CREATE TABLE time (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "fundacao VARCHAR(10)" +
                    ");";

    private static final String CREATE_TABLE_JOGADOR =
            "CREATE TABLE jogador (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "posicao TEXT, " +
                    "id_time INTEGER, " +
                    "FOREIGN KEY(id_time) REFERENCES time(id)" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TIME);
        db.execSQL(CREATE_TABLE_JOGADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS time");
        db.execSQL("DROP TABLE IF EXISTS jogador");
        onCreate(db);
    }
}
