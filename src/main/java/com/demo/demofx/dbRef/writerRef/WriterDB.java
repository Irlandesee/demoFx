package com.demo.demofx.dbRef.writerRef;

import com.demo.demofx.dbRef.DbRef;
import com.demo.demofx.person.Person;
import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriterDB {

    private File db;
    private DbRef dbRef;

    public WriterDB(File f, DbRef dbRef){
        this.db = f;
        this.dbRef = dbRef;
    }

    public void write(int count, Person p){
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(db, true));
            String line = count + "," + p.toString() + "\n";
            bWriter.write(line);
            bWriter.close();
        }catch(IOException ioException){ioException.printStackTrace();}

    }

    public void writeCache(HashMap<Integer, Person> cache){
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(db));
            StringBuilder builder = new StringBuilder();
            for(Map.Entry<Integer, Person> entry: cache.entrySet()){
                builder.append(entry.getKey()).append(":")
                        .append(entry.getValue().getNome()).append(",")
                        .append(entry.getValue().getCognome()).append("\n");
                bWriter.write(builder.toString());
            }
            bWriter.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }

    public boolean remove(long pos, Person p){
        try{
            RandomAccessFile raf = new RandomAccessFile(db, "rw");
            try{
                raf.seek(pos);
                String lineToRemove = raf.readLine();
                System.out.println(lineToRemove);
                raf.writeUTF("\n");
                raf.close();
            }catch(IOException ioe){ioe.printStackTrace();}
        }catch(FileNotFoundException foe){foe.printStackTrace();}
        return false;
    }

    public void rewriteDB(List<Pair<Integer, Person>> testList){
        File db = new File("db.csv");
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(db));
            for(Pair<Integer, Person> entry: testList){
                String line = entry.getKey() + "," + entry.getValue().getNome() + "," + entry.getValue().getCognome() + "\n";
                bWriter.write(line);
            }
            bWriter.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        System.out.println("Done writing test file");
    }

}
