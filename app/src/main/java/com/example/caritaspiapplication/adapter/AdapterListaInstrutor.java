package com.example.caritaspiapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.model.Instrutor;

import java.util.List;

public class AdapterListaInstrutor extends BaseAdapter {

    private Context context;
    private List<Instrutor> instrutorList; // -----

    public AdapterListaInstrutor(Context context, List<Instrutor> instrutorList) { // ----- // -----
        this.context = context;
        this.instrutorList = instrutorList;
    }

    @Override
    public int getCount() {
        return this.instrutorList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.instrutorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removerInstrutor(int position){ // -----
        this.instrutorList.remove(position);
        notifyDataSetChanged();
    }

    public void refreshLista(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_instrutor, null);

        TextView nome = (TextView) v.findViewById(R.id.tvNome);
        TextView rg = (TextView) v.findViewById(R.id.tvRg);

        nome.setText(this.instrutorList.get(position).getNome());
        rg.setText(this.instrutorList.get(position).getRg());

        return v;
    }

    public void refresh(List<Instrutor> instrutorList){ // -----
        this.instrutorList.clear();
        this.instrutorList = instrutorList;
        this.notifyDataSetChanged();
    }
}
