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
                ris.add(parsePerson(line));
            }

            bReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return ris;
    }


    public long search(Person p){
        try{
            RandomAccessFile raf = new RandomAccessFile(db.getName(), "r");
            String line;
            long pos = raf.getFilePointer();
            while((line = raf.readLine()) != null){
                Pair<Integer, Person> res = parsePerson(line);
                pos = raf.getFilePointer();
                if(res.getValue().equals(p)) {
                    System.out.println(pos);
                    raf.close();
                    return pos;
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


    private Pair<Integer, Person> parsePerson(String line){
        String[] tmp = line.split(",");
        try{
            return new Pair<Integer, Person>(Integer.parseInt(tmp[0]), new Person(tmp[1], tmp[2]));
        }
        catch(NumberFormatException nfe){nfe.printStackTrace();}
        return null;
    }

    public List<Pair<Integer, Person>> readTestFile(){
        File testFile = new File("testFiles/test.csv");
        List<Pair<Integer, Person>> res = new LinkedList<Pair<Integer, Person>>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(testFile));
            String line;
            while((line = bReader.readLine()) != null){
                res.add(parsePerson(line));
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }

}
