package com.example.meuapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.meuapp.models.Usuario;
import com.example.meuapp.repository.local.LocalDatabase;
import com.example.meuapp.repository.local.UsuarioDAO;

public class UsuarioRepository {

    private LocalDatabase database;
    private UsuarioDAO usuarioDAO;

    public UsuarioRepository(Application context) {
        database = LocalDatabase.getInstance(context);
        usuarioDAO = database.usuarioDAO();
    }

    public Boolean verificaEmailExistente(String email) {
        Log.d(getClass().getName(), "Dentro do verificaEmailExistente");
        Boolean existe = usuarioDAO.verificarEmailExistente(email);
        return existe;
    }

    public Usuario cadastrar(Usuario usuario) {
        Log.d(getClass().getName(), " dentro do cadastrar");

        if (!verificaEmailExistente(usuario.getEmail())) {
            Log.d(getClass().getName(), " cadastrou o usuario email " + usuario.getEmail());
            long id = usuarioDAO.inserir(usuario);
            usuario.setId(id);
            return usuario;
        }

        Log.d(getClass().getName(), " Não cadastrou o usuario email " + usuario.getEmail());
        return null;
    }

    public void atualizar(Usuario usuario) {
        Log.d(getClass().getName(), "dentro do atualizar usuário");
        usuarioDAO.atualizar(usuario);
    }

    public LiveData<Usuario> pesquisaPorIdLive(long id) {
        return usuarioDAO.consultarPorIdLive(id);
    }

    public LiveData<Usuario> buscaPorEmailESenha(Usuario usuario) {
        return usuarioDAO.buscaPorEmailESenha(usuario.getEmail(), usuario.getSenha());
    }
}
