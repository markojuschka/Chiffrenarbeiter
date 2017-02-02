/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffrenarbeiter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

/**
 * Die Klasse liest den Chiffretext ein, stellt das Objekt, an dem die Dechiffriermethoden angewandt werden,  
 * mittels Konstruktor zur Verfügung, und schreibt den (möglichen) Klartext in eine Datei. <br>
 * Zusätzlich stellt die Klasse Methoden für die Kryptanalyse der Substitutionschiffre zur Verfügung
 * 
 * @author user2
 */
public class Dechiffrierer {
   
    ByteArrayOutputStream baos_2_analyse = new ByteArrayOutputStream(); 
    ByteArrayOutputStream baos_result = new ByteArrayOutputStream(); 
    String text_decrypt = new String();
    int buchstabenzaehler = 0;
    int[] buchstaben_haeufigkeit = new int[37];
    
    byte[] klartext_alphabet = new byte[37];
    byte[] geheimtext_alphabet = new byte[37];
    String substitution = new String();
    byte [][] subst = new byte[37][2];
    
    /**
     * Konstruktor Dechiffrierer <br>
     * Aufgerufen wird der Konstruktor in {@link Chiffrenarbeiter} 
     */
    public Dechiffrierer() {
        int c = 0;  
        try {
            RandomAccessFile f = new RandomAccessFile("output.txt", "r");
            byte[] b = new byte[(int)f.length()];
            f.read(b);
            for (int i=0; i<b.length; i++) {
                c=this.FitToAlphabet(b[i]);
                if (c > 0) {
                    baos_2_analyse.write(b[i]);
                    buchstabenzaehler++;  // Gesamtzahl der Buchstaben im Chiffriertext
                    // Nun wird im Array Buchstabenhäufigkeit der Index um 1 erhöht, 
                    // der dem aktuellen Buchstaben aus dem Chiffriertext entspricht 
                    // UND dieser Buchstabe im Alphabet beim selben Index steht
                    buchstaben_haeufigkeit[Definitions.s_alphabet.indexOf(b[i])]++;
                }
            }
            f.close();   
        } catch (IOException ie) {
            System.out.println("IO-Fehler bei Lesen der Datei 'output.txt'.\n" + ie.toString());
        }     
    }
    
