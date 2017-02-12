package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;


public class VsHumanoLocal extends VsIA {

    private Button btnTirarAdversario;
    private Button btnTirarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnRoll.setVisibility(View.GONE);
        btnTirarAdversario = (Button) findViewById(R.id.btnTirarAdversario);
        btnTirarAdversario.setOnClickListener(this);
        btnTirarAdversario.setVisibility(View.VISIBLE);
        btnTirarUsuario = (Button) findViewById(R.id.btnTirarUsuario);
        btnTirarUsuario.setOnClickListener(this);
        btnTirarUsuario.setVisibility(View.VISIBLE);

        dadoIA1.setOnClickListener(this);
        dadoIA2.setOnClickListener(this);
        dadoIA3.setOnClickListener(this);
        dadoIA4.setOnClickListener(this);
        dadoIA5.setOnClickListener(this);
        corIA1.setScaleY(-1);
        corIA2.setScaleY(-1);
        corIA3.setScaleY(-1);
        corIA4.setScaleY(-1);
        corIA5.setScaleY(-1);
        txtResIA.setRotation(180);


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //DADOS ADVERSARIO
            case R.id.dadoIA1:
                if (!primeraTirada) {
                    if (dadoIA1.getColorFilter() == null)
                        dadoIA1.setColorFilter(colorSel, PorterDuff.Mode.MULTIPLY);
                    else
                        dadoIA1.clearColorFilter();
                }
                break;
            case R.id.dadoIA2:
                if (!primeraTirada) {
                    if (dadoIA2.getColorFilter() == null)
                        dadoIA2.setColorFilter(colorSel, PorterDuff.Mode.MULTIPLY);
                    else
                        dadoIA2.clearColorFilter();
                }
                break;
            case R.id.dadoIA3:
                if (!primeraTirada) {
                    if (dadoIA3.getColorFilter() == null)
                        dadoIA3.setColorFilter(colorSel, PorterDuff.Mode.MULTIPLY);
                    else
                        dadoIA3.clearColorFilter();
                }
                break;
            case R.id.dadoIA4:
                if (!primeraTirada) {
                    if (dadoIA4.getColorFilter() == null)
                        dadoIA4.setColorFilter(colorSel, PorterDuff.Mode.MULTIPLY);
                    else
                        dadoIA4.clearColorFilter();
                }
                break;
            case R.id.dadoIA5:
                if (!primeraTirada) {
                    if (dadoIA5.getColorFilter() == null)
                        dadoIA5.setColorFilter(colorSel, PorterDuff.Mode.MULTIPLY);
                    else
                        dadoIA5.clearColorFilter();
                }
                break;
            case R.id.btnTirarAdversario:
                if (!tirando) {
                    btnTirarAdversario.setEnabled(false);
                    if (!btnTirarUsuario.isEnabled())
                        tirarDados();
                }
                break;
            case R.id.btnTirarUsuario:
                if (!tirando) {
                    btnTirarUsuario.setEnabled(false);
                    if (!btnTirarAdversario.isEnabled())
                        tirarDados();
                }
                break;
        }
    }


    @Override
    public void finalizarRonda() {
        if (vidaH == 0 || vidaIA == 0) {
            mediaPlayer.stop();
            String titulo = "¡SACABÓ!";
            String mensaje = (vidaH == 0) ? "¡Ha ganado el jugador invertido!" : "¡Ha ganado el jugador normal!";
            soundPool.play(idMusicLose, 1, 1, 1, 0, 1);
            soundPool.play(idMusicVictory, 0.5f, 0.5f, 1, 0, 1);

            new AlertDialog.Builder(this)
                    .setTitle(titulo)
                    .setMessage(mensaje)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            primeraTirada = !primeraTirada;
            tirando = false;
            btnTirarAdversario.setEnabled(true);
            btnTirarUsuario.setEnabled(true);
        }
    }


    @Override
    public void enAnimacionFinal() {
        actualizarValores();
        mostrarResultado();
        actualizarVida();
        finalizarRonda();
    }
}
