package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdaptadorTertulia extends RecyclerView.Adapter<AdaptadorTertulia.ViewHolderTertulia> {


    public class ViewHolderTertulia extends RecyclerView.ViewHolder {
        private LinearLayout seleccion,recyclerviewvacio;
        private TextView nombre_tertulia,iniciotertulia,fintertulia,tertuliaactivada;
        private FirebaseUser user;
        private FirebaseAuth mAuth;
        private FirebaseFirestore db;
        public ViewHolderTertulia(@NonNull View itemView) {
            super(itemView);
            nombre_tertulia=itemView.findViewById(R.id.nombretertulia);
            iniciotertulia = itemView.findViewById(R.id.fechainiciotertulia);
            fintertulia=itemView.findViewById(R.id.fechafintertulia);
            tertuliaactivada=itemView.findViewById(R.id.activadotertulia);
            seleccion=itemView.findViewById(R.id.seleccion_tertulia);
            recyclerviewvacio=itemView.findViewById(R.id.recyclerviewvacio_tertulia);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        }
    }
    private ArrayList<Tertulia> listaTertulia = new ArrayList<>();
    private String vacio;
    Context context;
    public AdaptadorTertulia(Context contexto, ArrayList<Tertulia> listaTertulia,String vacio) {
        this.listaTertulia.clear();
        this.listaTertulia = listaTertulia;
        context = contexto;
        this.vacio = vacio;
        if(vacio.equals("vacio"))
        {
            Tertulia tertulia = new Tertulia(null,null,null,0,null);
            this.listaTertulia.add(tertulia);
        }
    }
    public void add(ArrayList<Tertulia> datos)
    {
        listaTertulia.clear();
        listaTertulia.addAll(datos);
    }

    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorTertulia.ViewHolderTertulia onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewtertulia, viewGroup, false);
        return new ViewHolderTertulia(view);
    }

    /**
     * A perfeccionar
     * @param viewHolderTertulia
     * @param i
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorTertulia.ViewHolderTertulia viewHolderTertulia, final int i) {
if(!vacio.equals("vacio")) {
    viewHolderTertulia.recyclerviewvacio.setVisibility(View.GONE);
    viewHolderTertulia.nombre_tertulia.setText(listaTertulia.get(i).getNombretertulia());
    viewHolderTertulia.iniciotertulia.setText(listaTertulia.get(i).getHorainicio());
    viewHolderTertulia.fintertulia.setText(listaTertulia.get(i).getHorafin());


    viewHolderTertulia.tertuliaactivada.setBackgroundResource(R.drawable.abierto);
    viewHolderTertulia.seleccion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listaTertulia.get(i).getActivado() == 1) {
                Intent intent = new Intent(context, ChatTertuliaLike.class);
                intent.putExtra("nombredocumento", viewHolderTertulia.nombre_tertulia.getText().toString());
                intent.putExtra("iniciotertulia", viewHolderTertulia.iniciotertulia.getText().toString());
                intent.putExtra("fintertulia", viewHolderTertulia.fintertulia.getText().toString());

                context.startActivity(intent);
            } else {
                viewHolderTertulia.tertuliaactivada.setBackgroundResource(R.drawable.cerrado);
                Toast.makeText(context.getApplicationContext(),
                        "Esta tertulia está cerrada aun", Toast.LENGTH_LONG).show();
            }
        }
    });

    if (listaTertulia.get(i).getActivado() == 0) {
        viewHolderTertulia.tertuliaactivada.setBackgroundResource(R.drawable.cerrado);
        Toast.makeText(context.getApplicationContext(),
                "La tertulia " + listaTertulia.get(i).getNombretertulia() + " está cerrada", Toast.LENGTH_LONG).show();
    }
}

else
{
    viewHolderTertulia.recyclerviewvacio.setVisibility(View.VISIBLE);
    viewHolderTertulia.seleccion.setVisibility(View.GONE);
}
    }


    @Override
    public int getItemCount() {
        return listaTertulia.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
