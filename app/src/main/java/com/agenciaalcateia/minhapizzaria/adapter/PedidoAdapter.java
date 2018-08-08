package com.agenciaalcateia.minhapizzaria.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agenciaalcateia.minhapizzaria.R;
import com.agenciaalcateia.minhapizzaria.model.Pedido;

import java.util.List;


public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {

    private List<Pedido> pedidos;
    private Context context;
    private String valor;

    public PedidoAdapter(@NonNull Context c, @NonNull List<Pedido> objects ) {
        this.pedidos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pedidos, parent,false);

        return new PedidoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        valor = "R$" + pedido.getValor();

        holder.textViewProdutos.setText(pedido.getProdutos());
        holder.textViewValor.setText(valor);
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewProdutos;
        TextView textViewValor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewProdutos = itemView.findViewById(R.id.tv_produtos);
            textViewValor = itemView.findViewById(R.id.tv_valor);

        }
    }
}
