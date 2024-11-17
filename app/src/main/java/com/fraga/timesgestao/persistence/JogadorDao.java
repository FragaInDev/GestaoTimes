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

public class JogadorDao {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public JogadorDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Método para inserir um novo jogador
    public long inserirJogador(String nome, String posicao, int idTime) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("posicao", posicao);
        values.put("id_time", idTime);

        return db.insert("jogador", null, values);
    }

    // Método para buscar todos os jogadores de um time específico
    public List<String> listarJogadoresPorTime(int idTime) {
        List<String> listaJogadores = new ArrayList<>();
        Cursor cursor = db.query("jogador", new String[]{"id", "nome", "posicao"},
                "id_time = ?", new String[]{String.valueOf(idTime)}, null, null, "nome ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String jogador = "ID: " + cursor.getInt(0) +
                        " | Nome: " + cursor.getString(1) +
                        " | Posição: " + cursor.getString(2);
                listaJogadores.add(jogador);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return listaJogadores;
    }

    // Método para atualizar um jogador
    public int atualizarJogador(int id, String nome, String posicao, int idTime) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("posicao", posicao);
        values.put("id_time", idTime);

        return db.update("jogador", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Método para deletar um jogador
    public int deletarJogador(int id) {
        return db.delete("jogador", "id = ?", new String[]{String.valueOf(id)});
    }
}
