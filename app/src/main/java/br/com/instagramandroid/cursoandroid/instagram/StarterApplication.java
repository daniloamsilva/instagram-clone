package br.com.instagramandroid.cursoandroid.instagram;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Habilita o armazenamento local
        Parse.enableLocalDatastore(this);

        // Código de configuração do App
        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("s1UV0WECDU2ri04qPEhHeXgZ5WL0q52lgx2die5V")
        .clientKey("Z4IK5sRbApWp3dFiKjPlTlOJpmFSFsgOEO2Gv8xY")
        .server("https://parseapi.back4app.com/")
        .build()
        );

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
