package com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.luiseliberal.primertestrecursoslimbicos.R;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.consulta.ReporteActivity;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.pruebas.PruebaPrincipal;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.registro.RegistroPerfilEncuestado;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.registro.RegistroPerfilEncuestador;

/**
* Created by luiseliberal on 29-06-2015.
*/

public class Login extends Activity {

    Button btnSignIn,btnSignUp;
    com.blogspot.luiseliberal.primertestrecursoslimbicos.utils.LoginDataBaseHelper loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        //CREAR INSTANCIA DE BASE DE DATOS SQLITE
        loginDataBaseAdapter=new com.blogspot.luiseliberal.primertestrecursoslimbicos.utils.LoginDataBaseHelper(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        //GET REFERENCIAS DE BOTONES
        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);

        //SE ONCLICK LISTENER DEL BOTON DE REGISTRO
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //CREAR INTENT DE REGISTRO Y COMIENZO DE LA ACTIVIDAD EN LA APP
                Intent intentSignUP=new Intent(getApplicationContext(),RegistroPerfilEncuestador.class);
                startActivity(intentSignUP);
            }
        });
    }
    //METODOS DE HANDLECLICK EVENT DEL BOTON DE INICIO DE SESION
    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(Login.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Sesi\u00f3n de Recursos L\u00edmbicos");

        //GET REFERENCIAS DE VISTA
        final EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //GET USERNAME Y PASSWORD
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();

                //fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSingleEntry(userName);

                //VALIDAR SI EL PASSWORD EN BD CORRESPONDE AL INGRESADO
                if(password.equals(storedPassword))
                {
                    Toast.makeText(Login.this, "\u00a1Bienvenido!", Toast.LENGTH_LONG).show();

                    //***************************************************************************************//
                    //*************************** AQUI SE LLAMARA AL INICIO DEL TEST ************************//

                    if("admin".equalsIgnoreCase(userName)){

                        System.out.println("ANTES DE IR A REPORTE");
                        //CREAR INTENT DE XML CONFIG Y COMIENZO DE LA ACTIVIDAD DE PRUEBA EN LA APP
                        Intent intentReporte = new Intent(getApplicationContext(), ReporteActivity.class);

                        //ENVIO DE USUARIO DE SESION A LA ACTIVIDAD DE PRUEBA PPAL
                        intentReporte.putExtra("SESSION_ID", userName);
                        startActivity(intentReporte);

                        dialog.dismiss();

                    }else {
                        //TODOS LOS DEMAS USUARIO REDIRIGIDOS A LA PRUEBA
                        System.out.println("ANTES DE ON CREATE XML CONFIG PARSER");
                        //CREAR INTENT DE XML CONFIG Y COMIENZO DE LA ACTIVIDAD DE PRUEBA EN LA APP
                        Intent intentRegistroPerfilEncuestado = new Intent(getApplicationContext(), RegistroPerfilEncuestado.class);

                        //ENVIO DE USUARIO DE SESION A LA ACTIVIDAD DE PRUEBA PPAL
                        intentRegistroPerfilEncuestado.putExtra("SESSION_ID", userName);
                        startActivity(intentRegistroPerfilEncuestado);

                        dialog.dismiss();
                    }

                }
                else
                {
                    Toast.makeText(Login.this, "El usuario y/o el password es(son) incorrecto(s)", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //CERRAR INSTANCIA DE BD
        loginDataBaseAdapter.close();
    }

}
