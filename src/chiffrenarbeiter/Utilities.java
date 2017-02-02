/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffrenarbeiter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Die Klasse Utilities stellt einige statische Methoden für die Chiffrierung 
 * und Dechiffrierung zur Verfügung
 * 
 * @author user2
 */
public class Utilities {
    
    /**
     * Methode für die Verschiebechiffre z --> (z + k) mod n
     * @param text2encrypt Klartext
     * @param shift Verschiebung um k Zeichen im Alphabet
     * @return Chiffretext
     */
    public static ByteArrayOutputStream EncryptCaesar (ByteArrayOutputStream text2encrypt, int shift) {
        ByteArrayOutputStream rueckgabe = new ByteArrayOutputStream();
        byte[] textfeld = text2encrypt.toByteArray();
        int pos = 0;
        for (int z=0; z<textfeld.length; z++) {
            pos = PositionImAlphabet(textfeld[z]);
            // Verschiebung nach Caesar, ausgehend von der ermittelten Position pos 
            // wird um den Wert shift im definierten Alphabet verschoben, dazu modulo Länge des Alphabets
            textfeld[z] = Definitions.alphabet[((pos + shift) % Definitions.modulo)];
        }
        try {
            rueckgabe.write(textfeld);
        } catch (IOException ie) {}
        return rueckgabe;
    }
    
    /**
     * Methode zum Dechiffrieren der Verschiebechiffre z --> (z + k) mod n
     * @param text2decrypt Chiffretext
     * @param shift Verschiebung um k Zeichen im Alphabet
     * @return Klartext
     */
    public static ByteArrayOutputStream DecryptCaesar (ByteArrayOutputStream text2decrypt, int shift) {
        ByteArrayOutputStream rueckgabe = new ByteArrayOutputStream();
        byte[] textfeld = text2decrypt.toByteArray();
        int pos = 0;
        for (int z=0; z<textfeld.length; z++) {
            pos = PositionImAlphabet(textfeld[z]);
            // Verschiebung nach Caesar, ausgehend von der ermittelten Position pos 
            // wird um den Wert shift im definierten Alphabet verschoben, dazu modulo Länge des Alphabets
            if (pos-shift < 0) {pos = pos + Definitions.modulo;}
            textfeld[z] = Definitions.alphabet[((pos - shift) % Definitions.modulo)];
        }
        // hoffentlich obsolet: text2decrypt.reset();
        try {
            rueckgabe.write(textfeld);
        } catch (IOException ie) {}
        return rueckgabe;
    }
    
    /**
     * Methode für die multiplikative Chiffre z --> (z * t) mod n
     * @param text2encrypt Klartext
     * @param multiplikator Mulitplikator t für jedes Klartextzeichen z 
     * @return Chiffretext
     */
    public static ByteArrayOutputStream ChiffreMultiplikativ (ByteArrayOutputStream text2encrypt, int multiplikator) {
        ByteArrayOutputStream rueckgabe = new ByteArrayOutputStream();
        byte[] textfeld = text2encrypt.toByteArray();
        int pos = 0;
        for (int z=0; z<textfeld.length; z++) {
            pos = PositionImAlphabet(textfeld[z]);
            textfeld[z] = Definitions.alphabet[((pos * multiplikator) % Definitions.modulo)];
        }
        try {
            rueckgabe.write(textfeld);
        } catch (IOException ie) {}
        return rueckgabe;
    }
    
    /**
     * Methode zum Dechiffrieren der multiplikativen Chiffre z --> (z * t) mod n
     * @param text2decrypt Chiffretext
     * @param multiplikator Mulitplikator t für jedes Klartextzeichen z 
     * @return Klartext
     */
    public static ByteArrayOutputStream DechiffrierMultiplikativ (ByteArrayOutputStream text2decrypt, int multiplikator) {
        ByteArrayOutputStream rueckgabe = new ByteArrayOutputStream();
        byte[] textfeld = text2decrypt.toByteArray();
        int pos = 0;
        for (int z=0; z<textfeld.length; z++) {
            pos = PositionImAlphabet(textfeld[z]);
            textfeld[z] = Definitions.alphabet[((pos * multiplikator) % Definitions.modulo)];
        }
        try {
            rueckgabe.write(textfeld);
        } catch (IOException ie) {}
        return rueckgabe;
    }
    
    /**
     * Die Methode realisiert einen Tauschchiffre z --> (Z * t + k) mod n
     * @param text2encrypt Klartext
     * @param multiplikator Mulitplikator t für jedes Klartextzeichen z 
     * @param shift Verschiebung um k Zeichen im Alphabet
     * @return Chiffretext
     */
    public static ByteArrayOutputStream ChiffreTausch (ByteArrayOutputStream text2encrypt, int multiplikator, int shift) {
        ByteArrayOutputStream rueckgabe = new ByteArrayOutputStream();
        byte[] textfeld = text2encrypt.toByteArray();
        int pos = 0;
        for (int z=0; z<textfeld.length; z++) {
            pos = PositionImAlphabet(textfeld[z]);
            textfeld[z] = Definitions.alphabet[(((pos * multiplikator) + shift) % Definitions.modulo)];
        }
        try {
            rueckgabe.write(textfeld);
        } catch (IOException ie) {}
        return rueckgabe;
    }
    
    /**
     * Die Methode realisiert das Dechiffrieren eines Tauschchiffre z --> (Z * t + k) mod n
     * @param text2decrypt Chiffretext
     * @param multiplikator Mulitplikator t für jedes Klartextzeichen z 
     * @param shift Verschiebung um k Zeichen im Alphabet
     * @return Klartext
     */
    public static ByteArrayOutputStream DechiffrierTausch (ByteArrayOutputStream text2decrypt, int multiplikator, int shift) {
        ByteArrayOutputStream rueckgabe = new ByteArrayOutputStream();
        byte[] textfeld = text2decrypt.toByteArray();
        int pos = 0;
        for (int z=0; z<textfeld.length; z++) {
            pos = PositionImAlphabet(textfeld[z]);
            textfeld[z] = Definitions.alphabet[(((pos * multiplikator) + shift) % Definitions.modulo)];
        }
        try {
            rueckgabe.write(textfeld);
        } catch (IOException ie) {}
        return rueckgabe;
    }
    
    public static ByteArrayOutputStream ChiffreSubst_Tabelle (ByteArrayOutputStream text2encrypt, byte[][] tabelle) {
        ByteArrayOutputStream rueckgabe = new ByteArrayOutputStream();
        byte[] textfeld = text2encrypt.toByteArray();
        int pos = 0;
        for (int z=0; z<textfeld.length; z++) {
            pos = PositionImAlphabet(textfeld[z]);
            textfeld[z] = tabelle[pos][1];
        }
        try {
            rueckgabe.write(textfeld);
        } catch (IOException ie) {}
        return rueckgabe;
    }
    
    /**
     * Liefert den Index des gesuchten Zeichens im festgelegten Alphabet zurück
     * @param buchstabe aktuelles Zeichen
     * @return Index (Position) im Alphabet
     */
    public static int PositionImAlphabet(byte buchstabe) {
        return Definitions.s_alphabet.indexOf(buchstabe);
    }
}
