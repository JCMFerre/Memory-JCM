package com.exemple.profedam.memory.controllers;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.exemple.profedam.memory.model.Carta;

import java.util.ArrayList;


/**
 * Created by ALUMNEDAM on 02/02/2016.
 */
public class GeneralListener implements AdapterView.OnItemClickListener {

    private JuegoActivity tauler;
    private boolean juegoNoIniciado = true;
    private ArrayList<Carta> cartasSeleccionadas;
    private ArrayList<Integer> posicionesSeleccionadas;


    public GeneralListener(JuegoActivity tauler) {
        this.tauler = tauler;
        cartasSeleccionadas = new ArrayList<>();
        posicionesSeleccionadas = new ArrayList<>();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        comprobarJuegoIniciado();
        Carta cartaSeleccionada = tauler.getPartida().getLlistaCartes().get(position);
        if (cartasSeleccionadas.size() != 2 && cartaSeleccionada.getEstat() != Carta.Estat.FIXED) {
            posicionesSeleccionadas.add(position);
            cartasSeleccionadas.add(cartaSeleccionada);
            cartaSeleccionada.girar();
            refrescarTablero();
            lanzarHandler(cartaSeleccionada);
            if (cartasSeleccionadas.size() == 2) {
                comprobacionCartas();
            }
        }
        if (jocIsFinalitzat()) {
            tauler.cancelarContador();
        }

    }

    private void comprobacionCartas() {
        if (cartasSeleccionadas.get(0).getFrontImage() == cartasSeleccionadas.get(1).getFrontImage()) {
            ponerCartasFixed();
        }
        limpiarListas();
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

    private void ponerCartasFixed() {
        for (Carta carta : cartasSeleccionadas) {
            carta.setEstat(Carta.Estat.FIXED);
        }
    }

    private void limpiarListas() {
        cartasSeleccionadas.clear();
        posicionesSeleccionadas.clear();
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
                cartaActual.girar();
                refrescarTablero();
                if (cartasSeleccionadas.contains(cartaActual)) {
                    cartasSeleccionadas.remove(cartasSeleccionadas.indexOf(cartaActual));
                }
            }
        }, 2000);
    }

}

