package com.example.meuapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meuapp.models.Postagem;
import com.example.meuapp.repository.remoto.PostagemService;
import com.example.meuapp.repository.remoto.RetrofitConfig;
import com.example.meuapp.util.ThreadManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PostagemRepository {
    private PostagemService postagemService;
    private PostagemDAO postagemDAO;
    private MutableLiveData<List<Postagem>> listaPostagem = new MutableLiveData<>();


    public PostagemRepository(){
        this.postagemService = new RetrofitConfig().getPostagemService();
    }
   public MutableLiveData<List<Postagem>> getListaPostagem(){
        atualizarPosts();
        return listaPostagem;
   }

    private void atualizarPosts() {
        ThreadManager.getExecutor().execute(()->{

            try {
                Call<List<Postagem>> chamadaRemota = postagemService.listarPosts();
                Response<List<Postagem>> respostaRemota = chamadaRemota.execute();
               if ( respostaRemota.isSuccessful()){
                   List<Postagem> listPostsAPI = respostaRemota.body();
                   listaPostagem.postValue(listPostsAPI);
               }else{
                   Log.e(getClass().getName(), "Erro na resposta da requisição com API");
               }

            } catch (IOException e) {
                Log.e(getClass().getName(), "Conexão com a API falhou: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
