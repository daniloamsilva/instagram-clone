package br.com.instagramandroid.cursoandroid.instagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.instagramandroid.cursoandroid.instagram.R;

public class HomeAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private ArrayList<ParseObject> postagens;

    public HomeAdapter(@NonNull Context c, @NonNull ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.postagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        // Verifica se não existe o objeto view criado
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_postagem, parent, false);
        }

        // Verifica se já existe postagens
        if (postagens.size() > 0){
            ImageView imagemPostagem = view.findViewById(R.id.image_lista_postagem);
            ParseObject parseObject = postagens.get(position);
            Picasso.get().load(parseObject.getParseFile("imagem").getUrl()).fit().into(imagemPostagem);
        }

        return view;
    }
}
