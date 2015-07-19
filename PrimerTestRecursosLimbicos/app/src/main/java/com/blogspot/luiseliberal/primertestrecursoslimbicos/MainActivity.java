package com.blogspot.luiseliberal.primertestrecursoslimbicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.login.Login;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.utils.bean.MemoryBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {

    Button btnIniciarTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarTest = (Button) findViewById(R.id.IniciarTest);

        //SE ONCLICK LISTENER DEL BOTON DE INICIO
        btnIniciarTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                FileInputStream is = null;

                try {

                    //SET CONTEXTO GRAL PARA LOS FRAGMENTOS
                    MemoryBean.setContext(getBaseContext());

                    System.out.println(MemoryBean.getContextBase().getExternalFilesDir(null));


                    File archivoRespuestasConfig = new File(MemoryBean.getContextBase().getExternalFilesDir(null)+"/limbicos_config_respuestas_perfiles.xml");
                    is = new FileInputStream(archivoRespuestasConfig);

                    //SET ITEMS DE RESPUESTA DESDE ARCHIVO DE CONFIGURACION
                    MemoryBean.setListaRespuestasPredefinidasComunes(is);

                    File archivoPreguntasConfig = new File(MemoryBean.getContextBase().getExternalFilesDir(null)+"/limbicos_test_ppal_config.xml");
                    is = new FileInputStream(archivoPreguntasConfig);

                    //SET ITEMS DE PREGUNTAS DESDE ARCHIVO DE CONFIGURACION
                    MemoryBean.setListaEstaticaPreguntasConfig(is);

                }catch (IOException io){
                    io.printStackTrace();
                }finally{
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //CREAR INTENT DE REGISTRO Y COMIENZO DE LA ACTIVIDAD EN LA APP
                Intent iniciarTest = new Intent(getApplicationContext(), Login.class);
                startActivity(iniciarTest);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
