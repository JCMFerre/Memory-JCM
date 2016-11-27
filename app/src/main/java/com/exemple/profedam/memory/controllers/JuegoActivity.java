package com.exemple.profedam.memory.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import com.exemple.profedam.memory.R;
import com.exemple.profedam.memory.model.Configuracion;
import com.exemple.profedam.memory.model.Partida;

public class JuegoActivity extends AppCompatActivity {

    private GridView gv;
    private Partida partida;
    private ImageAdapter adapter;
    private Contador timer;
    private Configuracion configJuego;

    public Configuracion getConfigJuego() {
        return configJuego;
    }

    public GridView getGv() {
        return gv;
    }

    public void setGv(GridView gv) {
        this.gv = gv;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        if (savedInstanceState == null) {
            cargarActivity();
        }
    }

    private void cargarActivity() {
        ((TextView) findViewById(R.id.textTimeLeft)).setText(getString(R.string.txtView_juego_inicial));
        configJuego = (Configuracion) getIntent().getSerializableExtra("config");
        gv = (GridView) findViewById(R.id.gridViewMemory);
        partida = new Partida(configJuego.getNumCartas(), configJuego.getCartaBack());
        adapter = new ImageAdapter(this, partida);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new GeneralListener(this));
    }

    public void iniciarContador() {
        timer = new Contador(this, configJuego.getTiempoPartidaMilis(), Configuracion.SEGUNDO_EN_MILIS);
        timer.start();
    }

    public void mostrarDialog(boolean tiempoFinalizado) {
        new AlertDialog.Builder(this)
                .setTitle((tiempoFinalizado) ? getString(R.string.dialog_tiempo_finalizado) : getString(R.string.dialog_ganador))
                .setMessage((tiempoFinalizado ? getString(R.string.tiempo_finalizado_1)
                        + configJuego.getNombre() + getString(R.string.tiempo_finalizado_2) :
                        getString(R.string.ganador_1) + configJuego.getNombre() + getString(R.string.ganador_2))
                        + "\n\n" + getString(R.string.dialog_info))
                .setNeutralButton(R.string.config, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reiniciarAConfig();
                    }
                })
                .setNegativeButton(R.string.salir, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finalizarActivity();
                    }
                })
                .setPositiveButton(R.string.reiniciar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reiniciarActivity();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void reiniciarAConfig() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void reiniciarActivity() {
        cargarActivity();
    }

    private void finalizarActivity() {
        finish();
    }

    public void cancelarContador() {
        timer.cancel();
    }

    public void refrescarTablero() {
        gv.setAdapter(adapter);
        gv.refreshDrawableState();
    }
}
