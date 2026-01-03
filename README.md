# Informationssysteme "PWA Deployment" GK
von: Maximilian Bauer, Matei Dragne, Denis Gernesch 5CHITM

## Deployment & CD
Während dieser Aufgabe wurden ein automatisches Deployment mittels Github Actions ermöglicht. Dabei werden bei einem Push auf den Main-Branch im CD alle Images erstellt. Anschließend werden diese auf einem Cloudserver gepulled und gestartet. 

Die Website ist unter [https://zuckeristdrueben.live](https://zuckeristdrueben.live) erreichbar.

Um das SSL-Zertifikat zu erhalten wurde der Caddy Reverse-Proxy verwendet.

### Einspielen von Backups
Damit der Postgres Container direkt mit einem DB Schema bespielt wird wurde das Restore.sql aufgeteilt, in init_schema.sql und import_backups.sql aufgeteilt. Für eine lokale- bzw. Testumgebung einget sich weiterhin der in "lokale Verwendung" beschriebene Befehl mit der Restore.sql Datei.

Um die Backupdateien auf dem Server einspielen zukönnen muss selbstverständlich direkter Zugriff auf den Server bestehen. Ist das der Fall und die Dateien sind unter /backups/ verfügbar, kann folgender Befehlt ausgeführt werden um die Daten in die Datenbank einzuspielen:

```bash
docker exec -i postgres psql -U postgres -d database -f /backups/import_backups.sql
```

Um die forcefully alle Daten der Datenbank zu löschen (inklusive der Schemas und der gesammten Datenbank) kann auf dem Server im Ordner in dem auch der Container liegt folgender Befehl benutzt werden:

```bash
sudo rm -rf postgres-data
```

# Informationssysteme "PWA Deployment" EK
von: Maximilian Bauer, Matei Dragne 5CHITM

## Projekt lokal starten – Schritt-für-Schritt Anleitung (aus vorherigen Aufgaben)

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

Danach muss mit folgendem Befehl das Frontend eingebunden werden.

```bash
docker exec -it frontend sh -c "cd frontend && yarn build && cp -r dist/* /usr/share/nginx/html"
```
---

### 2. Datenbank-Backup wiederherstellen

Sobald der PostgreSQL-Container läuft, kann das Datenbank-Backup wiederhergestellt werden. Dazu müssen die .dat Datein mit dem Backup, sowie das Restore.sql File im Ordner /backups vorhanden sein. Dieser Befehl verwendet das tar.dat file alle Schema Anweisungen die in diesem File nicht enthalten sind werden nicht übernommen.

```bash
docker exec -i postgres pg_restore -U postgres -d database -Fd /backups/venlab_backup_0530
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

## Starten von Cypress-Tests
Durch den folgenden Befehl, der unter /web/frontend ausgeführt werden muss, werden alle E2E Test von Cypress ausgeführt. Nach Abschluss lassen sich unter web/frontend/cypress/reports der Test Report und unter web/frontend/cypress/videos eine Video aufnahme der Test finden.

```bash
yarn test:report
```