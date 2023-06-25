package com.example.deber2_consumirapirestful;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    TextView txtJson;
    JSONObject partjson,verjson;
    JSONArray listjson;
    String recorrer;
    Switch activarsw;
    WebService ws;
    Map<String, String> datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ingresar a la página para consumir datos
        datos = new HashMap<String, String>();
        ws= new WebService(
                "https://dummyjson.com/users",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");
        activarsw=(Switch) findViewById(R.id.swMostrarJson);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        //acceder a users para seleccionar los datos a consumir (nombre, edad, correo)
        txtJson = (TextView) findViewById(R.id.lbldatosJson);
        txtJson.setText(result);
        partjson = new JSONObject(result);
        listjson = partjson.getJSONArray("users");
        recorrer ="";
        //Separación de datos (nombre, edad, correo)
        int i=0;
        while (i<listjson.length())
        {
            verjson = listjson.getJSONObject(i);
            recorrer=recorrer + "\n"+ verjson.get("firstName")+", "+ verjson.get("age")+", "+ verjson.get("email")+ "\n";
            i++;
        }
        txtJson.setText(recorrer);
    }
    public void MostrarJson(View view)
    {
        //Activar y desativar switch
            if (view.getId()==R.id.swMostrarJson)
            {
                if (activarsw.isChecked())
                    txtJson.setVisibility(view.VISIBLE);
                else
                    txtJson.setVisibility(GONE);
            }
    }
}