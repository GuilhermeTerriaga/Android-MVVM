package com.example.meuapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meuapp.R;
import com.example.meuapp.models.Usuario;
import com.example.meuapp.viewmodels.TelaCadastroViewModel;

import java.util.Observable;

public class AtividadeCadastro extends AppCompatActivity {

    private TelaCadastroViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_cadastro);
        getSupportActionBar().hide();
        ViewModelProvider vmp = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory());
        viewModel = vmp.get(TelaCadastroViewModel.class);
        Log.i(getClass().getName(), "Asoociou a view mmodel" + viewModel + "a atividade" + this);
    }

    public void botaoCadastrarClick(final View view){
        Log.i(getClass().getName(), "Dentro do cadastrar");
        EditText campoEmail = findViewById(R.id.email);
        EditText campoSenha = findViewById(R.id.senha);
        final TextView errorMsg = (TextView) findViewById(R.id.erro);

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();


        Usuario usuario = new Usuario(email, senha);
        Log.i(getClass().getName(), "Prestes a fazer a magica acontecer");
        viewModel.getErrorMessageCode().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.i(getClass().getName(), "Fez a magica acontecer");
                errorMsg.setVisibility(View.VISIBLE);
            }
        });
        viewModel.cadastrar(usuario);

    }

}