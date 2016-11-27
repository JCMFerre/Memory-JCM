package com.exemple.profedam.memory.model;

import java.io.Serializable;

/**
 * Created by Reskitow on 27/11/2016.
 */

public class Configuracion implements Serializable {

    private String nombre;
    private int numCartas;
    private int tiempoPartidaMilis;
    private int tiempoCartaGiradaMilis;
    private int cartaBack;
    public static final int SEGUNDO_EN_MILIS = 1000;

    public Configuracion(String nombre, int numCartas, int tiempoPartidaSegundos, double tiempoCartaGiradaSegundos, int cartaBack) {
        this.nombre = nombre;
        this.numCartas = numCartas;
        this.tiempoPartidaMilis = tiempoPartidaSegundos * SEGUNDO_EN_MILIS;
        this.tiempoCartaGiradaMilis = (int) (tiempoCartaGiradaSegundos * SEGUNDO_EN_MILIS);
        this.cartaBack = cartaBack;
    }

    public Configuracion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
