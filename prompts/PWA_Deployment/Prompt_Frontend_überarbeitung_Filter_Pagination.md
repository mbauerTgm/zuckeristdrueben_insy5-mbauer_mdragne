# Prompt_Frontend_überarbeitung_Filter_Pagination
## Gemini Pro 3


\[Kontext\]: Frontend

Ich habe das Frontend und Backend um Pagination und Filterung erweitert, ich habe bereits den fetch angepasst und die Variablen hinzugefügt. Bitte füge hier mit den neuen Pagination Variablen ein Pagination System ein. Wenn man auf eine andere Seite wechselt, soll das fetch wieder aufgerufen werden. Gib den edit und delete buttons bitte auch schönere Farben. Füge bitte auch noch ein Feld ähnlich wie bei spalten ein, bei dem man wenn man in analysis ist folgende Ranges eingeben können soll:

 private Range<Long> id;



    private Range<String> sId;



    private Range<LocalDate> dateIn;

    private Range<LocalDate> dateOut;



    private Range<String> aFlags;



nene das feld backend filterung und gib diese eingaben so dann in den fetch befehl hinein:



if (this.searchParams.dateInFrom) {

params.append('dateIn.from', this.searchParams.dateInFrom);

}

Und bitte mach das wenn man das Spalten Fenster öffnet beim ersten mal immer alle optionen ausgewählt sind.



Überlege alle schritte gut und verändere nur was nötig ist!

--- 

Hier ist der angepasste Code für Frontend.vue.

Ich habe folgende Änderungen vorgenommen:

Pagination: Unter der Tabelle wurden Buttons für Vor/Zurück und Seitennummern hinzugefügt. Die fetchTableData Methode sendet nun den page Parameter mit.

Backend-Filter: Ein neuer "Filter"-Button (nur bei Tabelle 'Analysis') öffnet ein Menü für die Ranges (id, sId, dateIn, dateOut, aFlags). Die Werte werden als field.from und field.to an das Backend gesendet.

Spalten-Logik: Beim Laden neuer Daten werden nun automatisch alle Spalten in die Auswahl übernommen (selectedColumns), damit die Checkboxen beim ersten Öffnen gefüllt sind.

Farben: Die Buttons für Editieren (jetzt ein angenehmes Orange) und Löschen (ein weicheres Rot) wurden optisch aufgewertet und an das Dark-Mode-Theme angepasst.