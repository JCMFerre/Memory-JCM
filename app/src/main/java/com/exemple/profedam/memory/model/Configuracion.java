package com.exemple.profedam.memory.model;

import com.exemple.profedam.memory.controllers.JuegoActivity;

import java.io.Serializable;

/**
 * Created by Reskitow on 27/11/2016.
 */

public class Configuracion implements Serializable {

    private int numCartas;
    private int tiempoPartidaMilis;
    private int tiempoCartaGiradaMilis;
    private int cartaBack;
    public static final int SEGUNDO_EN_MILIS = 1000;

    public Configuracion(int numCartas, int tiempoPartidaSegundos, double tiempoCartaGiradaSegundos, int cartaBack) {
        this.numCartas = numCartas;
        this.tiempoPartidaMilis = tiempoPartidaSegundos * SEGUNDO_EN_MILIS;
        this.tiempoCartaGiradaMilis = (int) (tiempoCartaGiradaSegundos * SEGUNDO_EN_MILIS);
        this.cartaBack = cartaBack;
    }

    public int getTiempoPartidaMilis() {
        return tiempoPartidaMilis;
    }

    public void setTiempoPartidaMilis(int tiempoPartidaMilis) {
        this.tiempoPartidaMilis = tiempoPartidaMilis;
    }

    public int getTiempoCartaGiradaMilis() {
        return tiempoCartaGiradaMilis;
    }

    public void setTiempoCartaGiradaMilis(int tiempoCartaGiradaMilis) {
        this.tiempoCartaGiradaMilis = tiempoCartaGiradaMilis;
    }

    public int getCartaBack() {
        return cartaBack;
    }

    public void setCartaBack(int cartaBack) {
        this.cartaBack = cartaBack;
    }

    public int getNumCartas() {
        return numCartas;
    }

    public void setNumCartas(int numCartas) {
        this.numCartas = numCartas;
    }

}
