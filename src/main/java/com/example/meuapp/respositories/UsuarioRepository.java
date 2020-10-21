package com.example.meuapp.respositories;

import android.util.Log;

import com.example.meuapp.models.Usuario;

public class UsuarioRepository {

    public Boolean verificaEmailExistente(String email){

        Boolean existe = "a@email.com".equals(email);
        return existe;
    }
    public Usuario cadastrar(Usuario usuario){
        Log.d(getClass().getName(), " dentro do cadastrar");

        if(!verificaEmailExistente(usuario.getEmail())){
            Log.d(getClass().getName(), " cadastrou o usuario email " + usuario.getEmail());
            usuario.setId(42);
            return usuario;
        }

        Log.d(getClass().getName(), " Não cadastrou o usuario email " + usuario.getEmail());
        return null;
    }
}
