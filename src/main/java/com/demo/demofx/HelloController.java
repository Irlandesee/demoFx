package com.demo.demofx;

import com.demo.demofx.dbRef.DbRef;
import com.demo.demofx.person.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;

public class HelloController {

    public Button addPersonButton;
    public Button rmvPersonButton;

    public Button loginButton;
    private DbRef dbRef;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cognomeTextField;

    @FXML
    private TextArea textArea;

    @FXML
    private void initialize(){

        nomeTextField.setOnMouseClicked(e -> nomeTextField.clear());
        cognomeTextField.setOnMouseClicked(e -> cognomeTextField.clear());

        this.dbRef = new DbRef(this);
        this.refreshTextAreaContent(dbRef.read());
    }

    @FXML
    protected void onAddPersonButtonClick(){
        if(dbRef.isDebug()){
            System.out.println("Add person button clicked");
        }
        Person p = parseButtons();
        if(p != null) dbRef.addPerson(p);
    }

    @FXML
    protected void onRmvPersonButtonClick(){
        if(dbRef.isDebug()){
            System.out.println("Remove person button click");
        }
        Person p = parseButtons();
        if(p != null) dbRef.rmPerson(p);
    }


    @FXML
    protected void login(){
        System.out.println("Login button clicked");
    }


    public void refreshTextAreaContent(List<Pair<Integer, Person>> l){
        StringBuilder builder = new StringBuilder();
        l.forEach(
                tuple -> builder.append(tuple.getKey()).append(":").append(tuple.getValue().getNome()).append(" ")
                        .append(tuple.getValue().getCognome()).append("\n")
        );
        String res = builder.toString();
        //System.out.println("String builder: \n"+res);
        this.textArea.setText(res);
    }

    private Person parseButtons(){
        String nome = nomeTextField.getText();
        String cognome = cognomeTextField.getText();
        if(!(nome.isBlank() && cognome.isBlank())){
            if(dbRef.isDebug()){System.out.printf("{%s}{%s}\n", nome, cognome);}
            nomeTextField.clear();
            cognomeTextField.clear();
            return new Person(nome, cognome);
        }
        return null;
    }

}