package com.agenciaalcateia.minhapizzaria;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.agenciaalcateia.minhapizzaria.adapter.ProdutoAdapter;
import com.agenciaalcateia.minhapizzaria.config.ConfiguracaoFirebase;
import com.agenciaalcateia.minhapizzaria.helper.Base64Custom;
import com.agenciaalcateia.minhapizzaria.helper.RecyclerItemClickListener;
import com.agenciaalcateia.minhapizzaria.model.Carrinho;
import com.agenciaalcateia.minhapizzaria.model.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarrinhoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;
    private ArrayList<Produto> cardapio = new ArrayList<>();
    private Query query;
    private ValueEventListener valueEventListenerProdutos;
    private String nome;
    private String valor;
    private Double valorFinal;
    private String quantidade;
    private int tamanhoPizza;
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        cardapio = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_cardapio);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        produtoAdapter = new ProdutoAdapter(
                getApplicationContext(),
                cardapio
        );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(produtoAdapter);

        query = ConfiguracaoFirebase.getFirebase().child("produtos").orderByChild("tipo");

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        final Produto produto = cardapio.get(position);

                        final Carrinho carrinho = new Carrinho();

                        if(produto.getTipo().equals("1")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CarrinhoActivity.this);

                            View mView = getLayoutInflater().inflate(R.layout.dialog_pizza, null);

                            final RadioGroup radioGroup = mView.findViewById(R.id.radioGroupTamanhoPizza);

                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                                    switch(checkedId) {
                                        case R.id.rb_p:
                                            tamanhoPizza = 1;
                                            valor = produto.getPrecoP();
                                            break;
                                        case R.id.rb_m:
                                            tamanhoPizza = 2;
                                            valor = produto.getPrecoM();
                                            break;
                                        case R.id.rb_g:
                                            tamanhoPizza = 3;
                                            valor = produto.getPrecoG();
                                            break;
                                    }
                                }
                            });

                            final TextInputEditText editTextQuantidade = mView.findViewById(R.id.text_quantidade);

                            nome = produto.getNome() + " " + produto.getSabor();

                            builder.setView(mView)
                                    .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if(radioGroup.getCheckedRadioButtonId() == -1 || editTextQuantidade.getText().toString().isEmpty()){
                                                Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                quantidade = editTextQuantidade.getText().toString();

                                                switch (tamanhoPizza){
                                                    case 1:
                                                        nome += " (P)";
                                                        break;
                                                    case 2:
                                                        nome += " (M)";
                                                        break;
                                                    case 3:
                                                        nome += " (G)";
                                                        break;
                                                }

                                                valorFinal = Double.parseDouble(valor.replace(",", ".")) * Double.parseDouble(quantidade);

                                                valor = valorFinal.toString().replace(".", ",");

                                                if(valor.endsWith(",0")){
                                                    valor = valor.replace(",0", ",00");
                                                }

                                                carrinho.setProdudo(nome);
                                                carrinho.setQuantidade(quantidade);
                                                carrinho.setValor(valor);

                                                databaseReference.child("carrinho").child(Base64Custom.codificarBase64(firebaseUser.getEmail())).push().setValue(carrinho);
                                                finish();
                                            }
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else if(produto.getTipo().equals("2")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CarrinhoActivity.this);

                            View mView = getLayoutInflater().inflate(R.layout.dialog_produto, null);

                            final TextInputEditText editTextQuantidade = mView.findViewById(R.id.text_quantidade);

                            nome = produto.getNome();

                            builder.setView(mView)
                                    .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            quantidade = editTextQuantidade.getText().toString();
                                            valor = produto.getValor();

                                            if(editTextQuantidade.getText().toString().isEmpty()){
                                                Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                quantidade = editTextQuantidade.getText().toString();

                                                valorFinal = Double.parseDouble(valor.replace(",", ".")) * Double.parseDouble(quantidade);

                                                valor = valorFinal.toString().replace(".", ",");

                                                if(valor.endsWith(",0")){
                                                    valor = valor.replace(",0", ",00");
                                                }

                                                carrinho.setProdudo(nome);
                                                carrinho.setQuantidade(quantidade);
                                                carrinho.setValor(valor);

                                                databaseReference.child("carrinho").child(Base64Custom.codificarBase64(firebaseUser.getEmail())).push().setValue(carrinho);
                                                finish();
                                            }


                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

    }

    public void listarProdutos(){

        valueEventListenerProdutos = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cardapio.clear();
                for(DataSnapshot produtos : dataSnapshot.getChildren()){
                    Produto produto = produtos.getValue(Produto.class);
                    cardapio.add(produto);
                }
                produtoAdapter.notifyDataSetChanged();
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

}
