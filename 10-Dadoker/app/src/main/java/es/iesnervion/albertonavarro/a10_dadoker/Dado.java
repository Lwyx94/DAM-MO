package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Dado extends ImageView{

    private int valor;

    public Dado(Context context) {
        super(context);
    }

    public Dado(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Dado(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
