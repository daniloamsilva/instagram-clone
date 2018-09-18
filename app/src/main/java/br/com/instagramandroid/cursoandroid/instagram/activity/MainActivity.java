package br.com.instagramandroid.cursoandroid.instagram.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.instagramandroid.cursoandroid.instagram.R;
import br.com.instagramandroid.cursoandroid.instagram.adapter.TabsAdapter;
import br.com.instagramandroid.cursoandroid.instagram.fragments.HomeFragment;
import br.com.instagramandroid.cursoandroid.instagram.util.Permissao;
import br.com.instagramandroid.cursoandroid.instagram.util.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbarPrincipal;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String[] permissoesNecessarias = new String[]{
      Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissao.validaPermissao(1,this, permissoesNecessarias);

        toolbarPrincipal = findViewById(R.id.toolbar_principal);
        toolbarPrincipal.setLogo(R.drawable.instagramlogo);
        setSupportActionBar(toolbarPrincipal);

        slidingTabLayout = findViewById(R.id.sliding_tab_main);
        viewPager = findViewById(R.id.view_pager_main);

        // Configuração do adpter
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.cinzaEscuro));
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sair:
                deslogarUsuario();
                return true;
            case R.id.action_configuracoes:
                return true;
            case R.id.action_compartilhar:
                compartilharFoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Teste do processo de retorno dos dados
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            // Recuperar local do recurso
            Uri localImagemSelecionada = data.getData();

            // Recupera a imagem do local que foi selecionada
            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                // Comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);

                // Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                // Criar um arquivo com formato próprio do parse
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
                String nomeImagem = dateFormat.format(new Date());
                ParseFile arquivoParse = new ParseFile(nomeImagem +"imagem.png", byteArray);

                // Monta objeto para salvar no parse
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("imagem", arquivoParse);

                // Salvar os dados
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e==null){
                            Toast.makeText(MainActivity.this, "Sua imagem foi postada!", Toast.LENGTH_SHORT).show();

                            // Atualizar a listagem de itens do Fragmento HomeFragment
                            TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment homeFragmentNovo = (HomeFragment) adapterNovo.getFragment(0);
                            homeFragmentNovo.atualizaPostagens();

                        }else{
                            Toast.makeText(MainActivity.this, "Erro ao postar sua imagem!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void compartilharFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    private void deslogarUsuario(){
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grandResults){
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);

        for (int resultado : grandResults){
            if (resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar este app, é necessário aceitar as permissões.");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
