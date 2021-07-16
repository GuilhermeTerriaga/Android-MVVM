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
import com.example.meuapp.viewmodels.AtividadeLoginViewModel;

public class AtividadeLogin extends AppCompatActivity {
    private AtividadeLoginViewModel viewModel;
    private Observer<Usuario> observadorUsuarioLogado = new Observer<Usuario>() {

        @Override
        public void onChanged(Usuario usuario) {
            if (usuario == null) {
                Log.i(getClass().getName(), "não está logado, ususário e null ");
                TextView mensagemErro = findViewById(R.id.erroLogin);
                mensagemErro.setText(getResources().getString(R.string.credenciais_incorretas));
                mensagemErro.setVisibility(TextView.VISIBLE);
            } else {
                Log.i(getClass().getName(), "não logado, ususário vai para o feed, com id = " + usuario.getId());
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
        setContentView(R.layout.activity_atividade_login);
        getSupportActionBar().hide();

        ViewModelProvider vmp = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()));
        viewModel = vmp.get(AtividadeLoginViewModel.class);
    }

    public void BotaoEntrarClique(View view) {
        EditText campoEmail = findViewById(R.id.editTextTextEmailAddress);
        EditText campoSenha = findViewById(R.id.editTextTextPassword);

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        Usuario usuario = new Usuario(email, senha);

        viewModel.autenticarUsuario(usuario).observe(this, observadorUsuarioLogado);
    }
}