package com.pedrosoares.ceep.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrosoares.ceep.R;
import com.pedrosoares.ceep.dao.NotaDAO;
import com.pedrosoares.ceep.model.Nota;
import com.pedrosoares.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import java.util.List;

public class activity_lista_notas extends AppCompatActivity {

    List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);

        NotaDAO dao = new NotaDAO();

        for (int i = 1; i <=1000; i ++){
            dao.insere(new Nota("titulo " + i, "Descrição " + i));
            todasNotas = dao.todos();
        }


        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(layoutManager);
    }
}
