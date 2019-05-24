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

public class AdaptadorMutimediaDisLike extends RecyclerView.Adapter<AdaptadorMutimediaDisLike.ViewHolderMultimedia> {


    public class ViewHolderMultimedia extends RecyclerView.ViewHolder {
        private TextView titulo,numvotosmas,numvotosmenos;
        private ImageView imagen;
        private LinearLayout seleccion;
        public ViewHolderMultimedia(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            numvotosmas = itemView.findViewById(R.id.numvotosmas);
            numvotosmenos = itemView.findViewById(R.id.numvotosmenos);
            imagen = itemView.findViewById(R.id.imagen);
            seleccion = itemView.findViewById(R.id.seleccion);

        }
    }
    private ArrayList<Multimedia> listaMultimedia ;
    Context context;
    public AdaptadorMutimediaDisLike(Context contexto, ArrayList<Multimedia> listaMultimedia) {
        this.listaMultimedia = listaMultimedia;
        context = contexto;
    }
    public void add(ArrayList<Multimedia> datos)
    {
        listaMultimedia.clear();
        listaMultimedia.addAll(datos);
    }

    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorMutimediaDisLike.ViewHolderMultimedia onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewvopohor,viewGroup,false);
        return new ViewHolderMultimedia(view);
    }

    /**
     * A perfeccionar
     * @param viewHolderMultimedia
     * @param i
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorMutimediaDisLike.ViewHolderMultimedia viewHolderMultimedia, final int i) {

        try {
            if (listaMultimedia.get(i).getCollection_Pelicula().equals("peliculas")) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String usuario = "", nombre = "", comentario = "";
                ArrayList<String> arrayusuarios = new ArrayList<>(), arraynombres = new ArrayList<>(), arraycomentarios = new ArrayList<>(), arrayvotantes = new ArrayList<>(), arrayreglas = new ArrayList<>();
//Este bucle recorre todos los usuarios que han votado alguna vez en la app ,despues con el usuario actual comprueba si existen en el mapa , y recoge los parametros
                for (int j = 0; j < listaMultimedia.get(i).getVotosusuarios().size(); j++) {
                    arrayvotantes.add(listaMultimedia.get(i).getVotosusuarios().get(j).getUsuariovoto());
                    arrayreglas.add(listaMultimedia.get(i).getVotosusuarios().get(j).getReglas());
                }
                //Este bucle recoge todos los comentarios de la app , aun tenemos que hacer que el boton eliminar se muestre para los comentarios del usuario actual
                for (int j = 0; j < listaMultimedia.get(i).getComentarios().size(); j++) {
                    arrayusuarios.add(listaMultimedia.get(i).getComentarios().get(j).getUsuario());
                    arraynombres.add(listaMultimedia.get(i).getComentarios().get(j).getNombre());
                    arraycomentarios.add(listaMultimedia.get(i).getComentarios().get(j).getComentario());


                }
                Resources res = viewHolderMultimedia.itemView.getContext().getResources();
                final Intent intent = new Intent(context, ContenedorMultimedia.class);
                intent.putExtra("ID", listaMultimedia.get(i).getID_Pelicula());
                intent.putExtra("titulo", listaMultimedia.get(i).getTitulo_Pelicula());
                intent.putExtra("descripcion", listaMultimedia.get(i).getDescripcion_Pelicula());
                intent.putExtra("genero", listaMultimedia.get(i).getGenero_Pelicula());
                intent.putExtra("votosmas", listaMultimedia.get(i).getVotosPositivos_Pelicula());
                intent.putExtra("votosmenos", listaMultimedia.get(i).getVotosNegativos_Pelicula());
                intent.putExtra("votantes", listaMultimedia.get(i).getVotantes_Pelicula());
                intent.putExtra("productora", listaMultimedia.get(i).getProductora_Pelicula());
                intent.putExtra("nmedia", listaMultimedia.get(i).getNotamedia_Pelicula());
                intent.putExtra("arrayusuarios", arrayusuarios);
                intent.putExtra("arraynombres", arraynombres);
                intent.putExtra("arraycomentarios", arraycomentarios);
                intent.putExtra("arrayvotantes", arrayvotantes);
                intent.putExtra("arrayreglas", arrayreglas);
                intent.putExtra("pelioserie", listaMultimedia.get(i).getCollection_Pelicula());
                intent.putExtra("urlimagen", listaMultimedia.get(i).getImagen_Pelicula());
                intent.putExtra("numero", i);

                try {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference gsReference = storage.getReferenceFromUrl(listaMultimedia.get(i).getImagen_Pelicula() + "");
                    final long ONE_MEGABYTE = 1024 * 1024;
                    gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            viewHolderMultimedia.imagen.setImageBitmap(bmp);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
                } catch (Exception e) {

                }
                viewHolderMultimedia.titulo.setText(listaMultimedia.get(i).getTitulo_Pelicula());
                viewHolderMultimedia.numvotosmas.setText(res.getString(R.string.votomas, listaMultimedia.get(i).getVotosPositivos_Pelicula()));
                viewHolderMultimedia.numvotosmenos.setText(res.getString(R.string.votomenos, listaMultimedia.get(i).getVotosNegativos_Pelicula()));

                viewHolderMultimedia.seleccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(intent);
                        new Like().cerrarActividad();
                    }
                });
            }
        }catch (Exception e)
        {
            if (listaMultimedia.get(i).getCollection_Serie().equals("series")) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String usuario = "", nombre = "", comentario = "";
                ArrayList<String> arrayusuarios = new ArrayList<>(), arraynombres = new ArrayList<>(), arraycomentarios = new ArrayList<>(), arrayvotantes = new ArrayList<>(), arrayreglas = new ArrayList<>();
//Este bucle recorre todos los usuarios que han votado alguna vez en la app ,despues con el usuario actual comprueba si existen en el mapa , y recoge los parametros
                for (int j = 0; j < listaMultimedia.get(i).getVotosusuarios().size(); j++) {
                    arrayvotantes.add(listaMultimedia.get(i).getVotosusuarios().get(j).getUsuariovoto());
                    arrayreglas.add(listaMultimedia.get(i).getVotosusuarios().get(j).getReglas());
                }
                //Este bucle recoge todos los comentarios de la app , aun tenemos que hacer que el boton eliminar se muestre para los comentarios del usuario actual
                for (int j = 0; j < listaMultimedia.get(i).getComentarios().size(); j++) {
                    arrayusuarios.add(listaMultimedia.get(i).getComentarios().get(j).getUsuario());
                    arraynombres.add(listaMultimedia.get(i).getComentarios().get(j).getNombre());
                    arraycomentarios.add(listaMultimedia.get(i).getComentarios().get(j).getComentario());


                }
                Resources res = viewHolderMultimedia.itemView.getContext().getResources();
                final Intent intent = new Intent(context, ContenedorMultimedia.class);
                intent.putExtra("ID", listaMultimedia.get(i).getID_Serie());
                intent.putExtra("titulo", listaMultimedia.get(i).getTitulo_Serie());
                intent.putExtra("capitulos", listaMultimedia.get(i).getNCapitulos());
                intent.putExtra("descripcion", listaMultimedia.get(i).getDescripcion_Serie());
                intent.putExtra("genero", listaMultimedia.get(i).getGenero_Serie());
                intent.putExtra("votosmas", listaMultimedia.get(i).getVotosPositivos_Serie());
                intent.putExtra("votosmenos", listaMultimedia.get(i).getVotosNegativos_Serie());
                intent.putExtra("votantes", listaMultimedia.get(i).getVotantes_Serie());
                intent.putExtra("productora", listaMultimedia.get(i).getProductora_Serie());
                intent.putExtra("nmedia", listaMultimedia.get(i).getNotamedia_Serie());
                intent.putExtra("arrayusuarios", arrayusuarios);
                intent.putExtra("arraynombres", arraynombres);
                intent.putExtra("arraycomentarios", arraycomentarios);
                intent.putExtra("arrayvotantes", arrayvotantes);
                intent.putExtra("arrayreglas", arrayreglas);
                intent.putExtra("pelioserie", listaMultimedia.get(i).getCollection_Serie());
                intent.putExtra("urlimagen", listaMultimedia.get(i).getImagen_Serie());
                intent.putExtra("numero", i);


                try {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference gsReference = storage.getReferenceFromUrl(listaMultimedia.get(i).getImagen_Serie() + "");
                    final long ONE_MEGABYTE = 1024 * 1024;
                    gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            viewHolderMultimedia.imagen.setImageBitmap(bmp);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
                } catch (Exception x) {

                }
                viewHolderMultimedia.titulo.setText(listaMultimedia.get(i).getTitulo_Serie());
                viewHolderMultimedia.numvotosmas.setText(res.getString(R.string.votomas, listaMultimedia.get(i).getVotosPositivos_Serie()));
                viewHolderMultimedia.numvotosmenos.setText(res.getString(R.string.votomenos, listaMultimedia.get(i).getVotosNegativos_Serie()));

                viewHolderMultimedia.seleccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(intent);
                        new Like().cerrarActividad();
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return listaMultimedia.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void addItem(Multimedia multimedia, int index) {
        listaMultimedia.add(multimedia);
        notifyItemInserted(index);
    }

}
