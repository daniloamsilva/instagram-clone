package br.com.instagramandroid.cursoandroid.instagram.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import br.com.instagramandroid.cursoandroid.instagram.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        ParseObject pontuacao = new ParseObject("Pontuacao");
        pontuacao.put("nome", "Danilo");
        pontuacao.put("pontos", 50);
        pontuacao.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.i("salvarPontos","Sucesso ao salvar os pontos.");
                }else{
                    Log.i("salvarPontos","Erro ao salvar os pontos.");
                }
            }
        });
        */

        /*
        ParseQuery<ParseObject> consulta = ParseQuery.getQuery("Pontuacao");
        consulta.getInBackground("BK9uM5fh4J", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    object.put("pontos", 1200);
                    object.saveInBackground();
                }else{
                    Log.i("consultaObjeto","Erro ao consultar o objeto.");
                }
            }
        });
        */

        ParseQuery<ParseObject> filtro = ParseQuery.getQuery("Pontuacao");

        // Aplicando filtros na listagem de dados
        //filtro.whereGreaterThan("pontos", 800);
        filtro.whereGreaterThanOrEqualTo("pontos", 800);
        //filtro.whereLessThan("pontos", 500);
        //filtro.whereEndsWith("nome", "ia");
        //filtro.whereStartsWith("nome", "Ja");
        //filtro.addAscendingOrder("pontos");
        filtro.addDescendingOrder("pontos");
        filtro.setLimit(1);

        // Listar os dados
        /*
        filtro.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    for (ParseObject object: objects){
                        Log.i("ListarDados","Objetos - Nome: "+ object.get("nome") + " pontos: " + object.get("pontos"));
                    }
                }else{
                    Log.i("ListarDados","Erro ao consultar os objetos: "+ e.getMessage());
                }
            }
        });
        */

        /*
        // Cadastro de usuário
        ParseUser usuario = new ParseUser();
        usuario.setUsername("daniloaugusto");
        usuario.setPassword("123456");
        usuario.setEmail("danilo@gmail.com");

        // Cadastrar
        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.i("cadastroUsuario","Sucesso ao cadastrar o usuário");
                }else {
                    Log.i("cadastroUsuario","Erro ao cadastrar o usuário: "+ e.getMessage());
                }
            }
        });
        */


        // Deslogar usuário
        //ParseUser.logOut();

        /*
        // Verificar usuário logado
        if (ParseUser.getCurrentUser() != null){
            Log.i("loginUsuario","Usuário esta logado");
        }else{
            Log.i("loginUsuario","Usuário não esta logado");
        }
        */

        // Fazer login do usuário
        ParseUser.logInInBackground("daniloaugusto", "1234", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.i("verificaLoginUsuario","Login realizado com sucesso!");
                }else{
                    Log.i("verificaLoginUsuario","Erro ao fazer login do usuário: "+ e.getMessage());
                }
            }
        });

    }
}
