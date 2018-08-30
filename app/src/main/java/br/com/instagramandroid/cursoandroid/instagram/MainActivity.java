package br.com.instagramandroid.cursoandroid.instagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject pontuacao = new ParseObject("Pontuacao");
        pontuacao.put("nome", "Mario");
        pontuacao.put("pontos", 800);
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

    }
}
