/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffrenarbeiter;

import java.io.ByteArrayOutputStream;

/**
 * Die Klasse Chiffrenarbeiter ist Haupt- und Startklasse der Anwendung.<br>
 * Mittels Eingabeparamter können Chiffrier- bzw. Dechiffrieralgorithmen ausgewählt werden, 
 * ebenso die Erstellung eines MD5-Hashes für einen Textstring.<br>
 * Eine Auflistung aller Parameter erhält man so:  <i><b>java -jar Chiffrenarbeiter.jar -h</b></i>
 * @author user2
 */
public class Chiffrenarbeiter {
    
    /**
     * Die Startmethode main sorgt anhand der Übergabeparameter für den Aufruf der entsprechenden 
     * Chiffrier- oder Dechiffriermethoden bzw. den Aufruf der Methode zur Erzeugung des MD5-Hashes
     * @param args Übergabeparameter-Feld an die Anwendung Chiffrenarbeiter 
     * @see chiffrenarbeiter.Definitions#usage_msg usage_msg für weitere Details der Parameter 
     */
    public static void main(String[] args) {    

        String chiffriermodus = "";
        int chiffrierparameter = 0;
        int chiffrierparameter2 = 0;
        ByteArrayOutputStream baos_bruteforce = new ByteArrayOutputStream();
        
        // Print "usage ..." if asked for help: '-h'
        if ((args.length>0) && (args[0].equals("-h"))) {     
            System.out.println(Definitions.usage_msg);
            System.exit(0);
        }
        
        // Modus: Erzeugen eines MD5-Hashes
        if ((args.length>0) && (args[0].equals("-md5"))) { 
            String text2md5 = "";
            if ((args.length>1) && (args[1]!=null)) {
               text2md5 = args[1];
                
            } else {
                System.out.println(Definitions.usage_msg);
                System.exit(0);
            }
            
            // Chiffrenerzeuger ce = new Chiffrenerzeuger();
            // String test = "nie wieder, oder doch?";
            System.out.println("TEXT:  " + text2md5);
            System.out.println("MD5 :  0x" + MD5_Hash.toHexString(MD5_Hash.computeMD5(text2md5.getBytes())));
            System.exit(0);
        }  // end of if
        
        
        
        // Modus: Erzeugen eines Chiffres
        // Einlesen der Übergabeparameter
        if ((args.length>0) && (args[0].equals("-e"))) {
            Chiffrenerzeuger ce = new Chiffrenerzeuger();
            
            if ((args.length>2) && (args[1]!=null) && (args[2]!=null)) {
                chiffriermodus = args[1].toLowerCase();
                chiffrierparameter = Integer.parseInt(args[2]);
                if (args.length>3) {
                    chiffrierparameter2 = Integer.parseInt(args[3]);
                }
            } else if ((args.length==2) && (args[1].equals("s"))) {
                chiffriermodus = args[1].toLowerCase(); // Substitutionschiffre nach Tabelle
            } else {
                System.out.println(Definitions.usage_msg);
                System.exit(0);
            } 
            // welcher Chifffriermodus
            switch(chiffriermodus){
                case "v":
                    System.out.println("Verschiebechiffre anwenden, Schlüssel: " + chiffrierparameter);
                    ce.baos_encrypted = Utilities.EncryptCaesar(ce.baos, chiffrierparameter);
                    System.out.println(ce.baos_encrypted.toString() + "   (Verschiebechiffre)");
                    ce.Ausgabe(ce.baos_encrypted, "output.txt");
                break;
                case "m":
                    System.out.println("Multiplikative Chiffre anwenden, Schlüssel: " + chiffrierparameter);
                    ce.baos_encrypted = Utilities.ChiffreMultiplikativ(ce.baos, chiffrierparameter);
                    System.out.println(ce.baos_encrypted.toString() + "   (Multiplikative Chiffre)");
                    ce.Ausgabe(ce.baos_encrypted, "output.txt");
                break;
                case "t":
                    System.out.println("Tauschchiffre anwenden, Schlüssel1: " + chiffrierparameter + ", Schlüssel2: " + chiffrierparameter2);
                    ce.baos_encrypted = Utilities.ChiffreTausch(ce.baos, chiffrierparameter, chiffrierparameter2);
                    System.out.println(ce.baos_encrypted.toString() + "   (Tauschchiffre)");
                    ce.Ausgabe(ce.baos_encrypted, "output.txt");
                break;
                case "s":
                    System.out.println("Substitutionschiffre anwenden, Tabelle in Datei 'subst.cfg'");
                    ce.LeseSubstitution();
                    ce.baos_encrypted = Utilities.ChiffreSubst_Tabelle(ce.baos, ce.subst);
                    System.out.println(ce.baos_encrypted.toString() + "   (Substitutionschiffre nach Tabelle)");
                    ce.Ausgabe(ce.baos_encrypted, "output.txt");
                break;
                default:
                    System.out.println(Definitions.usage_msg);
                    System.exit(0);
            } // end of switch            
        } // end of if
        
        // Modus: Analyse eines Chiffres
        // Einlesen der Übergabeparameter
        if ((args.length>0) && (args[0].equals("-d"))) {
            Dechiffrierer de = new Dechiffrierer();
            if ((args.length>2) && (args[1]!=null) && (args[2]!=null)) {
                chiffriermodus = args[1].toLowerCase();
                chiffrierparameter = Integer.parseInt(args[2]);
                if ((args.length>3) && (!args[3].equals("-i"))) {
                    chiffrierparameter2 = Integer.parseInt(args[3]);
                }
            } else {
                System.out.println(Definitions.usage_msg);
                System.exit(0);
            } 
            
            // welcher Dechifffriermodus
            switch(chiffriermodus){
                case "v":
                    if (chiffrierparameter == 0) {
                        for (int p=0; p<37; p++) {
                            System.out.print("Verschiebechiffre anwenden, Schlüssel: " + p + "  **  ");
                            System.out.println(de.baos_2_analyse.toString() + "   (Chiffretext)");
                            de.baos_result = Utilities.DecryptCaesar(de.baos_2_analyse, p);
                            System.out.println(de.baos_result.toString() + "   (Verschiebechiffre)");
                        }
                        // TO DO: de.Ausgabe...
                    } else {
                        System.out.println("Verschiebechiffre anwenden, Schlüssel: " + chiffrierparameter);
                        de.baos_result = Utilities.DecryptCaesar(de.baos_2_analyse, chiffrierparameter);
                        System.out.println(de.baos_result.toString() + "   (Verschiebechiffre)");
                        de.Ausgabe(de.baos_result, "result.txt");
                    }
                break;
                case "m":
                    if (chiffrierparameter == 0) {
                        for (int p=1; p<37; p++) {
                            System.out.println("Multiplikative Chiffre anwenden, Schlüssel: " + p);
                            de.baos_result = Utilities.DechiffrierMultiplikativ(de.baos_2_analyse, p);
                            System.out.println(de.baos_result.toString() + "   (Multiplikative Chiffre)");
                        }
                        // TO DO: de.Ausgabe...
                    } else {
                        System.out.println("Multiplikative Chiffre anwenden, Schlüssel: " + chiffrierparameter);
                        de.baos_result = Utilities.DechiffrierMultiplikativ(de.baos_2_analyse, chiffrierparameter);
                        System.out.println(de.baos_result.toString() + "   (Multiplikative Chiffre)\");\n"); 
                        de.Ausgabe(de.baos_result, "result.txt");
                    }                   
                break;
                case "t":
                    if (chiffrierparameter == 0) {
                        for (int p1=1; p1<37; p1++) {
                            for (int p2=1; p2<37; p2++) {
                                System.out.println("Tauschchiffre anwenden, Schlüssel1: " + p1 + ", Schlüssel2: " + p2);
                                de.baos_result = Utilities.DechiffrierTausch(de.baos_2_analyse, p1, p2);
                                System.out.println(de.baos_result.toString() + "   (Tauschhiffre)");
                            }
                            // TO DO: de.Ausgabe...
                        }
                        
                    } else {
                        System.out.println("Tauschchiffre anwenden, Schlüssel1: " + chiffrierparameter + ", Schlüssel2: " + chiffrierparameter2);
                        de.baos_result = Utilities.DechiffrierTausch(de.baos_2_analyse, chiffrierparameter, chiffrierparameter2);
                        System.out.println(de.baos_result.toString() + "   (Tauschchiffre)\");\n"); 
                        de.Ausgabe(de.baos_result, "result.txt");
                    }                   
                break;
                case "s":
                    if (chiffrierparameter == 0) {
                        de.Haeufigkeitsliste();  // erstelle eine Liste mit der Häufigkeit der Buchstaben im Chiffretext
                        de.SchreibeSubstitution();  // es wird die Datei "subst.cfg" erstellt, 
                                                    // die für die Dechiffrier-Versuche eingesetzt wird
                    } else {
                        // Aufgrund vorhandener Datei "subst.cfg" (Alphabet-Ersetzungstabelle)
                        // wird ein Dechiffrierversuch unternommen und im Display dargestellt
                        de.LeseSubstitution();
                        de.DruckeVersuch();
                        
                    }
                break;
                default:
                    System.out.println(Definitions.usage_msg);
                    System.exit(0);
            } // end of switch 
        } // end of if
           
    }
    
} // end of class
