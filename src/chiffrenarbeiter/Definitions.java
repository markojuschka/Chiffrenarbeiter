/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiffrenarbeiter;

/**
 * Die Klasse dient dazu, Konstanten für die Arbeit mit den Chiffre-Algorithmen festzulegen 
 * und zusätzlich definiert sie eine sog. "usage-message" (Hilfe oder manual page)
 * 
 * @author user2
 */
public class Definitions {
 
    /**
     * Alle erlaubten Zeichen werden als Alphabet in einem Byte-Array definiert
     */
    public static final byte[] alphabet = {' ','0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
     
    /**
     * Die Mächtigkeit (Länge) des Alphabets ergibt zugleich den Wert, mit dem Modulo zu rechnen ist, 
     * um bei den Chiffre-Algorithmen innerhalb des vorgegebenen Alphabets zu verbleiben. <br> 
     * <b>modulo = alphabet.length</b>
     */
    public static final int modulo = alphabet.length;
    
    /**
     * Alphabet als String - Pendant zum Byte-Array des Alphabets
     */
    public static final String s_alphabet = new String(alphabet);
    
    // die Werte für t im Array müssen alle teilerfremd zur Mächtigkeit des alphabets sein 
    // DA IN DIESEM FALL alphabet.length = 37 EINE PRIMZAHL IST, SIND FÜR t (MULTIPLIKATIONSCHIFFRE) ALLE ZAHLEN BIS 37 MÖGLICH
    // public static final int[] t = {1, 2, 3, ..., 37};
    
    /**
     * Für die Ausgabe (Kryptanalyse des Substitutionschiffre) auf der Konsole wird die Anzahl der Zeichen pro Zeile festgelegt <br> 
     * hier: 80 Zeichen pro Zeile
     */
    public static final int zeilenlaenge4stdout = 80;
    
    
    /**
     * Fester Textstring für die Hilfemeldung bei Aufruf <i><b>java -jar Chiffrenarbeiter.jar -h</b></i> 
     */
    public static final String usage_msg = "\nusage: java -jar Chiffrenarbeiter.jar <option> <param1> <param2> [<param3>] \n\n"
            + "OPTION:\n-e   encrypt text from input textfile\n"
            + "\n\tPARAMETER 1:\n\t v   Verschiebechiffre"
            + "\n\t m   Multiplikative Chiffre"
            + "\n\t t   Tauschchiffre"
            + "\n\t s   Substitutionschiffre nach Subst.-Tabelle"
            + "\n\n\tPARAMETER 2:\n\t [1..36]   Schlüsselwert"
            + "\n\n\tPARAMETER 3: (bei Tauschchiffre)\n\t [1..36]   Schlüsselwert2 (additiver)"
            
            + "\n\nOPTION:\n-d   decrypt text from output textfile\n"
            + "\n\tPARAMETER 1:\n\t v   Verschiebechiffre"
            + "\n\t m   Multiplikative Chiffre"
            + "\n\t t   Tauschchiffre"
            + "\n\t s   Substitutionschiffre nach Subst.-Tabelle"
            + "\n\n\tPARAMETER 2:\n\t 0   alle Schlüsselwerte  (bei Subst.chiffre wird Häufigkeitsanalyse u. Substitutionstabelle erstellt)"
            + "\n\t [1..36]   Schlüsselwert  (1: bei Subst.chiffre wird aufgrund Tabelle 'subst.cfg' ein Versuch ausgegeben)"
            + "\n\n\tPARAMETER 3: (bei Tauschchiffre)\n\t [1..36]   Schlüsselwert2 (additiver)"
            
            + "\n\nOPTION:\n-md5   generates MD5-Hash for textstring in <param1> \n"
            + "\n\tPARAMETER 1:\n\t '...'   Textstring in einfachen Anführungszeichen"
            + "\n\n";
    
    
    /**
     * Die boolsche Variable definiert, ob an den (bisherigen) Konsolen-Ausgaben ("System.out.println(...)") zur Kontrolle der 
     * Werte von Parametern, Variablen o.ä. tatsächlich eine Ausgabe erfolgen soll oder nicht
     */
    public static boolean DEBUG = false;
}
