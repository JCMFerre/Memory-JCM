package com.exemple.profedam.memory.controllers;

import android.os.CountDownTimer;
import android.widget.GridView;
import android.widget.TextView;

import com.exemple.profedam.memory.R;

/**
 * Created by Reskitow on 24/11/2016.
 */

public class Contador extends CountDownTimer {

    private final JuegoActivity juegoActivity;

    public Contador(JuegoActivity juegoActivity, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.juegoActivity = juegoActivity;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        ((TextView) juegoActivity.findViewById(R.id.textTimeLeft)).setText(
                juegoActivity.getString(R.string.txtView_juego_iniciado) + " " + (millisUntilFinished / 1000) + ".");
    }

    @Override
    public void onFinish() {
        ((TextView) juegoActivity.findViewById(R.id.textTimeLeft)).setText("YA HAS FINALIZADO");
        juegoActivity.getGv().setOnItemClickListener(null);
    }
}
