package co.uk.bluegumtree.code.java.api.goodreads;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public enum Language {

	unknown, gr_abk, gr_ace, gr_ach, gr_ada, gr_ady, gr_aar, gr_afh, gr_afr, gr_afa, gr_ain, gr_aka, gr_akk, gr_sqi, gr_ale, gr_alg, gr_tut, gr_amh, gr_anp, gr_apa, gr_ara, gr_arg, gr_arp, gr_arw, gr_hye, gr_rup, gr_art, gr_asm, gr_ast, gr_ath, gr_aus, gr_map, gr_ava, gr_ave, gr_awa, gr_aym, gr_aze, gr_ban, gr_bat, gr_bal, gr_bam, gr_bai, gr_bad, gr_bnt, gr_bas, gr_bak, gr_eus, gr_btk, gr_bej, gr_bel, gr_bem, gr_ben, gr_ber, gr_bho, gr_bih, gr_bik, gr_bin, gr_bis, gr_byn, gr_zbl, gr_nob, gr_bos, gr_bra, gr_bre, gr_bug, gr_bul, gr_bua, gr_mya, gr_cad, gr_cat, gr_cau, gr_ceb, gr_cel, gr_cai, gr_khm, gr_chg, gr_cmc, gr_cha, gr_che, gr_chr, gr_chy, gr_chb, gr_nya, gr_zho, gr_chn, gr_chp, gr_cho, gr_chu, gr_chk, gr_chv, gr_nwc, gr_syc, gr_cop, gr_cor, gr_cos, gr_cre, gr_mus, gr_crp, gr_cpe, gr_cpf, gr_cpp, gr_crh, gr_scr, gr_cus, gr_cze, gr_dak, gr_dan, gr_dar, gr_del, gr_din, gr_div, gr_doi, gr_dgr, gr_dra, gr_dua, gr_nl, gr_dum, gr_dyu, gr_dzo, gr_frs, gr_efi, gr_egy, gr_eka, gr_elx, gr_eng, gr_enm, gr_ang, gr_myv, gr_epo, gr_est, gr_ewe, gr_ewo, gr_fan, gr_fat, gr_fao, gr_fij, gr_fil, gr_fin, gr_fiu, gr_fon, gr_fre, gr_frm, gr_fro, gr_fur, gr_ful, gr_gaa, gr_gla, gr_car, gr_glg, gr_lug, gr_gay, gr_gba, gr_gez, gr_kat, gr_ger, gr_gmh, gr_goh, gr_gem, gr_gil, gr_gon, gr_gor, gr_got, gr_grb, gr_grc, gr_gre, gr_grn, gr_guj, gr_gwi, gr_hai, gr_hat, gr_hau, gr_haw, gr_heb, gr_her, gr_hil, gr_him, gr_hin, gr_hmo, gr_hit, gr_hmn, gr_hun, gr_hup, gr_iba, gr_isl, gr_ido, gr_ibo, gr_ijo, gr_ilo, gr_smn, gr_inc, gr_ine, gr_ind, gr_inh, gr_ina, gr_ile, gr_iku, gr_ipk, gr_ira, gr_gle, gr_mga, gr_sga, gr_iro, gr_ita, gr_jpn, gr_jav, gr_jrb, gr_jpr, gr_kbd, gr_kab, gr_kac, gr_kal, gr_xal, gr_kam, gr_kan, gr_kau, gr_kaa, gr_krc, gr_krl, gr_kar, gr_kas, gr_csb, gr_kaw, gr_kaz, gr_kha, gr_khi, gr_kho, gr_kik, gr_kmb, gr_kin, gr_kir, gr_tlh, gr_kom, gr_kon, gr_kok, gr_kor, gr_kos, gr_kpe, gr_kro, gr_kua, gr_kum, gr_kur, gr_kru, gr_kut, gr_lad, gr_lah, gr_lam, gr_day, gr_lao, gr_lat, gr_lav, gr_lez, gr_lim, gr_lin, gr_lit, gr_jbo, gr_nds, gr_dsb, gr_loz, gr_lub, gr_lua, gr_lui, gr_smj, gr_lun, gr_luo, gr_lus, gr_ltz, gr_mkd, gr_mad, gr_mag, gr_mai, gr_mak, gr_mlg, gr_msa, gr_mal, gr_mlt, gr_mnc, gr_mdr, gr_man, gr_mni, gr_mno, gr_glv, gr_mri, gr_arn, gr_mar, gr_chm, gr_mah, gr_mwr, gr_mas, gr_myn, gr_men, gr_wtm, gr_mic, gr_min, gr_mwl, gr_moh, gr_mdf, gr_mol, gr_mkh, gr_lol, gr_mon, gr_mos, gr_mul, gr_mun, gr_nqo, gr_nah, gr_nau, gr_nav, gr_nde, gr_nbl, gr_ndo, gr_nap, gr_new, gr_nep, gr_nia, gr_nic, gr_ssa, gr_niu, gr_nog, gr_non, gr_nai, gr_frr, gr_sme, gr_nno, gr_nor, gr_nub, gr_nym, gr_nyn, gr_nyo, gr_nzi, gr_oci, gr_arc, gr_oji, gr_ori, gr_orm, gr_osa, gr_oss, gr_oto, gr_pal, gr_pau, gr_pli, gr_pam, gr_pag, gr_pan, gr_pap, gr_paa, gr_nso, gr_per, gr_peo, gr_phi, gr_phn, gr_pon, gr_pol, gr_por, gr_pra, gr_pro, gr_pus, gr_que, gr_raj, gr_rap, gr_rar, gr_qaa, gr_roa, gr_rum, gr_roh, gr_rom, gr_run, gr_rus, gr_sal, gr_sam, gr_smi, gr_smo, gr_sad, gr_sag, gr_san, gr_sat, gr_srd, gr_sas, gr_sco, gr_sel, gr_sem, gr_srp, gr_srr, gr_shn, gr_sna, gr_iii, gr_scn, gr_sid, gr_sgn, gr_bla, gr_snd, gr_sin, gr_sit, gr_sio, gr_sms, gr_den, gr_sla, gr_slo, gr_slv, gr_sog, gr_som, gr_son, gr_snk, gr_wen, gr_sot, gr_sai, gr_alt, gr_sma, gr_spa, gr_srn, gr_suk, gr_sux, gr_sun, gr_sus, gr_swa, gr_ssw, gr_swe, gr_gsw, gr_syr, gr_tgl, gr_tah, gr_tai, gr_tgk, gr_tmh, gr_tam, gr_tat, gr_tel, gr_ter, gr_tet, gr_tha, gr_tib, gr_tig, gr_tir, gr_tem, gr_tiv, gr_tli, gr_tpi, gr_tkl, gr_tog, gr_ton, gr_tsi, gr_tso, gr_tsn, gr_tum, gr_tup, gr_tur, gr_ota, gr_tuk, gr_tvl, gr_tyv, gr_twi, gr_udm, gr_uga, gr_uig, gr_ukr, gr_umb, gr_mis, gr_und, gr_hsb, gr_urd, gr_uzb, gr_vai, gr_ven, gr_vie, gr_vol, gr_vot, gr_wak, gr_wal, gr_wln, gr_war, gr_was, gr_wel, gr_fry, gr_wol, gr_xho, gr_sah, gr_yao, gr_yap, gr_yid, gr_yor, gr_ypk, gr_znd, gr_zap, gr_zza, gr_zen, gr_zha, gr_zul, gr_zun;

	private static HashMap<String, String[]> NAMES;
	static {
		HashMap<String, String[]> aMap = new HashMap<String, String[]>();

		// WARNING: Goodreads code for Dutch, Flemish is ONLY 2 characters long
		// (nl)
		aMap.put("gr_eng", new String[] { "eng", "English" });
		aMap.put("gr_spa", new String[] { "spa", "Spanish" });
		aMap.put("gr_fre", new String[] { "fre", "French" });
		aMap.put("gr_ger", new String[] { "ger", "German" });
		aMap.put("gr_abk", new String[] { "abk", "Abkhazian" });
		aMap.put("gr_ace", new String[] { "ace", "Achinese" });
		aMap.put("gr_ach", new String[] { "ach", "Acoli" });
		aMap.put("gr_ada", new String[] { "ada", "Adangme" });
		aMap.put("gr_ady", new String[] { "ady", "Adyghe; Adygei" });
		aMap.put("gr_aar", new String[] { "aar", "Afar" });
		aMap.put("gr_afh", new String[] { "afh", "Afrihili" });
		aMap.put("gr_afr", new String[] { "afr", "Afrikaans" });
		aMap.put("gr_afa", new String[] { "afa", "Afro-Asiatic (Other)" });
		aMap.put("gr_ain", new String[] { "ain", "Ainu" });
		aMap.put("gr_aka", new String[] { "aka", "Akan" });
		aMap.put("gr_akk", new String[] { "akk", "Akkadian" });
		aMap.put("gr_sqi", new String[] { "sqi", "Albanian" });
		aMap.put("gr_ale", new String[] { "ale", "Aleut" });
		aMap.put("gr_alg", new String[] { "alg", "Algonquian languages" });
		aMap.put("gr_tut", new String[] { "tut", "Altaic (Other)" });
		aMap.put("gr_amh", new String[] { "amh", "Amharic" });
		aMap.put("gr_anp", new String[] { "anp", "Angika" });
		aMap.put("gr_apa", new String[] { "apa", "Apache languages" });
		aMap.put("gr_ara", new String[] { "ara", "Arabic" });
		aMap.put("gr_arg", new String[] { "arg", "Aragonese" });
		aMap.put("gr_arp", new String[] { "arp", "Arapaho" });
		aMap.put("gr_arw", new String[] { "arw", "Arawak" });
		aMap.put("gr_hye", new String[] { "hye", "Armenian" });
		aMap.put("gr_rup", new String[] { "rup", "Aromanian; Arumanian; Macedo-Romanian" });
		aMap.put("gr_art", new String[] { "art", "Artificial (Other)" });
		aMap.put("gr_asm", new String[] { "asm", "Assamese" });
		aMap.put("gr_ast", new String[] { "ast", "Asturian; Bable; Leonese; Asturleonese" });
		aMap.put("gr_ath", new String[] { "ath", "Athapascan languages" });
		aMap.put("gr_aus", new String[] { "aus", "Australian languages" });
		aMap.put("gr_map", new String[] { "map", "Austronesian (Other)" });
		aMap.put("gr_ava", new String[] { "ava", "Avaric" });
		aMap.put("gr_ave", new String[] { "ave", "Avestan" });
		aMap.put("gr_awa", new String[] { "awa", "Awadhi" });
		aMap.put("gr_aym", new String[] { "aym", "Aymara" });
		aMap.put("gr_aze", new String[] { "aze", "Azerbaijani" });
		aMap.put("gr_ban", new String[] { "ban", "Balinese" });
		aMap.put("gr_bat", new String[] { "bat", "Baltic (Other)" });
		aMap.put("gr_bal", new String[] { "bal", "Baluchi" });
		aMap.put("gr_bam", new String[] { "bam", "Bambara" });
		aMap.put("gr_bai", new String[] { "bai", "Bamileke languages" });
		aMap.put("gr_bad", new String[] { "bad", "Banda languages" });
		aMap.put("gr_bnt", new String[] { "bnt", "Bantu (Other)" });
		aMap.put("gr_bas", new String[] { "bas", "Basa" });
		aMap.put("gr_bak", new String[] { "bak", "Bashkir" });
		aMap.put("gr_eus", new String[] { "eus", "Basque" });
		aMap.put("gr_btk", new String[] { "btk", "Batak languages" });
		aMap.put("gr_bej", new String[] { "bej", "Beja; Bedawiyet" });
		aMap.put("gr_bel", new String[] { "bel", "Belarusian" });
		aMap.put("gr_bem", new String[] { "bem", "Bemba" });
		aMap.put("gr_ben", new String[] { "ben", "Bengali" });
		aMap.put("gr_ber", new String[] { "ber", "Berber (Other)" });
		aMap.put("gr_bho", new String[] { "bho", "Bhojpuri" });
		aMap.put("gr_bih", new String[] { "bih", "Bihari" });
		aMap.put("gr_bik", new String[] { "bik", "Bikol" });
		aMap.put("gr_bin", new String[] { "bin", "Bini; Edo" });
		aMap.put("gr_bis", new String[] { "bis", "Bislama" });
		aMap.put("gr_byn", new String[] { "byn", "Blin; Bilin" });
		aMap.put("gr_zbl", new String[] { "zbl", "Blissymbols; Blissymbolics; Bliss" });
		aMap.put("gr_nob", new String[] { "nob", "Bokmål, Norwegian; Norwegian Bokmål" });
		aMap.put("gr_bos", new String[] { "bos", "Bosnian" });
		aMap.put("gr_bra", new String[] { "bra", "Braj" });
		aMap.put("gr_bre", new String[] { "bre", "Breton" });
		aMap.put("gr_bug", new String[] { "bug", "Buginese" });
		aMap.put("gr_bul", new String[] { "bul", "Bulgarian" });
		aMap.put("gr_bua", new String[] { "bua", "Buriat" });
		aMap.put("gr_mya", new String[] { "mya", "Burmese" });
		aMap.put("gr_cad", new String[] { "cad", "Caddo" });
		aMap.put("gr_cat", new String[] { "cat", "Catalan; Valencian" });
		aMap.put("gr_cau", new String[] { "cau", "Caucasian (Other)" });
		aMap.put("gr_ceb", new String[] { "ceb", "Cebuano" });
		aMap.put("gr_cel", new String[] { "cel", "Celtic (Other)" });
		aMap.put("gr_cai", new String[] { "cai", "Central American Indian (Other)" });
		aMap.put("gr_khm", new String[] { "khm", "Central Khmer" });
		aMap.put("gr_chg", new String[] { "chg", "Chagatai" });
		aMap.put("gr_cmc", new String[] { "cmc", "Chamic languages" });
		aMap.put("gr_cha", new String[] { "cha", "Chamorro" });
		aMap.put("gr_che", new String[] { "che", "Chechen" });
		aMap.put("gr_chr", new String[] { "chr", "Cherokee" });
		aMap.put("gr_chy", new String[] { "chy", "Cheyenne" });
		aMap.put("gr_chb", new String[] { "chb", "Chibcha" });
		aMap.put("gr_nya", new String[] { "nya", "Chichewa; Chewa; Nyanja" });
		aMap.put("gr_zho", new String[] { "zho", "Chinese" });
		aMap.put("gr_chn", new String[] { "chn", "Chinook jargon" });
		aMap.put("gr_chp", new String[] { "chp", "Chipewyan; Dene Suline" });
		aMap.put("gr_cho", new String[] { "cho", "Choctaw" });
		aMap.put("gr_chu", new String[] { "chu", "Church Slavic; Old Slavonic; Old Bulgarian;" });
		aMap.put("gr_chk", new String[] { "chk", "Chuukese" });
		aMap.put("gr_chv", new String[] { "chv", "Chuvash" });
		aMap.put("gr_nwc", new String[] { "nwc", "Classical Newari; Old Newari; Classical Nepal Bhasa" });
		aMap.put("gr_syc", new String[] { "syc", "Classical Syriac" });
		aMap.put("gr_cop", new String[] { "cop", "Coptic" });
		aMap.put("gr_cor", new String[] { "cor", "Cornish" });
		aMap.put("gr_cos", new String[] { "cos", "Corsican" });
		aMap.put("gr_cre", new String[] { "cre", "Cree" });
		aMap.put("gr_mus", new String[] { "mus", "Creek" });
		aMap.put("gr_crp", new String[] { "crp", "Creoles and pidgins (Other)" });
		aMap.put("gr_cpe", new String[] { "cpe", "Creoles and pidgins, English based (Other)" });
		aMap.put("gr_cpf", new String[] { "cpf", "Creoles and pidgins, French-based (Other)" });
		aMap.put("gr_cpp", new String[] { "cpp", "Creoles and pidgins, Portuguese-based (Other)" });
		aMap.put("gr_crh", new String[] { "crh", "Crimean Tatar; Crimean Turkish" });
		aMap.put("gr_scr", new String[] { "scr", "Croatian" });
		aMap.put("gr_cus", new String[] { "cus", "Cushitic (Other)" });
		aMap.put("gr_cze", new String[] { "cze", "Czech" });
		aMap.put("gr_dak", new String[] { "dak", "Dakota" });
		aMap.put("gr_dan", new String[] { "dan", "Danish" });
		aMap.put("gr_dar", new String[] { "dar", "Dargwa" });
		aMap.put("gr_del", new String[] { "del", "Delaware" });
		aMap.put("gr_din", new String[] { "din", "Dinka" });
		aMap.put("gr_div", new String[] { "div", "Divehi; Dhivehi; Maldivian" });
		aMap.put("gr_doi", new String[] { "doi", "Dogri" });
		aMap.put("gr_dgr", new String[] { "dgr", "Dogrib" });
		aMap.put("gr_dra", new String[] { "dra", "Dravidian (Other)" });
		aMap.put("gr_dua", new String[] { "dua", "Duala" });
		aMap.put("gr_nl", new String[] { "nl", "Dutch; Flemish" });
		aMap.put("gr_dum", new String[] { "dum", "Dutch, Middle (ca.1050-1350)" });
		aMap.put("gr_dyu", new String[] { "dyu", "Dyula" });
		aMap.put("gr_dzo", new String[] { "dzo", "Dzongkha" });
		aMap.put("gr_frs", new String[] { "frs", "Eastern Frisian" });
		aMap.put("gr_efi", new String[] { "efi", "Efik" });
		aMap.put("gr_egy", new String[] { "egy", "Egyptian (Ancient)" });
		aMap.put("gr_eka", new String[] { "eka", "Ekajuk" });
		aMap.put("gr_elx", new String[] { "elx", "Elamite" });
		aMap.put("gr_eng", new String[] { "eng", "English" });
		aMap.put("gr_enm", new String[] { "enm", "English, Middle (1100-1500)" });
		aMap.put("gr_ang", new String[] { "ang", "English, Old (ca.450-1100)" });
		aMap.put("gr_myv", new String[] { "myv", "Erzya" });
		aMap.put("gr_epo", new String[] { "epo", "Esperanto" });
		aMap.put("gr_est", new String[] { "est", "Estonian" });
		aMap.put("gr_ewe", new String[] { "ewe", "Ewe" });
		aMap.put("gr_ewo", new String[] { "ewo", "Ewondo" });
		aMap.put("gr_fan", new String[] { "fan", "Fang" });
		aMap.put("gr_fat", new String[] { "fat", "Fanti" });
		aMap.put("gr_fao", new String[] { "fao", "Faroese" });
		aMap.put("gr_fij", new String[] { "fij", "Fijian" });
		aMap.put("gr_fil", new String[] { "fil", "Filipino; Pilipino" });
		aMap.put("gr_fin", new String[] { "fin", "Finnish" });
		aMap.put("gr_fiu", new String[] { "fiu", "Finno-Ugrian (Other)" });
		aMap.put("gr_fon", new String[] { "fon", "Fon" });
		aMap.put("gr_fre", new String[] { "fre", "French" });
		aMap.put("gr_frm", new String[] { "frm", "French, Middle (ca.1400-1600)" });
		aMap.put("gr_fro", new String[] { "fro", "French, Old (842-ca.1400)" });
		aMap.put("gr_fur", new String[] { "fur", "Friulian" });
		aMap.put("gr_ful", new String[] { "ful", "Fulah" });
		aMap.put("gr_gaa", new String[] { "gaa", "Ga" });
		aMap.put("gr_gla", new String[] { "gla", "Gaelic; Scottish Gaelic" });
		aMap.put("gr_car", new String[] { "car", "Galibi Carib" });
		aMap.put("gr_glg", new String[] { "glg", "Galician" });
		aMap.put("gr_lug", new String[] { "lug", "Ganda" });
		aMap.put("gr_gay", new String[] { "gay", "Gayo" });
		aMap.put("gr_gba", new String[] { "gba", "Gbaya" });
		aMap.put("gr_gez", new String[] { "gez", "Geez" });
		aMap.put("gr_kat", new String[] { "kat", "Georgian" });
		aMap.put("gr_ger", new String[] { "ger", "German" });
		aMap.put("gr_gmh", new String[] { "gmh", "German, Middle High (ca.1050-1500)" });
		aMap.put("gr_goh", new String[] { "goh", "German, Old High (ca.750-1050)" });
		aMap.put("gr_gem", new String[] { "gem", "Germanic (Other)" });
		aMap.put("gr_gil", new String[] { "gil", "Gilbertese" });
		aMap.put("gr_gon", new String[] { "gon", "Gondi" });
		aMap.put("gr_gor", new String[] { "gor", "Gorontalo" });
		aMap.put("gr_got", new String[] { "got", "Gothic" });
		aMap.put("gr_grb", new String[] { "grb", "Grebo" });
		aMap.put("gr_grc", new String[] { "grc", "Greek, Ancient (to 1453)" });
		aMap.put("gr_gre", new String[] { "gre", "Greek, Modern (1453-)" });
		aMap.put("gr_grn", new String[] { "grn", "Guarani" });
		aMap.put("gr_guj", new String[] { "guj", "Gujarati" });
		aMap.put("gr_gwi", new String[] { "gwi", "Gwich'in" });
		aMap.put("gr_hai", new String[] { "hai", "Haida" });
		aMap.put("gr_hat", new String[] { "hat", "Haitian; Haitian Creole" });
		aMap.put("gr_hau", new String[] { "hau", "Hausa" });
		aMap.put("gr_haw", new String[] { "haw", "Hawaiian" });
		aMap.put("gr_heb", new String[] { "heb", "Hebrew" });
		aMap.put("gr_her", new String[] { "her", "Herero" });
		aMap.put("gr_hil", new String[] { "hil", "Hiligaynon" });
		aMap.put("gr_him", new String[] { "him", "Himachali" });
		aMap.put("gr_hin", new String[] { "hin", "Hindi" });
		aMap.put("gr_hmo", new String[] { "hmo", "Hiri Motu" });
		aMap.put("gr_hit", new String[] { "hit", "Hittite" });
		aMap.put("gr_hmn", new String[] { "hmn", "Hmong" });
		aMap.put("gr_hun", new String[] { "hun", "Hungarian" });
		aMap.put("gr_hup", new String[] { "hup", "Hupa" });
		aMap.put("gr_iba", new String[] { "iba", "Iban" });
		aMap.put("gr_isl", new String[] { "isl", "Icelandic" });
		aMap.put("gr_ido", new String[] { "ido", "Ido" });
		aMap.put("gr_ibo", new String[] { "ibo", "Igbo" });
		aMap.put("gr_ijo", new String[] { "ijo", "Ijo languages" });
		aMap.put("gr_ilo", new String[] { "ilo", "Iloko" });
		aMap.put("gr_smn", new String[] { "smn", "Inari Sami" });
		aMap.put("gr_inc", new String[] { "inc", "Indic (Other)" });
		aMap.put("gr_ine", new String[] { "ine", "Indo-European (Other)" });
		aMap.put("gr_ind", new String[] { "ind", "Indonesian" });
		aMap.put("gr_inh", new String[] { "inh", "Ingush" });
		aMap.put("gr_ina", new String[] { "ina", "Interlingua" });
		aMap.put("gr_ile", new String[] { "ile", "Interlingue; Occidental" });
		aMap.put("gr_iku", new String[] { "iku", "Inuktitut" });
		aMap.put("gr_ipk", new String[] { "ipk", "Inupiaq" });
		aMap.put("gr_ira", new String[] { "ira", "Iranian (Other)" });
		aMap.put("gr_gle", new String[] { "gle", "Irish" });
		aMap.put("gr_mga", new String[] { "mga", "Irish, Middle (900-1200)" });
		aMap.put("gr_sga", new String[] { "sga", "Irish, Old (to 900)" });
		aMap.put("gr_iro", new String[] { "iro", "Iroquoian languages" });
		aMap.put("gr_ita", new String[] { "ita", "Italian" });
		aMap.put("gr_jpn", new String[] { "jpn", "Japanese" });
		aMap.put("gr_jav", new String[] { "jav", "Javanese" });
		aMap.put("gr_jrb", new String[] { "jrb", "Judeo-Arabic" });
		aMap.put("gr_jpr", new String[] { "jpr", "Judeo-Persian" });
		aMap.put("gr_kbd", new String[] { "kbd", "Kabardian" });
		aMap.put("gr_kab", new String[] { "kab", "Kabyle" });
		aMap.put("gr_kac", new String[] { "kac", "Kachin; Jingpho" });
		aMap.put("gr_kal", new String[] { "kal", "Kalaallisut; Greenlandic" });
		aMap.put("gr_xal", new String[] { "xal", "Kalmyk; Oirat" });
		aMap.put("gr_kam", new String[] { "kam", "Kamba" });
		aMap.put("gr_kan", new String[] { "kan", "Kannada" });
		aMap.put("gr_kau", new String[] { "kau", "Kanuri" });
		aMap.put("gr_kaa", new String[] { "kaa", "Kara-Kalpak" });
		aMap.put("gr_krc", new String[] { "krc", "Karachay-Balkar" });
		aMap.put("gr_krl", new String[] { "krl", "Karelian" });
		aMap.put("gr_kar", new String[] { "kar", "Karen languages" });
		aMap.put("gr_kas", new String[] { "kas", "Kashmiri" });
		aMap.put("gr_csb", new String[] { "csb", "Kashubian" });
		aMap.put("gr_kaw", new String[] { "kaw", "Kawi" });
		aMap.put("gr_kaz", new String[] { "kaz", "Kazakh" });
		aMap.put("gr_kha", new String[] { "kha", "Khasi" });
		aMap.put("gr_khi", new String[] { "khi", "Khoisan (Other)" });
		aMap.put("gr_kho", new String[] { "kho", "Khotanese" });
		aMap.put("gr_kik", new String[] { "kik", "Kikuyu; Gikuyu" });
		aMap.put("gr_kmb", new String[] { "kmb", "Kimbundu" });
		aMap.put("gr_kin", new String[] { "kin", "Kinyarwanda" });
		aMap.put("gr_kir", new String[] { "kir", "Kirghiz; Kyrgyz" });
		aMap.put("gr_tlh", new String[] { "tlh", "Klingon; tlhIngan-Hol" });
		aMap.put("gr_kom", new String[] { "kom", "Komi" });
		aMap.put("gr_kon", new String[] { "kon", "Kongo" });
		aMap.put("gr_kok", new String[] { "kok", "Konkani" });
		aMap.put("gr_kor", new String[] { "kor", "Korean" });
		aMap.put("gr_kos", new String[] { "kos", "Kosraean" });
		aMap.put("gr_kpe", new String[] { "kpe", "Kpelle" });
		aMap.put("gr_kro", new String[] { "kro", "Kru languages" });
		aMap.put("gr_kua", new String[] { "kua", "Kuanyama; Kwanyama" });
		aMap.put("gr_kum", new String[] { "kum", "Kumyk" });
		aMap.put("gr_kur", new String[] { "kur", "Kurdish" });
		aMap.put("gr_kru", new String[] { "kru", "Kurukh" });
		aMap.put("gr_kut", new String[] { "kut", "Kutenai" });
		aMap.put("gr_lad", new String[] { "lad", "Ladino" });
		aMap.put("gr_lah", new String[] { "lah", "Lahnda" });
		aMap.put("gr_lam", new String[] { "lam", "Lamba" });
		aMap.put("gr_day", new String[] { "day", "Land Dayak languages" });
		aMap.put("gr_lao", new String[] { "lao", "Lao" });
		aMap.put("gr_lat", new String[] { "lat", "Latin" });
		aMap.put("gr_lav", new String[] { "lav", "Latvian" });
		aMap.put("gr_lez", new String[] { "lez", "Lezghian" });
		aMap.put("gr_lim", new String[] { "lim", "Limburgan; Limburger; Limburgish" });
		aMap.put("gr_lin", new String[] { "lin", "Lingala" });
		aMap.put("gr_lit", new String[] { "lit", "Lithuanian" });
		aMap.put("gr_jbo", new String[] { "jbo", "Lojban" });
		aMap.put("gr_nds", new String[] { "nds", "Low German; Low Saxon; German, Low;" });
		aMap.put("gr_dsb", new String[] { "dsb", "Lower Sorbian" });
		aMap.put("gr_loz", new String[] { "loz", "Lozi" });
		aMap.put("gr_lub", new String[] { "lub", "Luba-Katanga" });
		aMap.put("gr_lua", new String[] { "lua", "Luba-Lulua" });
		aMap.put("gr_lui", new String[] { "lui", "Luiseno" });
		aMap.put("gr_smj", new String[] { "smj", "Lule Sami" });
		aMap.put("gr_lun", new String[] { "lun", "Lunda" });
		aMap.put("gr_luo", new String[] { "luo", "Luo (Kenya and Tanzania)" });
		aMap.put("gr_lus", new String[] { "lus", "Lushai" });
		aMap.put("gr_ltz", new String[] { "ltz", "Luxembourgish; Letzeburgesch" });
		aMap.put("gr_mkd", new String[] { "mkd", "Macedonian" });
		aMap.put("gr_mad", new String[] { "mad", "Madurese" });
		aMap.put("gr_mag", new String[] { "mag", "Magahi" });
		aMap.put("gr_mai", new String[] { "mai", "Maithili" });
		aMap.put("gr_mak", new String[] { "mak", "Makasar" });
		aMap.put("gr_mlg", new String[] { "mlg", "Malagasy" });
		aMap.put("gr_msa", new String[] { "msa", "Malay" });
		aMap.put("gr_mal", new String[] { "mal", "Malayalam" });
		aMap.put("gr_mlt", new String[] { "mlt", "Maltese" });
		aMap.put("gr_mnc", new String[] { "mnc", "Manchu" });
		aMap.put("gr_mdr", new String[] { "mdr", "Mandar" });
		aMap.put("gr_man", new String[] { "man", "Mandingo" });
		aMap.put("gr_mni", new String[] { "mni", "Manipuri" });
		aMap.put("gr_mno", new String[] { "mno", "Manobo languages" });
		aMap.put("gr_glv", new String[] { "glv", "Manx" });
		aMap.put("gr_mri", new String[] { "mri", "Maori" });
		aMap.put("gr_arn", new String[] { "arn", "Mapudungun; Mapuche" });
		aMap.put("gr_mar", new String[] { "mar", "Marathi" });
		aMap.put("gr_chm", new String[] { "chm", "Mari" });
		aMap.put("gr_mah", new String[] { "mah", "Marshallese" });
		aMap.put("gr_mwr", new String[] { "mwr", "Marwari" });
		aMap.put("gr_mas", new String[] { "mas", "Masai" });
		aMap.put("gr_myn", new String[] { "myn", "Mayan languages" });
		aMap.put("gr_men", new String[] { "men", "Mende" });
		aMap.put("gr_wtm", new String[] { "wtm", "Mewati" });
		aMap.put("gr_mic", new String[] { "mic", "Mi'kmaq; Micmac" });
		aMap.put("gr_min", new String[] { "min", "Minangkabau" });
		aMap.put("gr_mwl", new String[] { "mwl", "Mirandese" });
		aMap.put("gr_moh", new String[] { "moh", "Mohawk" });
		aMap.put("gr_mdf", new String[] { "mdf", "Moksha" });
		aMap.put("gr_mol", new String[] { "mol", "Moldavian" });
		aMap.put("gr_mkh", new String[] { "mkh", "Mon-Khmer (Other)" });
		aMap.put("gr_lol", new String[] { "lol", "Mongo" });
		aMap.put("gr_mon", new String[] { "mon", "Mongolian" });
		aMap.put("gr_mos", new String[] { "mos", "Mossi" });
		aMap.put("gr_mul", new String[] { "mul", "Multiple languages" });
		aMap.put("gr_mun", new String[] { "mun", "Munda languages" });
		aMap.put("gr_nqo", new String[] { "nqo", "N'Ko" });
		aMap.put("gr_nah", new String[] { "nah", "Nahuatl languages" });
		aMap.put("gr_nau", new String[] { "nau", "Nauru" });
		aMap.put("gr_nav", new String[] { "nav", "Navajo; Navaho" });
		aMap.put("gr_nde", new String[] { "nde", "Ndebele, North; North Ndebele" });
		aMap.put("gr_nbl", new String[] { "nbl", "Ndebele, South; South Ndebele" });
		aMap.put("gr_ndo", new String[] { "ndo", "Ndonga" });
		aMap.put("gr_nap", new String[] { "nap", "Neapolitan" });
		aMap.put("gr_new", new String[] { "new", "Nepal Bhasa; Newari" });
		aMap.put("gr_nep", new String[] { "nep", "Nepali" });
		aMap.put("gr_nia", new String[] { "nia", "Nias" });
		aMap.put("gr_nic", new String[] { "nic", "Niger-Kordofanian (Other)" });
		aMap.put("gr_ssa", new String[] { "ssa", "Nilo-Saharan (Other)" });
		aMap.put("gr_niu", new String[] { "niu", "Niuean" });
		aMap.put("gr_nog", new String[] { "nog", "Nogai" });
		aMap.put("gr_non", new String[] { "non", "Norse, Old" });
		aMap.put("gr_nai", new String[] { "nai", "North American Indian" });
		aMap.put("gr_frr", new String[] { "frr", "Northern Frisian" });
		aMap.put("gr_sme", new String[] { "sme", "Northern Sami" });
		aMap.put("gr_nno", new String[] { "nno", "Norwegian Nynorsk; Nynorsk, Norwegian" });
		aMap.put("gr_nor", new String[] { "nor", "Norwegian" });
		aMap.put("gr_nub", new String[] { "nub", "Nubian languages" });
		aMap.put("gr_nym", new String[] { "nym", "Nyamwezi" });
		aMap.put("gr_nyn", new String[] { "nyn", "Nyankole" });
		aMap.put("gr_nyo", new String[] { "nyo", "Nyoro" });
		aMap.put("gr_nzi", new String[] { "nzi", "Nzima" });
		aMap.put("gr_oci", new String[] { "oci", "Occitan (post 1500); Provençal" });
		aMap.put("gr_arc", new String[] { "arc", "Official Aramaic; Imperial Aramaic" });
		aMap.put("gr_oji", new String[] { "oji", "Ojibwa" });
		aMap.put("gr_ori", new String[] { "ori", "Oriya" });
		aMap.put("gr_orm", new String[] { "orm", "Oromo" });
		aMap.put("gr_osa", new String[] { "osa", "Osage" });
		aMap.put("gr_oss", new String[] { "oss", "Ossetian; Ossetic" });
		aMap.put("gr_oto", new String[] { "oto", "Otomian languages" });
		aMap.put("gr_pal", new String[] { "pal", "Pahlavi" });
		aMap.put("gr_pau", new String[] { "pau", "Palauan" });
		aMap.put("gr_pli", new String[] { "pli", "Pali" });
		aMap.put("gr_pam", new String[] { "pam", "Pampanga; Kapampangan" });
		aMap.put("gr_pag", new String[] { "pag", "Pangasinan" });
		aMap.put("gr_pan", new String[] { "pan", "Panjabi; Punjabi" });
		aMap.put("gr_pap", new String[] { "pap", "Papiamento" });
		aMap.put("gr_paa", new String[] { "paa", "Papuan (Other)" });
		aMap.put("gr_nso", new String[] { "nso", "Pedi; Sepedi; Northern Sotho" });
		aMap.put("gr_per", new String[] { "per", "Persian" });
		aMap.put("gr_peo", new String[] { "peo", "Persian, Old (ca.600-400 B.C.)" });
		aMap.put("gr_phi", new String[] { "phi", "Philippine (Other)" });
		aMap.put("gr_phn", new String[] { "phn", "Phoenician" });
		aMap.put("gr_pon", new String[] { "pon", "Pohnpeian" });
		aMap.put("gr_pol", new String[] { "pol", "Polish" });
		aMap.put("gr_por", new String[] { "por", "Portuguese" });
		aMap.put("gr_pra", new String[] { "pra", "Prakrit languages" });
		aMap.put("gr_pro", new String[] { "pro", "Provençal, Old (to 1500)" });
		aMap.put("gr_pus", new String[] { "pus", "Pushto; Pashto" });
		aMap.put("gr_que", new String[] { "que", "Quechua" });
		aMap.put("gr_raj", new String[] { "raj", "Rajasthani" });
		aMap.put("gr_rap", new String[] { "rap", "Rapanui" });
		aMap.put("gr_rar", new String[] { "rar", "Rarotongan; Cook Islands Maori" });
		aMap.put("gr_qaa", new String[] { "qaa", "Reserved for local use" });
		aMap.put("gr_roa", new String[] { "roa", "Romance (Other)" });
		aMap.put("gr_rum", new String[] { "rum", "Romanian" });
		aMap.put("gr_roh", new String[] { "roh", "Romansh" });
		aMap.put("gr_rom", new String[] { "rom", "Romany" });
		aMap.put("gr_run", new String[] { "run", "Rundi" });
		aMap.put("gr_rus", new String[] { "rus", "Russian" });
		aMap.put("gr_sal", new String[] { "sal", "Salishan languages" });
		aMap.put("gr_sam", new String[] { "sam", "Samaritan Aramaic" });
		aMap.put("gr_smi", new String[] { "smi", "Sami languages (Other)" });
		aMap.put("gr_smo", new String[] { "smo", "Samoan" });
		aMap.put("gr_sad", new String[] { "sad", "Sandawe" });
		aMap.put("gr_sag", new String[] { "sag", "Sango" });
		aMap.put("gr_san", new String[] { "san", "Sanskrit" });
		aMap.put("gr_sat", new String[] { "sat", "Santali" });
		aMap.put("gr_srd", new String[] { "srd", "Sardinian" });
		aMap.put("gr_sas", new String[] { "sas", "Sasak" });
		aMap.put("gr_sco", new String[] { "sco", "Scots" });
		aMap.put("gr_sel", new String[] { "sel", "Selkup" });
		aMap.put("gr_sem", new String[] { "sem", "Semitic (Other)" });
		aMap.put("gr_srp", new String[] { "srp", "Serbian" });
		aMap.put("gr_srr", new String[] { "srr", "Serer" });
		aMap.put("gr_shn", new String[] { "shn", "Shan" });
		aMap.put("gr_sna", new String[] { "sna", "Shona" });
		aMap.put("gr_iii", new String[] { "iii", "Sichuan Yi; Nuosu" });
		aMap.put("gr_scn", new String[] { "scn", "Sicilian" });
		aMap.put("gr_sid", new String[] { "sid", "Sidamo" });
		aMap.put("gr_sgn", new String[] { "sgn", "Sign Languages" });
		aMap.put("gr_bla", new String[] { "bla", "Siksika" });
		aMap.put("gr_snd", new String[] { "snd", "Sindhi" });
		aMap.put("gr_sin", new String[] { "sin", "Sinhala; Sinhalese" });
		aMap.put("gr_sit", new String[] { "sit", "Sino-Tibetan (Other)" });
		aMap.put("gr_sio", new String[] { "sio", "Siouan languages" });
		aMap.put("gr_sms", new String[] { "sms", "Skolt Sami" });
		aMap.put("gr_den", new String[] { "den", "Slave (Athapascan)" });
		aMap.put("gr_sla", new String[] { "sla", "Slavic (Other)" });
		aMap.put("gr_slo", new String[] { "slo", "Slovak" });
		aMap.put("gr_slv", new String[] { "slv", "Slovenian" });
		aMap.put("gr_sog", new String[] { "sog", "Sogdian" });
		aMap.put("gr_som", new String[] { "som", "Somali" });
		aMap.put("gr_son", new String[] { "son", "Songhai languages" });
		aMap.put("gr_snk", new String[] { "snk", "Soninke" });
		aMap.put("gr_wen", new String[] { "wen", "Sorbian languages" });
		aMap.put("gr_sot", new String[] { "sot", "Sotho, Southern" });
		aMap.put("gr_sai", new String[] { "sai", "South American Indian (Other)" });
		aMap.put("gr_alt", new String[] { "alt", "Southern Altai" });
		aMap.put("gr_sma", new String[] { "sma", "Southern Sami" });
		aMap.put("gr_spa", new String[] { "spa", "Spanish" });
		aMap.put("gr_srn", new String[] { "srn", "Sranan Tongo" });
		aMap.put("gr_suk", new String[] { "suk", "Sukuma" });
		aMap.put("gr_sux", new String[] { "sux", "Sumerian" });
		aMap.put("gr_sun", new String[] { "sun", "Sundanese" });
		aMap.put("gr_sus", new String[] { "sus", "Susu" });
		aMap.put("gr_swa", new String[] { "swa", "Swahili" });
		aMap.put("gr_ssw", new String[] { "ssw", "Swati" });
		aMap.put("gr_swe", new String[] { "swe", "Swedish" });
		aMap.put("gr_gsw", new String[] { "gsw", "Swiss German; Alemannic; Alsatian" });
		aMap.put("gr_syr", new String[] { "syr", "Syriac" });
		aMap.put("gr_tgl", new String[] { "tgl", "Tagalog" });
		aMap.put("gr_tah", new String[] { "tah", "Tahitian" });
		aMap.put("gr_tai", new String[] { "tai", "Tai (Other)" });
		aMap.put("gr_tgk", new String[] { "tgk", "Tajik" });
		aMap.put("gr_tmh", new String[] { "tmh", "Tamashek" });
		aMap.put("gr_tam", new String[] { "tam", "Tamil" });
		aMap.put("gr_tat", new String[] { "tat", "Tatar" });
		aMap.put("gr_tel", new String[] { "tel", "Telugu" });
		aMap.put("gr_ter", new String[] { "ter", "Tereno" });
		aMap.put("gr_tet", new String[] { "tet", "Tetum" });
		aMap.put("gr_tha", new String[] { "tha", "Thai" });
		aMap.put("gr_tib", new String[] { "tib", "Tibetan" });
		aMap.put("gr_tig", new String[] { "tig", "Tigre" });
		aMap.put("gr_tir", new String[] { "tir", "Tigrinya" });
		aMap.put("gr_tem", new String[] { "tem", "Timne" });
		aMap.put("gr_tiv", new String[] { "tiv", "Tiv" });
		aMap.put("gr_tli", new String[] { "tli", "Tlingit" });
		aMap.put("gr_tpi", new String[] { "tpi", "Tok Pisin" });
		aMap.put("gr_tkl", new String[] { "tkl", "Tokelau" });
		aMap.put("gr_tog", new String[] { "tog", "Tonga (Nyasa)" });
		aMap.put("gr_ton", new String[] { "ton", "Tonga (Tonga Islands)" });
		aMap.put("gr_tsi", new String[] { "tsi", "Tsimshian" });
		aMap.put("gr_tso", new String[] { "tso", "Tsonga" });
		aMap.put("gr_tsn", new String[] { "tsn", "Tswana" });
		aMap.put("gr_tum", new String[] { "tum", "Tumbuka" });
		aMap.put("gr_tup", new String[] { "tup", "Tupi languages" });
		aMap.put("gr_tur", new String[] { "tur", "Turkish" });
		aMap.put("gr_ota", new String[] { "ota", "Turkish, Ottoman (1500-1928)" });
		aMap.put("gr_tuk", new String[] { "tuk", "Turkmen" });
		aMap.put("gr_tvl", new String[] { "tvl", "Tuvalu" });
		aMap.put("gr_tyv", new String[] { "tyv", "Tuvinian" });
		aMap.put("gr_twi", new String[] { "twi", "Twi" });
		aMap.put("gr_udm", new String[] { "udm", "Udmurt" });
		aMap.put("gr_uga", new String[] { "uga", "Ugaritic" });
		aMap.put("gr_uig", new String[] { "uig", "Uighur; Uyghur" });
		aMap.put("gr_ukr", new String[] { "ukr", "Ukrainian" });
		aMap.put("gr_umb", new String[] { "umb", "Umbundu" });
		aMap.put("gr_mis", new String[] { "mis", "Uncoded languages" });
		aMap.put("gr_und", new String[] { "und", "Undetermined" });
		aMap.put("gr_hsb", new String[] { "hsb", "Upper Sorbian" });
		aMap.put("gr_urd", new String[] { "urd", "Urdu" });
		aMap.put("gr_uzb", new String[] { "uzb", "Uzbek" });
		aMap.put("gr_vai", new String[] { "vai", "Vai" });
		aMap.put("gr_ven", new String[] { "ven", "Venda" });
		aMap.put("gr_vie", new String[] { "vie", "Vietnamese" });
		aMap.put("gr_vol", new String[] { "vol", "Volapük" });
		aMap.put("gr_vot", new String[] { "vot", "Votic" });
		aMap.put("gr_wak", new String[] { "wak", "Wakashan languages" });
		aMap.put("gr_wal", new String[] { "wal", "Walamo" });
		aMap.put("gr_wln", new String[] { "wln", "Walloon" });
		aMap.put("gr_war", new String[] { "war", "Waray" });
		aMap.put("gr_was", new String[] { "was", "Washo" });
		aMap.put("gr_wel", new String[] { "wel", "Welsh" });
		aMap.put("gr_fry", new String[] { "fry", "Western Frisian" });
		aMap.put("gr_wol", new String[] { "wol", "Wolof" });
		aMap.put("gr_xho", new String[] { "xho", "Xhosa" });
		aMap.put("gr_sah", new String[] { "sah", "Yakut" });
		aMap.put("gr_yao", new String[] { "yao", "Yao" });
		aMap.put("gr_yap", new String[] { "yap", "Yapese" });
		aMap.put("gr_yid", new String[] { "yid", "Yiddish" });
		aMap.put("gr_yor", new String[] { "yor", "Yoruba" });
		aMap.put("gr_ypk", new String[] { "ypk", "Yupik languages" });
		aMap.put("gr_znd", new String[] { "znd", "Zande languages" });
		aMap.put("gr_zap", new String[] { "zap", "Zapotec" });
		aMap.put("gr_zza", new String[] { "zza", "Zaza; Dimili; Dimli; Kirdki; Kirmanjki; Zazaki" });
		aMap.put("gr_zen", new String[] { "zen", "Zenaga" });
		aMap.put("gr_zha", new String[] { "zha", "Zhuang; Chuang" });
		aMap.put("gr_zul", new String[] { "zul", "Zulu" });
		aMap.put("gr_zun", new String[] { "zun", "Zuni" });

		NAMES = aMap;
	};

	/**
	 * Returns an instance of this Enumeration, for the language that matches
	 * the id supplied.
	 * 
	 * @param id
	 * the Goodreads id code that represents this language.
	 * 
	 * @return an instance of Language for the language that matches the id
	 * supplied.
	 * 
	 * @throws InvalidLanguageException
	 * if the id doesn't match a language
	 */
	public static Language getLanguage(Integer id) throws InvalidLanguageException {

		/*
		 * Disabled pending further investigation into whether this field is
		 * *ever* populated and what the language codes are.
		 * 
		 * if (id == null) { return Language.unknown; } switch (id) { case 44:
		 * return Language.eng; // case 66: // return Language.fre; } throw new
		 * InvalidLanguageException(id);
		 */

		// Temporarily return null to all requests
		return null;
	}

	/**
	 * Converts the Goodreads code for a language into a custom, internal code.
	 * Required because some of the Goodreads codes are reserved keywords in
	 * Java.
	 * 
	 * @param goodreadsCode
	 * the native Goodreads language code. Usually, but not always, 3 characters
	 * long.
	 * 
	 * @return the internal equivalent language code.
	 * 
	 * @throws InvalidLanguageException
	 * if the Goodreads language code isn't null or empty but still doesn't
	 * match a known language code.
	 */
	public static Language getLanguage(String goodreadsCode) throws InvalidLanguageException {

		if (goodreadsCode == null || goodreadsCode.equals("")) {
			return Language.unknown;
		}

		Set<String> keys = NAMES.keySet();
		for (String currKey : keys) {

			String[] alternatives = NAMES.get(currKey);

			if (alternatives[0].equals(goodreadsCode)) {
				return Language.valueOf(currKey);
			}
		}

		throw new InvalidLanguageException(goodreadsCode);
	}

	/**
	 * Returns the original, shorter Goodreads code for this Language. WARNING:
	 * it includes at least one code that's also a reserved keyword in Java
	 * 
	 * @return the original Goodreads code for this language
	 */
	public String toGoodreadsCode() {

		// Retrieve the two alternative values for this Language
		String[] alternatives = NAMES.get(this);

		if (alternatives != null) {

			// Return the original Goodreads code
			return alternatives[0];
		}

		// An alternative wasn't found. Language unknown.
		return "Unknown";
	}

	/**
	 * @return the longer-form name associated with this Language.
	 */
	public String getName() {

		// Retrieve the two alternative values for this Language
		String[] alternatives = NAMES.get(this);

		if (alternatives != null) {

			// Return the longer form name
			return alternatives[1];
		}

		// An alternative wasn't found. Language unknown.
		return "Unknown";
	}
}
