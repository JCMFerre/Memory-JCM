package com.exemple.profedam.memory.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.exemple.profedam.memory.R;

/**
 * Created by ALUMNEDAM on 29/01/2016.
 */
public class Carta {

    public enum Estat {
        BACK, FRONT, FIXED
    }

    private int backImage;
    private int frontImage;
    private Estat estat;

    public Carta(int frontImage, int backImage) {
        this.frontImage = frontImage;
        this.estat = Estat.BACK;
        this.backImage = backImage;
    }

    private int assignarDorso() {
        return R.drawable.back_frozen;
    }

    public int getFrontImage() {
        return frontImage;
    }

    public Estat getEstat() {
        return estat;
    }

    public void setEstat(Estat estat) {
        this.estat = estat;
    }

    public void girar() {
        switch (estat) {
            case BACK: {
                this.estat = Estat.FRONT;
                break;
            }
            case FRONT: {
                this.estat = Estat.BACK;
                break;
            }
        }
    }

    public int getActive() {
        int activeImage = 0;
        switch (estat) {
            case BACK: {
                activeImage = this.backImage;
                break;
            }
            case FRONT:
            case FIXED: {
                activeImage = this.frontImage;
                break;
            }
        }
        return activeImage;
    }
}
