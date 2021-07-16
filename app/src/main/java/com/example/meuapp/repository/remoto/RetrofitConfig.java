package com.example.meuapp.repository.remoto;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.212:8080/WEB-INF/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public PostagemService getPostagemService(){
        return this.retrofit.create(PostagemService.class);
    }

}
