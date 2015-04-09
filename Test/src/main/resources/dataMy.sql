insert into Manufacturer(id, name, country) values(1000, 'TOPTUL', 'Taivan');
insert into Manufacturer(id, name, country) values(1001, 'JTC', 'Taivan');
insert into Manufacturer(id, name, country) values(1002, 'KINGTONY', 'Taivan');
insert into Manufacturer(id, name, country) values(1003, 'FORCE', 'China');
insert into Manufacturer(id, name, country) values(1004, 'SVavto', 'German');
insert into Category(id, parent_id, name) values(1017, 0, 'Специнструмент');
insert into Category(id, parent_id, name) values(1034, 0, 'Диагностика');
insert into Category(id, parent_id, name) values(1016, 0, 'Инструмент');
insert into Category(id, parent_id, name) values(1035, 1034, 'Автомобильные сканеры');
insert into Category(id, parent_id, name) values(1037, 1034, 'Видеобороскопы');
insert into Category(id, parent_id, name) values(1025, 1021, 'Гайковерты');
insert into Category(id, parent_id, name) values(1048, 1016, 'Динамометрические ключи');
insert into Category(id, parent_id, name) values(1018, 1017, 'Домкраты');
insert into Category(id, parent_id, name) values(1019, 1017, 'Замена масел');
insert into Category(id, parent_id, name) values(1049, 1048, 'Квадрат 1');
insert into Category(id, parent_id, name) values(1050, 1048, 'Квадрат 1/2');
insert into Category(id, parent_id, name) values(1051, 1048, 'Квадрат 1/4');
insert into Category(id, parent_id, name) values(1024, 1016, 'Ключи гаечные');
insert into Category(id, parent_id, name) values(1028, 1024, 'Ключи комбинированные');
insert into Category(id, parent_id, name) values(1029, 1024, 'Ключи комбинированные с трещоткой');
insert into Category(id, parent_id, name) values(1030, 1024, 'Ключи комбинированные супердлинные');
insert into Category(id, parent_id, name) values(1026, 1021, 'Молотки пневматические');
insert into Category(id, parent_id, name) values(1032, 1022, 'Отвертка-вороток');
insert into Category(id, parent_id, name) values(1022, 1016, 'Отвертки');
insert into Category(id, parent_id, name) values(1033, 1022, 'Отвертки TORX');
insert into Category(id, parent_id, name) values(1031, 1022, 'Отвертки крестовые');
insert into Category(id, parent_id, name) values(1027, 1021, 'Пистолеты для подкачки');
insert into Category(id, parent_id, name) values(1021, 1016, 'Пневмоинструмент');
insert into Category(id, parent_id, name) values(1020, 1017, 'Правка кузова');
insert into Category(id, parent_id, name) values(1046, 1017, 'Специнструмент BMW&BENZ');
insert into Category(id, parent_id, name) values(1047, 1017, 'Специнструмент VW&AUDI');
insert into Category(id, parent_id, name) values(1036, 1034, 'Стенды очистки форсунок');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1101, 1028, 1000, 'Ключ комбинированный 12mm', 28.00, 'AAEB1012', 1, 'img\228.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1099, 1028, 1000, 'Ключ комбинированный 10mm', 37.00, 'AAEB1010', 1, 'img\228.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1087, 1031, 1000, 'Отвертка крестовая PH0 60mm', 41.00, 'FBAB0006', 1, 'img\111.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1100, 1028, 1000, 'Ключ комбинированный 11mm', 41.00, 'AAEB1011', 1, 'img\228.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1104, 1030, 1000, 'Ключ комбинированный супердлинный 10mm', 42.00, 'AAAL1010', 1, 'img\0000.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1089, 1031, 1003, 'Отвертка крестовая PH1 80mm', 45.23,  'FBAB1100', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1090, 1031, 1001, 'Отвертка крестовая PH1 25mm', 46.00,	'FBAB1303', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1094, 1033, 1000, 'Отвертка TORX T15', 47.00, 'T15', 1, 'img\44.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1095, 1033, 1000, 'Отвертка TORX T20', 50.00, 'T20', 1, 'img\44.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1102, 1028, 1000, 'Ключ комбинированный 13mm', 51.00, 'AAEB1013', 1, 'img\228.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1091, 1032, 1000, 'Отвертка-вороток 1/4"  150mm', 55.00, 'CAIA0815', 1, 'img\333.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1092, 1032, 1000, 'Отвертка-вороток 1/4"  160mm', 56.00, 'CAIA0815', 1, 'img\333.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1103, 1028, 1000, 'Ключ комбинированный 15mm', 60.00, 'AAEB1015', 1, 'img\228.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1128, 1047, 1001, 'Головка для маслосливных пробок VW, AUDI', 72.00, 'M16T (VW, AUDI)', 1, 'img\1303_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1127, 1047, 1001, 'Головка для маслосливных пробок VW, AUDI', 72.00, '', 1, 'img\1303_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1126, 1047, 1001, 'Головка для маслосливных пробок VW, AUDI', 72.00, '', 1,	'img\1303_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1105, 1030, 1002, 'Ключ комбинированный супердлинный 16mm', 95.00, 'AAAL1016', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1133, 1047, 1001, 'Фиксатор коленвала m14', 107.00, 'M14xP1.5 1.4, 1.6 FSI, TSI', 1, 'img\1327_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1106, 1030, 1000, 'Ключ комбинированный супердлинный 17mm', 118.00, 'AAAL1017', 1, 'img\0000.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1107, 1030, 1000, 'Ключ комбинированный супердлинный 18mm', 135.00, 'AAAL1018',	1, 'img\0000.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1097, 1029, 1000, 'Ключ с трещоткой 11mm', 144.00, '11mm', 1, 'img\1111.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1098, 1029, 1000, 'Ключ с трещоткой 12mm', 151.00, '12mm', 1, 'img\1111.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1132, 1047, 1001, 'Фиксатор зубчатого колеса топливного насоса', 216.00, 'Passat 2006>, Touareg 2003>, Audi Q7 2007>', 1, 'img\1327_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1131, 1047, 1001, 'Приспособление для снятия хомутов', 4086.00, '', 1, 'img\1327_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1144, 1051, 1000, 'Ключ динамометрический 1/4"x350mm(L) 6-30Nm', 1025.00, '1/4"x350mm(L) 6-30Nm', 1, 'img\anam1610.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1122, 1046, 1001, 'Захват шкива коленвала', 634.00, 'MERCEDES  M113 , M112',	1, 'img\1011_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1120, 1046, 1001, 'Захват шкива коленвала', 634.00, '', 1, 'img\1011_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1121, 1046, 1001, 'Захват шкива коленвала', 634.00, '', 1, 'img\1011_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1085, 1027, 1003, 'Манометр', 307.00, '', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1064, 1019, 1001, 'Поддон для слива масла', 687.00, 'Объем 10 л', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1086, 1027, 1000, 'Пистолет для подкачки шин', 929.00, 'JEAL160A', 1, 'img\14.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1083, 1026, 1003, 'Молоток пневматический 3200ob/min', 1257.00, '', 1, 'img\10.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1084, 1026, 1000, 'Молоток пневматический 1/4', 1257.00, '', 1, 'img\3309.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1129, 1047, 1001, 'Приспособление для снятия сайлентблоков задней подвески', 1431.00, 'Audi A3 / VW Golf IV / Bora 1,4 / 1,6 / 1,8 / 2,0  1.9D (2001 ~ 2003)', 1, 'img\4649a_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1142, 1051, 1000, 'Ключ динамометрический 1/4"x260mm(L) 6-30Nm', 1496.00, '1/2"x680mm(L) 80-400Nm', 1, 	'img\anam1610.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1143, 1051, 1000, 'Ключ динамометрический 1/4"x290mm(L) 1-25Nm', 1546.00, '1/4"x290mm(L) 1-25Nm', 1, 'img\anam1610.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1130, 1047, 1001, 'Рассухариватель клапанов', 1711.00, 'Turbo(5V) AUDI A4,  PASSAT B5', 1, 'img\4188.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1139, 1050, 1000, 'Ключ динамометрический-1/2"x405mm(L) 10-100Nm', 1744.00, '1/2"x405mm(L) 10-100Nm', 1, 'img\anaa2470.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1140, 1050, 1000, 'Ключ динамометрический1/2"x500mm(L) 20-200Nm', 1834.00, '', 1, 'img\anaa2470.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1065, 1019, 1003, 'Приспособление для откачки техн. жидкостей',	2038.00, 'Рабочее давление 90psi', 1, 'img\Prisposoblenie.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1119, 1046, 1001, 'Головака для снятия фиксаторов редуктора', 2123.00, 'Mercedes 107, 114, 115, 116.', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1082, 1025, 1001, 'Гайковерт пневматический ударный 1/4', 2149.00, '1084 Nm (800ft-lb)', 1, 'img\9.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1136, 1020, 1003, 'Удлинители для гидроцилиндра', 2211.00, '(5", 10", 15", 20")', 1, 'img\8p101aa_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1141, 1050, 1000, 'Ключ динамометрический 1/2"x680mm(L) 80-400Nm', 2474.00, '1/2"x680mm(L) 80-400Nm', 1, 'img\anam1610.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1071, 1018, 1001, 'Подставка под авто 12t', 2906.00, 'Diapazon 614- 868vv', 1, 'img\Podstavka.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1080, 1025, 1003, 'Гайковерт пневматический ударный 1/2', 3149.00, '1084 Nm (800ft-lb)', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1081, 1025, 1001, 'Гайковерт пневматический ударный 1/2', 3149.00, '1084 Nm (800ft-lb)', 1, 'img\9.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1069, 1018, 1001, 'Домкрат гидравлический бутылочный 30t', 5146.00, '', 1, 'img\Domkrat2.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1117, 1037, 1003, 'Бороскоп 4825A', 4819.04, '', 1, 'img\5.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1135, 1020, 1001, 'Гидравлический насос с пневмо-приводом', 5921.00, '', 1, 'img\8p101aa_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1063, 1019, 1003, 'Пневмоустановка для подачи смазки', 7686.00, 'Емкость 20л', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1138, 1049, 1000, 'Ключ динамометрический 1-x1230mm', 8496.00, '140-980 Hm', 1, 'img\anaa2470.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1123, 1046, 1001, 'Инструмент для фиксации коробки передач', 8763.00, 'BENZ, BMW, VOLVO, AUDI', 1, 'img\1848.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1079, 1025, 1000, 'Гайковерт пневматический 3400 ob/min', 10971.00, '3390 Nm (2500ft-lb)', 1, 'img\7.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1067, 1018, 1003, 'Домкрат подкатной 5t', 12103.00, 'Domkrat podkatnoj 5t', 1, '');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1076, 1037, 1001, 'Бороскоп многофункциональный', 12727.00, '', 1, 'img\5.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1068, 1018, 1001, 'Домкрат подкатной 7t', 13203.00, 'Domkrat podkatnoj 7t', 1, 'img\Domkrat.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1124, 1046, 1001, 'Комплект для снятия-установки сайлентблоков подрамника', 13545.00, 'BENZ:W220 , W211 ,W203 BMW:E31 , E32, E34, E38, E39, E53, E60, E65, E66', 1, 'img\4820.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1074, 1035, 1000, 'Сканер X-431 QUICHEK', 15030.00, '', 0, 'img\3.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1073, 1035, 1000, 'Сканер X-431 PRO', 21710.00, 'Mercedes Benz, Audi, BMW, Ford (Europe), Citroen, Fiat, Lancia, Opel, Landrover, Peugeot, Renault', 1, 'img\x4312.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1137, 1020, 1003, 'Колонна рихтовочная', 26938.00, '(5", 10", 15", 20")', 1, 'img\pf1101_enl.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1072, 1035, 1000, 'Сканер X-431 PAD', 53440.00, 'ZOTYE; TATA, Daihatsu, Perodua', 0, 'img\scaner.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1077, 1036, 1012, 'Стенд диагностики и очистки форсунок', 65897.00, '370?295?333 mm', 1, 'img\6.jpg');
insert into Product(prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image) values(1078, 1036, 1012, 'Стенд очистки топливной системы', 65897.00, 'CDI, HDi, CRDi, TDCi, dCi, CDTi, DI-D, JTD, TDI, SDI', 1, 'img\7.jpg');