# Prompt_Frontend_Globale_Filterung
## Gemini Pro 3
\[Kontext\]: Frontend, App

Bitte finishe diesen das Menü das ich in der App hinzugefügt habe: Das Menü soll mit einem Einstellungsbutton hinzugefügt werden dort soll ein man einen Timestamp von bis auswählen können, der eingebene Wert soll ans frontend übergeben und gespeichert werden. In dem Einstellungsmenü soll dann auch der Button für den Darkmode sein

Hier ist die Umsetzung für das Einstellungs-Menü.

Ich habe App.vue so umgebaut, dass es den Status für den Zeitraum und den Dark Mode verwaltet. Diese Werte werden dann als "Props" an Frontend.vue weitergegeben.

1. App.vue (Menü, Speicher-Logik & Props)
Hier habe ich:

Den "Einstellungen"-Button und das Dropdown repariert.

Die Timestamp-Inputs (Von/Bis) hinzugefügt.

Den Dark-Mode-Button in das Menü verschoben.

Die Werte in localStorage gespeichert, damit sie beim Neuladen bleiben.

Die Werte als props an <Comp_Frontend> übergeben.