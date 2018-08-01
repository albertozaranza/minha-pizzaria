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
import com.agenciaalcateia.minhapizzaria.model.Pedido;

import java.util.ArrayList;


public class PedidoAdapter extends ArrayAdapter<Pedido> {

    private ArrayList<Pedido> pedidos;
    private Context context;
    private String valor;

    public PedidoAdapter(@NonNull Context c, @NonNull ArrayList<Pedido> objects ) {
        super(c, 0, objects);
        this.pedidos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(pedidos!=null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_pedidos, parent, false);

            TextView textViewProdutos = view.findViewById(R.id.tv_produtos);
            TextView textViewValor = view.findViewById(R.id.tv_valor);

            Pedido pedido = pedidos.get(position);

            valor = "R$" + pedido.getValor();

            textViewProdutos.setText(pedido.getProdutos());
            textViewValor.setText(valor);
        }

        return view;
    }
}
