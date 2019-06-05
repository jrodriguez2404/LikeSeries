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
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolderPeliculas> {


    public class ViewHolderPeliculas extends RecyclerView.ViewHolder {
        private TextView titulo,numvotosmas,numvotosmenos,notamedia,votomas,votomenos;
        private ImageView imagen;
        private LinearLayout seleccion,recyclerviewvacio;

        public ViewHolderPeliculas(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            numvotosmas = itemView.findViewById(R.id.numvotosmas);
            numvotosmenos = itemView.findViewById(R.id.numvotosmenos);
            imagen = itemView.findViewById(R.id.imagen);
            seleccion = itemView.findViewById(R.id.seleccion);
            notamedia = itemView.findViewById(R.id.notamedia_recycler);
            votomas = itemView.findViewById(R.id.imagenmas);
            votomenos = itemView.findViewById(R.id.imagenmenos);
            recyclerviewvacio = itemView.findViewById(R.id.recyclerviewvacio);
        }
    }
    private ArrayList<Pelicula> listaPeliculas =new ArrayList<>();
    private String vacio;
    Context context;
    public AdaptadorPeliculas(Context contexto, ArrayList<Pelicula> listaPeliculas, String vacio) {
        this.listaPeliculas.clear();

        context = contexto;
        this.vacio=vacio;
if(vacio.equals("vacio"))
{
    Pelicula pelicula = new Pelicula(null,null,null,null,null,null,null,null,null,null,null,0,0,0,0,null,null,null,null);
    this.listaPeliculas.add(pelicula);
}
else
{
    this.listaPeliculas = listaPeliculas;
}
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview, viewGroup, false);

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
        if(!vacio.equals("vacio")) {
            viewHolderPeliculas.recyclerviewvacio.setVisibility(View.GONE);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            ArrayList<String> arrayusuarios = new ArrayList<>(), arraynombres = new ArrayList<>(), arraycomentarios = new ArrayList<>(), arrayvotantes = new ArrayList<>(), arrayreglas = new ArrayList<>();
//Este bucle recorre todos los usuarios que han votado alguna vez en la app ,despues con el usuario actual comprueba si existen en el mapa , y recoge los parametros
try {

    for (int j = 0; j < listaPeliculas.get(i).getVotosusuarios().size(); j++) {
        arrayvotantes.add(listaPeliculas.get(i).getVotosusuarios().get(j).getUsuariovoto());
        arrayreglas.add(listaPeliculas.get(i).getVotosusuarios().get(j).getReglas());
    }
    //Este bucle recoge todos los comentarios de la app , aun tenemos que hacer que el boton eliminar se muestre para los comentarios del usuario actual
    for (int j = 0; j < listaPeliculas.get(i).getComentarios().size(); j++) {
        arrayusuarios.add(listaPeliculas.get(i).getComentarios().get(j).getUsuario());
        arraynombres.add(listaPeliculas.get(i).getComentarios().get(j).getNombre());
        arraycomentarios.add(listaPeliculas.get(i).getComentarios().get(j).getComentario());


    }
}catch (Exception e)
{

}
            DecimalFormat formato = new DecimalFormat("#.#");
            Resources res = viewHolderPeliculas.itemView.getContext().getResources();
            final Intent intent = new Intent(context, ContenedorMultimedia.class);
            intent.putExtra("ID", listaPeliculas.get(i).getID_Pelicula());
            intent.putExtra("titulo", listaPeliculas.get(i).getTitulo_Pelicula());
            intent.putExtra("descripcion", listaPeliculas.get(i).getDescripcion_Pelicula());
            intent.putExtra("genero", listaPeliculas.get(i).getGenero_Pelicula());
            intent.putExtra("votosmas", listaPeliculas.get(i).getVotosPositivos_Pelicula());
            intent.putExtra("votosmenos", listaPeliculas.get(i).getVotosNegativos_Pelicula());
            intent.putExtra("votantes", listaPeliculas.get(i).getVotantes_Pelicula());
            intent.putExtra("productora", listaPeliculas.get(i).getProductora_Pelicula());
            intent.putExtra("nmedia", formato.format(listaPeliculas.get(i).getNotamedia_Pelicula()));
            intent.putExtra("arrayusuarios", arrayusuarios);
            intent.putExtra("arraynombres", arraynombres);
            intent.putExtra("arraycomentarios", arraycomentarios);
            intent.putExtra("arrayvotantes", arrayvotantes);
            intent.putExtra("arrayreglas", arrayreglas);
            intent.putExtra("pelioserie", "peliculas");
            intent.putExtra("urlimagen", listaPeliculas.get(i).getImagen_Pelicula());
            intent.putExtra("director", listaPeliculas.get(i).getDirector_Pelicula());
            intent.putExtra("fechaestreno", listaPeliculas.get(i).getFechaEstreno_Pelicula());
            intent.putExtra("trailer", listaPeliculas.get(i).getTrailer_Pelicula());
            intent.putExtra("duracion", listaPeliculas.get(i).getDuración_Pelicula());
            if(!listaPeliculas.get(i).getGenero_Pelicula().get(3).equals("Anime")) {
                intent.putExtra("actores", listaPeliculas.get(i).getActores_peliculas());
                intent.putExtra("actoresno","si");
            }
            else
            {
                intent.putExtra("actoresno","no");
            }
            FirebaseStorage storage = FirebaseStorage.getInstance();
            try {
                StorageReference gsReference = storage.getReferenceFromUrl(listaPeliculas.get(i).getImagen_Pelicula() + "");
                final long ONE_MEGABYTE = 1024 * 1024;
                gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        viewHolderPeliculas.imagen.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            }catch (Exception a)
            {

            }
            viewHolderPeliculas.titulo.setText(listaPeliculas.get(i).getTitulo_Pelicula());
            viewHolderPeliculas.numvotosmas.setText(res.getString(R.string.votomas, listaPeliculas.get(i).getVotosPositivos_Pelicula()));
            viewHolderPeliculas.votomas.setBackgroundResource(R.drawable.like);
            viewHolderPeliculas.numvotosmenos.setText(res.getString(R.string.votomenos, listaPeliculas.get(i).getVotosNegativos_Pelicula()));
            viewHolderPeliculas.votomenos.setBackgroundResource(R.drawable.nolike);
            viewHolderPeliculas.notamedia.setText(res.getString(R.string.notamedia, listaPeliculas.get(i).getNotamedia_Pelicula()));
            viewHolderPeliculas.seleccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(intent);
                    try {
                        new Like().cerrarActividad();
                    } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                        Toast.makeText(context.getApplicationContext(),
                                "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else
        {

           viewHolderPeliculas.recyclerviewvacio.setVisibility(View.VISIBLE);
           viewHolderPeliculas.seleccion.setVisibility(View.GONE);
        }
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
    public void addItem(Pelicula pelicula,int index)
    {
        listaPeliculas.add(pelicula);
        notifyItemInserted(index);
    }

}
