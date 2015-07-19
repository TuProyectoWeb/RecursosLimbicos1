package com.blogspot.luiseliberal.primertestrecursoslimbicos.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.consulta.bean.ReporteAuxBean;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.actions.consulta.bean.ReportePreguntaLinealAuxBean;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.utils.bean.MemoryBean;
import com.blogspot.luiseliberal.primertestrecursoslimbicos.utils.bean.OperacionesBdBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luiseliberal on 29-06-2015.
 */
public class LoginDataBaseHelper implements OperacionesBdInterface{

    static final String DATABASE_NAME = "recursoslimbicos_ppal.db";
    static final int DATABASE_VERSION = 7;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.

    static final String TBL_LOGIN_TAG = "LOGIN";
    static final String TBL_REPORTE_TAG = "REPORTE";

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE_TBL_LOGIN = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";

    static final String DATABASE_CREATE_TBL_REPORTE = "create table "+"REPORTE"+
            "( " +"ID"+" integer primary key autoincrement, ENCUESTADOR text, ID_EXAM integer, ID_PREGUNTA integer, " +
            "PREGUNTA text, ID_RESPUESTA integer, RESPUESTA text, TIEMPO_RESPUESTA integer, DIA_ENCUESTADO integer, " +
            "HORA_ENCUESTADO integer, LOCACION_ENCUESTADO integer, GENERO_ENCUESTADO integer, EDAD_ENCUESTADO integer, " +
            "CLASE_USUARIO_ENCUESTADO integer, COMUNA_ENCUESTADO integer, FRECUENCIA_ENCUESTADO integer, MOTIVO_ENCUESTADO integer, " +
            "OCUPACION_ENCUESTADO integer, OTROS1_ENCUESTADO integer default 0, OTROS2_ENCUESTADO integer default 0, " +
            "OTROS3_ENCUESTADO integer default 0, OTROS4_ENCUESTADO integer default 0, OTROS5_ENCUESTADO integer default 0); ";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private com.blogspot.luiseliberal.primertestrecursoslimbicos.utils.DBHelper dbHelper;
    public  LoginDataBaseHelper(Context _context)
    {
        context = _context;
        dbHelper = new com.blogspot.luiseliberal.primertestrecursoslimbicos.utils.DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseHelper open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userName,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSingleEntry(String userName)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD", password);

        String where="USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{userName});
    }

