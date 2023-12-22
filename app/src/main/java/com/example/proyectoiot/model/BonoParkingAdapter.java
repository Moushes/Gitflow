package com.example.proyectoiot.model;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoiot.R;
import com.example.proyectoiot.presentation.PagarActivity;
import com.example.proyectoiot.presentation.VistaParkingActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BonoParkingAdapter extends FirestoreRecyclerAdapter<Parking, BonoParkingAdapter.ParkingViewHolder> {

    private int limite; // Campo para almacenar el valor de días
    public BonoParkingAdapter(@NonNull FirestoreRecyclerOptions<Parking> options,Integer dias) {
        super(options);
        this.limite = (dias != null) ? dias : 0; // Asigna el valor de dias o 0 si es nulo
    }

    @Override
    protected void onBindViewHolder(@NonNull ParkingViewHolder holder, int position, @NonNull Parking model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public ParkingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_bono, parent, false);
        return new ParkingViewHolder(view);
    }

    class ParkingViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreTextView;


        public ParkingViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreParkingBono);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), PagarActivity.class);
                    Log.d("Prueba", nombreTextView.getText().toString());
                    Log.d("Prueba", String.valueOf(limite));
                    intent.putExtra("Nombre", nombreTextView.getText().toString());
                    intent.putExtra("Dias", limite);
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Parking parking) {
            nombreTextView.setText(parking.getNombre());
        }
    }
}