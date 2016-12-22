package es.iesnervion.albertonavarro.a07_spinner;


public class Band implements Comparable<Band>{



    private String name;
    private int logo;

    public Band(String name, int logo) {
        this.name = name;
        this.logo = logo;
    }

    public String getName() {
        return this.name;
    }

    public int getLogo(){
        return this.logo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogo(int logo){
        this.logo=logo;
    }


    @Override
    public int compareTo(Band b) {
        return  this.name.compareTo(b.getName());
    }
}
