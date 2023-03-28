package com.demo.demofx.dbRef;

import com.demo.demofx.HelloController;
import com.demo.demofx.dbRef.readerRef.ReaderDB;
import com.demo.demofx.dbRef.writerRef.WriterDB;
import com.demo.demofx.person.Person;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbRef {

    private ReaderDB readerREF;
    private WriterDB writerREF;

    private File db;
    private String dbName = "";

    private final String defaultDBName = "db.csv";
    private final boolean DEBUG = true;
    private int count;

    private HashMap<Integer, Person> cache;

    private HelloController controller;

    public DbRef(HelloController controller){
        this.dbName = defaultDBName;
        this.controller = controller;
        this.db = new File(this.dbName);
        this.cache = new HashMap<Integer, Person>();
        readerREF = new ReaderDB(db, this);
        writerREF = new WriterDB(db, this);

        if(DEBUG){
            writerREF.rewriteDB(readerREF.readTestFile());
            this.read();
            this.printDB();
        }
        if(!db.exists()){
            try{
                boolean res = db.createNewFile();
                if (res) System.out.println("Created new file at: " + db.toURI());
                else System.out.printf("Failed creating file: {%s}\n", db.toString());
                this.count = 0;
            }catch(IOException ioe){ioe.printStackTrace();}
        }else{
            this.count = readerREF.readLastKey();
            System.out.println("Populating cache");
            this.populateCache(this.read());
            System.out.println("File already exists, last key: " + this.count);
            this.count += 1;
        }
    }
    public DbRef(String dbName, HelloController controller) {
        this.dbName = dbName;
        this.controller = controller;
        this.cache = new HashMap<Integer, Person>();
        if (dbName.isBlank()) this.dbName = defaultDBName;

        this.db = new File(dbName);
        readerREF = new ReaderDB(db, this);
        writerREF = new WriterDB(db, this);
        if(DEBUG){
            writerREF.rewriteDB(readerREF.readTestFile());
            this.read();
            this.printDB();
        }
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
            this.count = readerREF.readLastKey();
            System.out.println("Populating cache");
            this.populateCache(this.read());
            System.out.println("File already exists, last key: " + this.count);
            this.count += 1;
        }
    }

    public void addPerson(Person p){
        //Se la persona non è presente, aggiungila
        if(checkPerson(p) != Integer.MIN_VALUE){
            this.count++;
            this.cache.put(this.count, p);
            writerREF.write(this.count, p);
        }
        if(DEBUG){
            System.out.printf("Person added {%d}:{%s}\n", this.count - 1, p);
            this.printDB();
        }
        controller.refreshTextAreaContent(this.read());
    }

    public boolean rmPerson(Person p){
        System.out.printf("Searching db for: {%s}\n", p);
        boolean res = false;
        //Se la persona è presente, rimuovila
        int key = checkPerson(p);
        if(key != Integer.MIN_VALUE){
            this.cache.remove(key);
            this.writerREF.writeCache(this.cache);
            controller.refreshTextAreaContent(this.read());
        }

        if(DEBUG){
            System.out.println("Remove returned: " + res);
            this.printDB();
        }
        return res;
    }

    public List<Pair<Integer, Person>> read(){
        return readerREF.read();
    }

    private void printDB(){
        List<Pair<Integer, Person>> people = this.read();
        for(Pair<Integer, Person> entry: people)
            System.out.println(entry.getKey() + ":" + entry.getValue());
    }

    private void printTestFile(){
        List<Pair<Integer, Person>> people = readerREF.readTestFile();
        for(Pair<Integer, Person> entry: people)
            System.out.println(entry.getKey() + ":" + entry.getValue());
    }

    private boolean diff(List<Pair<Integer, Person>> testList, List<Pair<Integer, Person>> l){
        //TODO: check testList and l length, then if equal, check l's entries against testList
        //they do not need to be sorted
        return false;
    }

    public boolean isDebug(){return this.DEBUG;}

    private void populateCache(List<Pair<Integer, Person>> l){
        l.forEach(tuple -> this.cache.put(tuple.getKey(), tuple.getValue()));
    }

    /**
     * Se la persona esiste, ritorna la chiave
     * altrimenti ritorna Integer.min
     */
    private int checkPerson(Person p){
        if(this.cache.containsValue(p)){
            for(Map.Entry<Integer, Person> set: this.cache.entrySet())
                if(p.equals(set.getValue())) return set.getKey();
        }else if(this.readerREF.search(p)){
            return readerREF.readPersonKey(p);
        }
        return Integer.MIN_VALUE;
    }

}
