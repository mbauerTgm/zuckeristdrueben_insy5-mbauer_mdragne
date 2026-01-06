# Prompt Backend Reports
### Gemini 3 Pro

\[Kontext\]: Datenbank Struktur + SQL Methoden + RestController
Erstellung und Anzeige der Reports auf Basis der SQL-Funktionen
Die Datenbank enthält vordefinierte SQL-Funktionen, die für tägliche
Reports gebraucht werden. Es sollen folgende Punkte analysiert und
angezeigt werden:
Rueckstellbehaelter beinhalten Probenummern ohne Analyse

Rueckstellbehaelter beinhalten keine Probenummer

Verdaechtige Probenummern im gewünschten Zeitraum

Analysen haben keine Rueckstellposition

Analysen haben 0 Werte

Analysen haben keine Start- bzw. Endzeit

Proben haben mehr als 1 Analyse (mit Angabe der Anzahl)

Verdaechtige Probenummern

Probenummern mit falscher EAN13 Pruefziffer

diese Punkte benötige ich, da ist meine sql und mein restcontroller, kannst du mir sagen wie ich das umsetze und was ich noch hinzufügen muss?, ich habe schon das springboot projekt und entities. 

---

hat die restore.sql die funktionen aber? also kann mit der die funktionen genutzt werden? oder brauch ich wirklich die query abfragen in den repositories?

---

okay, danke für die Hilfe