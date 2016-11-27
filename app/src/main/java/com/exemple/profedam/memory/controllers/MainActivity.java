package com.exemple.profedam.memory.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.exemple.profedam.memory.R;

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
                //Toast.makeText(MainActivity.this, "Dificultad: "+spinnerDificultad.getSelectedItemPosition()+"\n" +
                  //      "Tema: " + spinnerTemas.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, JuegoActivity.class));
            }
        });
    }

}


