package com.exemple.profedam.memory.controllers;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.exemple.profedam.memory.model.Carta;


/**
 * Created by ALUMNEDAM on 02/02/2016.
 */
public class GeneralListener implements AdapterView.OnItemClickListener, Runnable {

    private ActivityJuego tauler;
    private Carta cartaOnClick;
    private int contadorPulsados = 0;
    private boolean listenerActive = true;


    public GeneralListener(ActivityJuego tauler) {
        this.tauler = tauler;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // Solo procesamos clicks si el listener es activo

        if (listenerActive) {
            contadorPulsados++;
            Toast.makeText(tauler, "position" + position, Toast.LENGTH_LONG).show();
            // view.setVisibility(View.INVISIBLE);

            cartaOnClick = tauler.getPartida().getLlistaCartes().get(position);
            cartaOnClick.girar();
            if (contadorPulsados == 2) {
                this.listenerActive = false;
                contadorPulsados = 0;
            }
            tauler.refrescarTablero();
            Handler delay = new Handler();
            delay.postDelayed(this, 2000);

        }

    }


    @Override
    public void run() {
        cartaOnClick.girar();
        tauler.refrescarTablero();
        listenerActive = true;
    }
}

