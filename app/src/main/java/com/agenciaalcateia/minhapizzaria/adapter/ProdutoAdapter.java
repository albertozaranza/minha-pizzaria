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
    private String precos;
    private String valor;
    private String nome;

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
            TextView textViewValor = view.findViewById(R.id.tv_valor);
            TextView textViewPreco = view.findViewById(R.id.tv_preco);

            Produto produto = produtos.get(position);

            nome = produto.getNome() + " " + produto.getSabor();
            precos = "P: R$" + produto.getPrecoP() + " / M: R$" + produto.getPrecoM() + " / G: R$" + produto.getPrecoG();
            valor = "R$" + produto.getValor();

            textViewNome.setText(nome);

            if(produto.getTipo().equals("1")){
                textViewPreco.setText(precos);
                textViewValor.setVisibility(View.GONE);
            } else if (produto.getTipo().equals("2")){
                textViewValor.setText(valor);
                textViewPreco.setVisibility(View.GONE);
            }

        }

        return view;
    }
}
