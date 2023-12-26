package com.example.turistico;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity2 extends AppCompatActivity {

    public TextView txtListaBancos;

    public interface LugaresAPI {
        @GET("lugar_turistico/json_getlistadoGrid")
        Call<LugarResponse> getLugares();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtListaBancos = findViewById(R.id.txtListaBancos);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uealecpeterson.net/turismo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LugaresAPI api = retrofit.create(LugaresAPI.class);

        Call<LugarResponse> call = api.getLugares();

        call.enqueue(new Callback<LugarResponse>() {
            @Override
            public void onResponse(Call<LugarResponse> call, Response<LugarResponse> response) {
                if (response.isSuccessful()) {
                    LugarResponse lugarResponse = response.body();
                    if (lugarResponse != null) {
                        mostrarNombresLugares(lugarResponse.getData());
                    } else {
                        Toast.makeText(MainActivity2.this,
                                "Respuesta nula", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity2.this,
                            "Respuesta no exitosa: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LugarResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(MainActivity2.this,
                        "Error al consultar API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarNombresLugares(List<Lugar> lugares) {
        StringBuilder nombres = new StringBuilder();
        for (Lugar lugar : lugares) {
            nombres.append("Categoria: "+lugar.getCategoria()+
                    "//  Nombre: "+ lugar.getNombre_lugar()+
            "//  Telefono :"+ lugar.getTelefono()).append("\n");
        }
        txtListaBancos.setText(nombres.toString());
    }

    public class LugarResponse {
        private List<Lugar> data;

        public List<Lugar> getData() {
            return data;
        }

        public void setData(List<Lugar> data) {
            this.data = data;
        }
    }

    public class Lugar {
        private String categoria;
        private String nombre_lugar;
        private String telefono;

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public String getNombre_lugar() {
            return nombre_lugar;
        }

        public void setNombre_lugar(String nombre_lugar) {
            this.nombre_lugar = nombre_lugar;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
    }
}
