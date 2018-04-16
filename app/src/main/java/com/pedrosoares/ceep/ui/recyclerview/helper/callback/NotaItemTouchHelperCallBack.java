package com.pedrosoares.ceep.ui.recyclerview.helper.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.pedrosoares.ceep.dao.NotaDAO;
import com.pedrosoares.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private ListaNotasAdapter mAdapter;

    public NotaItemTouchHelperCallBack(ListaNotasAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(0, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDeslizada = viewHolder.getAdapterPosition();
        new NotaDAO().remove(posicaoDeslizada);
        mAdapter.remove(posicaoDeslizada);
    }
}
