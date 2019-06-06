package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.ViewHolderChat> {


    public class ViewHolderChat extends RecyclerView.ViewHolder {
        private TextView nombre_chat,mensaje_chat,eliminar;
        private FirebaseUser user;
        private FirebaseAuth mAuth;
        private FirebaseFirestore db;
        private LinearLayout chat;
        public ViewHolderChat(@NonNull View itemView) {
            super(itemView);
        nombre_chat=itemView.findViewById(R.id.nombre_chat);
        mensaje_chat = itemView.findViewById(R.id.mensaje_chat);
        eliminar=itemView.findViewById(R.id.eliminarmensaje);
            chat = itemView.findViewById(R.id.chat_linearlayout);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        }
    }
    private String chatterttulia;
    private ArrayList<ChatGeneral> listaChat;

    Context context;
    public AdaptadorChat(Context contexto, ArrayList<ChatGeneral> listaChat,String chatterttulia) {
        this.listaChat = listaChat;
        context = contexto;
        this.chatterttulia=chatterttulia;
    }
    public void add(ArrayList<ChatGeneral> datos)
    {
        listaChat.clear();
        listaChat.addAll(datos);
    }

    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorChat.ViewHolderChat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewchat, viewGroup, false);

        return new ViewHolderChat(view);
    }

    /**
     * A perfeccionar
     * @param viewHolderChat
     * @param i
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorChat.ViewHolderChat viewHolderChat, final int i) {

    if (chatterttulia.equals("chat")) {
        try {
            DocumentReference admin = viewHolderChat.db.collection("usuarios").document(viewHolderChat.user.getUid());
            admin.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Usuario user = documentSnapshot.toObject(Usuario.class);
                    try {
                        if (listaChat.get(i).getUid().equals(viewHolderChat.user.getUid())) {
                            viewHolderChat.eliminar.setVisibility(View.VISIBLE);
                            viewHolderChat.eliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final FirebaseFirestore db;
                                    db = FirebaseFirestore.getInstance();
                                    listaChat.remove(i);

                                    DocumentReference docRef = db.collection("chatgeneral").document("chat");
                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            try {
                                                Chat chat = new Chat(listaChat);
                                                db.collection("chatgeneral").document("chat").set(chat);
                                                notifyItemRemoved(i);
                                            }
                                            catch (Exception e)
                                            {

                                            }
                                        }
                                    });
                                }
                            });

                        } else {
                            viewHolderChat.eliminar.setVisibility(View.GONE);
                        }
                        if (user.getAdministrador() == 1) {
                            viewHolderChat.eliminar.setTextColor(R.color.rojo);
                            viewHolderChat.eliminar.setVisibility(View.VISIBLE);
                            viewHolderChat.eliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final FirebaseFirestore db;
                                    db = FirebaseFirestore.getInstance();
                                    listaChat.remove(i);

                                    DocumentReference docRef = db.collection("chatgeneral").document("chat");
                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            try {

                                                Chat chat = new Chat(listaChat);
                                                db.collection("chatgeneral").document("chat").set(chat);
                                                notifyItemRemoved(i);
                                            }
                                            catch (Exception e)
                                            {

                                            }
                                        }
                                    });
                                }
                            });
                        }
                        viewHolderChat.nombre_chat.setText(listaChat.get(i).getNombre());
                        viewHolderChat.mensaje_chat.setText(listaChat.get(i).getMensaje());
                    } catch (Exception e) {

                    }
                }

            });


        } catch (Exception e) {

        }
    } else if (chatterttulia.equals("tertulia")) {
        final DocumentReference admin = viewHolderChat.db.collection("usuarios").document(viewHolderChat.user.getUid());
        admin.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario user = documentSnapshot.toObject(Usuario.class);
                try {
                    if (listaChat.get(i).getUid().equals(viewHolderChat.user.getUid())) {
                        viewHolderChat.eliminar.setVisibility(View.VISIBLE);
                        viewHolderChat.eliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                DocumentReference docRef = db.collection("tertulia").document(listaChat.get(i).getNombredocumento());
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        try {
                                            String documento = listaChat.get(i).getNombredocumento();
                                            listaChat.remove(i);
                                            Chat chat = new Chat(listaChat);
                                            ArrayList<Chat>arraychat = new ArrayList<>();
                                            arraychat.add(chat);
                                            Tertulias tertulias = documentSnapshot.toObject(Tertulias.class);
                                            Tertulia tertulia1 = new Tertulia(tertulias.getTertulia().get(0).getNombretertulia(), tertulias.getTertulia().get(0).getHorainicio(), tertulias.getTertulia().get(0).getHorafin(), tertulias.getTertulia().get(0).getActivado(),arraychat);
                                            ArrayList<Tertulia>arrayTertulia=new ArrayList<>();
                                            arrayTertulia.add(tertulia1);
                                            Tertulias tertulias1 = new Tertulias(arrayTertulia);
                                            db.collection("tertulia").document(documento).set(tertulias1);
                                            notifyItemRemoved(i);
                                        }
                                        catch (Exception e)
                                        {

                                        }
                                    }
                                });
                            }
                        });

                    } else {
                        viewHolderChat.eliminar.setVisibility(View.GONE);
                    }
                    if (user.getAdministrador() == 1) {
                        try {
                            viewHolderChat.eliminar.setTextColor(R.color.rojo);
                            viewHolderChat.eliminar.setVisibility(View.VISIBLE);
                            viewHolderChat.eliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final FirebaseFirestore db;
                                    db = FirebaseFirestore.getInstance();


                                    DocumentReference docRef = db.collection("tertulia").document(listaChat.get(i).getNombredocumento());
                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Tertulias tertulias = documentSnapshot.toObject(Tertulias.class);
                                            String nombre = listaChat.get(i).getNombredocumento();
                                            listaChat.remove(i);
                                            Chat chat = new Chat(listaChat);
                                            ArrayList<Chat>array = new ArrayList<>();
                                            array.add(chat);
                                            Tertulia tertulia1 = new Tertulia(tertulias.getTertulia().get(0).getNombretertulia(),tertulias.getTertulia().get(0).getHorainicio(),tertulias.getTertulia().get(0).getHorafin(),tertulias.getTertulia().get(0).getActivado(),array);
                                            ArrayList<Tertulia> arrayTertulia = new ArrayList<>();
                                            arrayTertulia.add(tertulia1);
                                            tertulias = new Tertulias(arrayTertulia);
                                            db.collection("tertulia").document(nombre).set(tertulias);
                                            notifyItemRemoved(i);
                                        }
                                    });
                                }
                            });
                        }catch (Exception e)
                        {

                        }
                    }
                    viewHolderChat.nombre_chat.setText(listaChat.get(i).getNombre());
                    viewHolderChat.mensaje_chat.setText(listaChat.get(i).getMensaje());
                } catch (Exception e) {

                }
            }

        });

    }
    }


    @Override
    public int getItemCount() {
        return listaChat.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void addItem(ChatGeneral chat, int index) {
        listaChat.add(chat);
        notifyItemInserted(index);
    }

}
