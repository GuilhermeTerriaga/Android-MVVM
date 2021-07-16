package com.example.meuapp.repository.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.meuapp.models.Usuario;

@Dao
public interface UsuarioDAO {

    @Insert
    public long inserir(Usuario usuario);

    @Query("SELECT count(id)>0 FROM usuario WHERE email = :email")
    public Boolean verificarEmailExistente(String email);

    @Update
    public void atualizar(Usuario usuario);

    @Query("SELECT  * FROM usuario WHERE id = :id")
    public LiveData<Usuario> consultarPorIdLive(long id);

    @Query("SELECT * FROM usuario WHERE email = :email AND senha = :senha")
    public LiveData<Usuario> buscaPorEmailESenha(String email, String senha);
}
