package br.com.instagramandroid.cursoandroid.instagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;

import br.com.instagramandroid.cursoandroid.instagram.R;

public class UsuariosAdapter extends ArrayAdapter<ParseUser> {

    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosAdapter(@NonNull Context c, @NonNull ArrayList<ParseUser> objects) {
        super(c, 0, objects);
        this.context = c;
        this.usuarios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        // Verifica se não existe o objeto view criado
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_usuarios, parent, false);
        }

        // Recuperar elementos para exibição
        TextView username = view.findViewById(R.id.text_username);

        // Configurar o textView pra exibir os usuários
        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername());

        return view;
    }
}
