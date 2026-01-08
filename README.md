# Informationssysteme "PWA Deployment" GK
von: Maximilian Bauer, Matei Dragne, Denis Gernesch 5CHITM

## Continuous Integration
Um bei jedem Push auf GitHub die vollständige Lauffähigkeit des Projekts sicherzustellen, wurde mithilfe von GitHub Actions ein automatisierter Integrationstest (CI – Build & Tests) eingerichtet. Darin wird das Projekt in Docker gebaut, die erforderliche Datenstruktur eingespielt und anschließend End-to-End-Tests (E2E) mit Cypress durchgeführt.

## Deployment & CD
Vorbereitung des Servers: Auf einem Privaten Cloud Server (über Oracle) wurde die Website gehostet. Dazu mussten zuerst in der OCI die Port 80 und 443 im Subnetz der Servers freigegeben werden. Danach mussten sie aufgrund der Voreinstellungen von Oracle im Linux System extra freigegeben werden. 

Während dieser Aufgabe wurden ein automatisches Deployment mittels Github Actions ermöglicht. Dabei werden bei einem Push auf den Main-Branch im CD alle Images erstellt. Anschließend werden diese auf einem Cloudserver gepulled und gestartet. Damit der Workflow auf den Server zugreifen kann wurden in den Github secrets sowohl die IP des Servers (HOST), als auch der Server Username und Privatekey des Servers eingetragen. Weiters wurde ein Deploy Key für den Serveruser erstellt, damit dieser änderungen aus dem Repo Pullen kann.