    /**
     * Die Methode FitToAlphabet stellt sicher, dass aus der Menge der eingelesenen Zeichen (output.txt) 
     * nur die erlaubten - also im definierten Alphabet enthaltenen - Zeichen übernommen werden.
     * @param b aus der Datei eingelesenes Byte (Zeichen)
     * @return ASCII-Wert des Zeichens (Kleinbuchstaben werden auf Großbuchstaben gemappt), Rückgabewert 0 entspricht einem nicht gültigen Zeichen 
     * 
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
     * Die Methode Haeufigkeitsliste übernimmt 2 Aufgaben <br>
     * 1. Es werden im Chiffretext die 3 am häufigsten vorkommenden Buchstaben ermittelt, 
     * diese können mit hoher Wahrscheinlichkeit bei einem dt. Text mit den drei im deutschen Alphabet 
     * am häufigsten vorkommenden Buchstaben E, N und I gleichgesetzt werden.
     * <br>
     * 2. Aufgrund der Ergebnisse aus Pkt.1 und der Annahme, dass Leerzeichen und Zahlen auf sich selbst abgebildet werden, <br>
     * erstellt die Methode ein 2-dimensionales Array <b>subst[] [] = new byte [37] [2]</b>, das eine anfängliche Substitutionstabelle abbildet 
     * (siehe Grafik) und diese Ergebnisse werden in die Datei "subst.cfg" geschrieben<br><br>
     * <img src="images/subst_tabelle_v2.png"> <br><br>
     * <b>Beispiele:  <br>
     * <ul>
     * <li>subst[1][0] = 0 und subst[1][1] = 0 <br>
     * <li>subst[11][0] = A und subst[11][1] = .
     * </ul></b>
     */
    public void Haeufigkeitsliste() {
        int index_erster = 0;
        int index_zweiter = 0;
        int index_dritter = 0;
        
        // Bestimmung der höchsten Buchstaben-Häufigkeit im Chiffretext
        int maximal = 0;
        for (int i=0; i<37; i++) {   
            if ((i>11) && (buchstaben_haeufigkeit[i]>maximal)) {
                index_erster = i;
                maximal = buchstaben_haeufigkeit[i];
            } // end of if
        } // end of for
        // Bestimmung der zweithöchsten Buchstaben-Häufigkeit im Chiffretext
        maximal = 0;
        for (int i=0; i<37; i++) {
            if ((i>11) && (buchstaben_haeufigkeit[i]>maximal) && (buchstaben_haeufigkeit[i]<buchstaben_haeufigkeit[index_erster])) {
                index_zweiter = i;
                maximal = buchstaben_haeufigkeit[i];
            } // end of if
        } // end of for
        // Bestimmung der dritthöchsten Buchstaben-Häufigkeit im Chiffretext
        maximal = 0;
        for (int i=0; i<37; i++) {
            if ((i>11) && (buchstaben_haeufigkeit[i]>maximal) && (buchstaben_haeufigkeit[i]<buchstaben_haeufigkeit[index_zweiter])) {
                index_dritter = i;
                maximal = buchstaben_haeufigkeit[i];
            } // end of if
        } // end of for
        
        String prozent_erster = String.format("%.2f", (double)buchstaben_haeufigkeit[index_erster]*100/buchstabenzaehler);
        String prozent_zweiter = String.format("%.2f", (double)buchstaben_haeufigkeit[index_zweiter]*100/buchstabenzaehler);
        String prozent_dritter = String.format("%.2f", (double)buchstaben_haeufigkeit[index_dritter]*100/buchstabenzaehler);
        System.out.println("Häufigkeiten im Chiffretext: ");
        System.out.println("1.Platz ("+ prozent_erster +"%) : " 
                + (char)Definitions.alphabet[index_erster] + " <--> 'E' im Klartextalphabet");
        System.out.println("2.Platz  ("+ prozent_zweiter +"%) : " 
                + (char)Definitions.alphabet[index_zweiter] + " <--> 'N' im Klartextalphabet");
        System.out.println("3.Platz  ("+ prozent_dritter +"%) : " 
                + (char)Definitions.alphabet[index_dritter] + " <--> 'I' im Klartextalphabet");
        
        // Erstelle die vorläufige / anfängliche Substitutionstabelle
        for (int i=0; i<37; i++) {
            if (i==15) {
                // das "E" entspricht dem i==15
                subst[i][0] = Definitions.alphabet[i];   // der Buchstabe "E" als häufigster Buchstabe im dt. Alphabet
                subst[i][1] = Definitions.alphabet[index_erster];  // häufigster Buchstabe im Chiffretext
            } 
            else if (i==24) {
                // das "N" entspricht dem i==24
                subst[i][0] = Definitions.alphabet[i];   // der Buchstabe "N" als 2.häufigster Buchstabe im dt. Alphabet
                subst[i][1] = Definitions.alphabet[index_zweiter];  // 2.häufigster Buchstabe im Chiffretext
            }
            else if (i==19) {
                // das "I" entspricht dem i==19
                subst[i][0] = Definitions.alphabet[i];   // der Buchstabe "I" als 3.häufigster Buchstabe im dt. Alphabet
                subst[i][1] = Definitions.alphabet[index_dritter];  // 3.häufigster Buchstabe im Chiffretext
            } 
            else {
                subst[i][0] = Definitions.alphabet[i];
                subst[i][1] = (byte)'.';
            }
            if (i<11) {
                subst[i][0] = Definitions.alphabet[i];
                subst[i][1] = Definitions.alphabet[i];
            }
        } // end of for
    }  // end of method
    
    public void Ausgabe(ByteArrayOutputStream writeout, String filename) {
        try {
            FileOutputStream outputStream = new FileOutputStream (filename); 
            writeout.writeTo(outputStream);
        } catch (IOException ie) {
        }
    }  // end of method
    
