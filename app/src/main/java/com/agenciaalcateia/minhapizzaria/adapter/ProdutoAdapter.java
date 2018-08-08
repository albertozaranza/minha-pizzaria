package com.agenciaalcateia.minhapizzaria.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agenciaalcateia.minhapizzaria.R;
import com.agenciaalcateia.minhapizzaria.model.Produto;

import java.util.List;


public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {

    private List<Produto> produtos;
    private Context context;
    private String precos;
    private String valor;
    private String nome;

    public ProdutoAdapter(@NonNull Context c, @NonNull List<Produto> objects) {
        this.produtos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_produtos, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        nome = produto.getNome() + " " + produto.getSabor();

        holder.textViewNome.setText(nome);

        if(produto.getTipo().equals("1")){
            precos = "P: R$" + produto.getPrecoP() + " / M: R$" + produto.getPrecoM() + " / G: R$" + produto.getPrecoG();
            holder.textViewPreco.setText(precos);
            holder.textViewValor.setVisibility(View.GONE);
        } else if (produto.getTipo().equals("2")){
            valor = "R$" + produto.getValor();
            holder.textViewValor.setText(valor);
            holder.textViewPreco.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNome;
        TextView textViewValor;
        TextView textViewPreco;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.tv_nome);
            textViewValor = itemView.findViewById(R.id.tv_valor);
            textViewPreco = itemView.findViewById(R.id.tv_preco);


        }
    }
}