Die Website ist unter [https://zuckeristdrueben.live](https://zuckeristdrueben.live) erreichbar. Die Domain wurde beim Anbieter mit der IP des Servers verbunden.

Um das SSL-Zertifikat zu erhalten wurde der Caddy Reverse-Proxy verwendet.

### Einspielen von Backups
Damit der Postgres Container direkt mit einem DB Schema bespielt wird wurde das Restore.sql aufgeteilt, in init_schema.sql und import_backups.sql aufgeteilt. Für eine lokale- bzw. Testumgebung einget sich weiterhin der in "lokale Verwendung" beschriebene Befehl mit der Restore.sql Datei.

Um die Backupdateien auf dem Server einspielen zukönnen muss selbstverständlich direkter Zugriff auf den Server bestehen. Ist das der Fall und die Dateien sind unter /backups/ verfügbar, kann folgender Befehlt ausgeführt werden um die Daten in die Datenbank einzuspielen:

```bash
docker exec -i zuckerpostgres psql -U postgres -d database -f /backups/import_backups.sql
```

### Server zurücksetzten

Um die Container zulöschen, damit sie beim nächsten deploy komplett neu gemacht werden, kann folgender Befehl verwendet werden:

```bash
docker compose -f docker-compose.yml -f docker-compose.prod.yml down
```

Um die forcefully alle Daten der Datenbank zu löschen (inklusive der Schemas und der gesammten Datenbank) kann auf dem Server im Ordner in dem auch der Container liegt folgender Befehl benutzt werden:

```bash
sudo rm -rf postgres-data
```

# Informationssysteme "PWA Deployment" EK
von: Maximilian Bauer, Matei Dragne 5CHITM

## Login mit JWT Tokens und Roles
Wir haben das Projekt um ein Security System erweitert, dass mit JWT/JWA und HTTP-Only Cookies logins sicher hält. Dazu wurden zwei neue Datenbank Schemas, Users und Role in der Datenbank eingebracht. Dann haben wir im Backend einen AuthControler hinzugefügt, der die API Endpoint für Login & out enthält, als auch einige Services wie den Passwortencrypter und den Service der die JTW Tokens erstellt. Diese werden dem Browser des Users übergeben, in den Cookies gespeichert.

 Nun wird bei jedem API Call durch den Befhel "credentials: 'include'" im Browser nach Cookies für die Domain gesucht und im Request mit geschickt. Bevor irgendein Controller (z.B. getAllAnalysis) ausgeführt wird, fängt dieser Filter in der Klasse 'JwtTokenFilter' die Anfrage ab. Er sucht im Request nach dem Cookie 'jwt', wenn er vorhanden ist überprüft er ihn. Wenn alles passt, liest er die User-ID und die Rollen (z.B. "Admin", "Researcher") aus dem Token aus und leitet den Befehl an den gesuchten Controller weiter.

Es gibt folgende Rollen:
- Admin: Hat volle Rechte, kann alle Daten einsehen, bearbeiten und löschen, kann neue Datensätze anlegen und (im Frontend noch nicht implementiert) neue Nutzer anlegen.
- Researcher: Kann Datensätze bearbeiten und erstellen, aber nicht löschen. Er kann in den Tabellen nur Daten aus Sample und Analysis sehen, bei denen die Flags mit F oder V beginnen.
- Reader: Kann alle Daten lesen (Read Only)

#### Einfügen eines Testusers in die Datenbank
Falls für das das lokale verwenden oder das ausführen der Tests, eine User benötigt wird kann mit folgendem Befehl ein User mit dem Username: 'TestAdmin' und dem Passwort 'Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253', in die Datenbank integriert werden:

```bash
docker exec -i zuckerpostgres psql -U postgres -d database -c "INSERT INTO venlab.users (username, password_hash, role_id, created_at) VALUES ('TestAdmin', '$2a$12$/x3aVXCCLs6YCvN5O4lwhu3TLNLfncNqhRAiIrkfFXkC0GrPYVU..', 1, NOW());"
```
Der Vorletzte Parameter in diesem INSERT Befehl ist die Rolle des Users. 1 - Admin, 2 - Researcher, 3- Reader. 
Der letzte Parameter ist der erstellungs Zeitstempel.

## Filterung & Paging im Backend
Um eine bessere Performance sowohl im Front- als auch im Backend zu erzielen wurde eine Filterung und ein Paging im Backend umgesetzt.

Für das Paging wurde Springboots Pagable interface verwendet. Es sortiert Daten und Paginiert diese automatisch im Backend. Dabei übergibt das Interface bei jedem Request die Daten im context[], aber auch Informationen für das Paging wie Seite, insgesammte Seiten und insgesammte Items der Tabelle. Dadurch musste das Frontend kaum geändert werden. 

Die Filterung im Backend wurde mittels Specifications und DynamicFilter umgesetzt.
Das Backend bekommt durch POJO Angaben, die Durch ein Dto vordefiniert sind, im HTTP Request mittgeteilt nach was gefiltert werden soll. Die Angaben werden dann durch die Implementierung der Specifications und DynamicFilter in fertige Querys umgewandelt und (gepaded) wieder an das Frontend geschickt.

Die ursprünglichen GET Request wurden nicht ersetzt, sondern die Server Paginated Verion und bei den Tabellen Analysis und Sample Serverseitig gefilterte Version ist unter `/api/<tablename>/filter` zu finden.

## Eingeschränkte Daten Anzeige für Researcher
Die Einschränkung für Reseacher wurde ebenfalls durch eine filterung im Backend umgesetzt.
Da wir zuerst das Security- und Rollenmanagment implementiert haben und danach den jenen Punkt hatten wir zwei Iterationen dieser Rollenbasierten Filterung. Bei beiden wurde aus dem SecurityContext die Rolle des Benutzers entnommen.

Zuerst wurde eifach das Repository von Analysis und Sample um eine Funktion mit @Query Annotation und simpler Sql Abfrage.
```java
@Query("SELECT a FROM Analysis a WHERE a.aFlags LIKE 'F%' OR a.aFlags LIKE 'V%'")
List<Analysis> findAllForResearcher();
```

Als die Backendfilterung mit Specifications und DynamicFilter implementiert wurde setzten wir es mittels `.add(...)` um. Damit wurde an den Query ein `AND` mit der Flags Bedinngung für den Researcher drangehängt.

## CSV Export über ReST-Schnittstelle
Im Gegensatz zur Web-Ansicht, die aus Performancegründen mit einer Paginierung (Seitenaufteilung) arbeitet, gibt es einen Export, der  sicherstellt, dass der gesamte Datensatz gemeinsam in einer Datei vorliegt.
Der Button löst einen GET-Request an `/api/export` aus. Das Backend generiert zur Laufzeit einen CSV-Stream, der über das Frontend als Download (`.csv`) bereitgestellt wird.

## Erstellung und Anzeige der Reports auf Basis der SQL-Funktionen
Um Qualität der täglichen Daten sicherzustellen, nutzt das System spezialisierte SQL-Funktionen die im Backend und der Datenbank definiert sind. Das Backend besitzt API-Endpunkte, die mit den Funktionen gezielt nach ungültigen oder inkonsistenten Datensätzen suchen. Das Ziel dieser Reports ist es, Fehler im Arbeitsablauf sofort sichtbar zu machen und sicherzustellen, dass jede Probe den korrekten Prozess durchläuft.

Beispielsweise konzentriert sich ein Teil dieser Analyse auf die Boxen. Hier wird automatisch geprüft, ob Boxen ohne zugewiesene Samplenummern existieren oder ob Samples im System gespeichert sind, für die noch keine Analyse gestartet wurde. Ebenso identifiziert das System Analysen, denen eine feste Rückstellposition im Lager fehlt.

## Globale Filterung
Die Globale Filterung vom DateIn wurde durch eine Erweiterung der bestehenden Filterung um ein DTO im POJO Request erweitert.

# Projekt lokal starten – Schritt-für-Schritt Anleitung (aus vorherigen Aufgaben)

Diese Anleitung beschreibt, wie der Container gestartet werden kann und das Frontend aufgerufen werden kann

---

### 1. Container lokal erstellen

In der Konsole muss folgender Befehl ausgeführt werden:

```bash
docker compose up -d --build
```
Dadurch wird automatisch der Container erstellt und sowohl das Back- und Frontend gebuildet, auch werden alle Depedencies erstellt.
Docker liest die `docker-compose.yml` und startet alle definierten Services.
Beim ersten Start kann der Vorgang etwas länger dauern, da Images gebaut oder heruntergeladen werden.

---

### 2. Datenbank-Backup wiederherstellen

Sobald der PostgreSQL-Container läuft, kann das Datenbank-Backup wiederhergestellt werden. Dazu müssen die .dat Datein mit dem Backup, sowie das Restore.sql File im Ordner /backups vorhanden sein. Dieser Befehl verwendet das tar.dat file alle Schema Anweisungen die in diesem File nicht enthalten sind werden nicht übernommen.

```bash
docker exec -i zuckerpostgres pg_restore -U postgres -d database -Fd /backups/venlab_backup_0530
```

**Erklärung:**

* `docker exec -i postgres` führt den Befehl im laufenden Postgres-Container aus.
* `pg_restore` importiert das Datenbank-Backup.
* `-U postgres` gibt den Datenbank-User an.
* `-d database` ist der Name der Ziel-Datenbank.
* `-Fd` bedeutet, dass das Backup im verzeichnisbasierten Format vorliegt.
* `/backups/venlab_backup_0530` ist der Pfad zum Backup im Container.

---

### 3. Frontend aufrufen

Wenn alle Container erfolgreich laufen, kann das Frontend über folgenden Link geöffnet werden:

[http://localhost:8082](http://localhost:8082)

Dort sollte das Frontend mit den geladenen Daten sichtbar sein.

### 4. Starten von Cypress-Tests
Durch den folgenden Befehl, der unter /web/frontend ausgeführt werden muss, werden alle E2E Test von Cypress ausgeführt. Nach Abschluss lassen sich unter web/frontend/cypress/reports der Test Report und unter web/frontend/cypress/videos eine Video aufnahme der Test finden. !ACHTUNG: Dadurch das es jetzt eine Login Page gibt muss wie "Einfügen eines Testusers in die Datenbank" beschrieben ein User in die Datenbank eingefügt werden!

```bash
yarn test:report
```

### 5. Open Api Dokumentation
Die Swagger Reports sind folgende:
* [Swagger Dokumentation](./api-doc/swagger.md)
* [Detaillierte API Übersicht](./api-doc/README.md)
Vorrausgesetzt alle Container laufen kann unter folgender Url die Swagger UI aufgerufen werden und manuell abfragen getestet werden.
```bash
http://localhost:9090/v3/api-docs
```

Mit folgendem Befehlen ließe sich ein Markdown Report erstellen. Jedoch durch die strenge Security Implementation ist die momentan nicht direkt möglich. Im Repository (sollte) ein korrekter Bericht beigelegt sein.
```bash
curl http://localhost:9090/v3/api-docs -o swagger.json
```

Nur eines der folgenden Befehle muss genutzt werden, es sind 2 verschieden gebaute Reports 
```bash
npx swagger-markdown -i swagger.json
```
```bash
docker run --rm -v "%cd%:/local" openapitools/openapi-generator-cli generate -i /local/swagger.json -g markdown -o /local/api-doc
```

### 6. PWA Umsetzung

Mit der Neusten Version wurde die Website als Progressive Web application umgesetzt, damit man die Seite auch als App nutzen kann. 
jetzt kann sie auf allen unterstüzten Geräten verwendet werden. 
[1]
**Manifest**</br>
Das die Seite auch installiert werden kann, wurde ein Web App Manifest erstellt. 

**Service Worker & Caching**</br>
Zusätzlich wurde ein Service Worker integriert, der zentrale PWA-Funktionen übernimmt
- Caching
- Offline kompatibilität
- Schnellere Ladezeiten
[1]

**Favicon generierung**</br>
Es gab an Anfang nur ein Favicon und mit der Website kann man die weiteren Generieren für die App </br>
[2]
**Testing / Validierung**

Die PWA-Funktionalität wurde mit Browser-Tools überprüft unter Chrome DevTools → Application → Manifest / Service Worker / Cache Storage

### 7. Dark mode
Die Weboberfläche unterstützt einen Dark Mode, um die Lesbarkeit bei wenig Licht zu verbessern und eine moderne UI bereitzustellen</br>
[3][4]
Die Styles werden zentral geregelt zb: :global(.....) und betreffen
- Tabelle 
- Hintergrund
- Dopdowns 
- Inputfelder

### 8. Einzelne Attribute auswählen

Die Tabellenansicht bietet eine Mehrfachauswahl für Spalten. 
Damit können Benutzer gezielt festlegen, welche Spalten angezeigt werden. 
Zusätzlich gibt es „Alle“ und „Keine“, um schnell alle Spalten ein- oder auszublenden.
[5][6]
