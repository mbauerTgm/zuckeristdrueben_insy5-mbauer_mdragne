-- Datei: import_backups.sql

-- Daten importieren
COPY backup.analysis FROM '/backups/2206.dat';
COPY backup.sample FROM '/backups/2207.dat';
COPY venlab.analysis FROM '/backups/2208.dat';
COPY venlab.box FROM '/backups/2210.dat';
COPY venlab.boxpos FROM '/backups/2211.dat';
COPY venlab.log FROM '/backups/2212.dat';
COPY venlab.sample FROM '/backups/2214.dat';
COPY venlab.threshold FROM '/backups/2215.dat';

-- Sequenzen (IDs) aktualisieren
SELECT pg_catalog.setval('venlab.analysis_a_id_seq', 576256, true);
SELECT pg_catalog.setval('venlab.log_log_id_seq', 11987488, true);