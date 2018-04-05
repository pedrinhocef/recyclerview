package com.pedrosoares.ceep.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pedrosoares.ceep.R;
import com.pedrosoares.ceep.dao.NotaDAO;
import com.pedrosoares.ceep.model.Nota;
import com.pedrosoares.ceep.ui.adapter.ListaNotasAdapter;

import java.util.List;

public class activity_lista_notas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        ListView listaNotas = findViewById(R.id.listView);

        NotaDAO dao = new NotaDAO();

        dao.insere(new Nota("Ptrimeira nota", "Primeira Descrição"));
        List<Nota> todasNotas = dao.todos();

        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
    }
}
