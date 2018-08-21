package com.agenciaalcateia.minhapizzaria.loginusuario;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.agenciaalcateia.minhapizzaria.R;
import com.agenciaalcateia.minhapizzaria.config.ConfiguracaoFirebase;
import com.agenciaalcateia.minhapizzaria.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private TextInputEditText editTextNome, editTextEmail, editTextSenha, editTextConfirmarSenha;
    private Button buttonCadastrar;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth;
    private String erroExcecao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        editTextNome = findViewById(R.id.text_nome);
        editTextEmail = findViewById(R.id.text_email);
        editTextSenha = findViewById(R.id.text_senha);
        editTextConfirmarSenha = findViewById(R.id.text_confirmacao_senha);

        buttonCadastrar = findViewById(R.id.btn_cadastrar);

        if(editTextNome.getText().toString().isEmpty()){
            buttonCadastrar.setEnabled(false);
        }

        if(editTextEmail.getText().toString().isEmpty()){
            buttonCadastrar.setEnabled(false);
        }

        editTextSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if((editTextSenha.getText().toString().equals(editTextConfirmarSenha.getText().toString())) && editTextSenha != null){
                    buttonCadastrar.setEnabled(true);
                } else {
                    buttonCadastrar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextConfirmarSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if((editTextConfirmarSenha.getText().toString().equals(editTextSenha.getText().toString())) && editTextConfirmarSenha != null){
                    buttonCadastrar.setEnabled(true);
                } else {
                    buttonCadastrar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(editTextNome.getText().toString());
                usuario.setEmail(editTextEmail.getText().toString());
                usuario.setSenha(editTextSenha.getText().toString());
                cadastrarUsuario();
            }
        });


    }

    private void cadastrarUsuario(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    usuario.setId(firebaseUser.getUid());
                    usuario.salvar();
                    firebaseAuth.signOut();
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é inválido. Digite um novo email!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "E-mail já cadastrado!";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
