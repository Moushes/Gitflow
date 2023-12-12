package com.example.proyectoiot.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoiot.R;
import com.example.proyectoiot.model.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static String correo = "";
    private String contraseña = "";
    private static String nombre = "";
    private static String matricula = "";
    private ViewGroup contenedor;
    private EditText etCorreo, etContraseña, etMatricula, etNombre;
    private TextInputLayout tilCorreo, tilContraseña,tilNombre,tilMatricula;
    private ProgressDialog dialogo;
    private static final int RC_GOOGLE_SIGN_IN = 123;
    GoogleSignInClient googleSignInClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etCorreo = (EditText) findViewById(R.id.correo_register);
        etContraseña = (EditText) findViewById(R.id.contraseña_register);
        etMatricula=(EditText) findViewById(R.id.matricula_register);
        etNombre=(EditText) findViewById(R.id.nombre_register);
        tilMatricula = (TextInputLayout) findViewById(R.id.til_matricula);
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilCorreo = (TextInputLayout) findViewById(R.id.til_correo_register);
        tilContraseña = (TextInputLayout) findViewById(R.id.til_contraseña_register);
        contenedor = (ViewGroup) findViewById(R.id.contenedor);
        dialogo = new ProgressDialog(this);
        dialogo.setTitle("Verificando usuario");
        dialogo.setMessage("Por favor espere...");
        verificaSiUsuarioValidado();
        //Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void verificaSiUsuarioValidado() {
        if (auth.getCurrentUser() != null) {
            agregarUsuarioAFirestore(new Usuario(nombre,correo,matricula));
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("Correo", correo);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }


    public void autentificarGoogle(View v) {
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                googleAuth(account.getIdToken());
            } catch (ApiException e) {
                mensaje("Error de autentificación con Google");
            }
        }
    }

    private void googleAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,
                null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            verificaSiUsuarioValidado();
                        } else {
                            mensaje(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    private void mensaje(String mensaje) {
        Snackbar.make(contenedor, mensaje, Snackbar.LENGTH_LONG).show();
    }

    public void registroCorreo(View v) {
        if (verificaCampos()) {
            dialogo.show();
            auth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                enviarEmailVerificacion();
                            } else {
                                dialogo.dismiss();
                                mensaje(task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }
    }

    private void enviarEmailVerificacion() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dialogo.dismiss();
                    if (task.isSuccessful()) {
                        mensaje("Registro exitoso. Se ha enviado un correo de verificación a " + user.getEmail());
                        verificaSiUsuarioValidado();
                    } else {
                        mensaje("No se pudo enviar el correo de verificación. " + task.getException().getLocalizedMessage());
                    }
                }
            });
        }
    }

    public void agregarUsuarioAFirestore(Usuario usuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usuario.setUid(user.getUid());
        db.collection("Usuarios").document(user.getUid()).set(usuario);

    }

    private boolean verificaCampos() {
        correo = etCorreo.getText().toString();
        contraseña = etContraseña.getText().toString();
        nombre = etNombre.getText().toString();
        matricula = etMatricula.getText().toString();
        Log.d("Usuario", "Nombre: " +nombre);
        Log.d("Usuario", "Correo: " +correo);
        Log.d("Usuario", "Matricula: " +matricula);
        tilCorreo.setError("");
        tilContraseña.setError("");
        tilMatricula.setError("");
        tilNombre.setError("");

        if (nombre.isEmpty()) {
            tilNombre.setError("Introduce un nombre");
            return false;
        } else if (!nombre.matches("^[A-Za-z\\s]+$")) {
            tilNombre.setError("Nombre no válido");
            return false;
        }
        if (correo.isEmpty()) {
            tilCorreo.setError("Introduce un correo");
            return false;
        } else if (!correo.matches(".+@.+[.].+")) {
                tilCorreo.setError("Correo no válido");
            return false;
        }
        if (contraseña.isEmpty()) {
            tilContraseña.setError("Introduce una contraseña");
            return false;
        } else if (contraseña.length() < 6) {
            tilContraseña.setError("Ha de contener al menos 6 caracteres");
            return false;
        } else if (!contraseña.matches(".*[0-9].*")) {
            tilContraseña.setError("Ha de contener un número");
            return false;
        } else if (!contraseña.matches(".*[A-Z].*")) {
            tilContraseña.setError("Ha de contener una letra mayúscula");
            return false;
        }
        if(matricula.isEmpty()) {
            tilMatricula.setError("Introduzca una matricula");
            return false;
        }
        else{
            return true;
            }
        }
    }

