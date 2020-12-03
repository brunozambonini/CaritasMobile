package com.example.caritaspiapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.model.Turma;

import java.util.List;

public class AdapterListaTurma extends BaseAdapter {

    private Context context;
    private List<Turma> turmaList;

    public AdapterListaTurma(Context context, List<Turma> turmaList) {
        this.context = context;
        this.turmaList = turmaList;
    }

    @Override
    public int getCount() {
        return this.turmaList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.turmaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removerturma(int position){
        this.turmaList.remove(position);
        notifyDataSetChanged();
    }

    public void refreshLista(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_turma, null);

        TextView nome = (TextView) v.findViewById(R.id.tvNome);
        TextView periodo = (TextView) v.findViewById(R.id.tvPeriodo);
        TextView instrutor = (TextView) v.findViewById(R.id.tvInstrutor);

        nome.setText(this.turmaList.get(position).getNome());
        periodo.setText(this.turmaList.get(position).getPeriodo());
        instrutor.setText(String.valueOf(this.turmaList.get(position).getCod_inst()));

        return v;
    }

    public void refresh(List<Turma> turmaList){
        this.turmaList.clear();
        this.turmaList = turmaList;
        this.notifyDataSetChanged();
    }
}
