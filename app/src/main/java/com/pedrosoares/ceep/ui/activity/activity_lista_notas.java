package com.pedrosoares.ceep.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        notasDeExemplo();
        configuraRecyclerView();
    }

    private void notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeira ", "Descrição pequena "),
                new Nota("Segunda ", "Segunda nota bem maior que a primeira"));
        todasNotas = dao.todos();

    }

    private void configuraRecyclerView() {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(listaNotas);
    }

    private void configuraAdapter(RecyclerView listaNotas) {
        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
    }
}
