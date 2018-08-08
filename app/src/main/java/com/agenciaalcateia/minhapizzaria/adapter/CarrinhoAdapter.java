package com.agenciaalcateia.minhapizzaria.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agenciaalcateia.minhapizzaria.R;
import com.agenciaalcateia.minhapizzaria.model.Carrinho;

import java.util.List;


public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.MyViewHolder> {

    private List<Carrinho> produto;
    private Context context;
    private String _produto;
    private String quantidade;
    private String valor;

    public CarrinhoAdapter(@NonNull Context c, @NonNull List<Carrinho> objects) {
        this.produto = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_carrinho, parent,false);

        return new CarrinhoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Carrinho carrinho = produto.get(position);

        _produto = carrinho.getProdudo();
        quantidade = "Quantidade: " + carrinho.getQuantidade();
        valor = "Valor: R$" + carrinho.getValor();

        holder.textViewNome.setText(_produto);
        holder.textViewQuantidade.setText(quantidade);
        holder.textViewValor.setText(valor);

    }

    @Override
    public int getItemCount() {
        return produto.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNome;
        TextView textViewQuantidade;
        TextView textViewValor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.tv_produto);
            textViewQuantidade = itemView.findViewById(R.id.tv_quantidade);
            textViewValor = itemView.findViewById(R.id.tv_valor);

        }
    }
}
