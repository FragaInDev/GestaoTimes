package com.fraga.timesgestao.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.fraga.timesgestao.R;
import com.fraga.timesgestao.controller.TimeController;

import java.util.List;

/**
 * @author: Bruno Fraga
 */

public class TimeFragment extends Fragment {

    private EditText edtNomeTime, edtFundacaoTime;
    private Button btnAdicionarTime, btnListarTimes, btnNavegarParaJogadores;
    private ListView listViewTimes;
    private TimeController timeController;

    public TimeFragment() {
        // Construtor padrão
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        // Inicializar componentes
        edtNomeTime = view.findViewById(R.id.edtNomeTime);
        edtFundacaoTime = view.findViewById(R.id.edtFundacaoTime);
        btnAdicionarTime = view.findViewById(R.id.btnAdicionarTime);
        btnListarTimes = view.findViewById(R.id.btnListarTimes);
        btnNavegarParaJogadores = view.findViewById(R.id.btnNavegarParaJogadores);
        listViewTimes = view.findViewById(R.id.listViewTimes);

        timeController = new TimeController(requireContext());

        // Configurar botão para adicionar time
        btnAdicionarTime.setOnClickListener(v -> {
            String nome = edtNomeTime.getText().toString();
            String fundacao = edtFundacaoTime.getText().toString();

            if (nome.isEmpty() || fundacao.isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.toast_preencher_campos), Toast.LENGTH_SHORT).show();
            } else {
                boolean sucesso = timeController.adicionarTime(nome, fundacao);
                if (sucesso) {
                    Toast.makeText(getContext(), getString(R.string.toast_time_adicionado), Toast.LENGTH_SHORT).show();
                    edtNomeTime.setText("");
                    edtFundacaoTime.setText("");
                } else {
                    Toast.makeText(getContext(), getString(R.string.toast_erro_adicionar_time), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar botão para listar times
        btnListarTimes.setOnClickListener(v -> {
            List<String> listaTimes = timeController.obterTimes();
            if (listaTimes == null || listaTimes.isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.toast_nenhum_time_encontrado), Toast.LENGTH_SHORT).show();
            } else {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaTimes);
                listViewTimes.setAdapter(adapter);
            }
        });

        // Configurar botão de navegação
        btnNavegarParaJogadores.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_timeFragment_to_jogadorFragment)
        );

        return view;
    }
}
