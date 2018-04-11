package com.pedrosoares.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pedrosoares.ceep.R;
import com.pedrosoares.ceep.dao.NotaDAO;
import com.pedrosoares.ceep.model.Nota;
import com.pedrosoares.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import com.pedrosoares.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.List;

import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

public class ListaNotaActivity extends AppCompatActivity {

    ListaNotasAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        botaoInsereNota();
    }

    private void botaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioNota();
            }
        });
    }

    private void vaiParaFormularioNota() {
        Intent vaiParaFormulario =
                new Intent(ListaNotaActivity.this, FormularioNotaActivity.class);
        startActivityForResult(vaiParaFormulario, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO();
        for (int i = 0; i < 10; i++) {
            dao.insere(new Nota("Titulo" + (i+1),
                    "Descricao" + (i+1)));
        }
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ehResultadoComNota(requestCode, resultCode, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
            adiciona(notaRecebida);
        }

        if (requestCode == 2 && resultCode == CODIGO_RESULTADO_NOTA_CRIADA
                && temNota(data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            Toast.makeText(this, notaRecebida.getTitulo()
                    , Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adiciona(Nota nota) {
        new NotaDAO().insere(nota);
        mAdapter.adiciona(nota);
    }

    private boolean ehResultadoComNota(int requestCode, int resultCode, Intent data) {
        return ehRequisicaoInsereNota(requestCode) &&
                ehCodigoResultadoNota(resultCode) && temNota(data);
    }

    private boolean temNota(Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehCodigoResultadoNota(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean ehRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        mAdapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                Intent abreFormularioComNota = new Intent(ListaNotaActivity.this,
                        FormularioNotaActivity.class);
                abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
                startActivityForResult(abreFormularioComNota,2);
            }
        });
    }


}
