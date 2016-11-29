package com.exemple.profedam.memory.controllers;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Carta;

import java.util.ArrayList;


/**
 * Created by ALUMNEDAM on 02/02/2016.
 */
public class GeneralListener implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private JuegoActivity juegoActivity;
    private boolean juegoNoIniciado;
    private ArrayList<Carta> cartasSeleccionadas;
    private MainActivity mainActivity;


    public GeneralListener(JuegoActivity juegoActivity) {
        this.juegoActivity = juegoActivity;
        juegoNoIniciado = true;
        cartasSeleccionadas = new ArrayList<>();
    }

    public GeneralListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        comprobarJuegoIniciado();
        Carta cartaSeleccionada = juegoActivity.getPartida().getLlistaCartes().get(position);
        if (cartasSeleccionadas.size() != 2 && cartaSeleccionada.getEstat() == Carta.Estat.BACK) {
            cartasSeleccionadas.add(cartaSeleccionada);
            cartaSeleccionada.girar();
            refrescarTablero();
            if (cartasSeleccionadas.size() == 2) {
                comprobarCartas();
                comprobarJuegoFinalizado();
            }
        }
    }

    private void comprobarJuegoFinalizado() {
        if (juegoEsFinalizado()) {
            juegoActivity.cancelarContador();
            juegoActivity.mostrarDialog(false);
        }
    }

    private void comprobarCartas() {
        if (cartasIguales()) {
            cambiarEstadoCartas(Carta.Estat.FIXED);
        } else {
            ponerCartasBack();
        }
    }

    private boolean cartasIguales() {
        return (cartasSeleccionadas.get(0).getFrontImage() == cartasSeleccionadas.get(1).getFrontImage());
    }

    private void ponerCartasBack() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cambiarEstadoCartas(Carta.Estat.BACK);
            }
        }, juegoActivity.getConfigJuego().getTiempoCartaGiradaMilis());
    }

    private boolean juegoEsFinalizado() {
        boolean comprobacion = false;
        for (Carta carta : juegoActivity.getPartida().getLlistaCartes()) {
            if (carta.getEstat() == Carta.Estat.FIXED) {
                comprobacion = true;
            } else {
                comprobacion = false;
                break;
            }
        }
        return comprobacion;
    }

    private void cambiarEstadoCartas(Carta.Estat estado) {
        for (Carta carta : cartasSeleccionadas) {
            carta.setEstat(estado);
        }
        limpiarLista();
        refrescarTablero();
    }

    /**
     * Limpia la lista de cartas seleccionadas.
     */
    private void limpiarLista() {
        cartasSeleccionadas.clear();
    }

    /**
     * Refresca el tablero utilizando el metodo refrescarTablero de juegoActivity.
     */
    private void refrescarTablero() {
        juegoActivity.refrescarTablero();
    }

    /**
     * Comprueba si el juego ha sido iniciado.
     */
    private void comprobarJuegoIniciado() {
        if (juegoNoIniciado) {
            juegoActivity.iniciarContador();
            juegoNoIniciado = false;
        }
    }

    public void setJuegoNoIniciado(boolean juegoNoIniciado) {
        this.juegoNoIniciado = juegoNoIniciado;
    }

    /* Control spinners */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerTemas) {
            ((ImageView) mainActivity.findViewById(R.id.imageView))
                    .setImageDrawable(mainActivity.getResources().getDrawable(mainActivity.getImagenBack()[position]));
        } else {
            mainActivity.findViewById(R.id.botonIniciar).setVisibility((position == 0) ? View.GONE : View.VISIBLE);
            mostrarTablaDificultades();
        }
    }

    private void mostrarTablaDificultades() {
        int dificultad = ((Spinner) mainActivity.findViewById(R.id.spinnerDificultad)).getSelectedItemPosition();
        mainActivity.findViewById(R.id.tabla_dificultad).setVisibility(dificultad == 0 ? View.GONE : View.VISIBLE);
        mainActivity.findViewById(R.id.txt_param_dificultad).setVisibility(dificultad == 0 ? View.GONE : View.VISIBLE);
        if (dificultad != 0) mostrarTxtDificultad(dificultad);
    }

    private void mostrarTxtDificultad(int dificultad) {
        int numCartas = mainActivity.getNumCartas(dificultad);
        double tiempoCarta = mainActivity.getTiempoCarta(dificultad);
        int tiempoPartida = mainActivity.getTiempoPartida(numCartas, tiempoCarta, dificultad);
        ((TextView) mainActivity.findViewById(R.id.txt_num_cartas)).setText(mainActivity.getString(R.string.txt_num_cartas)
                + " " + numCartas);
        ((TextView) mainActivity.findViewById(R.id.txt_tiempo_carta)).setText(mainActivity.getString(R.string.txt_tiempo_carta)
                + " " + tiempoCarta + " " + mainActivity.getString(R.string.segundos));
        ((TextView) mainActivity.findViewById(R.id.txt_tiempo_partida)).setText(mainActivity.getString(R.string.txt_tiempo_partida)
                + " " + tiempoPartida + " " + mainActivity.getString(R.string.segundos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Obliga a implementarlo
    }
}

