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
        inicializarSpinners();
        setListenerBoton();
        setearValores();
    }

    /**
     * Inicializa los spinners de la activity.
     */
    private void inicializarSpinners() {
        inicializarSpinnerDificultad();
        inicializarSpinnerTemas();
    }

    /**
     * Cambia el estado de las vistas con los valores que le llegan desde la otra activity,
     * si no le llega ningún valor no cambia nada.
     * <p>
     * El único que se queda por defecto es el tema.
     */
    private void setearValores() {
        Configuracion configuracion = (Configuracion) getIntent().getSerializableExtra("configuracion");
        if (configuracion != null) {
            ((EditText) findViewById(R.id.et_nombre)).setText(configuracion.getNombre());
            spinnerDificultad.setSelection((configuracion.getNumCartas() / 6));
        }
    }

    /**
     * Inicializa el spinner de las dificultades, añadiéndole el adaptador y el listener.
     */
    private void inicializarSpinnerDificultad() {
        spinnerDificultad = (Spinner) findViewById(R.id.spinnerDificultad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dificultad,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultad.setAdapter(adapter);
        spinnerDificultad.setOnItemSelectedListener(new GeneralListener(this));
    }

    /**
     * Inicializa el spinner de los temas, añadiéndole el adaptador y el listener.
     */
    private void inicializarSpinnerTemas() {
        spinnerTemas = (Spinner) findViewById(R.id.spinnerTemas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.temas,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTemas.setAdapter(adapter);
        spinnerTemas.setOnItemSelectedListener(new GeneralListener(this));
    }

    /**
     * Asignamos el escuchador al botón.
     */
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

    /**
     * Calcula el tiempo total de la partida (en segundos).
     *
     * @param numCartas   Número de cartas
     * @param tiempoCarta Tiempo que tardan al girar las dos cartas seleccionadas.
     * @param dificultad  Opción seleccionada en el spinner.
     * @return tiempoPartida Devuelve el tiempo total en segundos de la partida.
     */
    public int getTiempoPartida(int numCartas, double tiempoCarta, int dificultad) {
        return (int) (((numCartas / 2) * tiempoCarta) * (6 - dificultad));
    }

    /**
     * Calcula el número de cartas totales del juego.
     *
     * @param dificultad Opción seleccionada en el spinner.
     * @return numCartas Devuleve el número de cartas.
     */
    public int getNumCartas(int dificultad) {
        return dificultad * 6;
    }

    /**
     * Calcula el tiempo que tardan las cartas en volver a dar la vuelta.
     *
     * @param dificultad Opción seleccionada en el spinner.
     * @return tiempoCarta Devuelve el tiempo que tardan en volver a girar las dos cartas seleccionadas.
     */
    public double getTiempoCarta(int dificultad) {
        return (double) 3 / dificultad;
    }

    /**
     * Devuelve el nombre introducido en el EditText principal.
     *
     * @return nombre Devuelve el nombre.
     */
    private String getNombreEt() {
        return ((EditText) findViewById(R.id.et_nombre)).getText().toString();
    }

    /**
     * Devuelve true el EditText se encuentra vacío, si es true además muestra una vista (TextView campo requerido),
     * false lo contrario.
     *
     * @return comprobación true si esta vacía, false lo contrario.
     */
    private boolean validarNombre() {
        boolean comprobacion = getNombreEt().equals("");
        findViewById(R.id.nombre_error).setVisibility(comprobacion ? View.VISIBLE : View.GONE);
        return comprobacion;
    }

    /**
     * Devuelve un array de enteros (int) con los id's de los drawables (Temas de los dorsos).
     *
     * @return imágenes Devuelve un array de tipo int, con los id's de los drawables.
     */
    public int[] getImagenBack() {
        return new int[]{R.drawable.back, R.drawable.back_frozen, R.drawable.back_paw, R.drawable.back_bob};
    }
}


