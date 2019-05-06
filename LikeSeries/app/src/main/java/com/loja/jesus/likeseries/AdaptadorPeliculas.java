package com.loja.jesus.likeseries;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolderPeliculas> {
    public class ViewHolderPeliculas extends RecyclerView.ViewHolder {
        private TextView titulo,numvotos;
        private ImageView imagen;
        private LinearLayout seleccion;

        public ViewHolderPeliculas(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            numvotos = itemView.findViewById(R.id.numvotos);
            imagen = itemView.findViewById(R.id.imagen);
            seleccion = itemView.findViewById(R.id.seleccion);
        }
    }
    private ArrayList<Pelicula> listaPeliculas ;
    Context context;
    public AdaptadorPeliculas(Context contexto, ArrayList<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
        context = contexto;
    }
    public void add(ArrayList<Pelicula> datos)
    {
        listaPeliculas.clear();
        listaPeliculas.addAll(datos);
    }

    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorPeliculas.ViewHolderPeliculas onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview,viewGroup,false);
        return new ViewHolderPeliculas(view);
    }

    /**
     * A perfeccionar
     * @param viewHolderPeliculas
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorPeliculas.ViewHolderPeliculas viewHolderPeliculas, final int i) {
        final Intent intent = new Intent(context,ContenedorMultimedia.class);
        intent.putExtra("titulo",listaPeliculas.get(i).getTitulo_PEL_NA());
        intent.putExtra("descripcion",listaPeliculas.get(i).getDescripcion_PEL_NA());
        intent.putExtra("genero",listaPeliculas.get(i).getGénero_PEL_NA());
        intent.putExtra("votos",listaPeliculas.get(i).getVotos_PEL_NA());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(listaPeliculas.get(i).getNombreimagen()+"");
        final long ONE_MEGABYTE = 1024 * 1024;
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                intent.putExtra("imagen",bytes);
                viewHolderPeliculas.imagen.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        viewHolderPeliculas.titulo.setText(listaPeliculas.get(i).getTitulo_PEL_NA());
        viewHolderPeliculas.numvotos.setText(listaPeliculas.get(i).getVotos_PEL_NA()+"");
        viewHolderPeliculas.seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
