package com.exemple.profedam.memory.controllers;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Carta;

import java.util.ArrayList;


/**
 * Created by ALUMNEDAM on 02/02/2016.
 */
public class GeneralListener implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private JuegoActivity tauler;
    private boolean juegoNoIniciado = true;
    private ArrayList<Carta> cartasSeleccionadas;
    private ArrayList<Integer> posicionesSeleccionadas;
    private MainActivity mainActivity;


    public GeneralListener(JuegoActivity tauler) {
        this.tauler = tauler;
        cartasSeleccionadas = new ArrayList<>();
        posicionesSeleccionadas = new ArrayList<>();
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
        }
        if (jocIsFinalitzat()) {
            tauler.cancelarContador();
        }
    }

    private void comprobarCartas() {
        if (cartasSeleccionadas.get(0).getFrontImage() == cartasSeleccionadas.get(1).getFrontImage()) {
            ponerCartasFixed();
            limpiarLista();
        } else {
            Toast.makeText(tauler, "Pareja erronea", Toast.LENGTH_SHORT).show();
        }
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
                if (cartasSeleccionadas.contains(cartaActual)) {
                    cartaActual.girar();
                    refrescarTablero();
                    cartasSeleccionadas.remove(cartasSeleccionadas.indexOf(cartaActual));
                }
            }
        }, 2000);
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
            int[] imagenVista = new int[]{R.drawable.back, R.drawable.back_frozen, R.drawable.back_paw, R.drawable.back_bob};
            ((ImageView) mainActivity.findViewById(R.id.imageView))
                    .setImageDrawable(mainActivity.getResources().getDrawable(imagenVista[position]));
        } else {
            mainActivity.findViewById(R.id.botonIniciar).setVisibility((position == 0) ? View.GONE : View.VISIBLE);
            Toast.makeText(mainActivity, "" + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Obliga a implementarlo
    }
}

