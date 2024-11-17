package com.fraga.timesgestao.controller;

import android.content.Context;

import com.fraga.timesgestao.persistence.TimeDao;

import java.util.List;

/**
 * @author: Bruno Fraga
 */

public class TimeController {

    private TimeDao timeDao;

    public TimeController(Context context) {
        timeDao = new TimeDao(context);
    }

    // Método para adicionar um time
    public boolean adicionarTime(String nome, String fundacao) {
        try {
            timeDao.open();
            long result = timeDao.inserirTime(nome, fundacao);
            timeDao.close();
            return result != -1; // Retorna true se o ID do time for válido
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar todos os times
    public List<String> obterTimes() {
        try {
            timeDao.open();
            List<String> listaTimes = timeDao.listarTimes();
            timeDao.close();
            return listaTimes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para atualizar informações de um time
    public boolean atualizarTime(int id, String nome, String fundacao) {
        try {
            timeDao.open();
            int result = timeDao.atualizarTime(id, nome, fundacao);
            timeDao.close();
            return result > 0; // Retorna true se pelo menos uma linha foi afetada
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para deletar um time
    public boolean deletarTime(int id) {
        try {
            timeDao.open();
            int result = timeDao.deletarTime(id);
            timeDao.close();
            return result > 0; // Retorna true se pelo menos uma linha foi removida
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
