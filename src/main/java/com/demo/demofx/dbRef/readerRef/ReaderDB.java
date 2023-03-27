package com.demo.demofx.dbRef.readerRef;

import com.demo.demofx.dbRef.DbRef;
import com.demo.demofx.person.Person;
import javafx.util.Pair;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
public class ReaderDB {

    private File db;
    private DbRef dbRef;
    public ReaderDB(File db, DbRef dbRef){
        this.dbRef = dbRef;
        this.db = db;
    }

    public List<Pair<Integer, Person>> read(){
        List<Pair<Integer, Person>> ris = new LinkedList<Pair<Integer, Person>>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(db));
            String line;
            while(((line = bReader.readLine()) != null)){
                String[] lineSplit = line.split(",");
                int key = Integer.parseInt(lineSplit[0]);
                Person p = new Person(lineSplit[1], lineSplit[2]);
                ris.add(new Pair<Integer, Person>(key, p));
            }

            bReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return ris;
    }


    public long search(Person p){
        try{
            RandomAccessFile raf = new RandomAccessFile(db.getName(), "rw");
            String line;
            while((line = raf.readLine()) != null){
                Pair<Integer, Person> res = readPerson(line);
                if(res.getValue().equals(p)) {
                    raf.close();
                    return raf.getFilePointer();
                }
            }
            raf.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return Long.MIN_VALUE;
    }


    public int readLastKey(){
        int lastKey = Integer.MIN_VALUE;
        try{
            ReversedLinesFileReader revReader = new ReversedLinesFileReader(db, Charset.defaultCharset());
            String line = revReader.readLine();
            revReader.close();
            lastKey = Integer.parseInt(line.split(",")[0]);
        }catch(IOException ioe){ioe.printStackTrace();}
        return lastKey;
    }


    private Pair<Integer, Person> readPerson(String line){
        String[] tmp = line.split(",");
        return new Pair<Integer, Person>(Integer.parseInt(tmp[0]), new Person(tmp[1], tmp[2]));
    }
}
