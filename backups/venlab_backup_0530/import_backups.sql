--
-- Data for Name: analysis; Type: TABLE DATA; Schema: backup; Owner: postgres
--

COPY backup.analysis (a_id, s_id, s_stamp, pol, nat, kal, an, dry, date_in, date_out, weight_mea, weight_nrm, weight_cur, weight_dif, density, a_flags, lane, comment, date_exported) FROM stdin;
\.
COPY backup.analysis (a_id, s_id, s_stamp, pol, nat, kal, an, dry, date_in, date_out, weight_mea, weight_nrm, weight_cur, weight_dif, density, a_flags, lane, comment, date_exported) FROM '//backups/2206.dat';

--
-- Data for Name: sample; Type: TABLE DATA; Schema: backup; Owner: postgres
--

COPY backup.sample (s_id, s_stamp, name, weight_net, weight_bru, weight_tar, quantity, distance, date_crumbled, s_flags, lane, comment, date_exported) FROM stdin;
\.
COPY backup.sample (s_id, s_stamp, name, weight_net, weight_bru, weight_tar, quantity, distance, date_crumbled, s_flags, lane, comment, date_exported) FROM '//backups/2207.dat';

--
-- Data for Name: analysis; Type: TABLE DATA; Schema: venlab; Owner: postgres
--

COPY venlab.analysis (a_id, s_id, s_stamp, pol, nat, kal, an, glu, dry, date_in, date_out, weight_mea, weight_nrm, weight_cur, weight_dif, density, a_flags, lane, comment, date_exported) FROM stdin;
\.
COPY venlab.analysis (a_id, s_id, s_stamp, pol, nat, kal, an, glu, dry, date_in, date_out, weight_mea, weight_nrm, weight_cur, weight_dif, density, a_flags, lane, comment, date_exported) FROM '//backups/2208.dat';

--
-- Data for Name: box; Type: TABLE DATA; Schema: venlab; Owner: postgres
--

COPY venlab.box (b_id, name, num_max, type, comment, date_exported) FROM stdin;
\.
COPY venlab.box (b_id, name, num_max, type, comment, date_exported) FROM '//backups/2210.dat';

--
-- Data for Name: boxpos; Type: TABLE DATA; Schema: venlab; Owner: postgres
--

COPY venlab.boxpos (b_id, bpos_id, s_id, s_stamp, date_exported) FROM stdin;
\.
COPY venlab.boxpos (b_id, bpos_id, s_id, s_stamp, date_exported) FROM '//backups/2211.dat';

--
-- Data for Name: log; Type: TABLE DATA; Schema: venlab; Owner: postgres
--

COPY venlab.log (log_id, date_created, level, info, s_id, s_stamp, a_id, date_exported) FROM stdin;
\.
COPY venlab.log (log_id, date_created, level, info, s_id, s_stamp, a_id, date_exported) FROM '//backups/2212.dat';

--
-- Data for Name: sample; Type: TABLE DATA; Schema: venlab; Owner: postgres
--

COPY venlab.sample (s_id, s_stamp, name, weight_net, weight_bru, weight_tar, quantity, distance, date_crumbled, s_flags, lane, comment, date_exported) FROM stdin;
\.
COPY venlab.sample (s_id, s_stamp, name, weight_net, weight_bru, weight_tar, quantity, distance, date_crumbled, s_flags, lane, comment, date_exported) FROM '/backups/2214.dat';

--
-- Data for Name: threshold; Type: TABLE DATA; Schema: venlab; Owner: postgres
--

COPY venlab.threshold (th_id, value_min, value_max, date_changed) FROM stdin;
\.
COPY venlab.threshold (th_id, value_min, value_max, date_changed) FROM '//backups/2215.dat';

--
-- Name: analysis_a_id_seq; Type: SEQUENCE SET; Schema: venlab; Owner: postgres
--

SELECT pg_catalog.setval('venlab.analysis_a_id_seq', 576256, true);


--
-- Name: log_log_id_seq; Type: SEQUENCE SET; Schema: venlab; Owner: postgres
--

SELECT pg_catalog.setval('venlab.log_log_id_seq', 11987488, true);