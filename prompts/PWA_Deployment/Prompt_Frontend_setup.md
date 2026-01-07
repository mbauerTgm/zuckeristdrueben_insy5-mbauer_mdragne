# Prompt Frontend setup 
# Author Denis Gernesch
### Gihub Copilot Claude Code mit Aufruf bezug auf das Repository

\[Kontext\]:
Projekt aufsetzen 

Promt 1: 
Error response from daemon: invalid pool request: Pool overlaps with other one on this address space
wie werde ich diesen Fehler los

Ergebniss:
1. Network Pool Overlap Error
The error Pool overlaps with other one on this address space means Docker is trying to create a network with an IP address range (subnet) that conflicts with an existing Docker network on your system.

Solutions:

Option A: Remove unused Docker networks

bash
# List all networks
docker network ls

# Remove unused networks
docker network prune

# Then try again
docker compose up -d --build

Promt 2:
Was macht der Command docker network prune?

Ergebniss:
Der Befehl docker network prune löscht alle ungenutzten Docker-Netzwerke von deinem System.

Im Detail:
Aspekt	Beschreibung
**Was wird gelöscht?**	Alle benutzerdefinierten Netzwerke, die von keinem laufenden Container verwendet werden
Was bleibt erhalten?	Die Standard-Netzwerke (bridge, host, none) und Netzwerke, die noch von Containern genutzt werden
Warum hilft das?	Es entfernt alte Netzwerke, deren IP-Bereiche mit deinem neuen Netzwerk kollidieren
Dein Problem:
Docker wollte ein neues Netzwerk erstellen, aber der IP-Adressbereich (z.B. 172.18.0.0/16) war bereits von einem anderen, nicht mehr genutzten Netzwerk belegt.

Nach dem Prune:
Wenn du danach docker compose up -d --build ausführst, kann Docker ein neues Netzwerk mit einem freien IP-Bereich erstellen.

Promt 3:
Kannst du mir einen User mit encryptet Passwort erstellen?

Ergebnis:
Öffne Adminer im Browser: http://localhost:8080

Login:

System: PostgreSQL
Server: zuckerpostgres
Benutzer: postgres
Passwort: Partly3More6annual
Datenbank: database
SQL-Befehl ausführen:
Gehe zu "SQL-Befehl" und führe aus:

SQL
INSERT INTO venlab. users (username, password_hash, role_id) 
VALUES ('denis', '$2a$10$N9qo8uLOickgx2ZMRZoMy. MqrqHqKq7fGZwdGYwN6W8xKcWYqYjHe', 1);
Deine Login-Daten für die App:
Feld	Wert
Username	denis
Passwort	test123
Rolle	Admin
Falls du ein anderes Passwort möchtest, sag mir welches und ich generiere dir den BCrypt-Hash dafür! 🔐

