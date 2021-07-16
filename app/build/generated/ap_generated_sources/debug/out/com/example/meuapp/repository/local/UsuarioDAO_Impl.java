package com.example.meuapp.repository.local;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.meuapp.models.Usuario;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UsuarioDAO_Impl implements UsuarioDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Usuario> __insertionAdapterOfUsuario;

  private final EntityDeletionOrUpdateAdapter<Usuario> __updateAdapterOfUsuario;

  public UsuarioDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsuario = new EntityInsertionAdapter<Usuario>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Usuario` (`id`,`email`,`senha`,`sincronizado`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuario value) {
        stmt.bindLong(1, value.getId());
        if (value.getEmail() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEmail());
        }
        if (value.getSenha() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSenha());
        }
        final Integer _tmp;
        _tmp = value.isSincronizado() == null ? null : (value.isSincronizado() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
      }
    };
    this.__updateAdapterOfUsuario = new EntityDeletionOrUpdateAdapter<Usuario>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Usuario` SET `id` = ?,`email` = ?,`senha` = ?,`sincronizado` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuario value) {
        stmt.bindLong(1, value.getId());
        if (value.getEmail() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEmail());
        }
        if (value.getSenha() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSenha());
        }
        final Integer _tmp;
        _tmp = value.isSincronizado() == null ? null : (value.isSincronizado() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public long inserir(final Usuario usuario) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUsuario.insertAndReturnId(usuario);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void atualizar(final Usuario usuario) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUsuario.handle(usuario);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Boolean verificarEmailExistente(final String email) {
    final String _sql = "SELECT count(id)>0 FROM usuario WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Boolean _result;
      if(_cursor.moveToFirst()) {
        final Integer _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(0);
        }
        _result = _tmp == null ? null : _tmp != 0;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Usuario> consultarPorIdLive(final long id) {
    final String _sql = "SELECT  * FROM usuario WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[]{"usuario"}, false, new Callable<Usuario>() {
      @Override
      public Usuario call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfSenha = CursorUtil.getColumnIndexOrThrow(_cursor, "senha");
          final int _cursorIndexOfSincronizado = CursorUtil.getColumnIndexOrThrow(_cursor, "sincronizado");
          final Usuario _result;
          if(_cursor.moveToFirst()) {
            _result = new Usuario();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _result.setEmail(_tmpEmail);
            final String _tmpSenha;
            _tmpSenha = _cursor.getString(_cursorIndexOfSenha);
            _result.setSenha(_tmpSenha);
            final Boolean _tmpSincronizado;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfSincronizado)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfSincronizado);
            }
            _tmpSincronizado = _tmp == null ? null : _tmp != 0;
            _result.setSincronizado(_tmpSincronizado);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Usuario> buscaPorEmailESenha(final String email, final String senha) {
    final String _sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (senha == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, senha);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"usuario"}, false, new Callable<Usuario>() {
      @Override
      public Usuario call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfSenha = CursorUtil.getColumnIndexOrThrow(_cursor, "senha");
          final int _cursorIndexOfSincronizado = CursorUtil.getColumnIndexOrThrow(_cursor, "sincronizado");
          final Usuario _result;
          if(_cursor.moveToFirst()) {
            _result = new Usuario();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _result.setEmail(_tmpEmail);
            final String _tmpSenha;
            _tmpSenha = _cursor.getString(_cursorIndexOfSenha);
            _result.setSenha(_tmpSenha);
            final Boolean _tmpSincronizado;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfSincronizado)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfSincronizado);
            }
            _tmpSincronizado = _tmp == null ? null : _tmp != 0;
            _result.setSincronizado(_tmpSincronizado);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
