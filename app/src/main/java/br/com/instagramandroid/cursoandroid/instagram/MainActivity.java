package br.com.instagramandroid.cursoandroid.instagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}
