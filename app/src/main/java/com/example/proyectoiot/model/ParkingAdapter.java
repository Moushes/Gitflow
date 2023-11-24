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
import com.example.proyectoiot.presentation.VistaParkingActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import android.content.Context;


public class ParkingAdapter extends FirestoreRecyclerAdapter<Parking, ParkingAdapter.ParkingViewHolder> {
    //private final Context context;

    public ParkingAdapter(@NonNull FirestoreRecyclerOptions<Parking> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ParkingViewHolder holder, int position, @NonNull Parking model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public ParkingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking, parent, false);
        return new ParkingViewHolder(view);
    }

    static class ParkingViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreTextView;
        private final TextView localizacionTextView;
        private final TextView plazasTextView;

        public ParkingViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            localizacionTextView = itemView.findViewById(R.id.localizacionTextView);
            plazasTextView = itemView.findViewById(R.id.plazasTextView);
            // Agregar un ClickListener al ViewHolder
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), VistaParkingActivity.class);
                    intent.putExtra("Nombre", nombreTextView.getText().toString());
                    intent.putExtra("Lugar", localizacionTextView.getText().toString());
                    intent.putExtra("Plazas", plazasTextView.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Parking parking) {
            // Bind the data to the views
            nombreTextView.setText(parking.getNombre());
            localizacionTextView.setText(parking.getDireccion());
            plazasTextView.setText(String.valueOf(parking.getPlazas()));
            Log.d("Plazas", String.valueOf(parking.getPlazas()));
        }
    }
}

