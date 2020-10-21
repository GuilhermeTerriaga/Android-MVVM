package com.example.meuapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.meuapp.models.Usuario;
import com.example.meuapp.respositories.UsuarioRepository;

public class TelaCadastroViewModel extends ViewModel {

    private UsuarioRepository usuarioRepository;
    private MutableLiveData<Integer> errorMessageCode = new MutableLiveData<>();
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public MutableLiveData<Integer> getErrorMessageCode(){
        return errorMessageCode;
    }
    public TelaCadastroViewModel(){
        usuarioRepository = new UsuarioRepository();
    }

    public void cadastrar (Usuario usuario){
        Log.i(getClass().getName(), "Dentro da viewModel e cadastrar usuario de email");
        usuarioRepository.cadastrar(usuario);
    }

    public void validarCadastro (String email){
        if (!email.matches(emailPattern)){
            errorMessageCode.setValue(0);
        }
    }
}
