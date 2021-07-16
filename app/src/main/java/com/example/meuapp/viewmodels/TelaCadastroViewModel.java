package com.example.meuapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.meuapp.R;
import com.example.meuapp.models.Usuario;
import com.example.meuapp.repository.PostagemRepository;
import com.example.meuapp.repository.UsuarioRepository;
import com.example.meuapp.util.ThreadManager;

public class TelaCadastroViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;
    private MutableLiveData<Integer> errorMessageCode = new MutableLiveData<>();
    private MutableLiveData<Usuario> usuarioCadastrado = new MutableLiveData<>();



    public TelaCadastroViewModel(Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
    }

    public void cadastrar(final Usuario usuario) {
        Log.i(getClass().getName(), "Dentro da viewModel e cadastrar usuario de email");
        ThreadManager.getExecutor().execute(() -> {
            Usuario u = usuarioRepository.cadastrar(usuario);
            usuarioCadastrado.postValue(u);
        });
    }

    public void validarCadastro(Usuario usuario) {
        ThreadManager.getExecutor().execute(() -> {
            if (usuarioRepository.verificaEmailExistente(usuario.getEmail())) {
                errorMessageCode.postValue(R.string.erroEmailIndisponivel);
            } else {
                errorMessageCode.postValue(null);
            }
        });
    }

    public MutableLiveData<Integer> getErrorMessageCode() {
        return errorMessageCode;
    }

    public MutableLiveData<Usuario> getUsuarioCadastrado() {
        return usuarioCadastrado;
    }
}
