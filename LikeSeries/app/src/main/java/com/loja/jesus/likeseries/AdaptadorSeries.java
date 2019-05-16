package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AdaptadorSeries extends RecyclerView.Adapter<AdaptadorSeries.ViewHolderSeries> {
    public class ViewHolderSeries extends RecyclerView.ViewHolder {
        private TextView titulo,numvotosmas,numvotosmenos;
        private ImageView imagen;
        private LinearLayout seleccion;

        public ViewHolderSeries(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            numvotosmas = itemView.findViewById(R.id.numvotosmas);
            numvotosmenos=itemView.findViewById(R.id.numvotosmenos);
            imagen = itemView.findViewById(R.id.imagen);
            seleccion = itemView.findViewById(R.id.seleccion);
        }
    }
    private ArrayList<Serie> listaSeries ;
    Context context;
    public AdaptadorSeries(Context contexto, ArrayList<Serie> listaSeries) {
        this.listaSeries = listaSeries;
        context = contexto;
    }
    public void add(ArrayList<Serie> datos)
    {
        listaSeries.clear();
        listaSeries.addAll(datos);
    }

    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorSeries.ViewHolderSeries onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview,viewGroup,false);
        return new ViewHolderSeries(view);
    }

    /**
     * A perfeccionar
     * @param viewHolderSeries
     * @param i
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorSeries.ViewHolderSeries viewHolderSeries, final int i) {
        long cargaBaseDatos = 1000;
        try {
            Thread.sleep(cargaBaseDatos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Boolean votonegativo=false;
        Boolean votopositivo=false;
//Este bucle recorre todos los usuarios que han votado alguna vez en la app ,despues con el usuario actual comprueba si existen en el mapa , y recoge los parametros
        for(int j = 0 ; j<listaSeries.get(i).getVotosusuarios().size();j++)
        {
            HashMap<String,Object> map = listaSeries.get(i).getVotosusuarios().get(j);
            if (map.containsValue(user.getUid())) {

                votonegativo =Boolean.parseBoolean(map.get("votonegativo").toString());
                votopositivo =Boolean.parseBoolean(map.get("votopositivo").toString());
            }

        }
        Resources res = viewHolderSeries.itemView.getContext().getResources();
        final Intent intent = new Intent(context, ContenedorMultimedia.class);
        intent.putExtra("titulo", listaSeries.get(i).getTitulo_SER());
        intent.putExtra("descripcion", listaSeries.get(i).getDescripcion_SER());
        intent.putExtra("genero", listaSeries.get(i).getGénero_SER());
        intent.putExtra("votosmas", listaSeries.get(i).getVotosmas_SER());
        intent.putExtra("votosmenos", listaSeries.get(i).getVotosmenos_SER());
        intent.putExtra("comentarios", listaSeries.get(i).getComentarios());
        intent.putExtra("usuariocomentario", listaSeries.get(i).getUsuariocomentario());
        intent.putExtra("mediavotos", listaSeries.get(i).getContadormedia());
        intent.putExtra("votonegativo",votonegativo);
        intent.putExtra("votopositivo",votopositivo);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(listaSeries.get(i).getNombreimagen() + "");
        final long ONE_MEGABYTE = 1024 * 1024;
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                intent.putExtra("imagen", bytes);
                viewHolderSeries.imagen.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        viewHolderSeries.titulo.setText(listaSeries.get(i).getTitulo_SER());
        viewHolderSeries.numvotosmas.setText(res.getString(R.string.votomas, listaSeries.get(i).getVotosmas_SER()));
        viewHolderSeries.numvotosmenos.setText(res.getString(R.string.votomenos, listaSeries.get(i).getVotosmenos_SER()));
        viewHolderSeries.seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaSeries.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
