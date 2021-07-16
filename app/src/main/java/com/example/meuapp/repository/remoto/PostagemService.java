package com.example.meuapp.repository.remoto;

import com.example.meuapp.models.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostagemService {

    @GET("posts")
    public Call<List<Postagem>> listarPosts();
}
