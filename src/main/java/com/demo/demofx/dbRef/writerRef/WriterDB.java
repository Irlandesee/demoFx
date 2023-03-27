package com.demo.demofx.dbRef.writerRef;

import com.demo.demofx.dbRef.DbRef;
import com.demo.demofx.person.Person;

import java.io.*;

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
            String line = "\n" + count + "," + p.toString();
            bWriter.write(line);
            bWriter.close();
        }catch(IOException ioException){ioException.printStackTrace();}

    }

    public boolean remove(long pos, Person p){
        try{
            RandomAccessFile raf = new RandomAccessFile(db, "rw");
            try{
                raf.seek(pos);
                raf.writeUTF("");
                raf.close();
            }catch(IOException ioe){ioe.printStackTrace();}
        }catch(FileNotFoundException foe){foe.printStackTrace();}
        return false;
    }

}
