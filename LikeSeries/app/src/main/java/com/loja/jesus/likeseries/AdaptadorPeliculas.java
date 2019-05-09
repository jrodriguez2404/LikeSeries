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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolderPeliculas> {
    public class ViewHolderPeliculas extends RecyclerView.ViewHolder {
        private TextView titulo,numvotosmas,numvotosmenos;
        private ImageView imagen;
        private LinearLayout seleccion;

        public ViewHolderPeliculas(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            numvotosmas = itemView.findViewById(R.id.numvotosmas);
            numvotosmenos = itemView.findViewById(R.id.numvotosmenos);
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
    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorPeliculas.ViewHolderPeliculas viewHolderPeliculas, final int i) {
        final long cargaBaseDatos = 2000;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Boolean votonegativo=false;
        Boolean votopositivo=false;
//Este bucle recorre todos los usuarios que han votado alguna vez en la app ,despues con el usuario actual comprueba si existen en el mapa , y recoge los parametros
        for(int j = 0 ; j<listaPeliculas.get(i).getVotosusuarios().size();j++)
        {
            HashMap<String,Object> map = listaPeliculas.get(i).getVotosusuarios().get(j);
            if (map.containsValue(user.getUid())) {

                 votonegativo =Boolean.parseBoolean(map.get("votonegativo").toString());
                 votopositivo =Boolean.parseBoolean(map.get("votopositivo").toString());
            }

        }
            Resources res = viewHolderPeliculas.itemView.getContext().getResources();
            final Intent intent = new Intent(context, ContenedorMultimedia.class);
            intent.putExtra("titulo", listaPeliculas.get(i).getTitulo_PEL());
            intent.putExtra("descripcion", listaPeliculas.get(i).getDescripcion_PEL());
            intent.putExtra("genero", listaPeliculas.get(i).getGénero_PEL());
            intent.putExtra("votosmas", listaPeliculas.get(i).getVotosmas_PEL());
            intent.putExtra("votosmenos", listaPeliculas.get(i).getVotosmenos_PEL());
            intent.putExtra("comentarios", listaPeliculas.get(i).getComentarios());
            intent.putExtra("usuariocomentario", listaPeliculas.get(i).getUsuariocomentario());
            intent.putExtra("mediavotos", listaPeliculas.get(i).getContadormedia());
            intent.putExtra("votonegativo",votonegativo);
            intent.putExtra("votopositivo",votopositivo);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference gsReference = storage.getReferenceFromUrl(listaPeliculas.get(i).getNombreimagen() + "");
            final long ONE_MEGABYTE = 1024 * 1024;
            gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    intent.putExtra("imagen", bytes);
                    viewHolderPeliculas.imagen.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
            viewHolderPeliculas.titulo.setText(listaPeliculas.get(i).getTitulo_PEL());
            viewHolderPeliculas.numvotosmas.setText(res.getString(R.string.votomas, listaPeliculas.get(i).getVotosmas_PEL()));
            viewHolderPeliculas.numvotosmenos.setText(res.getString(R.string.votomenos, listaPeliculas.get(i).getVotosmenos_PEL()));
            viewHolderPeliculas.seleccion.setEnabled(false);
            viewHolderPeliculas.seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
            });
            TimerTask task = new TimerTask() {
            @Override
            public void run() {
                viewHolderPeliculas.seleccion.setEnabled(true);
            }
        };
        Timer timer = new Timer();

        timer.schedule(task, cargaBaseDatos);
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
