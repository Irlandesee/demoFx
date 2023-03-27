package com.demo.demofx.person;

public class Person {

    private String nome;
    private String cognome;
    public Person(String nome, String cognome){
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome(){return this.nome;}
    public String getCognome(){return this.cognome;}

    public boolean equals(Person p){
        if(this.nome.equals(p.nome) && this.cognome.equals(p.cognome)) return true;
        else return false;
    }
    @Override
    public String toString(){
        return nome + "," + cognome;
    }
}
