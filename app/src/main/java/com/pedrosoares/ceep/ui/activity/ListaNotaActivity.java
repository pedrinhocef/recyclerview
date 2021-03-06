package com.pedrosoares.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.pedrosoares.ceep.R;
import com.pedrosoares.ceep.dao.CeepDAO;
import com.pedrosoares.ceep.dao.NotaDAO;
import com.pedrosoares.ceep.model.Nota;
import com.pedrosoares.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import com.pedrosoares.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import com.pedrosoares.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallBack;

import java.util.List;

import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.pedrosoares.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class ListaNotaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    ListaNotasAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITULO_APPBAR);
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
                vaiParaFormularioInsereNota();
            }
        });
    }

    private void vaiParaFormularioInsereNota() {
        Intent vaiParaFormulario =
                new Intent(ListaNotaActivity.this,
                        FormularioNotaActivity.class);
        startActivityForResult(vaiParaFormulario, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
//        NotaDAO dao = new NotaDAO();
//        return dao.todos();
          CeepDAO dao = new CeepDAO(this);
          return dao.buscaNotas();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ehResultadoInsereNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adiciona(notaRecebida);
            }
        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (ehPosicaoValida(posicaoRecebida)) {
//                    new NotaDAO().altera(posicaoRecebida, notaRecebida);
//                    mAdapter.altera(notaRecebida, posicaoRecebida);
                    new CeepDAO(this).altera(notaRecebida);
                    mAdapter.altera(notaRecebida,posicaoRecebida);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA
                && temNota(data);
    }

    private void adiciona(Nota nota) {
//        new NotaDAO().insere(nota);
//        mAdapter.adiciona(nota);
          new CeepDAO(this).insereNota(nota);
          mAdapter.adiciona(nota);
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehRequisicaoInsereNota(requestCode)
                && temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new NotaItemTouchHelperCallBack(mAdapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        mAdapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioAlteraNota(nota, posicao);
            }
        });
    }

    private void vaiParaFormularioAlteraNota(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotaActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }


}
