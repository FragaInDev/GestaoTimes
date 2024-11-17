package com.fraga.timesgestao.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.fraga.timesgestao.R;
import com.fraga.timesgestao.controller.JogadorController;
import com.fraga.timesgestao.controller.TimeController;

import java.util.List;

/**
 * @author: Bruno Fraga
 */

public class JogadorFragment extends Fragment {

    private EditText edtNomeJogador, edtPosicaoJogador;
    private Spinner spinnerTimes;
    private Button btnAdicionarJogador, btnListarJogadores, btnNavegarParaTimes;
    private ListView listViewJogadores;
    private JogadorController jogadorController;
    private TimeController timeController;

    public JogadorFragment() {
        // Construtor padrão
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jogador, container, false);

        // Inicializar componentes
        edtNomeJogador = view.findViewById(R.id.edtNomeJogador);
        edtPosicaoJogador = view.findViewById(R.id.edtPosicaoJogador);
        spinnerTimes = view.findViewById(R.id.spinnerTimes);
        btnAdicionarJogador = view.findViewById(R.id.btnAdicionarJogador);
        btnListarJogadores = view.findViewById(R.id.btnListarJogadores);
        btnNavegarParaTimes = view.findViewById(R.id.btnNavegarParaTimes);
        listViewJogadores = view.findViewById(R.id.listViewJogadores);

        jogadorController = new JogadorController(requireContext());
        timeController = new TimeController(requireContext());

        carregarTimesNoSpinner();

        // Configurar botão para adicionar jogador
        btnAdicionarJogador.setOnClickListener(v -> {
            String nome = edtNomeJogador.getText().toString();
            String posicao = edtPosicaoJogador.getText().toString();
            int idTime = obterIdTimeSelecionado();

            if (nome.isEmpty() || posicao.isEmpty() || idTime == -1) {
                Toast.makeText(getContext(), getString(R.string.toast_preencher_campos), Toast.LENGTH_SHORT).show();
            } else {
                boolean sucesso = jogadorController.adicionarJogador(nome, posicao, idTime);
                if (sucesso) {
                    Toast.makeText(getContext(), getString(R.string.toast_jogador_adicionado), Toast.LENGTH_SHORT).show();
                    edtNomeJogador.setText("");
                    edtPosicaoJogador.setText("");
                } else {
                    Toast.makeText(getContext(), getString(R.string.toast_erro_adicionar_jogador), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar botão para listar jogadores
        btnListarJogadores.setOnClickListener(v -> {
            int idTime = obterIdTimeSelecionado();
            if (idTime == -1) {
                Toast.makeText(getContext(), getString(R.string.toast_selecione_time), Toast.LENGTH_SHORT).show();
            } else {
                List<String> listaJogadores = jogadorController.obterJogadoresPorTime(idTime);
                if (listaJogadores == null || listaJogadores.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.toast_nenhum_jogador_encontrado), Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaJogadores);
                    listViewJogadores.setAdapter(adapter);
                }
            }
        });

        // Configurar botão de navegação
        btnNavegarParaTimes.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_jogadorFragment_to_timeFragment)
        );

        return view;
    }

    private void carregarTimesNoSpinner() {
        List<String> listaTimes = timeController.obterTimes();
        if (listaTimes != null && !listaTimes.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listaTimes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTimes.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), getString(R.string.toast_nenhum_time_encontrado), Toast.LENGTH_SHORT).show();
        }
    }

    private int obterIdTimeSelecionado() {
        if (spinnerTimes.getSelectedItem() != null) {
            String timeSelecionado = (String) spinnerTimes.getSelectedItem();
            String[] partes = timeSelecionado.split("ID: ");
            if (partes.length > 1) {
                return Integer.parseInt(partes[1].split(" ")[0]);
            }
        }
        return -1;
    }
}
