package com.example.meuapp.repository.local;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.meuapp.models.Usuario;

@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instancia;

    public static LocalDatabase getInstance(Application context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(context, LocalDatabase.class, "local_database").build();
        }
        return instancia;
    }

    public abstract UsuarioDAO usuarioDAO();
}
