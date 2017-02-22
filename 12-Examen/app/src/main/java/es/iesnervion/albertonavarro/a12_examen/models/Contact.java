package es.iesnervion.albertonavarro.a12_examen.models;


public class Contact {

    private int id;
    private String name;
    private String phone;
    private int age;
    private String gender;

    public Contact(){
        this.id = -1;
        this.name = "Name";
        this.phone = "111222333";
        this.age = 0;
        this.gender = "Male";
    }

    public Contact(int id, String name, String phone, int age, String gender) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name+" "+phone;
    }
}
