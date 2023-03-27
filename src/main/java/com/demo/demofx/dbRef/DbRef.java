package com.demo.demofx.dbRef;

import com.demo.demofx.dbRef.readerRef.ReaderDB;
import com.demo.demofx.dbRef.writerRef.WriterDB;
import com.demo.demofx.person.Person;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DbRef {

    private ReaderDB readerREF;
    private WriterDB writerREF;

    private File db;
    private String dbName = "";

    private final String defaultDBName = "db.csv";

    private int count;

    public DbRef(){
        this.dbName = defaultDBName;
        this.db = new File(this.dbName);
        readerREF = new ReaderDB(db, this);
        writerREF = new WriterDB(db, this);

        if(!db.exists()){
            try{
                boolean res = db.createNewFile();
                if (res) System.out.println("Created new file at: " + db.toURI());
                else System.out.printf("Failed creating file: {%s}\n", db.toString());
                this.count = 0;
            }catch(IOException ioe){ioe.printStackTrace();}
        }else{
            this.count = readerREF.readLastKey() + 1;
            System.out.println("File already exists, last key: " +this.count);

        }

    }
    public DbRef(String dbName) {
        this.dbName = dbName;
        if (dbName.isBlank()) this.dbName = defaultDBName;

        this.db = new File(dbName);
        readerREF = new ReaderDB(db, this);
        writerREF = new WriterDB(db, this);
        if (!db.exists()) {
            //create new file
            try {
                boolean res = db.createNewFile();
                if (res) System.out.println("Created new file at: " + db.toURI());
                else System.out.printf("Failed creating file: {%s}\n", db.toString());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            this.count = 0;
        }else{
            this.count = readerREF.readLastKey() + 1;
            System.out.println("File already exists, last key: " +this.count);
        }

    }

    public void addPerson(Person p){
        writerREF.write(this.count++, p);
        System.out.printf("Person added {%d}:{%s}\n", this.count - 1, p);
    }

    public boolean rmPerson(Person p){
        System.out.printf("Searching db for: {%s}\n", p);
        long found = readerREF.search(p);
        if(found == Long.MIN_VALUE) {
            System.out.println("Could not find person: " +p.toString());
            return false;
        }

        return writerREF.remove(found, p);
    }

    public List<Pair<Integer, Person>> read(){
        return readerREF.read();
    }

}