    /**
     * Die Methode schreibt die Substitutionstabelle in die Datei "subst.cfg"
     */
    public void SchreibeSubstitution() {
        // Herunterschreiben von Klar- u. Geheimtextalphabet auf 1-dimensionale Arrays
        for (int i=0; i<37; i++) {
            klartext_alphabet[i] = subst[i][0];
            geheimtext_alphabet[i] = subst[i][1];
            // Ausgabe am Bildschirm
            if (Definitions.DEBUG) { System.out.println("klar["+i+"]: " + (char)subst[i][0] + " --- geheim["+i+"]: " + (char)subst[i][1]); }
        }
        try {
            FileOutputStream fos = new FileOutputStream("subst.cfg");
            fos.write(klartext_alphabet);
            fos.write((byte)13);
            fos.write((byte)10);
            fos.write(geheimtext_alphabet);
            fos.close();
        } catch (IOException e) {
        }  
        System.out.println("Datei 'subst.cfg' wurde erstellt.");     
    }  // end of method
    
    /**
     * Die Methode liest die Substitutionstabelle aus der Datei "subst.cfg"
     */
    public void LeseSubstitution() {
        byte [] input = new byte[56];
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
            if ((char)bFile[39]=='0') {
                for (int i=38; i<75; i++) {
                    subst[i-38][1] = bFile[i];
                    System.out.print((char)bFile[i]);
                }
            } else {
                for (int i=39; i<75; i++) {
                    subst[i-39][1] = bFile[i];
                    System.out.print((char)bFile[i]);
                }
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }
        System.out.println();
    }  // end of method

    /**
     * Die Methode gibt auf der Konsole auf Basis der aktuellen Substitutionstabelle in der Datei "subst.cfg"
     * einen zeilenweisen Dechiffrier-Versuch aus 
     */
    public void DruckeVersuch() {
        int index = 0;
        int zeilencounter = 0;
        int zeilenlaenge = Definitions.zeilenlaenge4stdout;
        // Bytearray für den chiffrierten Text 
        byte[] printchiffre = this.baos_2_analyse.toByteArray();
        // Bytearray für den möglichen dechiffrierten Text (Versuch nach Substitutionstabelle!!)
        byte[] printdechiffre = new byte[printchiffre.length];
        
        for (int i=0; i<printchiffre.length; i++) {
            
            if ( (i>0) && (i%zeilenlaenge==0) ) { 
                System.out.println();
                for (int j=(0+zeilencounter*zeilenlaenge); j<(zeilenlaenge+zeilencounter*zeilenlaenge); j++) { 
                    System.out.print((char)printdechiffre[j]);
                }
                System.out.println("\n");
                zeilencounter++;
            }  // enbd of if
            
            System.out.print((char)printchiffre[i]);
            index = this.InSubstitutionEnthalten((char)printchiffre[i]);
            if (index>-1) {
                printdechiffre[i] = subst[index][0];
            } else {
                printdechiffre[i] = 46;  // ASCII-Code für '.'  (Platzhalter in "subst.cfg")
            }        
            
            // Die allerletzte Reihe erhält noch die Dechiffre Textzeichen
            if (printchiffre.length==i+1) {
                
                System.out.println();
                for (int j=(0+zeilencounter*zeilenlaenge); j<(printchiffre.length); j++) { 
                    System.out.print((char)printdechiffre[j]);
                }
                System.out.println("\n");
            }
            
        }  // end of for
    }  // end of method
   
    /**
     * Gibt den Indexwert zurück, falls das übergebene Zeichen in der Substitutionstabelle schon vorhanden ist, 
     * ansonsten swird ein negativer Wert zurückgegeben
     * @param a Zeichen (char), was in der Substitutionstabelle gesucht wird
     * @return Indexwert (1. Dimension) von subst[][], falls vorhanden, ansonsten -1 
     */
    public int InSubstitutionEnthalten(char a) {
        for (int i=0; i<37; i++) {
            if ((char)subst[i][1]==a) {
                return i;
            } 
        }
        return -1;
    }  // end of method
    
} // end of class
