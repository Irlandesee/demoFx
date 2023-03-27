package com.demo.demofx;

import com.demo.demofx.dbRef.DbRef;
import com.demo.demofx.person.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HelloController {

    public Button addPersonButton;
    public Button rmvPersonButton;
    private DbRef dbRef;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cognomeTextField;

    @FXML
    private void initialize(){
        this.dbRef = new DbRef();
    }

    @FXML
    protected void onAddPersonButtonClick(){
        System.out.println("Add person button clicked");
        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        nomeTextField.clear();
        cognomeTextField.clear();
        Person p = new Person(nome, cognome);
        dbRef.addPerson(p);
    }

    @FXML
    protected void onRmvPersonButtonClick(){
        System.out.println("Remove person button click");
        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        nomeTextField.clear();
        cognomeTextField.clear();
        Person p = new Person(nome, cognome);
        boolean res = dbRef.rmPerson(p);
        System.out.println("Persona eliminata: " +res);
    }
}