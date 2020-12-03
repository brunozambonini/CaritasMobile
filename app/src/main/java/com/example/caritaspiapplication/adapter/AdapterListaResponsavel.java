package com.example.caritaspiapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caritaspiapplication.R;
import com.example.caritaspiapplication.model.Responsavel;

import java.util.List;

public class AdapterListaResponsavel extends BaseAdapter {

    private Context context;
    private List<Responsavel> responsavelList;

    public AdapterListaResponsavel(Context context, List<Responsavel> responsavelList) {
        this.context = context;
        this.responsavelList = responsavelList;
    }

    @Override
    public int getCount() {
        return this.responsavelList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.responsavelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removerResponsavel(int position){
        this.responsavelList.remove(position);
        notifyDataSetChanged();
    }

    public void refreshLista(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_responsavel, null);

        TextView nome = (TextView) v.findViewById(R.id.tvNome);
        TextView nis = (TextView) v.findViewById(R.id.tvNis);
        TextView rg = (TextView) v.findViewById(R.id.tvRg);
        TextView cpf = (TextView) v.findViewById(R.id.tvCpf);
        TextView data_nasci = (TextView) v.findViewById(R.id.tvDataNasci);
        TextView rua = (TextView) v.findViewById(R.id.tvRua);
        TextView bairro = (TextView) v.findViewById(R.id.tvBairro);
        TextView n_casa = (TextView) v.findViewById(R.id.tvN_casa);
        TextView telefone = (TextView) v.findViewById(R.id.tvTelefone);

        nome.setText(this.responsavelList.get(position).getNome());
        nis.setText(this.responsavelList.get(position).getNis());
        rg.setText(this.responsavelList.get(position).getRg());
        cpf.setText(this.responsavelList.get(position).getCpf());
        data_nasci.setText(this.responsavelList.get(position).getData_nasci());
        rua.setText(this.responsavelList.get(position).getRua());
        bairro.setText(this.responsavelList.get(position).getBairro());
        n_casa.setText(this.responsavelList.get(position).getN_casa());
        telefone.setText(this.responsavelList.get(position).getTelefone());

        return v;
    }

    public void refresh(List<Responsavel> responsavelList){
        this.responsavelList.clear();
        this.responsavelList = responsavelList;
        this.notifyDataSetChanged();
    }
}
