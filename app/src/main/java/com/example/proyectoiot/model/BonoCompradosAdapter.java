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

public class BonoCompradosAdapter extends FirestoreRecyclerAdapter<Bono, BonoCompradosAdapter.BonoViewHolder> {

    public BonoCompradosAdapter(@NonNull FirestoreRecyclerOptions<Bono> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BonoViewHolder holder, int position, @NonNull Bono model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public BonoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bono_comprado, parent, false);
        return new BonoViewHolder(view);
    }

    class BonoViewHolder extends RecyclerView.ViewHolder {
        private final TextView matriculaTextView;
        private final TextView duracionTextView;
        private final TextView parkingTextView;

        public BonoViewHolder(@NonNull View itemView) {
            super(itemView);
            matriculaTextView = itemView.findViewById(R.id.matriculaTextView);
            duracionTextView = itemView.findViewById(R.id.duracionTextView);
            parkingTextView = itemView.findViewById(R.id.parkingTextView);
        }

        public void bind(Bono bono) {
            matriculaTextView.setText(bono.getMatricula());
            duracionTextView.setText(String.valueOf(bono.getDuracion()));
            parkingTextView.setText(bono.getParking());
        }
    }
}
