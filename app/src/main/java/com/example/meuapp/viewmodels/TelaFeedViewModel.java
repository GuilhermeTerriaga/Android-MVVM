package com.example.meuapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.meuapp.models.Postagem;
import com.example.meuapp.models.Usuario;
import com.example.meuapp.repository.PostagemRepository;
import com.example.meuapp.repository.UsuarioRepository;
import com.example.meuapp.util.ThreadManager;

import java.util.List;

public class TelaFeedViewModel extends AndroidViewModel {

    private LiveData<Usuario> userLogado;
    private UsuarioRepository usuarioRepository;
    private LiveData<List<Postagem>> postagens;
    private PostagemRepository postagemRepository;

    public TelaFeedViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        postagemRepository = new PostagemRepository();
    }

    public LiveData<List<Postagem>> getPosts() {
        return postagemRepository.getListaPostagem();
    }

    public void CarregarUsuarioLogado(long id) {
        userLogado = usuarioRepository.pesquisaPorIdLive(id);
    }

    public LiveData<Usuario> getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(LiveData<Usuario> userLogado) {
        this.userLogado = userLogado;
    }

    public void sincronizarUsuario() {
        Usuario usuario = userLogado.getValue();
        usuario.setSincronizado(true);
        ThreadManager.getExecutor().execute(() -> {
            usuarioRepository.atualizar(usuario);
        });
    }
}
