package br.com.instagramandroid.cursoandroid.instagram.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.instagramandroid.cursoandroid.instagram.R;
import br.com.instagramandroid.cursoandroid.instagram.adapter.UsuariosAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<ParseUser> adapter;
    private ArrayList<ParseUser> usuarios;
    private ParseQuery<ParseUser> query;

    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        // Montar ListView e Adapter
        usuarios = new ArrayList<>();
        listView = view.findViewById(R.id.list_usuarios);
        adapter = new UsuariosAdapter(getActivity(), usuarios);
        listView.setAdapter(adapter);

        // Recupera os usuários
        getUsuarios();

        return view;
    }

    private void getUsuarios(){
        // Recuperar os usuários do Parse
        query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null){
                    if (objects.size() > 0){
                        usuarios.clear();
                        for (ParseUser parseUser: objects){
                            usuarios.add(parseUser);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

}
