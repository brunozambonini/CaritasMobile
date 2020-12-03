package com.example.caritaspiapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.model.Matricula;

import java.util.List;

public class AdapterListaMatricula extends BaseAdapter {

    private Context context;
    private List<Matricula> matriculaList;

    public AdapterListaMatricula(Context context, List<Matricula> matriculaList) {
        this.context = context;
        this.matriculaList = matriculaList;
    }

    @Override
    public int getCount() {
        return this.matriculaList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.matriculaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removerMatricula(int position){
        this.matriculaList.remove(position);
        notifyDataSetChanged();
    }

    public void refreshLista(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_matricula, null);

        TextView nome_turma = (TextView) v.findViewById(R.id.tvNomeTurma);
        TextView nome_atend = (TextView) v.findViewById(R.id.tvNomeAtend);
        TextView cod_atend = (TextView) v.findViewById(R.id.tvCod_atend);

        nome_turma.setText(this.matriculaList.get(position).getNome_turma());
        nome_atend.setText(this.matriculaList.get(position).getNome_atend());
        cod_atend.setText(String.valueOf(this.matriculaList.get(position).getCod_atend()));

        return v;
    }

    public void refresh(List<Matricula> matriculaList){
        this.matriculaList.clear();
        this.matriculaList = matriculaList;
        this.notifyDataSetChanged();
    }
}
