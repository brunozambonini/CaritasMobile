package com.example.caritaspiapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.model.Atendido;

import java.util.List;

public class AdapterListaAtendido extends BaseAdapter {

    private Context context;
    private List<Atendido> atendidoList;

    public AdapterListaAtendido(Context context, List<Atendido> atendidoList) {
        this.context = context;
        this.atendidoList = atendidoList;
    }

    @Override
    public int getCount() {
        return this.atendidoList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.atendidoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeratendido(int position){
        this.atendidoList.remove(position);
        notifyDataSetChanged();
    }

    public void refreshLista(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_atendido, null);

        TextView nome = (TextView) v.findViewById(R.id.tvNome);
        TextView nis = (TextView) v.findViewById(R.id.tvNis);
        TextView ra = (TextView) v.findViewById(R.id.tvRa);
        TextView data_nasci = (TextView) v.findViewById(R.id.tvDataNasci);
        TextView rua = (TextView) v.findViewById(R.id.tvRua);
        TextView bairro = (TextView) v.findViewById(R.id.tvBairro);
        TextView n_casa = (TextView) v.findViewById(R.id.tvN_casa);
        TextView responsavel = (TextView) v.findViewById(R.id.tvResponsavel);

        nome.setText(this.atendidoList.get(position).getNome());
        nis.setText(this.atendidoList.get(position).getNis());
        ra.setText(this.atendidoList.get(position).getRa());
        data_nasci.setText(this.atendidoList.get(position).getData_nasci());
        rua.setText(this.atendidoList.get(position).getRua());
        bairro.setText(this.atendidoList.get(position).getBairro());
        n_casa.setText(this.atendidoList.get(position).getN_casa());
        responsavel.setText(String.valueOf(this.atendidoList.get(position).getCod_res()));

        System.out.println(this.atendidoList.get(position).getCod_res());

        return v;
    }

    public void refresh(List<Atendido> atendidoList){
        this.atendidoList.clear();
        this.atendidoList = atendidoList;
        this.notifyDataSetChanged();
    }

}
