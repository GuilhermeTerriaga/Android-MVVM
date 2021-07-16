package com.example.meuapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.meuapp.models.Usuario;
import com.example.meuapp.repository.UsuarioRepository;

public class AtividadeLoginViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;


    public AtividadeLoginViewModel(@NonNull Application application) {
        super(application);

        usuarioRepository = new UsuarioRepository(application);

    }


    public LiveData<Usuario> autenticarUsuario(Usuario usuario) {
        Log.i(getClass().getName(), "dentro do autenticarUsuario" + usuario.getEmail() + " " + usuario.getSenha());
        LiveData<Usuario> usuarioLogado = usuarioRepository.buscaPorEmailESenha(usuario);
        return usuarioLogado;
    }
}
