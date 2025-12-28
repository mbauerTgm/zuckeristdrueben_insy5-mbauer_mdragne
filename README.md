# Informationssysteme "PWA Deployment" GK
von: Maximilian Bauer, Matei Dragne, Denis Gernesch 5CHITM

# Informationssysteme "PWA Deployment" EK
von: Maximilian Bauer, Matei Dragne 5CHITM

## Projekt starten – Schritt-für-Schritt Anleitung

Diese Anleitung beschreibt, wie der Container gestartet werden kann und das Frontend aufgerufen werden kann

---

### 1. Container erstellen

In der Konsole muss folgender Befehl ausgeführt werden:

```bash
docker compose up -d --build
```
Dadurch wird automatisch der Container erstellt und sowohl das Back- und Frontend gebuildet, auch werden alle Depedencies erstellt.
Docker liest die `docker-compose.yml` und startet alle definierten Services.
Beim ersten Start kann der Vorgang etwas länger dauern, da Images gebaut oder heruntergeladen werden.

#### Frontend updaten ohne kompletten Container neu zu bauen
```bash
docker exec -it frontend sh -c "cd frontend && yarn build && cp -r dist/* /usr/share/nginx/html"
```
---

### 2. Datenbank-Backup wiederherstellen

Sobald der PostgreSQL-Container läuft, kann das Datenbank-Backup wiederhergestellt werden. Dazu müssen die .dat Datein mit dem Backup, sowie das Restore.sql File im Ordner /backups vorhanden sein.

```bash
docker exec -i postgres911 pg_restore -U postgres -d database -Fd /backups/venlab_backup_0530
```

**Erklärung:**

* `docker exec -i postgres911` führt den Befehl im laufenden Postgres-Container aus.
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
Durch den folgenden Befehl werden alle E2E Test von Cypress ausgeführt. Nach Abschluss lassen sich unter web/frontend/cypress/reports der Test Report und unter web/frontend/cypress/videos eine Video aufnahme der Test finden.

```bash
yarn test:report
```