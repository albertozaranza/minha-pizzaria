package com.agenciaalcateia.minhapizzaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.agenciaalcateia.minhapizzaria.config.ConfiguracaoFirebase;
import com.agenciaalcateia.minhapizzaria.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class NovoPedidoActivity extends AppCompatActivity {

    private Button buttonAdicionarPedido;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pedido);

        buttonAdicionarPedido = findViewById(R.id.btn_adicionar_pedido);

        buttonAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = ConfiguracaoFirebase.getFirebase().child("pedidos");
                Usuario usuario = new Usuario();
                usuario.setNome(firebaseUser.getDisplayName());
                usuario.setEmail(firebaseUser.getEmail());
                databaseReference.push().setValue(usuario);
            }
        });
    }
}
