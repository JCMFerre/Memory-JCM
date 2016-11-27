package com.exemple.profedam.memory.model;

import com.exemple.profedam.memory.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by ALUMNEDAM on 29/01/2016.
 */
public class Partida {


    private Integer[] totalCartes = {R.drawable.c0, R.drawable.c1,
            R.drawable.c2, R.drawable.c3,
            R.drawable.c4, R.drawable.c5,
            R.drawable.c6, R.drawable.c7,
            R.drawable.c8, R.drawable.c9,
            R.drawable.c10, R.drawable.c11
    };
    private ArrayList<Carta> llistaCartes;
    private int numeroCartes;

    public Partida(int numeroCartes) {
        this.numeroCartes = numeroCartes;
        List<Integer> llistaTotal = Arrays.asList(totalCartes);
        Collections.shuffle(llistaTotal);
        llistaCartes = new ArrayList();
        for (int cont = 0; cont < numeroCartes; cont++) {
            llistaCartes.add(new Carta(llistaTotal.get(cont / 2)));
        }
        Collections.shuffle(llistaCartes);
    }

    public int getNumeroCartes() {
        return numeroCartes;
    }

    public ArrayList<Carta> getLlistaCartes() {
        return llistaCartes;
    }


}
