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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_bono, parent, false);
        return new BonoViewHolder(view);
    }

    class BonoViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreTextView;

        public BonoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreParkingBono);

        }

        public void bind(Bono bono) {
            nombreTextView.setText(bono.getParking());
        }
    }
}
