package com.example.proyectoiot.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoiot.R;
import com.example.proyectoiot.databinding.FragmentNotificationsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private EditText editTextUsuario, editTextEmail, editTextMatricula, editTextContraseña;
    private FirebaseFirestore db;
    private TextView usuario;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        editTextUsuario = root.findViewById(R.id.editTextUsuario);
        editTextEmail = root.findViewById(R.id.editTextEmail);
        editTextMatricula = root.findViewById(R.id.editTextMatricula);
        editTextContraseña = root.findViewById(R.id.editTextContraseña);
        usuario=root.findViewById(R.id.textoUsuario);
        db = FirebaseFirestore.getInstance();

        // Obtén la instancia de FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Obtiene el usuario actualmente autenticado
        FirebaseUser user = mAuth.getCurrentUser();

        // El usuario está autenticado, puedes obtener su ID
        String idUsuario = user.getUid();
        Log.d("Comprobacion", idUsuario);
        // Actualiza la referencia al documento utilizando el ID del usuario
        db.collection("Usuarios").document(idUsuario).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Log.d("Comprobacion", "Ha entrado en la coleccion");
                if (document.exists()) {
                    // Aquí obtén los datos del documento y configura los EditText correspondientes
                    String usuario = document.getString("nombre");
                    String email = document.getString("correo");
                    String matricula = document.getString("matricula");


                    editTextUsuario.setText(usuario);
                    editTextEmail.setText(email);
                    editTextMatricula.setText(matricula);
                } else {
                    Log.d("Comprobacion", "El documento no existe");
                }
            } else {
                Log.d("Comprobacion", "No ha entrado en la coleccion");
            }
        });
        // Configura el botón y agrega un listener
        Button btnActualizarInformacion = root.findViewById(R.id.btnActualizarInformacion);
        obtenerInformacionUsuario(user.getUid());
        btnActualizarInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes iniciar la lógica para actualizar la información
                actualizarInformacion(idUsuario,editTextUsuario.getText().toString(),editTextEmail.getText().toString(),editTextMatricula.getText().toString(),editTextContraseña.getText().toString());
            }
        });

        return root;
    }
    private void actualizarInformacion(String idUsuario, String nuevoNombre, String nuevoCorreo, String nuevaMatricula,String nuevaContraseña) {
        // Obtén la referencia al documento del usuario
        DocumentReference usuarioRef = db.collection("Usuarios").document(idUsuario);
        // Obtén la instancia de FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Obtiene el usuario actualmente autenticado
        FirebaseUser user = mAuth.getCurrentUser();

        // Crea un mapa con los campos que deseas actualizar
        Map<String, Object> actualizaciones = new HashMap<>();
        actualizaciones.put("nombre", nuevoNombre);
        actualizaciones.put("correo", nuevoCorreo);
        actualizaciones.put("matricula", nuevaMatricula);
        user.updatePassword(nuevaContraseña);

        // Actualiza el documento en Firestore
        usuarioRef.update(actualizaciones)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // La actualización fue exitosa
                        Toast.makeText(getContext(), "Información actualizada correctamente", Toast.LENGTH_SHORT).show();
                        // Puedes realizar acciones adicionales después de la actualización, si es necesario
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al actualizar el documento
                        Toast.makeText(getContext(), "Ha habido un error", Toast.LENGTH_SHORT).show();
                        Log.e("Comprobacion", "Error al actualizar la información", e);
                        // Puedes manejar el error según tus necesidades
                    }
                });
    }
    private boolean verificaCampos() {
       String correo = editTextEmail.getText().toString();
        String contraseña = editTextContraseña.getText().toString();
        String nombre = editTextUsuario.getText().toString();
        String matricula = editTextMatricula.getText().toString();
        editTextEmail.setError("");
        editTextContraseña.setError("");
        editTextUsuario.setError("");
        editTextMatricula.setError("");

        if (nombre.isEmpty()) {
            editTextUsuario.setError("Introduce un nombre");
        } else if (!nombre.matches("^[A-Za-z\\s]+$")) {
            editTextUsuario.setError("Nombre no válido");
        }
        if (correo.isEmpty()) {
            editTextEmail.setError("Introduce un correo");
        } else if (!correo.matches(".+@.+[.].+")) {
            editTextEmail.setError("Correo no válido");
        }
        if (contraseña.isEmpty()) {
            editTextContraseña.setError("Introduce una contraseña");
        } else if (contraseña.length() < 6) {
            editTextContraseña.setError("Ha de contener al menos 6 caracteres");
        } else if (!contraseña.matches(".*[0-9].*")) {
            editTextContraseña.setError("Ha de contener un número");
        } else if (!contraseña.matches(".*[A-Z].*")) {
            editTextContraseña.setError("Ha de contener una letra mayúscula");
        }
        if(matricula.isEmpty()) {
            editTextMatricula.setError("Introduzca una matricula");
        }
        else{
            return true;
        }
        return false;
    }
    private void obtenerInformacionUsuario(String uid) {
        // Referencia a la colección "Usuarios" en Firestore

        DocumentReference usuarioRef = FirebaseFirestore.getInstance().collection("Usuarios").document(uid);

        usuarioRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // El documento existe, obten el valor del campo "Nombre"
                        String nombreUsuario = documentSnapshot.getString("nombre");
                        // Actualiza la interfaz de usuario con el nombre obtenido
                        usuario.setText(nombreUsuario);
                    } else {
                        // El documento no existe
                        usuario.setText("!Hola¡");
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento
                    usuario.setText("¡El documento fallo en cargar!");
                });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
