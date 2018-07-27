package com.agenciaalcateia.minhapizzaria.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agenciaalcateia.minhapizzaria.R;
import com.agenciaalcateia.minhapizzaria.model.Produto;

import java.util.ArrayList;


public class ProdutoAdapter extends ArrayAdapter<Produto> {

    private ArrayList<Produto> produtos;
    private Context context;

    public ProdutoAdapter(@NonNull Context c, @NonNull ArrayList<Produto> objects) {
        super(c, 0, objects);
        this.produtos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(produtos!=null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_produtos, parent, false);

            TextView textViewNome = view.findViewById(R.id.tv_nome);

            Produto produto = produtos.get(position);

            textViewNome.setText(produto.getNome() + " " + produto.getSabor());

        }

        return view;
    }
}
