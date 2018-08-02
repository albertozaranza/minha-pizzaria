package com.agenciaalcateia.minhapizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.agenciaalcateia.minhapizzaria.adapter.CarrinhoAdapter;
import com.agenciaalcateia.minhapizzaria.config.ConfiguracaoFirebase;
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

public class NovoPedidoActivity extends AppCompatActivity {

    private Button buttonFinalizarPedido;
    private Button buttonAdicionarProduto;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<Carrinho> produtos;
    private Query query;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pedido);

        buttonFinalizarPedido = findViewById(R.id.btn_finalizaar_pedido);
        buttonAdicionarProduto = findViewById(R.id.btn_adicionar_produto);

        produtos = new ArrayList<>();

        listView = findViewById(R.id.lv_carrinho);

        arrayAdapter = new CarrinhoAdapter(
                getApplicationContext(),
                produtos
        );

        listView.setAdapter(arrayAdapter);

        buttonAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NovoPedidoActivity.this, CarrinhoActivity.class));
            }
        });

        buttonFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = ConfiguracaoFirebase.getFirebase().child("pedidos").child(firebaseUser.getUid());

                Pedido pedido = new Pedido();
                pedido.setProdutos("Pizza + Suco");
                pedido.setValor("15,00");

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                pedido.setData(dateFormat.format(date));

                databaseReference.push().setValue(pedido);
            }
        });

        query = ConfiguracaoFirebase.getFirebase().child("carrinho").child(firebaseUser.getUid());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();
                for(DataSnapshot _produtos : dataSnapshot.getChildren()){
                    Carrinho carrinho = _produtos.getValue(Carrinho.class);
                    produtos.add(carrinho);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        query.removeEventListener(valueEventListener);
    }
}
