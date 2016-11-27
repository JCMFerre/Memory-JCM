package com.exemple.profedam.memory.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Configuracion;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerDificultad;
    private Spinner spinnerTemas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarSpinnerDificultad();
        inicializarSpinnerTemas();
        setListenerBoton();
    }

    private void inicializarSpinnerDificultad() {
        spinnerDificultad = (Spinner) findViewById(R.id.spinnerDificultad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dificultad,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultad.setAdapter(adapter);
        spinnerDificultad.setOnItemSelectedListener(new GeneralListener(this));
    }

    private void inicializarSpinnerTemas() {
        spinnerTemas = (Spinner) findViewById(R.id.spinnerTemas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.temas,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTemas.setAdapter(adapter);
        spinnerTemas.setOnItemSelectedListener(new GeneralListener(this));
    }

    private void setListenerBoton() {
        (findViewById(R.id.botonIniciar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(validarNombre())) {
                    Intent i = new Intent(MainActivity.this, JuegoActivity.class);
                    int dificultad = spinnerDificultad.getSelectedItemPosition();
                    int numCartas = getNumCartas(dificultad);
                    double tiempoCarta = getTiempoCarta(dificultad);
                    int tiempoPartida = getTiempoPartida(numCartas, tiempoCarta, dificultad);
                    i.putExtra("config", new Configuracion(getNombreEt(), numCartas,
                            tiempoPartida, tiempoCarta, getImagenBack()[spinnerTemas.getSelectedItemPosition()]));
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    public int getTiempoPartida(int numCartas, double tiempoCarta, int dificultad) {
        return (int) (((numCartas / 2) * tiempoCarta) * (6 - dificultad));
    }

    public int getNumCartas(int dificultad) {
        return dificultad * 6;
    }

    public double getTiempoCarta(int dificultad) {
        return (double) 3 / dificultad;
    }

    private String getNombreEt() {
        return ((EditText) findViewById(R.id.et_nombre)).getText().toString();
    }

    private boolean validarNombre() {
        boolean comprobacion = getNombreEt().equals("");
        findViewById(R.id.nombre_error).setVisibility(comprobacion ? View.VISIBLE : View.GONE);
        return comprobacion;
    }

    public int[] getImagenBack() {
        return new int[]{R.drawable.back, R.drawable.back_frozen, R.drawable.back_paw, R.drawable.back_bob};
    }
}


