package com.example.turistico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity2 extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Map<String, String> datos = new HashMap<String, String>();

        WebService ws = new
                WebService("https://uealecpeterson.net/turismo/lugar_turistico/json_getlistadoGrid",
                datos,
                MainActivity2.this,
                MainActivity2.this);

        ws.execute("GET");

    }


    @Override
    public void processFinish(String result) throws JSONException {
        Log.d("API_RESPONSE", result);  // Imprime la respuesta en la consola

        TextView txtBancos = findViewById(R.id.txtListaBancos);

        // Muestra el resultado JSON completo en el TextView
        txtBancos.setText("Respuesta WS: " + result);
    }

}