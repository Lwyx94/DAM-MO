package es.iesnervion.albertonavarro.a06_boletin51;

public class Equipo {

    //Atributos
    private String nombre;
    private int logo;

    //Constructor
    public Equipo(String nombre, int logo) {
        this.nombre = nombre;
        this.logo = logo;
    }


    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

}
