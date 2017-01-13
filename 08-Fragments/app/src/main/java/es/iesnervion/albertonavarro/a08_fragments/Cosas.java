package es.iesnervion.albertonavarro.a08_fragments;


public class Cosas {

    static Cosa[] Cosas = {
            new Cosa("Alberto", "espacio vacio", R.drawable.foto ),
            new Cosa("Donald", "Idealista\nUtópico\nNaranja", R.drawable.trump),
            new Cosa("Dalas", "Inteligente\nAtractivo\nFular", R.drawable.dalas),
            new Cosa("Salvador", "Carismático\nProfundo\nRaya", R.drawable.salvador)
    };



    public static String[] getNombresCosas(){
        String nombreCosas[] = new String[Cosas.length];
        for (int i = 0; i < Cosas.length; i++) {
            nombreCosas[i] = Cosas[i].getNombre();
        }
        return nombreCosas;
    }
}
