--
-- PostgreSQL database dump
--

\restrict bvmthY5amHB7qQGb2IK8wc9lk8bl3zD8dj7PUb1c316obHQyjhMcqY8NaQYHJ8f

-- Dumped from database version 13.23 (Debian 13.23-1.pgdg13+1)
-- Dumped by pg_dump version 13.23 (Debian 13.23-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: backup; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA backup;


--
-- Name: venlab; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA venlab;


--
-- Name: find_all_modified_analysis(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_modified_analysis() RETURNS TABLE(a_id bigint, s_id character varying, s_stamp timestamp without time zone, pol numeric, nat numeric, kal numeric, an numeric, glu numeric, dry numeric, date_in timestamp without time zone, date_out timestamp without time zone, weight_mea numeric, weight_nrm numeric, weight_cur numeric, weight_dif numeric, density numeric, a_flags character varying, lane integer, comment character varying, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.analysis a WHERE a.date_exported = timestamp '2000-01-01 00:00:00';
END;
$$;


--
-- Name: find_all_modified_box(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_modified_box() RETURNS TABLE(b_id character varying, name character varying, num_max integer, type integer, comment character varying, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.box b WHERE b.date_exported = timestamp '2000-01-01 00:00:00';
END;
$$;


--
-- Name: find_all_modified_boxpos(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_modified_boxpos() RETURNS TABLE(b_id character varying, bpos_id integer, s_id character varying, s_stamp timestamp without time zone, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.boxpos b WHERE b.date_exported = timestamp '2000-01-01 00:00:00';
END;
$$;


--
-- Name: find_all_modified_log(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_modified_log() RETURNS TABLE(log_id bigint, date_created timestamp without time zone, level character varying, info character varying, s_id character varying, s_stamp timestamp without time zone, a_id bigint, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.log l WHERE l.date_exported = timestamp '2000-01-01 00:00:00';
END;
$$;


--
-- Name: find_all_modified_sample(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_modified_sample() RETURNS TABLE(s_id character varying, s_stamp timestamp without time zone, name character varying, weight_net numeric, weight_bru numeric, weight_tar numeric, quantity integer, distance numeric, date_crumbled timestamp without time zone, s_flags character varying, lane integer, comment character varying, date_exported timestamp without time zone, boxpos character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
    SELECT *,( SELECT venlab.get_box_positions(s.s_id,s.s_stamp) as boxpos ) FROM venlab.sample s WHERE s.date_exported = timestamp '2000-01-01 00:00:00';
END;
$$;


--
-- Name: find_all_not_exported_analysis(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_not_exported_analysis() RETURNS TABLE(a_id bigint, s_id character varying, s_stamp timestamp without time zone, pol numeric, nat numeric, kal numeric, an numeric, glu numeric, dry numeric, date_in timestamp without time zone, date_out timestamp without time zone, weight_mea numeric, weight_nrm numeric, weight_cur numeric, weight_dif numeric, density numeric, a_flags character varying, lane integer, comment character varying, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.analysis a WHERE a.date_exported IS NULL;
END;
$$;


--
-- Name: find_all_not_exported_box(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_not_exported_box() RETURNS TABLE(b_id character varying, name character varying, num_max integer, type integer, comment character varying, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.box b WHERE b.date_exported IS NULL;
END;
$$;


--
-- Name: find_all_not_exported_boxpos(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_not_exported_boxpos() RETURNS TABLE(b_id character varying, bpos_id integer, s_id character varying, s_stamp timestamp without time zone, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.boxpos b WHERE b.date_exported IS NULL;
END;
$$;


--
-- Name: find_all_not_exported_log(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_not_exported_log() RETURNS TABLE(log_id bigint, date_created timestamp without time zone, level character varying, info character varying, s_id character varying, s_stamp timestamp without time zone, a_id bigint, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT * FROM venlab.log l WHERE l.date_exported IS NULL;
END;
$$;


--
-- Name: find_all_not_exported_sample(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_not_exported_sample() RETURNS TABLE(s_id character varying, s_stamp timestamp without time zone, name character varying, weight_net numeric, weight_bru numeric, weight_tar numeric, quantity integer, distance numeric, date_crumbled timestamp without time zone, s_flags character varying, lane integer, comment character varying, date_exported timestamp without time zone, boxpos character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
 SELECT *,( SELECT venlab.get_box_positions(s.s_id,s.s_stamp) as boxpos ) FROM venlab.sample s WHERE s.date_exported IS NULL;
END;
$$;


--
-- Name: find_all_not_plausible_analysis(); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.find_all_not_plausible_analysis() RETURNS TABLE(a_id bigint, s_id character varying, s_stamp timestamp without time zone, pol numeric, nat numeric, kal numeric, an numeric, glu numeric, dry numeric, date_in timestamp without time zone, date_out timestamp without time zone, weight_mea numeric, weight_nrm numeric, weight_cur numeric, weight_dif numeric, density numeric, a_flags character varying, lane integer, comment character varying, date_exported timestamp without time zone)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY
SELECT * FROM venlab.analysis a WHERE a.date_in IS NULL AND a.date_out IS NOT NULL AND (a.date_exported IS NULL OR a.date_exported = (timestamp '2000-01-01 00:00:00'));
END;
$$;


--
-- Name: get_box_positions(character varying, timestamp without time zone); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.get_box_positions(sid character varying, sstamp timestamp without time zone) RETURNS character varying
    LANGUAGE plpgsql STABLE
    AS $$
DECLARE
    bid text;
    bpos text;
    count int;
BEGIN
SELECT venlab.boxpos.b_id, to_char(venlab.boxpos.bpos_id,'FM00') INTO bid, bpos FROM venlab.boxpos WHERE venlab.boxpos.s_stamp = sstamp AND venlab.boxpos.s_id = sid;
SELECT count(*) INTO count FROM venlab.boxpos WHERE venlab.boxpos.s_stamp = sstamp AND venlab.boxpos.s_id = sid;
IF count > 1 THEN
RETURN concat(bid, '!', bpos);
ELSIF count = 1 THEN
RETURN concat(bid, '/', bpos);
ELSE
RETURN '';
END IF;
END;
$$;


--
-- Name: get_box_positions(character varying, timestamp with time zone); Type: FUNCTION; Schema: venlab; Owner: -
--

CREATE FUNCTION venlab.get_box_positions(sid character varying, sstamp timestamp with time zone) RETURNS character varying
    LANGUAGE plpgsql STABLE
    AS $$
DECLARE
    bid text;
    bpos text;
    count int;
BEGIN
SELECT venlab.boxpos.b_id, to_char(venlab.boxpos.bpos_id,'FM00') INTO bid, bpos FROM venlab.boxpos WHERE venlab.boxpos.s_stamp = sstamp AND venlab.boxpos.s_id = sid;
SELECT count(*) INTO count FROM venlab.boxpos WHERE venlab.boxpos.s_stamp = sstamp AND venlab.boxpos.s_id = sid;
IF count > 1 THEN
RETURN concat(bid, '!', bpos);
ELSIF count = 1 THEN
RETURN concat(bid, '/', bpos);
ELSE
RETURN '';
END IF;
END;
$$;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: analysis; Type: TABLE; Schema: backup; Owner: -
--

CREATE TABLE backup.analysis (
    a_id bigint,
    s_id character varying(13),
    s_stamp timestamp without time zone,
    pol numeric(8,2),
    nat numeric(8,2),
    kal numeric(8,2),
    an numeric(8,2),
    dry numeric(8,2),
    date_in timestamp without time zone,
    date_out timestamp without time zone,
    weight_mea numeric(8,2),
    weight_nrm numeric(8,2),
    weight_cur numeric(8,2),
    weight_dif numeric(8,2),
    density numeric(8,2),
    a_flags character varying(15),
    lane integer,
    comment character varying(255),
    date_exported timestamp without time zone
);


--
-- Name: sample; Type: TABLE; Schema: backup; Owner: -
--

CREATE TABLE backup.sample (
    s_id character varying(13),
    s_stamp timestamp without time zone,
    name character varying(255),
    weight_net numeric(8,2),
    weight_bru numeric(8,2),
    weight_tar numeric(8,2),
    quantity integer,
    distance numeric(8,2),
    date_crumbled timestamp without time zone,
    s_flags character varying(10),
    lane integer,
    comment character varying(255),
    date_exported timestamp without time zone
);


--
-- Name: analysis; Type: TABLE; Schema: venlab; Owner: -
--

CREATE TABLE venlab.analysis (
    a_id bigint NOT NULL,
    s_id character varying(13),
    s_stamp timestamp without time zone,
    pol numeric(8,2),
    nat numeric(8,2),
    kal numeric(8,2),
    an numeric(8,2),
    glu numeric(8,2),
    dry numeric(8,2),
    date_in timestamp without time zone,
    date_out timestamp without time zone,
    weight_mea numeric(8,2),
    weight_nrm numeric(8,2),
    weight_cur numeric(8,2),
    weight_dif numeric(8,2),
    density numeric(8,2),
    a_flags character varying(15) DEFAULT '----------'::character varying,
    lane integer DEFAULT 1,
    comment character varying(255),
    date_exported timestamp without time zone
);


--
-- Name: analysis_a_id_seq; Type: SEQUENCE; Schema: venlab; Owner: -
--

CREATE SEQUENCE venlab.analysis_a_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: analysis_a_id_seq; Type: SEQUENCE OWNED BY; Schema: venlab; Owner: -
--

ALTER SEQUENCE venlab.analysis_a_id_seq OWNED BY venlab.analysis.a_id;


--
-- Name: box; Type: TABLE; Schema: venlab; Owner: -
--

CREATE TABLE venlab.box (
    b_id character varying(4) NOT NULL,
    name character varying(255),
    num_max integer DEFAULT 40,
    type integer DEFAULT 1,
    comment character varying(255),
    date_exported timestamp without time zone,
    CONSTRAINT con_box_num_max CHECK (((num_max > 0) AND (num_max < 1000))),
    CONSTRAINT con_box_type CHECK (((type > 0) AND (type < 10)))
);


--
-- Name: boxpos; Type: TABLE; Schema: venlab; Owner: -
--

CREATE TABLE venlab.boxpos (
    b_id character varying(4) NOT NULL,
    bpos_id integer NOT NULL,
    s_id character varying(13),
    s_stamp timestamp without time zone,
    date_exported timestamp without time zone
);


--
-- Name: log; Type: TABLE; Schema: venlab; Owner: -
--

CREATE TABLE venlab.log (
    log_id bigint NOT NULL,
    date_created timestamp without time zone DEFAULT now(),
    level character varying(10),
    info character varying(255),
    s_id character varying(13),
    s_stamp timestamp without time zone,
    a_id bigint,
    date_exported timestamp without time zone
);


--
-- Name: log_log_id_seq; Type: SEQUENCE; Schema: venlab; Owner: -
--

CREATE SEQUENCE venlab.log_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: log_log_id_seq; Type: SEQUENCE OWNED BY; Schema: venlab; Owner: -
--

ALTER SEQUENCE venlab.log_log_id_seq OWNED BY venlab.log.log_id;


--
-- Name: sample; Type: TABLE; Schema: venlab; Owner: -
--

CREATE TABLE venlab.sample (
    s_id character varying(13) NOT NULL,
    s_stamp timestamp without time zone NOT NULL,
    name character varying(255),
    weight_net numeric(8,2),
    weight_bru numeric(8,2),
    weight_tar numeric(8,2),
    quantity integer,
    distance numeric(8,2),
    date_crumbled timestamp without time zone,
    s_flags character varying(10) DEFAULT '-----'::character varying,
    lane integer,
    comment character varying(255),
    date_exported timestamp without time zone
);


--
-- Name: sample_boxpos; Type: VIEW; Schema: venlab; Owner: -
--

CREATE VIEW venlab.sample_boxpos AS
 SELECT sample.s_id,
    sample.s_stamp,
    ( SELECT venlab.get_box_positions(sample.s_id, sample.s_stamp) AS boxpos) AS boxpos
   FROM venlab.sample;


--
-- Name: threshold; Type: TABLE; Schema: venlab; Owner: -
--

CREATE TABLE venlab.threshold (
    th_id character varying(10) NOT NULL,
    value_min numeric(8,2),
    value_max numeric(8,2),
    date_changed timestamp without time zone DEFAULT now()
);


--
-- Name: analysis a_id; Type: DEFAULT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.analysis ALTER COLUMN a_id SET DEFAULT nextval('venlab.analysis_a_id_seq'::regclass);


--
-- Name: log log_id; Type: DEFAULT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.log ALTER COLUMN log_id SET DEFAULT nextval('venlab.log_log_id_seq'::regclass);


--
-- Name: role; Type: TABLE; Schema: venlab; Owner: postgres
--

CREATE TABLE venlab.role (
    role_id SERIAL PRIMARY KEY,
    role_name character varying(50) NOT NULL UNIQUE,
    description character varying(255)
);

ALTER TABLE venlab.role OWNER TO postgres;

--
-- Insert Roles (Reader, Admin, Researcher)
--
INSERT INTO venlab.role (role_name, description) VALUES 
('Admin', 'Admin Rights'), 
('Reader', 'Read Only'),
('Researcher', 'Partial Rights: only validated Data (Flags F/V)');

--
-- Name: user; Type: TABLE; Schema: venlab; Owner: postgres
--

CREATE TABLE venlab.users (
    u_id SERIAL PRIMARY KEY,
    username character varying(100) NOT NULL UNIQUE,
    password_hash character varying(255) NOT NULL,
    role_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES venlab.role(role_id)
);

ALTER TABLE venlab.users OWNER TO postgres;

CREATE INDEX idx_users_username ON venlab.users(username);

--
-- Name: log_log_id_seq; Type: SEQUENCE OWNED BY; Schema: venlab; Owner: postgres
--
CREATE SEQUENCE venlab.user_u_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venlab.user_u_id_seq OWNER TO postgres;

--
-- Name: users u_id; Type: DEFAULT; Schema: venlab; Owner: postgres
--

ALTER TABLE ONLY venlab.users ALTER COLUMN u_id SET DEFAULT nextval('venlab.user_u_id_seq'::regclass);

--
-- Name: analysis analysis_pkey; Type: CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.analysis
    ADD CONSTRAINT analysis_pkey PRIMARY KEY (a_id);


--
-- Name: box box_pkey; Type: CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.box
    ADD CONSTRAINT box_pkey PRIMARY KEY (b_id);


--
-- Name: boxpos boxpos_pkey; Type: CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.boxpos
    ADD CONSTRAINT boxpos_pkey PRIMARY KEY (bpos_id, b_id);


--
-- Name: log log_pkey; Type: CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.log
    ADD CONSTRAINT log_pkey PRIMARY KEY (log_id);


--
-- Name: sample sample_pkey; Type: CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.sample
    ADD CONSTRAINT sample_pkey PRIMARY KEY (s_id, s_stamp);


--
-- Name: threshold threshold_pkey; Type: CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.threshold
    ADD CONSTRAINT threshold_pkey PRIMARY KEY (th_id);


--
-- Name: idx_analysis_a_flags; Type: INDEX; Schema: venlab; Owner: -
--

CREATE INDEX idx_analysis_a_flags ON venlab.analysis USING btree (a_flags);


--
-- Name: idx_analysis_date_in; Type: INDEX; Schema: venlab; Owner: -
--

CREATE INDEX idx_analysis_date_in ON venlab.analysis USING btree (date_in);


--
-- Name: idx_sample_date_crumbled; Type: INDEX; Schema: venlab; Owner: -
--

CREATE INDEX idx_sample_date_crumbled ON venlab.sample USING btree (date_crumbled);


--
-- Name: idx_sample_name; Type: INDEX; Schema: venlab; Owner: -
--

CREATE INDEX idx_sample_name ON venlab.sample USING btree (name);


--
-- Name: idx_sample_s_flags; Type: INDEX; Schema: venlab; Owner: -
--

CREATE INDEX idx_sample_s_flags ON venlab.sample USING btree (s_flags);


--
-- Name: analysis analysis_s_id_fkey; Type: FK CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.analysis
    ADD CONSTRAINT analysis_s_id_fkey FOREIGN KEY (s_id, s_stamp) REFERENCES venlab.sample(s_id, s_stamp);


--
-- Name: boxpos boxpos_b_id_fkey; Type: FK CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.boxpos
    ADD CONSTRAINT boxpos_b_id_fkey FOREIGN KEY (b_id) REFERENCES venlab.box(b_id);


--
-- Name: boxpos boxpos_s_id_fkey; Type: FK CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.boxpos
    ADD CONSTRAINT boxpos_s_id_fkey FOREIGN KEY (s_id, s_stamp) REFERENCES venlab.sample(s_id, s_stamp);


--
-- Name: log log_a_id_fkey; Type: FK CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.log
    ADD CONSTRAINT log_a_id_fkey FOREIGN KEY (a_id) REFERENCES venlab.analysis(a_id);


--
-- Name: log log_s_id_fkey; Type: FK CONSTRAINT; Schema: venlab; Owner: -
--

ALTER TABLE ONLY venlab.log
    ADD CONSTRAINT log_s_id_fkey FOREIGN KEY (s_id, s_stamp) REFERENCES venlab.sample(s_id, s_stamp);


--
-- PostgreSQL database dump complete
--
-- zusätzliche Funktionen & Views
\i functions.sql

\unrestrict bvmthY5amHB7qQGb2IK8wc9lk8bl3zD8dj7PUb1c316obHQyjhMcqY8NaQYHJ8f

