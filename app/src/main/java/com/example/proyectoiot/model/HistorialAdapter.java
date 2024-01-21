package com.example.proyectoiot.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoiot.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistorialAdapter extends FirestoreRecyclerAdapter<Bono, HistorialAdapter.HistorialViewHolder> {
    public HistorialAdapter(@NonNull FirestoreRecyclerOptions<Bono> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HistorialAdapter.HistorialViewHolder holder, int position, @NonNull Bono model) {
        // Llama al método bind del ViewHolder para configurar las vistas con los datos del modelo
        holder.bind(model);
    }

    @NonNull
    @Override
    public HistorialAdapter.HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new HistorialAdapter.HistorialViewHolder(view);
    }
    class HistorialViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreTextView;
        private final TextView duracionTextView;
        private final TextView tipoTextView;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreParking);
            duracionTextView= itemView.findViewById(R.id.Hora);
            tipoTextView= itemView.findViewById(R.id.tipoReserva);


        }

        public void bind(Bono bono) {
            nombreTextView.setText(bono.getParking());
            cambiarfecha(duracionTextView);

        }
        public void cambiarfecha(TextView fechalimite) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
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
                                    // Obtener el valor de fecha y duración
                                    Long fechaInicio = document.getLong("fecha");
                                    Long duracion = document.getLong("duracion");

                                    // Calcular la fecha final sumando la duración a la fecha inicial
                                    Long fechaFinal = fechaInicio + convertirDiasAMillis(duracion);

                                    // Convertir las fechas al formato deseado
                                    String fechaInicioFormateada = convertirFecha(fechaInicio.toString());
                                    String fechaFinalFormateada = convertirFecha(fechaFinal.toString());

                                    fechalimite.setText(fechaInicioFormateada +"-"+fechaFinalFormateada);

                                }
                            } else {
                                fechalimite.setText("Ha ocurrido un error");
                            }
                        });
            } else {
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
        public long convertirDiasAMillis(Long dias) {
            // 1 día = 24 horas, 1 hora = 60 minutos, 1 minuto = 60 segundos, 1 segundo = 1000 milisegundos
            long millisPorDia = 24L * 60L * 60L * 1000L;
            return dias * millisPorDia;
        }
    }
}
