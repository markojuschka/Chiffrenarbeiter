/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffrenarbeiter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Die Klasse liest den Klartext ein, stellt das Objekt, an dem die Chiffriermethoden angewandt werden,  
 * mittels Konstruktor zur Verfügung, und schreibt das Chiffrat in eine Datei
 * 
 * @author user2
 */
public class Chiffrenerzeuger {
   
    ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
    ByteArrayOutputStream baos_encrypted = new ByteArrayOutputStream(); 
    String text_encrypt = new String();
    byte [][] subst = new byte[37][2];
    
    /**
     * Konstruktor Chiffrenerzeuger <br>
     * Aufgerufen wird der Konstruktor in {@link Chiffrenarbeiter} 
     * 
     */
    public Chiffrenerzeuger() {
        int c = 0;  // Character-Zeichen für den endgültigen, zu chiffrierenden Datensatz
        try {
            RandomAccessFile f = new RandomAccessFile("input.txt", "r");
            byte[] b = new byte[(int)f.length()];
            f.read(b);
            for (int i=0; i<b.length; i++) {
                c=this.FitToAlphabet(b[i]);
                if (c > 0) {
                    baos.write(c);
                }
            }
            // Eingelesenen Klartext zur Kontrolle 1x auf der Konsole ausgeben:
            if (Definitions.DEBUG) { System.out.println(baos.toString() + "   (Klartext)"); }
            f.close();   
        } catch (IOException ie) {
            System.out.println("IO-Fehler: " + ie.toString());
        }
    }
    
    /**
     * Die Methode sorgt dafür, dass aus der eingelesenen Datei "input.txt" 
     * lediglich zulässige Zeichen übernommen werden. 
     * So werden z. Bsp. aus Klein- Großbuchstaben gemacht.
     * @param b Bytewert des eigelesenen Zeichens
     */
    public int FitToAlphabet(byte b) {
        if ((int)b == 32 ) {
            return (int)b;  // Leerzeichen
        }
        if (((int)b > 47 ) && ((int)b < 58)) {
            return (int)b;   // Zahlen von 0 bis 9
        }
        if (((int)b > 64 ) && ((int)b < 91)) {
            return (int)b;   // Buchstaben von A bis Z
        }
        if (((int)b > 96 ) && ((int)b < 123)) {
            return ((int)b - 32);   // Kleinbuchstaben von a bis z werden zu Großbuchstaben A bis Z
        } else {
            // im Fall eines nicht zum Alphabet gehörenden Zeichens wird Null zurückgegeben, 
            // dann wird dieses Byte dem ByteArrayOutputStream NICHT hinzugefügt
            return 0;
        }
    }
    
    /**
     * Die Methode schreibt den chiffrierten Text in die Datei "output.txt" 
     * 
     * @param writeout der chiffrierte Text wird als ByteArrayOutputStream übergeben
     * @param filename hier: "output.txt"
     */
    public void Ausgabe(ByteArrayOutputStream writeout, String filename) {
        try {
            FileOutputStream outputStream = new FileOutputStream ("" + filename); 
            writeout.writeTo(outputStream);
        } catch (IOException ie) {
            
        }
    }
    
   /**
     * Die Methode liest die Substitutionstabelle aus der Datei "subst.cfg"
     */
    public void LeseSubstitution() {
        FileInputStream fileInputStream = null;
        File file = new File("subst.cfg");
        byte[] bFile = new byte[(int) file.length()];
        try {
            //convert file into array of bytes
	    fileInputStream = new FileInputStream(file);
	    fileInputStream.read(bFile);
	    fileInputStream.close();
            System.out.println("Filelänge: " + bFile.length);
	       
	    for (int i=0; i<37; i++) {
                    subst[i][0] = bFile[i];
                    System.out.print((char)bFile[i]);
            }    
            System.out.println();
            for (int i=38; i<75; i++) {
                    subst[i-38][1] = bFile[i];
                    System.out.print((char)bFile[i]);
            }   
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }  // end of method
    
    
} // end of class
