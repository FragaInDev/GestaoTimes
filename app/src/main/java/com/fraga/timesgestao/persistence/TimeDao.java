package com.fraga.timesgestao.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fraga.timesgestao.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bruno Fraga
 */

public class TimeDao {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public TimeDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Método para inserir um novo time
    public long inserirTime(String nome, String fundacao) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("fundacao", fundacao);

        return db.insert("time", null, values);
    }

    // Método para buscar todos os times
    public List<String> listarTimes() {
        List<String> listaTimes = new ArrayList<>();
        Cursor cursor = db.query("time", new String[]{"id", "nome", "fundacao"},
                null, null, null, null, "nome ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String time = "ID: " + cursor.getInt(0) +
                        " | Nome: " + cursor.getString(1) +
                        " | Fundação: " + cursor.getString(2);
                listaTimes.add(time);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listaTimes;
    }

    // Método para atualizar um time existente
    public int atualizarTime(int id, String nome, String fundacao) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("fundacao", fundacao);

        return db.update("time", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Método para deletar um time
    public int deletarTime(int id) {
        return db.delete("time", "id = ?", new String[]{String.valueOf(id)});
    }
}
