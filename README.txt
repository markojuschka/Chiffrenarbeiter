README:
-------

Das Java-Programm "Chiffrenarbeiter" ist mit Hilfe von NetBeans IDE 8.0.2 entwickelt worden.
Der Quellcode ist unter "src" zu finden, eine Quellcode-Dokumentation unter "dist/javadoc". 


Der Klartext, welcher zu chiffrieren ist, sollte sich standardmässig im Hauptordner 
in einer Datei namens "input.txt" befinden. Das Chiffrat wird dann in eine Datei 
namens "output.txt" geschrieben. 
Für die Krpytoanalyse wird dann aus der Datei "output.txt" gelesen und das (die) Ergebnis(se) 
in eine Datei namens "result.txt" geschrieben. 

 
Aus dem Hauptverzeichnis heraus lässt sich eine Hilfe aufrufen mittels:

> java -jar "dist/Chiffrenarbeiter.jar" -h

usage: java -jar Chiffrenarbeiter.jar <option> <param1> <param2> [<param3>] 

OPTION:
-e   encrypt text from input textfile

	PARAMETER 1:
	 v   Verschiebechiffre
	 m   Multiplikative Chiffre
	 t   Tauschchiffre
	 s   Substitutionschiffre nach Subst.-Tabelle

	PARAMETER 2:
	 [1..36]   Schlüsselwert

	PARAMETER 3: (bei Tauschchiffre)
	 [1..36]   Schlüsselwert2 (additiver)

OPTION:
-d   decrypt text from output textfile

	PARAMETER 1:
	 v   Verschiebechiffre
	 m   Multiplikative Chiffre
	 t   Tauschchiffre
	 s   Substitutionschiffre nach Subst.-Tabelle

	PARAMETER 2:
	 0   alle Schlüsselwerte  (bei Subst.chiffre wird Häufigkeitsanalyse u. Substitutionstabelle erstellt)
	 [1..36]   Schlüsselwert  (1: bei Subst.chiffre wird aufgrund Tabelle 'subst.cfg' ein Versuch ausgegeben)

	PARAMETER 3: (bei Tauschchiffre)
	 [1..36]   Schlüsselwert2 (additiver)

OPTION:
-md5   generates MD5-Hash for textstring in <param1> 

	PARAMETER 1:
	 '...'   Textstring in einfachen Anführungszeichen



