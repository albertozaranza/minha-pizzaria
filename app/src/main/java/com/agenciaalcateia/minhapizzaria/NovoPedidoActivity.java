package com.agenciaalcateia.minhapizzaria;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agenciaalcateia.minhapizzaria.adapter.CarrinhoAdapter;
import com.agenciaalcateia.minhapizzaria.config.ConfiguracaoFirebase;
import com.agenciaalcateia.minhapizzaria.helper.Base64Custom;
import com.agenciaalcateia.minhapizzaria.helper.RecyclerItemClickListener;
import com.agenciaalcateia.minhapizzaria.model.Carrinho;
import com.agenciaalcateia.minhapizzaria.model.Pedido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NovoPedidoActivity extends AppCompatActivity {

    private Button buttonFinalizarPedido, buttonAdicionarProduto;
    private TextView textViewTotal;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private CarrinhoAdapter carrinhoAdapter;
    private List<Carrinho> produtos = new ArrayList<>();
    private Query query;
    private ValueEventListener valueEventListenerProdutos;
    private String produtosCarrinho = "";
    private Double valorCarrinho = 0.0;
    private Double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pedido);

        buttonFinalizarPedido = findViewById(R.id.btn_finalizaar_pedido);
        buttonAdicionarProduto = findViewById(R.id.btn_adicionar_produto);
        textViewTotal = findViewById(R.id.tv_valor);

        recyclerView = findViewById(R.id.rv_cardapio);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        carrinhoAdapter = new CarrinhoAdapter(
                getApplicationContext(),
                produtos
        );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(carrinhoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        removerItem(recyclerView.findViewHolderForAdapterPosition(position));
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

        query = ConfiguracaoFirebase.getFirebase().child("carrinho").child(Base64Custom.codificarBase64(firebaseUser.getEmail()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total = 0.0;
                for(DataSnapshot _produtos : dataSnapshot.getChildren()){
                    Carrinho carrinho = _produtos.getValue(Carrinho.class);
                    total += Double.parseDouble(carrinho.getValor().replace(",", "."));
                }
                String valor = total.toString().replace(".", ",");
                if(valor.endsWith(",0")){
                    valor = valor.replace(",0", ",00");
                }
                valor = "R$" + valor;
                textViewTotal.setText(valor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NovoPedidoActivity.this, CarrinhoActivity.class));
            }
        });

        buttonFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = ConfiguracaoFirebase.getFirebase().child("pedidos").child(Base64Custom.codificarBase64(firebaseUser.getEmail()));

                for(int i=0; i<produtos.size(); i++){
                    Carrinho carrinho = produtos.get(i);
                    if(i == produtos.size()-1){
                        adicionarProduto(carrinho.getProdudo(), carrinho.getValor(), true);
                    } else {
                        adicionarProduto(carrinho.getProdudo(), carrinho.getValor(), false);
                    }

                }

                Pedido pedido = new Pedido();

                pedido.setProdutos(produtosCarrinho);

                String valor = valorCarrinho.toString().replace(".", ",");

                if(valor.endsWith(",0")){
                    valor = valor.replace(",0", ",00");
                }

                pedido.setValor(valor);

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                pedido.setData(dateFormat.format(date));
                databaseReference.push().setValue(pedido);

                databaseReference = ConfiguracaoFirebase.getFirebase().child("carrinho").child(Base64Custom.codificarBase64(firebaseUser.getEmail()));
                databaseReference.removeValue();

                produtos.clear();
                carrinhoAdapter.notifyDataSetChanged();
                finish();
            }
        });

    }

    public void listarProdutos(){

        valueEventListenerProdutos = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();
                for(DataSnapshot _produtos : dataSnapshot.getChildren()){
                    Carrinho carrinho = _produtos.getValue(Carrinho.class);
                    carrinho.setKey(_produtos.getKey());
                    produtos.add(carrinho);
                }
                carrinhoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        listarProdutos();
    }

    @Override
    public void onStop() {
        super.onStop();
        query.removeEventListener(valueEventListenerProdutos);
    }

    public void removerItem(final RecyclerView.ViewHolder viewHolder){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Excluir");
        alertDialog.setMessage("Excluir produto?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                Carrinho carrinho = produtos.get(position);
                databaseReference = ConfiguracaoFirebase.getFirebase().child("carrinho").child(Base64Custom.codificarBase64(firebaseUser.getEmail()));
                databaseReference.child(carrinho.getKey()).removeValue();
                carrinhoAdapter.notifyItemRemoved(position);
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(NovoPedidoActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void adicionarProduto(String produto, String valor, boolean finalLista){
        if(finalLista){
            produtosCarrinho += produto;
        } else {
            produtosCarrinho += produto.concat(" + ");
        }
        valorCarrinho += Double.parseDouble(valor.replace(",", "."));
    }

}