    @Override
    public void sendDataReport(String usuario, int idExamen, int idPregunta, String pregunta, int idRespuesta, String respuesta, int tiempoRespuesta, String[] datosEncuestado) {

        ContentValues newValues = new ContentValues();

        try {
        // Assign values for each row.
        newValues.put("ENCUESTADOR", usuario);
        newValues.put("ID_EXAM",idExamen);
        newValues.put("ID_PREGUNTA",idPregunta);
        newValues.put("PREGUNTA", pregunta);
        newValues.put("ID_RESPUESTA",idRespuesta);
        newValues.put("RESPUESTA",respuesta);
        newValues.put("TIEMPO_RESPUESTA", tiempoRespuesta);

        //DATOS ENCUESTADO
        newValues.put("DIA_ENCUESTADO", Integer.parseInt(datosEncuestado[0]));
        newValues.put("HORA_ENCUESTADO", Integer.parseInt(datosEncuestado[1]));
        newValues.put("LOCACION_ENCUESTADO", Integer.parseInt(datosEncuestado[2]));
        newValues.put("GENERO_ENCUESTADO", Integer.parseInt(datosEncuestado[3]));
        newValues.put("EDAD_ENCUESTADO", Integer.parseInt(datosEncuestado[4]));
        newValues.put("CLASE_USUARIO_ENCUESTADO", Integer.parseInt(datosEncuestado[5]));
        newValues.put("COMUNA_ENCUESTADO", Integer.parseInt(datosEncuestado[6]));
        newValues.put("FRECUENCIA_ENCUESTADO", Integer.parseInt(datosEncuestado[7]));
        newValues.put("MOTIVO_ENCUESTADO", Integer.parseInt(datosEncuestado[8]));
        newValues.put("OCUPACION_ENCUESTADO", Integer.parseInt(datosEncuestado[9]));
        //OPCIONALES
        if(!"".equals(datosEncuestado[10])) {
            newValues.put("OTROS1_ENCUESTADO", Integer.parseInt(datosEncuestado[10]));
        }
        if(!"".equals(datosEncuestado[11])) {
            newValues.put("OTROS2_ENCUESTADO", Integer.parseInt(datosEncuestado[11]));
        }
        if(!"".equals(datosEncuestado[12])) {
            newValues.put("OTROS3_ENCUESTADO", Integer.parseInt(datosEncuestado[12]));
        }
        if(!"".equals(datosEncuestado[13])) {
            newValues.put("OTROS4_ENCUESTADO", Integer.parseInt(datosEncuestado[13]));
        }
        if(!"".equals(datosEncuestado[14])) {
            newValues.put("OTROS5_ENCUESTADO", Integer.parseInt(datosEncuestado[14]));
        }
            // Insert the row into your table
            db.insert("REPORTE", null, newValues);

            Toast.makeText(context, "Respuesta enviada al servidor", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Error al intentar enviar la respuesta al servidor", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public HashMap<String, OperacionesBdBean> getTotalReportesPorUsuario(String usuario) {

        HashMap<String, OperacionesBdBean> mapaDeReportesUsuario = new LinkedHashMap<>();
        OperacionesBdBean operacionesBdBean;
        Cursor cursor = null;

        try {

            if("admin".equalsIgnoreCase(usuario)){

                cursor = db.rawQuery("select ID, ENCUESTADOR, ID_EXAM, ID_PREGUNTA, PREGUNTA, ID_RESPUESTA, RESPUESTA, TIEMPO_RESPUESTA, DIA_ENCUESTADO," +
                        "HORA_ENCUESTADO, LOCACION_ENCUESTADO, GENERO_ENCUESTADO, EDAD_ENCUESTADO," +
                        "CLASE_USUARIO_ENCUESTADO, COMUNA_ENCUESTADO, FRECUENCIA_ENCUESTADO, MOTIVO_ENCUESTADO," +
                        "OCUPACION_ENCUESTADO, OTROS1_ENCUESTADO, OTROS2_ENCUESTADO, OTROS3_ENCUESTADO," +
                        "OTROS4_ENCUESTADO, OTROS5_ENCUESTADO FROM REPORTE;",null);

            }else{

                cursor = db.rawQuery("select ID, ENCUESTADOR, ID_EXAM, ID_PREGUNTA, PREGUNTA, ID_RESPUESTA, RESPUESTA, TIEMPO_RESPUESTA, DIA_ENCUESTADO, "+
                        "HORA_ENCUESTADO, LOCACION_ENCUESTADO, GENERO_ENCUESTADO, EDAD_ENCUESTADO, " +
                                "CLASE_USUARIO_ENCUESTADO, COMUNA_ENCUESTADO, FRECUENCIA_ENCUESTADO, MOTIVO_ENCUESTADO, " +
                                "OCUPACION_ENCUESTADO, OTROS1_ENCUESTADO, OTROS2_ENCUESTADO, OTROS3_ENCUESTADO, " +
                                "OTROS4_ENCUESTADO, OTROS5_ENCUESTADO FROM REPORTE WHERE ENCUESTADOR = '"+usuario+"';",null);

            }

            if (cursor.getCount() < 1) //USUARIO NO EXISTE RETORNA NULL
            {
                cursor.close();
                return null;

            } else {

                cursor.moveToFirst();

                for (int x = 0; x < cursor.getCount(); x++) {

                    int idRow = cursor.getInt(cursor.getColumnIndex("ID"));
                    String usuarioRep = cursor.getString(cursor.getColumnIndex("ENCUESTADOR"));

                    operacionesBdBean = new OperacionesBdBean();

                    operacionesBdBean.setIdRow(idRow);
                    operacionesBdBean.setUsuario(usuarioRep);
                    operacionesBdBean.setIdExamen(cursor.getInt(cursor.getColumnIndex("ID_EXAM")));
                    operacionesBdBean.setIdPregunta(cursor.getInt(cursor.getColumnIndex("ID_PREGUNTA")));
                    operacionesBdBean.setPregunta(cursor.getString(cursor.getColumnIndex("PREGUNTA")));
                    operacionesBdBean.setIdRespuesta(cursor.getInt(cursor.getColumnIndex("ID_RESPUESTA")));
                    operacionesBdBean.setRespuesta(cursor.getString(cursor.getColumnIndex("RESPUESTA")));
                    operacionesBdBean.setTiempoDeRespuesta(cursor.getInt(cursor.getColumnIndex("TIEMPO_RESPUESTA")));
                    //DATOS PERFIL ENCUESTADO
                    operacionesBdBean.setDia(cursor.getInt(cursor.getColumnIndex("DIA_ENCUESTADO")));
                    operacionesBdBean.setHora(cursor.getInt(cursor.getColumnIndex("HORA_ENCUESTADO")));
                    operacionesBdBean.setLocacion(cursor.getInt(cursor.getColumnIndex("LOCACION_ENCUESTADO")));
                    operacionesBdBean.setGenero(cursor.getInt(cursor.getColumnIndex("GENERO_ENCUESTADO")));
                    operacionesBdBean.setEdad(cursor.getInt(cursor.getColumnIndex("EDAD_ENCUESTADO")));
                    operacionesBdBean.setClaseUsuario(cursor.getInt(cursor.getColumnIndex("CLASE_USUARIO_ENCUESTADO")));
                    operacionesBdBean.setComuna(cursor.getInt(cursor.getColumnIndex("COMUNA_ENCUESTADO")));
                    operacionesBdBean.setFrecuencia(cursor.getInt(cursor.getColumnIndex("FRECUENCIA_ENCUESTADO")));
                    operacionesBdBean.setMotivo(cursor.getInt(cursor.getColumnIndex("MOTIVO_ENCUESTADO")));
                    operacionesBdBean.setOcupacion(cursor.getInt(cursor.getColumnIndex("OCUPACION_ENCUESTADO")));

                    operacionesBdBean.setOtros1(cursor.getInt(cursor.getColumnIndex("OTROS1_ENCUESTADO")));
                    operacionesBdBean.setOtros2(cursor.getInt(cursor.getColumnIndex("OTROS2_ENCUESTADO")));
                    operacionesBdBean.setOtros3(cursor.getInt(cursor.getColumnIndex("OTROS3_ENCUESTADO")));
                    operacionesBdBean.setOtros4(cursor.getInt(cursor.getColumnIndex("OTROS4_ENCUESTADO")));
                    operacionesBdBean.setOtros5(cursor.getInt(cursor.getColumnIndex("OTROS5_ENCUESTADO")));

                    //SE AGREGAN LOS DATOS EN EL MAPA
                    mapaDeReportesUsuario.put(idRow+"-"+usuarioRep, operacionesBdBean);

                    cursor.moveToNext();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Lo sentimos, ocurrio un error", Toast.LENGTH_LONG).show();
        }finally {
            cursor.close();
        }

        return mapaDeReportesUsuario;
    }


    public String validarDataReporte(String userName, String idPregunta)
    {
        Cursor cursor=db.query("REPORTE", null, " ENCUESTADOR=? AND ID_PREGUNTA=?", new String[]{userName, idPregunta}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "";
        }
        cursor.moveToFirst();
        String idRespuesta = cursor.getString(cursor.getColumnIndex("ID_RESPUESTA"));
        cursor.close();
        return idRespuesta;
    }

    public int validarUltimoIdExamenPorUsuario(String usuario){

        Cursor cursor=db.query("REPORTE", null, " ENCUESTADOR=? AND ID_EXAM = (SELECT MAX(ID_EXAM) FROM REPORTE WHERE ENCUESTADOR =?)", new String[]{usuario, usuario}, null, null, null);
        int idExamen = 0;

        if(cursor.getCount()> 1) //EXISTEN REGISTROS PARA EL USUARIO
        {

            cursor.moveToFirst();

            idExamen = cursor.getInt(cursor.getColumnIndex("ID_EXAM"));

            //AUTO INCREMENTO
            idExamen = idExamen + 1;

            cursor.close();
        }

        return idExamen;
    }


    public boolean validarRegistroExistenteInsert(String usuario, int idExam, int idPregunta){

        Cursor cursor = db.rawQuery("select * FROM REPORTE WHERE ENCUESTADOR = '" + usuario + "' AND ID_EXAM = " + idExam + " AND ID_PREGUNTA = " + idPregunta + ";", null);
        boolean registroExiste = true;

        if(cursor.getCount()> 0) //EXISTEN REGISTROS PARA EL USUARIO
        {
            cursor.close();
        }else{
            registroExiste = false;
            cursor.close();
        }

        return registroExiste;
    }

    public HashMap<String, ReporteAuxBean> getTotalReporteLinealPorEncuesta() {

        HashMap<String, ReporteAuxBean> mapaDeReporteLinealPorEncuesta = new LinkedHashMap<>();
        ReporteAuxBean reporteAuxBean;
        ReportePreguntaLinealAuxBean reportePreguntaLinealAuxBean;
        Cursor cursor = null;
        int totalPreguntasDinamicas = MemoryBean.getListaEstaticaPreguntasConfig().size();
        StringBuilder cadenaQueryBuild = new StringBuilder();
        int contadorAuxMapaPreguntas = 0;

        try {

            cadenaQueryBuild.append("SELECT ID, ENCUESTADOR, ID_EXAM, DIA_ENCUESTADO, HORA_ENCUESTADO, LOCACION_ENCUESTADO, " +
                    "GENERO_ENCUESTADO, EDAD_ENCUESTADO, CLASE_USUARIO_ENCUESTADO, COMUNA_ENCUESTADO, FRECUENCIA_ENCUESTADO, " +
                    "MOTIVO_ENCUESTADO, OCUPACION_ENCUESTADO, OTROS1_ENCUESTADO, OTROS2_ENCUESTADO, OTROS3_ENCUESTADO, " +
                    "OTROS4_ENCUESTADO, OTROS5_ENCUESTADO, ");

                        for(int x = 1; x < totalPreguntasDinamicas+1; x++) {
                            cadenaQueryBuild.append(" SUM(CASE ID_PREGUNTA WHEN " + x + " THEN ID_PREGUNTA ELSE NULL END) AS P" + x +", ");
                            cadenaQueryBuild.append(" SUM(CASE ID_PREGUNTA WHEN " + x + " THEN ID_RESPUESTA ELSE NULL END) AS R" + x+ ", ");
                            if(x+1 < totalPreguntasDinamicas+1) {
                                cadenaQueryBuild.append(" SUM(CASE ID_PREGUNTA WHEN " + x + " THEN TIEMPO_RESPUESTA ELSE NULL END) AS T" + x + ", ");
                            }else{
                                cadenaQueryBuild.append(" SUM(CASE ID_PREGUNTA WHEN " + x + " THEN TIEMPO_RESPUESTA ELSE NULL END) AS T" + x);
                            }
                        }

                            cadenaQueryBuild.append(" FROM REPORTE GROUP BY ID_EXAM, ENCUESTADOR ORDER BY ID;");

            System.out.println("##### QUERY FINAL ###### -> " + cadenaQueryBuild.toString());

                cursor = db.rawQuery(cadenaQueryBuild.toString(), null);

            if (cursor.getCount() < 1) //USUARIO NO EXISTE RETORNA NULL
            {
                cursor.close();
                return null;

            } else {

                cursor.moveToFirst();

                for (int x = 0; x < cursor.getCount(); x++) {

                    int idRow = cursor.getInt(cursor.getColumnIndex("ID"));
                    String usuarioRep = cursor.getString(cursor.getColumnIndex("ENCUESTADOR"));
                    int idExam = cursor.getInt(cursor.getColumnIndex("ID_EXAM"));

                    reporteAuxBean = new ReporteAuxBean();

                    reporteAuxBean.setIdRow(idRow);
                    reporteAuxBean.setEncuestador(usuarioRep);
                    reporteAuxBean.setIdExamen(idExam);
                    //DATOS PERFIL ENCUESTADO
                    reporteAuxBean.setDia(cursor.getInt(cursor.getColumnIndex("DIA_ENCUESTADO")));
                    reporteAuxBean.setHora(cursor.getInt(cursor.getColumnIndex("HORA_ENCUESTADO")));
                    reporteAuxBean.setLocacion(cursor.getInt(cursor.getColumnIndex("LOCACION_ENCUESTADO")));
                    reporteAuxBean.setGenero(cursor.getInt(cursor.getColumnIndex("GENERO_ENCUESTADO")));
                    reporteAuxBean.setEdad(cursor.getInt(cursor.getColumnIndex("EDAD_ENCUESTADO")));
                    reporteAuxBean.setClaseUsuario(cursor.getInt(cursor.getColumnIndex("CLASE_USUARIO_ENCUESTADO")));
                    reporteAuxBean.setComuna(cursor.getInt(cursor.getColumnIndex("COMUNA_ENCUESTADO")));
                    reporteAuxBean.setFrecuencia(cursor.getInt(cursor.getColumnIndex("FRECUENCIA_ENCUESTADO")));
                    reporteAuxBean.setMotivo(cursor.getInt(cursor.getColumnIndex("MOTIVO_ENCUESTADO")));
                    reporteAuxBean.setOcupacion(cursor.getInt(cursor.getColumnIndex("OCUPACION_ENCUESTADO")));
                    reporteAuxBean.setOtros1(cursor.getInt(cursor.getColumnIndex("OTROS1_ENCUESTADO")));
                    reporteAuxBean.setOtros2(cursor.getInt(cursor.getColumnIndex("OTROS2_ENCUESTADO")));
                    reporteAuxBean.setOtros3(cursor.getInt(cursor.getColumnIndex("OTROS3_ENCUESTADO")));
                    reporteAuxBean.setOtros4(cursor.getInt(cursor.getColumnIndex("OTROS4_ENCUESTADO")));
                    reporteAuxBean.setOtros5(cursor.getInt(cursor.getColumnIndex("OTROS5_ENCUESTADO")));

                    //SECCION PREGUNTAS LINEAL DINAMICA HDP
                    for(int y = 1; y < totalPreguntasDinamicas+1; y++) {
                        contadorAuxMapaPreguntas++;
                        reportePreguntaLinealAuxBean = new ReportePreguntaLinealAuxBean();

                        reportePreguntaLinealAuxBean.setIdPreguntaLinea(cursor.getInt(cursor.getColumnIndex("P" + y)));
                        reportePreguntaLinealAuxBean.setIdRespuestaLinea(cursor.getInt(cursor.getColumnIndex("R" + y)));
                        reportePreguntaLinealAuxBean.setIdTiempoRespuestaLinea(cursor.getInt(cursor.getColumnIndex("T" + y)));

                        reporteAuxBean.getPreguntaIdMapaPorLinea().put(contadorAuxMapaPreguntas + "-" + idExam + "-" + usuarioRep, reportePreguntaLinealAuxBean);
                    }
                    //SE AGREGAN LOS DATOS EN EL MAPA
                    mapaDeReporteLinealPorEncuesta.put(idExam+"-"+usuarioRep, reporteAuxBean);

                    cursor.moveToNext();
                }//FIN FOR DEL CURSOR PRINCIPAL
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Lo sentimos, ocurrio un error", Toast.LENGTH_LONG).show();
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return mapaDeReporteLinealPorEncuesta;
    }

}
