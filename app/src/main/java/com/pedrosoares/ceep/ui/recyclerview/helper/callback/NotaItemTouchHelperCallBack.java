package com.pedrosoares.ceep.ui.recyclerview.helper.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.pedrosoares.ceep.dao.CeepDAO;
import com.pedrosoares.ceep.dao.NotaDAO;
import com.pedrosoares.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter mAdapter;

    public NotaItemTouchHelperCallBack(ListaNotasAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocaNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        new NotaDAO().troca(posicaoInicial, posicaoFinal);
        mAdapter.troca(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDeslizada = viewHolder.getAdapterPosition();
        removeNota(posicaoDeslizada);
    }

    private void removeNota(int posicaoDeslizada) {
        new NotaDAO().remove(posicaoDeslizada);
        mAdapter.remove(posicaoDeslizada);
    }
}
