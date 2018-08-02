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
import com.agenciaalcateia.minhapizzaria.model.Carrinho;
import com.agenciaalcateia.minhapizzaria.model.Produto;

import java.util.ArrayList;


public class CarrinhoAdapter extends ArrayAdapter<Carrinho> {

    private ArrayList<Carrinho> produto;
    private Context context;
    private String _produto;
    private String quantidade;
    private String valor;

    public CarrinhoAdapter(@NonNull Context c, @NonNull ArrayList<Carrinho> objects) {
        super(c, 0, objects);
        this.produto = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(produto!=null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_carrinho, parent, false);

            TextView textViewNome = view.findViewById(R.id.tv_produto);
            TextView textViewQuantidade = view.findViewById(R.id.tv_quantidade);
            TextView textViewValor = view.findViewById(R.id.tv_valor);

            Carrinho carrinho = produto.get(position);

            _produto = carrinho.getProdudo();
            quantidade = "Quantidade: " + carrinho.getQuantidade();
            valor = "Valor: R$" + carrinho.getValor();

            textViewNome.setText(_produto);
            textViewQuantidade.setText(quantidade);
            textViewValor.setText(valor);

        }

        return view;
    }
}
