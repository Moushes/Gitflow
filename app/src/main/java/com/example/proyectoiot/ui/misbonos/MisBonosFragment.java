package com.example.proyectoiot.ui.misbonos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoiot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MisBonosFragment extends Fragment {

    private RecyclerView recyclerView;
    // Add other UI elements as needed

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_misbonos, container, false);

        // Initialize UI elements
        recyclerView = root.findViewById(R.id.recyclerView2);
        // Initialize other UI elements

        // Set up RecyclerView adapter and layout manager if needed

        // Set up click listeners for buttons
        TextView fechalimite = root.findViewById(R.id.TextoFechaLimite);
        cambiarfecha(fechalimite);
        return root;
    }
    public void cambiarfecha(TextView fechalimite) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        fechalimite.setText("Ha ocurrido un error detectando al usuario");
        if (currentUser != null) {
            // Acceder a la colección "reservas" en Firestore y buscar la reserva con el ID del usuario
            String uid = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Reservas")
                    .whereEqualTo("idUsuario", uid)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Iterar sobre los documentos resultantes (debería haber solo uno, ya que buscamos por ID de usuario)
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Obtener el valor de fechaLimite
                                Long fechaLimite = document.getLong("fecha");
                                Long dias = document.getLong("duracion");
                                Long diasenMillis=convertirDiasAMillis(dias);
                                Long duracion=(fechaLimite +diasenMillis);
                                // Convertir la fechaLimite al formato deseado
                                String fechaLimiteFormateada = convertirFecha(duracion.toString());

                                // Actualizar el TextView con la fechaLimite formateada

                                fechalimite.setText(fechaLimiteFormateada);
                            }
                        } else {
                            fechalimite.setText("Ha ocurrido un error");
                        }
                    });
        }else{
            fechalimite.setText("Ha ocurrido un error detectando al usuario");
        }
    }

    private String convertirFecha(String fechaMillis) {
        try {
            // Convertir el tiempo en milisegundos a un objeto Date
            Date fecha = new Date(Long.parseLong(fechaMillis));

            // Formatear la fecha al formato deseado ("DD/MM/AAAA")
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
            return formatoSalida.format(fecha);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Manejar la excepción según tus necesidades
        }

    }
    public static long convertirDiasAMillis(Long dias) {
        // 1 día = 24 horas, 1 hora = 60 minutos, 1 minuto = 60 segundos, 1 segundo = 1000 milisegundos
        long millisPorDia = 24L * 60L * 60L * 1000L;
        return dias * millisPorDia;
    }
}
