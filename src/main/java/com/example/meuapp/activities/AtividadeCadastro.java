package com.example.meuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.meuapp.R;
import com.example.meuapp.models.Usuario;
import com.example.meuapp.viewmodels.TelaCadastroViewModel;

public class AtividadeCadastro extends AppCompatActivity {

    private TelaCadastroViewModel viewModel;


    private Observer<Integer> observadorMensagemErro = new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            Log.d(getClass().getName(), "Dentro do observador Mensagem de erro, houve mudança no live data");
            TextView erro = findViewById(R.id.erroLogin);
            if (integer != null) {
                erro.setText(getResources().getText(integer));
                erro.setVisibility(TextView.VISIBLE);
            } else {

                erro.setText("");
                erro.setVisibility(TextView.INVISIBLE);
            }
        }
    };
    private Observer<Usuario> observadorUsuarioCadastrado = new Observer<Usuario>() {
        @Override
        public void onChanged(Usuario usuario) {
            if (usuario != null) {
                Log.d(getClass().getName(), "Dentro do observadorUsuarioCadastrado, houve mudança no live data, o id é " + usuario.getId());

                Intent intencao = new Intent();
                intencao.setClass(getApplicationContext(), TelaFeed.class);
                intencao.putExtra("usuarioId", usuario.getId());
                startActivity(intencao);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_cadastro);
        getSupportActionBar().hide();

        ViewModelProvider vmp = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()));
        viewModel = vmp.get(TelaCadastroViewModel.class);
        Log.i(getClass().getName(), "Asoociou a view mmodel" + viewModel + "a atividade" + this);

        viewModel.getErrorMessageCode().observe(this, observadorMensagemErro);
        viewModel.getUsuarioCadastrado().observe(this, observadorUsuarioCadastrado);

    }

    public void botaoCadastrarClick(final View view) {
        Log.i(getClass().getName(), "Dentro do cadastrar");
        EditText campoEmail = findViewById(R.id.email);
        EditText campoSenha = findViewById(R.id.senha);
        final TextView errorMsg = (TextView) findViewById(R.id.erroLogin);

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();


        Usuario usuario = new Usuario(email, senha);
        viewModel.validarCadastro(usuario);
        viewModel.cadastrar(usuario);

    }
}