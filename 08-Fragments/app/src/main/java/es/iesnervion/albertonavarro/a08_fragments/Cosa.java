package es.iesnervion.albertonavarro.a08_fragments;


public class Cosa {

    private String nombre;
    private String descripcion;
    private int foto;

    public Cosa(){
        this.nombre="Nombre";
        this.descripcion="Descripción.";
        this.foto= R.drawable.foto;
    }


    public Cosa(String nombre, String descripcion, int foto){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.foto=foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }


}
