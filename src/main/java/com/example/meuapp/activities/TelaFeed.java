package com.example.meuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.meuapp.R;
import com.example.meuapp.models.Postagem;
import com.example.meuapp.models.Usuario;
import com.example.meuapp.viewmodels.TelaFeedViewModel;

import java.util.List;

public class TelaFeed extends AppCompatActivity {

    private TelaFeedViewModel viewModel;
    private Observer<Usuario> observerUsuarioLogado = new Observer<Usuario>() {
        @Override
        public void onChanged(Usuario usuario) {
            if (usuario == null) {
                Log.d(getClass().getName(), " dentro do onChanged com usuario == null");
                Intent intencao = new Intent();
                intencao.setClass(getApplicationContext(), AtividadeLogin.class);
                startActivity(intencao);
            } else {
                if (usuario.isSincronizado() == true) {
                    //apaga mesagem de sincronizar
                    Log.d(getClass().getName(), " dentro do onChanged com usuario já sincronizado");
                    TextView aviso = findViewById(R.id.avisoEmailNaoValidado);
                    aviso.setVisibility(TextView.GONE);
                } else {
                    //exibe mensagem de sincronizar
                    Log.d(getClass().getName(), " dentro do onChanged com usuario não sincronizado o email é:" + usuario.getEmail());
                    TextView aviso = findViewById(R.id.avisoEmailNaoValidado);
                    aviso.setVisibility(TextView.VISIBLE);
                }
            }
        }
    };

    private Observer<List<Postagem>> observadorPosts = new Observer<List<Postagem>>() {

        @Override
        public void onChanged(List<Postagem> listaPosts) {
            if (listaPosts != null) {
                for (Postagem postagem : listaPosts) {
                LinearLayout layout = findViewById(R.id.postsLinearLayout);
                TextView textViewPost = new TextView(getApplicationContext());

                textViewPost.setText(postagem.getTitulo() + "\r\n" + postagem.getTexto() + "\r\n\r\n" );

                layout.addView(textViewPost);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_feed);

        ViewModelProvider vmp = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()));
        viewModel = vmp.get(TelaFeedViewModel.class);

        long id = getIntent().getLongExtra("usuarioId", 0);
        Log.i(getClass().getName(), "Dentro do oncreate, id igual a " + id);

        viewModel.CarregarUsuarioLogado(id);
        viewModel.getUserLogado().observe(this, observerUsuarioLogado);
        viewModel.getPosts().observe(this, observadorPosts);

    }

    public void AvisoEmailNaoValidadoClick(View view) {
        Log.i(getClass().getName(), "Dentro do email não validado click");

        viewModel.sincronizarUsuario();
    }

}