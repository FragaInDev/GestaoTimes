package com.fraga.timesgestao.controller;

import android.content.Context;

import com.fraga.timesgestao.persistence.JogadorDao;

import java.util.List;

/**
 * @author: Bruno Fraga
 */

public class JogadorController {

    private JogadorDao jogadorDao;

    public JogadorController(Context context) {
        jogadorDao = new JogadorDao(context);
    }

    // Método para adicionar um jogador
    public boolean adicionarJogador(String nome, String posicao, int idTime) {
        try {
            jogadorDao.open();
            long result = jogadorDao.inserirJogador(nome, posicao, idTime);
            jogadorDao.close();
            return result != -1; // Retorna true se o ID do jogador for válido
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar jogadores de um time específico
    public List<String> obterJogadoresPorTime(int idTime) {
        try {
            jogadorDao.open();
            List<String> listaJogadores = jogadorDao.listarJogadoresPorTime(idTime);
            jogadorDao.close();
            return listaJogadores;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para atualizar informações de um jogador
    public boolean atualizarJogador(int id, String nome, String posicao, int idTime) {
        try {
            jogadorDao.open();
            int result = jogadorDao.atualizarJogador(id, nome, posicao, idTime);
            jogadorDao.close();
            return result > 0; // Retorna true se pelo menos uma linha foi afetada
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para deletar um jogador
    public boolean deletarJogador(int id) {
        try {
            jogadorDao.open();
            int result = jogadorDao.deletarJogador(id);
            jogadorDao.close();
            return result > 0; // Retorna true se pelo menos uma linha foi removida
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
