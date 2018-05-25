package com.pedrosoares.ceep.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pedrosoares.ceep.model.Nota;

import java.util.ArrayList;
import java.util.List;

public class CeepDAO extends SQLiteOpenHelper {

    public CeepDAO(Context context){
        super(context, "notas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Notas (id INTEGER PRIMARY KEY, " +
                "titulo TEXT NOT NULL, " +
                "descricao TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insereNota(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pedaDadosNotas(nota);

        db.insert("Notas", null, dados);

    }

    @NonNull
    private ContentValues pedaDadosNotas(Nota nota) {
        ContentValues dados = new ContentValues();
        dados.put("titulo", nota.getTitulo());
        dados.put("descricao", nota.getDescricao());
        return dados;
    }

    public List<Nota> buscaNotas() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Notas;";
        Cursor c = db.rawQuery(sql, null);

        List<Nota> notas = new ArrayList<>();

        while(c.moveToNext()){
            Nota nota = new Nota();

            nota.setId(c.getLong(c.getColumnIndex("id")));
            nota.setTitulo(c.getString(c.getColumnIndex("titulo")));
            nota.setDescricao(c.getString(c.getColumnIndex("descricao")));

            notas.add(nota);

        }
        c.close();
        return notas;
    }

    public void deleta(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(nota.getId())};

        db.delete("Notas","id = ?", params);
    }

    public void altera(Nota nota) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pedaDadosNotas(nota);
        String[] params = {String.valueOf(nota.getId())};

        db.update("Notas", dados, "id = ?", params);
    }
}
