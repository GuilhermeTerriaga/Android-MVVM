package com.example.meuapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.meuapp.R;

public class AtividadePrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);
        getSupportActionBar().hide();
    }

    public void botaoCadastroClick(View view){
        Intent intent = new Intent();
        intent.setClass(this.getApplicationContext(), AtividadeCadastro.class);
        startActivity(intent);

    }
    public void botaoLoginClick(View view){
        Intent intent = new Intent();
        intent.setClass(this.getApplicationContext(), AtividadeLogin.class);
        startActivity(intent);

    }

}