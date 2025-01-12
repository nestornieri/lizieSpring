package com.example.lizieT123;

public class Animal {
    String nombre;
    String familia;

    public Animal(String nombre, String familia) {
        this.nombre = nombre;
        this.familia = familia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String saludar(){
        return "Holiiii Humano";
    }

    @Override
    public String toString() {
        return "Animal{" +
                "nombre='" + nombre + '\'' +
                ", familia='" + familia + '\'' +
                '}';
    }
}
