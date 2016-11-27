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

    boolean bloqueoPareja;
    private JuegoActivity tauler;
    private boolean juegoNoIniciado;
    private ArrayList<Carta> cartasSeleccionadas;
    private ArrayList<Integer> posicionesSeleccionadas;
    private MainActivity mainActivity;


    public GeneralListener(JuegoActivity tauler) {
        this.tauler = tauler;
        juegoNoIniciado = true;
        cartasSeleccionadas = new ArrayList<>();
        posicionesSeleccionadas = new ArrayList<>();
        bloqueoPareja = false;
    }

    public GeneralListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        comprobarJuegoIniciado();
        Carta cartaSeleccionada = tauler.getPartida().getLlistaCartes().get(position);
        if (cartasSeleccionadas.size() != 2 && cartaSeleccionada.getEstat() == Carta.Estat.BACK) {
            cartasSeleccionadas.add(cartaSeleccionada);
            cartaSeleccionada.girar();
            refrescarTablero();
            lanzarHandler(cartaSeleccionada);
            if (cartasSeleccionadas.size() == 2) {
                comprobarCartas();
            }
            if (jocIsFinalitzat()) {
                tauler.cancelarContador();
                tauler.mostrarDialog(false);
            }
        }

    }

    private void comprobarCartas() {
        if (cartasSeleccionadas.get(0).getFrontImage() == cartasSeleccionadas.get(1).getFrontImage()) {
            cambiarEstadoCartas(Carta.Estat.FIXED);
            limpiarLista();
        } else {
            bloqueoPareja = true;
            ponerCartasBack();
        }
    }

    private void ponerCartasBack() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cambiarEstadoCartas(Carta.Estat.BACK);
                limpiarLista();
                bloqueoPareja = false;
            }
        }, tauler.getConfigJuego().getTiempoCartaGiradaMilis() / 2);
    }

    private boolean jocIsFinalitzat() {
        boolean comprobacion = false;
        for (Carta carta : tauler.getPartida().getLlistaCartes()) {
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
        refrescarTablero();
    }

    private void limpiarLista() {
        cartasSeleccionadas.clear();
    }

    private void refrescarTablero() {
        tauler.refrescarTablero();
    }

    private void comprobarJuegoIniciado() {
        if (juegoNoIniciado) {
            tauler.iniciarContador();
            juegoNoIniciado = false;
        }
    }

    private void lanzarHandler(final Carta cartaActual) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!bloqueoPareja) {
                    if (cartasSeleccionadas.contains(cartaActual)) {
                        cartaActual.girar();
                        refrescarTablero();
                        cartasSeleccionadas.remove(cartasSeleccionadas.indexOf(cartaActual));
                    }
                }
            }
        }, tauler.getConfigJuego().getTiempoCartaGiradaMilis());
    }

    /**
     * Spinner
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
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

