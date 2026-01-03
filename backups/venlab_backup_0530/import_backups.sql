-- Datei: import_backups.sql

-- 1. Unabhängige Backup-Tabellen
COPY backup.analysis FROM '/backups/2206.dat';
COPY backup.sample FROM '/backups/2207.dat';

-- 2. WICHTIG: Erst die Eltern-Tabellen (Box und Sample)
COPY venlab.box FROM '/backups/2210.dat';
COPY venlab.sample FROM '/backups/2214.dat';

-- 3. DANN die Kinder-Tabellen (die auf Sample/Box verweisen)
COPY venlab.analysis FROM '/backups/2208.dat';
COPY venlab.boxpos FROM '/backups/2211.dat';
COPY venlab.log FROM '/backups/2212.dat';

-- 4. Rest
-- Hinweis: Wir leeren threshold vorher, falls das Init-Skript schon Defaults gesetzt hat
TRUNCATE venlab.threshold; 
COPY venlab.threshold FROM '/backups/2215.dat';

-- 5. Sequenzen setzen
SELECT pg_catalog.setval('venlab.analysis_a_id_seq', 576256, true);
SELECT pg_catalog.setval('venlab.log_log_id_seq', 11987488, true);

-- 6. Users & Rollen
\i /backups/venlab_backup_0530/import_user_role.sql