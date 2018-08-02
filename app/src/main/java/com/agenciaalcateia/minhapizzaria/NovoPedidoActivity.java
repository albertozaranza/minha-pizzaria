package com.agenciaalcateia.minhapizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.agenciaalcateia.minhapizzaria.config.ConfiguracaoFirebase;
import com.agenciaalcateia.minhapizzaria.model.Pedido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NovoPedidoActivity extends AppCompatActivity {

    private Button buttonFinalizarPedido;
    private Button buttonAdicionarProduto;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pedido);

        buttonFinalizarPedido = findViewById(R.id.btn_finalizaar_pedido);
        buttonAdicionarProduto = findViewById(R.id.btn_adicionar_produto);

        buttonAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NovoPedidoActivity.this, CardapioActivity.class));
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
    }
}
