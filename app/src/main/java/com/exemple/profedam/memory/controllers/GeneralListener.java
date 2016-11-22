package com.exemple.profedam.memory.controllers;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.exemple.profedam.memory.model.Carta;


/**
 * Created by ALUMNEDAM on 02/02/2016.
 */
public class GeneralListener implements AdapterView.OnItemClickListener, Runnable {

    private MainActivity tauler;
    private Carta cartaOnClick;
    private boolean listenerActive = true;


    public GeneralListener(MainActivity tauler) {
        this.tauler = tauler;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            // Solo procesamos clicks si el listener es activo

        if (listenerActive) {

            Toast.makeText(tauler, "position" + position, Toast.LENGTH_LONG).show();
            // view.setVisibility(View.INVISIBLE);

            cartaOnClick = tauler.getPartida().getLlistaCartes().get(position);
            cartaOnClick.girar();
            this.listenerActive = false;
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

