INSERT INTO users (id, full_name, email, phone_number, password, image, role)
VALUES (1, 'Admin', 'admin@gmail.co', '+996 997997997', '$2a$10$UG2qe9cH4iB00B9dZvLp0exUt5WyQrN/eMOboNE3.tHV4hFXD9lr2',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'ADMIN'),
       (2, 'Default Vendor', 'vendor@gmail.com', '+996 707707707', '$2a$10$ifGBxnBxLfkBzgpZR/bBA.ocjTs2SOilwpZMJ47KLRnuU8sousuY6',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'USER'),
       (3, 'Default User', 'user@gmail.com', '+996 555555555', '$2a$10$RRAYDWDs/igIi02Zmhsfs.WrEq/rgAll8MZA0LsQ5cw15FIxHu9hK',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'USER'),
       (4, 'Bill Gates', 'billgates@gmail.com', '+996 222222222', '$2a$10$.YQm2c3IhgaqYVzeh3REFeMpCswkZ99bFznWwNLG7l0TRmji/YvRi',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'USER'),
       (5, 'Steve Jobs,', 'steve@gmail.com', '+996 770077077', '$2a$10$.YQm2c3IhgaqYVzeh3REFeMpCswkZ99bFznWwNLG7l0TRmji/YvRi',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'USER');

INSERT INTO regions(id, region_name)
VALUES (1, 'Bishkek'),
       (2, 'Osh City'),
       (3, 'Batken'),
       (4, 'Jalalabad'),
       (5, 'Naryn'),
       (6, 'Osh Obl'),
       (7, 'Talas'),
       (8, 'Chui'),
       (9, 'Issyk-Kul');

INSERT INTO addresses(id, city, address, region_id)
VALUES (1, 'Bishkek', 'Grazhdanskyi 119', 1),
       (2, 'Bishkek', 'Isakeeva 18', 1),
       (3, 'Bishkek', '12-57-85', 1),
       (4, 'Osh City', 'Raimbekova 11', 2),
       (5, 'Osh City', 'Shkolnaya 1', 2),
       (6, 'Osh City', 'Aravanskaya 117', 2),
       (7, 'Kyzyl-Kia', 'Sportivnaya 1', 3),
       (8, 'Kadamjay', 'Severnaya 1', 3),
       (9, 'Batken', 'Nooruz 55', 3),
       (10, 'Jalalabad', 'Lenina 15', 4),
       (11, 'Jalalabad', 'Ostrovskogo 143', 4),
       (12, 'Jalalabad', 'Jenijok 54', 4),
       (13, 'Naryn', 'Lenin 92', 5),
       (14, 'Naryn', 'Sayakbai 17', 5),
       (15, 'Naryn', 'Salkyn-Tor 5', 5),
       (16, 'Nookat', 'Kok-jar 39', 6),
       (17, 'Kara-Suu', 'Babash-Ata 115', 6),
       (18, 'Ozgon', 'Kurhsab 23', 6),
       (19, 'Talas', 'Berdike Baatry 340', 7),
       (20, 'Talas', 'Manas 77', 7),
       (21, 'Talas', 'Semetey 88', 7),
       (22, 'Tokmok', 'Kirova 16', 8),
       (23, 'Kara Balta', 'Asanov 22', 8),
       (24, 'Belovodsk', 'Aksu 18', 8),
       (25, 'Cholpon-Ata', 'Akmatbay Ata 116', 9),
       (26, 'Balykchy', 'Abdrahmanova 63', 9),
       (27, 'Karakol', 'Tynystanov 128', 9);

INSERT INTO announcements(id, created_at, title, description, price, max_guests, house_type, status,
                                       location_id, owner_id, likes, bookmarks, view_Announcements, color_Of_Like, color_Of_Bookmark, message_from_admin)
VALUES (1, '2022-08-19', 'Peaksoft', '4 спальни, 5 кроватей, 4 ванные', 2500, 10, 'HOUSE', 1, 1, 4, 23, 0, 348758, null,null, null ),
       (2, '2022-04-10', 'Уютная квартира в Тунгуче', '3-комнат, 8-этаж', 1500, 3, 'APARTMENT', 1, 2, 5, 43, 0, 35, null,null, null  ),
       (3, '2022-07-27', 'Элитка в микрашах', '1 спальня, 1 кровать, 1 ванная', 3500, 4, 'APARTMENT', 1, 3, 4, 15, 0, 65, null,null, null  ),
       (4, '2022-08-04', 'Маяк', '4 спальни, 5 кроватей, 4 ванные', 1700, 8, 'HOUSE', 1, 4, 5, 24, 0, 354, null,null, null  ),
       (5, '2022-08-05', 'Гостевой дом Парус', '2 спальни, 5 кроватей, 2 ванные', 1700, 5, 'HOUSE', 1, 5, 5, 51, 0, 357, null,null, null  ),
       (6, '2022-08-06', 'Гостиница Алай', '6 спальни, 12 кроватей, 6 ванные', 1700, 18, 'HOUSE', 1, 6, 4, 52, 0, 643, null,null, null  ),
       (7, '2022-08-06', 'Квартира для студентов', '2 спальни, 4 кроватей, газ, центр', 1700, 5, 'APARTMENT', 1, 7, 5, 16, 0, 74, null,null, null  ),
       (8, '2022-08-07', 'Квартира для семьи', '1 спальни, газ, лифт', 800, 4, 'APARTMENT', 1, 8, 4, 26, 0, 564, null,null, null  ),
       (9, '2022-08-07', 'Гостевой дом Айгул', '3 спальни, аристон,стоянка ', 1800, 8, 'HOUSE', 1, 9, 5, 23, 0, 30, null,null, null  ),
       (10, '2022-08-07', 'Квартира', '2-ком,район горбольницы,', 1800, 4, 'APARTMENT', 1, 10, 5, 12, 0, 340, null,null, null  ),
       (11, '2022-08-08', 'Квартира', '1-ком,все удобства', 2500, 3, 'APARTMENT', 1, 11, 4, 73, 0, 975, null,null, null  ),
       (12, '2022-08-08', 'Гост дом Арсланбоб','все удобства,район автовокзала', 1200, 10, 'HOUSE', 1, 12, 4, 38, 0, 56, null,null, null  ),
       (13, '2022-08-09', 'Гост дом Ат-Башы','все удобства,центр,охрана', 1500, 8, 'HOUSE', 1, 13, 5, 53, 0, 665, null,null, null  ),
       (14, '2022-08-09', 'Гост дом Саякбай','все удобства,центр,охрана', 1700, 10, 'HOUSE', 1, 14, 5, 79, 0, 5463, null,null, null  ),
       (15, '2022-08-09', 'Гост дом Салкын-Тор','все удобства,центр,охрана', 1800, 9, 'HOUSE', 1, 15, 5, 17, 0, 768, null,null, null  ),
       (16, '2022-08-10', 'Muhammeds Residense','Все удобства,центр,стоянка,охрана', 1000, 15, 'HOUSE', 1, 16, 4, 73, 0, 23, null,null, null  ),
       (17, '2022-08-10', 'Babash House','Все удобства,рядом с рынком', 1000, 6, 'HOUSE', 1, 17, 4, 97, 0, 422, null,null, null  ),
       (18, '2022-08-10', 'Квартира','1-ком,Все удобства,район военкомата', 500, 5, 'APARTMENT', 1, 18, 4, 67, 0, 235, null,null, null  ),
       (19, '2022-08-11', 'Квартира', '1-ком,все удобства,только иностранцам,', 1500, 3, 'APARTMENT', 1, 19, 5, 34, 0, 2353, null,null, null  ),
       (20, '2022-08-11', 'Квартира', '2-ком,все удобства,семейным только,', 700, 5, 'APARTMENT', 1, 20, 5, 65, 0, 32523, null,null, null  ),
       (21, '2022-08-11', 'Квартира', '3-ком,все удобства,центр', 1200, 6, 'APARTMENT', 0, 21, 5, 61, 0, 6755, null,null, null  ),
       (22, '2022-08-12', 'Гостиница', 'центр,стоянка,охрана,гор.вода', 1350, 22, 'HOUSE', 0, 22, 4, 87, 0, 235, null,null, null  ),
       (23, '2022-08-12', 'Гостевой Дом Жайыл', 'район вокзала,стоянка,охрана,гор.вода', 1200, 14, 'HOUSE', 0, 23, 4, 54, 0, 23, null,null, null  ),
       (24, '2022-08-12', 'Гостевой Дом Аксу', 'все удобства', 1200, 12, 'HOUSE', 0, 24, 4, 99, 0, 23523, null,null, null  ),
       (25, '2022-08-13', 'Квартира', '2-ком,5-мин от пляжа,все удобства', 3000, 6, 'APARTMENT', 0, 25, 5, 34, 0, 2352, null,null, null  ),
       (26, '2022-08-13', 'Квартира', '1-ком,100 метров до пляжа,все удобства', 2000, 3, 'APARTMENT', 0, 26, 5, 34, 0, 23350, null,null, null  ),
       (27, '2022-08-13', 'Квартира', '1-ком,центр,студенткам ИГУ,все удобства', 1000, 3, 'APARTMENT', 0, 27, 5, 23, 0, 340, null,null, null  );


INSERT INTO announcement_images(announcement_id,images)
VALUES (1,'https://a.cdn-hotels.com/cos/hotelsnearme/type_hotel.jpg'),
       (1,'https://media.timeout.com/images/105234673/630/472/image.jpg'),
       (1,'https://www.executivefantasyhotels.com/wp-content/uploads/2018/06/Emperador-Panorama5.jpg'),
       (1,'https://media.cntraveler.com/photos/57c705ed523900e805f2e389/master/w_2048,h_1536,c_limit/ExteriorTOut-MontageBeverlyHills-BeverlyHillsCA_CRHotel.jpg'),
       (2,'https://blog.virginia.org/wp-content/uploads/2019/06/HR19050101V_033.jpg'),
       (2,'https://blog.virginia.org/wp-content/uploads/2019/06/HR19050101V_033.jpg'),
       (3,'https://blueorangetravel.com/wp-content/uploads/2018/09/1_hotel_brooklyn_bridge_king_guestroom_dlt_1_hi__x_large.jpg'),
       (4,'https://media-cdn.tripadvisor.com/media/photo-s/06/ea/25/29/double-six-luxury-hotel.jpg'),
       (4,'https://media.boutiquehotel.me/hotel/cover-small/1695747_1523295060.jpg'),
       (4,'https://media.boutiquehotel.me/hotel/cover-small/240595_1565710448.jpg'),
       (4,'https://luxurysafes.me/blog/wp-content/uploads/2019/09/The-Best-Luxury-Hotels-At-World%E2%80%99s-Top-Marinas-2.jpg'),
       (5,'https://travel.home.sndimg.com/content/dam/images/travel/fullset/2013/05/06/00/grand-canyon-resorts-mirage.rend.hgtvcom.1280.960.suffix/1491591684843.jpeg'),
       (5,'https://img.etimg.com/thumb/width-640,height-480,imgsize-281456,resizemode-1,msid-61635046/luxury-hotels.jpg'),
       (5,'https://i1.wp.com/www.disneytouristblog.com/wp-content/uploads/2015/11/waldorf-astoria-orlando-disney-world-hotel-020.jpg'),
       (6,'https://static-new.lhw.com/HotelImages/Final/LW1319/lw1319_112716968_720x450.jpg'),
       (6,'https://specials-images.forbesimg.com/imageserve/5e3d0be3a854780006b0b73b/960x0.jpg'),
       (6,'https://i.pinimg.com/originals/99/e3/bc/99e3bc8c77b0676092d62685459c33fc.jpg'),
       (6,'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/p11-1567015448.jpg'),
       (7,'https://i0.wp.com/theluxurytravelexpert.com/wp-content/uploads/2016/11/me-ibiza-ibiza.jpg'),
       (8,'https://cf.bstatic.com/xdata/images/hotel/max500/299293216.jpg?k=78bcab67d9fc7b51b2271817fc24a2a0fcae18e74c4c0cc25c7b9fbbb3a265cf&o='),
       (8,'https://tropki.ru/images/hotels3/-672753/b256646769-sevastopol-uyutnyi-guesthouse.webp'),
       (8,'https://cf.bstatic.com/xdata/images/hotel/max1024x768/306007155.jpg?k=7a118a7c870b4e7e4f82807110a8b4e025b6e6199733df1a62949294a1e53f11&o=&hp=1'),
       (9,'https://tropki.ru/images/hotels3/-692793/b205775196-kabardinka-guest-house-dom-v-kedrovoy-roshche.webp'),
       (9,'https://papamaster.su/wp-content/uploads/2014/03/%D0%B1%D0%B0%D0%BD%D1%8F-%D0%B3%D0%BE%D1%81%D1%82%D0%B5%D0%B2%D0%BE%D0%B9-%D0%B4%D0%BE%D0%BC.jpg'),
       (9,'https://www.home-projects.ru/upload/medialibrary/e58/post_5c402fe6dbdcb.jpeg'),
       (9,'https://skalice.ru/800/600/http/remstroidomufa.ru/wp-content/uploads/2018/05/03d69b927ba898a4a3f2fba80558e0d8.jpg'),
       (10,'https://e-cis.info/upload/iblock/468/468522cdf3e9e11790f358f444e44b92.jpg'),
       (10,'https://q-xx.bstatic.com/xdata/images/hotel/max1024x768/215469846.jpg?k=65e8c41108c7fb7b4b542b4ec039e4960c331436d8b6310378898c4cdd7b975c&o='),
       (10,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkECMgcJIgnsBMGQQKxDj-tm4ha9NeZi0Ew4Z7b9dpdhnyS2nCUSwXXWY8zr7XHSLiikg&usqp=CAU'),
       (11,'https://hotel.am/uploads/posts/2020-02/1582535305_round1-3.jpg'),
       (12,'https://berejki-hall.ru/assets/images/resources/386/square/0img-6853.jpg'),
       (12,'https://berejki-hall.ru/assets/images/resources/387/square/0img-9580.jpg'),
       (13,'https://edem-v-gosti.ru/upload/resize_cache/iblock/3d1/300_200_2/solaris_vardane_00002.png'),
       (13,'https://edem-v-gosti.ru/upload/resize_cache/iblock/3bc/300_200_2/solaris_vardane_00001.png'),
       (13,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBlMvwuz3fxZpz1gkRHnCvXTgKPWhSwW5YnC_MK-XbfvYiAqY3F4VbOaCbpEZZVeUIWrE&usqp=CAU'),
       (14,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtaOBh9secoz2mugKnoQo65si6OnTApVjoAw&usqp=CAU'),
       (14,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF0cq85-i7n6syHXZgDaqiKByCx5GOFFJdJzuy64vilWRPkitKigSm4Z4fg7s8A2WFiyg&usqp=CAU'),
       (15,'https://edem-v-gosti.ru/upload/resize_cache/iblock/e98/300_200_2/vozba_17_gudauta_00001.png'),
       (15,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQE4ttU1-mukZ_VGTvtAGULWB8IfHEVLAXMhjzL9-BFVJtyLNU-xVX8H-zkT2pdX77EbqY&usqp=CAU'),
       (15,'https://img.privettur.ru/modules/photogallery/src/images/cache/163154-538-374-2-c9c81d65.jpg'),
       (16,'https://www.openbusiness.ru/upload/iblock/465/gostevoidom.jpg'),
       (17,'https://avatars.mds.yandex.net/get-altay/4802381/2a0000017a7a999b2bcdeacb08b830f324c0/M'),
       (17,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxGHbZjnpxIomHC8ebksHNY0KNXNo5UfkHRjZ8DIH1Y0a5HM_0xzfmvHMp7YicntXm35c&usqp=CAU'),
       (18,'https://cf.bstatic.com/xdata/images/hotel/270x200/375562086.jpg?k=13e5c3e36160a5115805fe83a8dc354cd417f836584b5c9d22c54f5097b72820&o='),
       (18,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTa-qniPQvJbpz9P-ZLG-22oxjVlVL_xS45aAbJacST0rhLS3cvrkbUI9s84QZ_UyJ2xQ&usqp=CAU'),
       (18,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJQduIkTeyNVu_jt7qzblkxhC4frNZhONlEjipHY_95XCqGJ5xM89YsUmYZqegOZ1JI5k&usqp=CAU'),
       (18,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTu4PzC6mZRRfPscLB7ktH4K_NtVIhlMc738KOdkJgQNI-LQn5eRDdzrEch92ZHLx6HqV0&usqp=CAU'),
       (19,'https://kolsaytur.kz/images/Ardak/Dom88.JPG'),
       (19,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlqYd5KCsOLWCFZVZSCw4i7GNFsr8Pq1fcwJdueGX6AsKh8wss5MSypQLWaP2R7F4Cpxk&usqp=CAU'),
       (20,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9-KFXZUs5jPt1YlLssgA1bPAZZVfp2LK7QA&usqp=CAU'),
       (20,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxg-ikkOZf9W6yiOixB7rfuK9cDVDE8wWDwQ&usqp=CAU'),
       (20,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSsqI_CNuGGhVdms3-iRKkwkv6Ns7_l-43skg&usqp=CAU'),
       (21,'https://otdih.nakubani.ru/m/catalogBig/57c041e4fe429c6b6e8b45df/'),
       (21,'https://travelandia.ru/data/n09-35-091651343709-895.jpg'),
       (22,'https://hotels-arkhyz.ru/assets/images/site/glavnaya/ekonom/sosnovi-bereg.jpg'),
       (22,'https://q-xx.bstatic.com/xdata/images/hotel/max500/152128841.jpg?k=2093253c208601ac9d2112cb2b65954a88247e395df2fb9af114f53954435af5&o='),
       (22,'https://tropki.ru/images/hotels3/-820272/b332958605-arkhyz-cottage-valentina-arhyz.webp'),
       (22,'https://tropki.ru/images/hotels3/1582818466/b278871200-arkhyz-guest-house-lesogorie.webp'),
       (23,'https://baikall.com/images/projivanie/1viktoriya-bo.jpg'),
       (24,'https://www.issykkul.biz/Portals/0/GHA_imgs/karavan/dvor_gostevogo_doma_karavan.JPG_140.JPG'),
       (24,'https://www.issykkul.biz/Portals/0/GHA_imgs/karavan/dvor_gostevogo_doma_karavan_2.JPG_734.JPG'),
       (25,'https://azur.ru/data/o/34/20434/th4/dsc04237.jpg?1636384038'),
       (25,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhQcfJYc-X6kOH_I47TIicPkqij32LusUlc9Cbs67uV6XkNBO12XYJoro7l2E3prWkNbo&usqp=CAU'),
       (25,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwhGxVfLjgAX-4ly-Egx0adX_BVnOL6ofV69jQgclUWks0Jm0gjCXqfjxXGt-8zdIFnwo&usqp=CAU'),
       (26,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaLfa5HdZrEqPuT_15tSP8oETSqBnNAkZ86Q&usqp=CAU'),
       (26,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfkg9oshTiKVV37XmRom1gjLerXTRxWGSgaWzMZILrCc_N_kJQ3KJuPEKTCETLl9aw_v4&usqp=CAU'),
       (27,'https://narochpark.by/wp-content/uploads/2021/08/DSC8045-1024x683.jpg'),
       (27,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdBYxHazfmNh6pjlVPrH5IHCxI6rEmbaImhLBt2JRH-l89yyYw1BZCiFZKEpinM8eIPwY&usqp=CAU'),
       (27,'https://gorod-kurort-anapa.ru/hot_img/323_gdv_semyaumorya/main.jpg'),
       (27,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1gMSnLoWeQD4uXCbc1iQ0kJELCRZ-W_MxEw&usqp=CAU');

INSERT INTO feedbacks(id, created_at, descriptions, likes, color_Of_Like,
                      dis_likes, color_Of_Dis_Like, rating, announcement_id, owner_id)
VALUES (1, '2022-08-19', '', 0, null, 0, null , 4, 1, 2 ),
       (2, '2022-08-19', '', 0, null, 0, null , 5, 1, 5 ),
       (3, '2022-08-19', '', 0, null, 0, null , 3, 1, 4 ),
       (4, '2022-08-19', '', 0, null, 0, null , 4, 1, 3 ),
       (5, '2022-08-19', '', 0, null, 0, null , 1, 2, 5 ),
       (6, '2022-08-19', '', 0, null, 0, null , 4, 1, 2 ),
       (7, '2022-04-10', '', 0, null, 0, null , 5, 2, 4 ),
       (8, '2022-04-10', '', 0, null, 0, null , 5, 2, 3 ),
       (9, '2022-04-10', '', 0, null, 0, null , 5, 2, 5 ),
       (10, '2022-04-10', '', 0, null, 0, null , 3, 2, 2 ),
       (11, '2022-04-10', '', 0, null, 0, null , 4, 2, 4 ),
       (12, '2022-07-27', '', 0, null, 0, null , 5, 3, 3 ),
       (13, '2022-07-27', '', 0, null, 0, null , 5, 3, 5 ),
       (14, '2022-07-27', '', 0, null, 0, null , 3, 3, 3 ),
       (15, '2022-07-27', '', 0, null, 0, null , 3, 3, 2 ),
       (16, '2022-07-27', '', 0, null, 0, null , 4, 3, 5 ),
       (17, '2022-08-04', '', 0, null, 0, null , 5, 4, 4 ),
       (18, '2022-08-04', '', 0, null, 0, null , 4, 4, 2 ),
       (19, '2022-08-04', '', 0, null, 0, null , 5, 4, 3 ),
       (20, '2022-08-04', '', 0, null, 0, null , 3, 4, 5 ),
       (21, '2022-08-04', '', 0, null, 0, null , 4, 4, 4 ),
       (22, '2022-08-05', '', 0, null, 0, null , 5, 5, 3 ),
       (23, '2022-08-05', '', 0, null, 0, null , 5, 5, 2 ),
       (24, '2022-08-05', '', 0, null, 0, null , 2, 5, 2 ),
       (25, '2022-08-05', '', 0, null, 0, null , 3, 5, 4 ),
       (26, '2022-08-05', '', 0, null, 0, null , 4, 5, 3 ),
       (27, '2022-08-06', '', 0, null, 0, null , 4, 6, 5 ),
       (28, '2022-08-06', '', 0, null, 0, null , 5, 6, 3 ),
       (29, '2022-08-06', '', 0, null, 0, null , 1, 6, 2 ),
       (30, '2022-08-06', '', 0, null, 0, null , 5, 6, 5 ),
       (31, '2022-08-06', '', 0, null, 0, null , 4, 6, 4 ),
       (32, '2022-08-06', '', 0, null, 0, null , 3, 7, 2 ),
       (33, '2022-08-06', '', 0, null, 0, null , 1, 7, 3 ),
       (34, '2022-08-06', '', 0, null, 0, null , 5, 7, 5 ),
       (35, '2022-08-06', '', 0, null, 0, null , 5, 7, 4 ),
       (36, '2022-08-06', '', 0, null, 0, null , 4, 7, 3 ),
       (37, '2022-08-07', '', 0, null, 0, null , 5, 8, 2 ),
       (38, '2022-08-07', '', 0, null, 0, null , 5, 8, 2 ),
       (39, '2022-08-07', '', 0, null, 0, null , 3, 8, 4 ),
       (40, '2022-08-07', '', 0, null, 0, null , 5, 8, 3 ),
       (41, '2022-08-07', '', 0, null, 0, null , 5, 8, 5 ),
       (42, '2022-08-07', '', 0, null, 0, null , 3, 9, 3 ),
       (43, '2022-08-07', '', 0, null, 0, null , 5, 9, 2 ),
       (44, '2022-08-07', '', 0, null, 0, null , 5, 9, 5 ),
       (45, '2022-08-07', '', 0, null, 0, null , 4, 9, 4 ),
       (46, '2022-08-07', '', 0, null, 0, null , 4, 9, 2 ),
       (47, '2022-08-07','', 0, null, 0, null , 3, 10, 3 ),
       (48, '2022-08-07','', 0, null, 0, null , 2, 10, 5 ),
       (49, '2022-08-07','', 0, null, 0, null , 5, 10, 4 ),
       (50, '2022-08-07','', 0, null, 0, null , 5, 10, 3 ),
       (51, '2022-08-07','', 0, null, 0, null , 3, 10, 2 ),
       (52, '2022-08-08','', 0, null, 0, null , 3, 11, 2 ),
       (53, '2022-08-08','', 0, null, 0, null , 3, 11, 4 ),
       (54, '2022-08-08','', 0, null, 0, null , 5, 11, 3 ),
       (55, '2022-08-08','', 0, null, 0, null , 5, 11, 5 ),
       (56, '2022-08-08','', 0, null, 0, null , 2, 11, 3 ),
       (57, '2022-08-08','', 0, null, 0, null , 5, 12, 2 ),
       (58, '2022-08-08','', 0, null, 0, null , 1, 12, 5 ),
       (59, '2022-08-08','', 0, null, 0, null , 4, 12, 4 ),
       (60, '2022-08-08','', 0, null, 0, null , 5, 12, 2 ),
       (61, '2022-08-08','', 0, null, 0, null , 3, 12, 3 ),
       (62, '2022-08-09','', 0, null, 0, null , 5, 13, 5 ),
       (63, '2022-08-09','', 0, null, 0, null , 5, 13, 4 ),
       (64, '2022-08-09','', 0, null, 0, null , 2, 13, 3 ),
       (65, '2022-08-09','', 0, null, 0, null , 5, 13, 2 ),
       (66, '2022-08-09','', 0, null, 0, null , 1, 13, 2 ),
       (67, '2022-08-09','', 0, null, 0, null , 5, 14, 4 ),
       (68, '2022-08-09','', 0, null, 0, null , 3, 14, 3 ),
       (69, '2022-08-09','', 0, null, 0, null , 4, 14, 5 ),
       (70, '2022-08-09','', 0, null, 0, null , 4, 14, 3 ),
       (71, '2022-08-09','', 0, null, 0, null , 4, 14, 2 ),
       (72, '2022-08-09','', 0, null, 0, null , 5, 15, 5 ),
       (73, '2022-08-09','', 0, null, 0, null , 5, 15, 4 ),
       (74, '2022-08-09','', 0, null, 0, null , 4, 15, 2 ),
       (75, '2022-08-09','', 0, null, 0, null , 5, 15, 3 ),
       (76, '2022-08-09','', 0, null, 0, null , 2, 15, 5 ),
       (77, '2022-08-10','', 0, null, 0, null , 5, 16, 4 ),
       (78, '2022-08-10','', 0, null, 0, null , 3, 16, 3 ),
       (79, '2022-08-10','', 0, null, 0, null , 2, 16, 2 ),
       (80, '2022-08-10','', 0, null, 0, null , 4, 16, 2 ),
       (81, '2022-08-10','', 0, null, 0, null , 4, 16, 4 ),
       (82, '2022-08-10','', 0, null, 0, null , 5, 17, 3 ),
       (83, '2022-08-10','', 0, null, 0, null , 2, 17, 5 ),
       (84, '2022-08-10','', 0, null, 0, null , 5, 17, 3 ),
       (85, '2022-08-10','', 0, null, 0, null , 2, 17, 2 ),
       (86, '2022-08-10','', 0, null, 0, null , 5, 17, 5 ),
       (87, '2022-08-10','', 0, null, 0, null , 5, 18, 4 ),
       (88, '2022-08-10','', 0, null, 0, null , 3, 18, 2 ),
       (89, '2022-08-10','', 0, null, 0, null , 5, 18, 3 ),
       (90, '2022-08-10','', 0, null, 0, null , 4, 18, 5 ),
       (91, '2022-08-10','', 0, null, 0, null , 5, 18, 4 ),
       (92, '2022-08-11','', 0, null, 0, null , 4, 19, 3 ),
       (93, '2022-08-11','', 0, null, 0, null , 4, 19, 2 ),
       (94, '2022-08-11','', 0, null, 0, null , 5, 19, 2 ),
       (95, '2022-08-11','', 0, null, 0, null , 5, 19, 3 ),
       (96, '2022-08-11','', 0, null, 0, null , 5, 19, 5 ),
       (97, '2022-08-11','', 0, null, 0, null , 4, 20, 4 ),
       (98, '2022-08-11','', 0, null, 0, null , 5, 20, 3 ),
       (99, '2022-08-11','', 0, null, 0, null , 2, 20, 2 ),
       (100, '2022-08-11','', 0, null, 0, null , 1, 20, 2 ),
       (101, '2022-08-11','', 0, null, 0, null , 1, 20, 4 ),
       (102, '2022-08-11','', 0, null, 0, null , 5, 21, 3 ),
       (103, '2022-08-11','', 0, null, 0, null , 4, 21, 5 ),
       (104, '2022-08-11','', 0, null, 0, null , 5, 21, 3 ),
       (105, '2022-08-11','', 0, null, 0, null , 4, 21, 2 ),
       (106, '2022-08-11','', 0, null, 0, null , 5, 21, 5 ),
       (107, '2022-08-12','', 0, null, 0, null , 5, 22, 4 ),
       (108, '2022-08-12','', 0, null, 0, null , 5, 22, 2 ),
       (109, '2022-08-12','', 0, null, 0, null , 5, 22, 3 ),
       (110, '2022-08-12','', 0, null, 0, null , 4, 22, 5 ),
       (111, '2022-08-12','', 0, null, 0, null , 4, 22, 4 ),
       (112, '2022-08-12','', 0, null, 0, null , 5, 23, 3 ),
       (113, '2022-08-12','', 0, null, 0, null , 5, 23, 2 ),
       (114, '2022-08-12','', 0, null, 0, null , 5, 23, 2 ),
       (115, '2022-08-12','', 0, null, 0, null , 5, 23, 4 ),
       (116, '2022-08-12','', 0, null, 0, null , 2, 23, 3 ),
       (117, '2022-08-12','', 0, null, 0, null , 5, 24, 5 ),
       (118, '2022-08-12','', 0, null, 0, null , 5, 24, 3 ),
       (119, '2022-08-12','', 0, null, 0, null , 4, 24, 2 ),
       (120, '2022-08-12','', 0, null, 0, null , 2, 24, 5 ),
       (121, '2022-08-12','', 0, null, 0, null , 3, 24, 4 ),
       (122, '2022-08-13','', 0, null, 0, null , 4, 25, 2 ),
       (123, '2022-08-13','', 0, null, 0, null , 5, 25, 3 ),
       (124, '2022-08-13','', 0, null, 0, null , 5, 25, 5 ),
       (125, '2022-08-13','', 0, null, 0, null , 5, 25, 4 ),
       (126, '2022-08-13','', 0, null, 0, null , 4, 25, 3 ),
       (127, '2022-08-13','', 0, null, 0, null , 3, 26, 2 ),
       (128, '2022-08-13','', 0, null, 0, null , 3, 26, 2 ),
       (129, '2022-08-13','', 0, null, 0, null , 3, 26, 5 ),
       (130, '2022-08-13','', 0, null, 0, null , 2, 26, 4 ),
       (131, '2022-08-13','', 0, null, 0, null , 2, 26, 2 ),
       (132, '2022-08-13','', 0, null, 0, null , 2, 27, 3 ),
       (133, '2022-08-13','', 0, null, 0, null , 2, 27, 5 ),
       (134, '2022-08-13','', 0, null, 0, null , 3, 27, 4 ),
       (135, '2022-08-13','', 0, null, 0, null , 5, 27, 3 ),
       (136, '2022-08-13','', 0, null, 0, null , 5, 27, 2 );

INSERT INTO feedback_images(feedback_id,images)
VALUES (3,  'https://pbs.twimg.com/media/DMh1uoPWkAAKcST.jpg'),
       (3,  'https://exno.ru/image/kat/800/kvartira_252.jpg'),
       (3,  'https://mykaleidoscope.ru/uploads/posts/2021-03/1615777426_9-p-dvukhurovnevaya-kvartira-studiya-obraz-10.jpg'),
       (5,  'http://almode.ru/uploads/posts/2021-05/1620464549_22-p-kvartira-posle-remonta-s-mebelyu-23.jpg'),
       (5,  'https://стройки.net/images/wp-content/uploads/2016/01/dizajn-interera-odnokomnatnoj-kvartiry-39-kv-m42.jpg'),
       (5,  'https://gallery.forum-grad.ru/files/2/2/6/1/8/proekt_1komn_kvartiry_8.jpg'),
       (14, 'https://интересныерешения.рф/wp-content/uploads/2020/12/dizajn-kvartiry-50-kv_5d23e5074ad74.jpg'),
       (14, 'https://glossyideas.com/wp-content/uploads/2020/02/small-apartment-living-room-lovely-9-small-space-ideas-to-steal-from-a-tiny-paris-apartment-of-small-apartment-living-room.jpg'),
       (14, 'http://mirdizajna.ru/wp-content/uploads/2016/11/3-4-1024x956.jpg'),
       (20, 'https://www.magazindomov.ru/wp-content/uploads/2015/10/Residenza-Privata-9.jpg'),
       (20, 'http://99.img.avito.st/1280x960/3414988599.jpg'),
       (20, 'https://pbs.twimg.com/media/EGMo-1RXYAELVGe.jpg'),
       (24, 'https://i.pinimg.com/736x/ab/8f/a3/ab8fa3ec6fcbfdab315b9463309ec35f--home-interiors-modern.jpg'),
       (24, 'https://i.pinimg.com/736x/ab/8f/a3/ab8fa3ec6fcbfdab315b9463309ec35f--home-interiors-modern.jpg'),
       (24, 'https://pbs.twimg.com/media/C3Ggsv5XAAAXUYj?format=jpg&name=medium'),
       (29, 'https://krovlyakryshi.ru/wp-content/uploads/dizajn-gostinoj-v-sovremennom-stile-foto-7.jpg'),
       (29, 'https://images.cdn.inmyroom.ru/inmyroom/thumb/1240x796/jpg:60/uploads/post/teaser/7d/7d90/jpg_2000_7d90f6aa-b77f-4f8c-9a58-2e9fae7a910e.jpg?sign=a6582868d7b89a4941044b8d71d58c2e95ff50fb393a172b2e03cc1d3c793cb3'),
       (33, 'https://pbs.twimg.com/media/EQQoLayXYAAm9J0.jpg'),
       (33, 'https://archidom.ru/upload/iblock/e8b/e8b001441882c88f59eb9362712a9ac6.jpg'),
       (39, 'https://i.pinimg.com/originals/b6/f8/5d/b6f85dbeeb5589ed033d42641e1202bb.jpg'),
       (39, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (39, 'https://pbs.twimg.com/media/EQ4JP72XsAAzQSy.jpg'),
       (42, 'https://varlamov.me/img/rigaapp/05.jpg'),
       (42, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (48, 'https://pbs.twimg.com/media/C3Ggsv5XAAAXUYj?format=jpg&name=medium'),
       (48, 'https://mykaleidoscope.ru/uploads/posts/2021-03/1615662907_56-p-bezhevaya-gostinaya-v-sovremennom-stile-ob-58.jpg'),
       (53, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (53, 'https://pbs.twimg.com/media/EQQoLayXYAAm9J0.jpg'),
       (53, 'https://mebel-go.ru/mebelgoer/2088Apartment-design-in-united-kingdom-unique-contemporary-apartment.jpg'),
       (58, 'https://varlamov.me/img/rigaapp/05.jpg'),
       (58, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (64, 'https://pbs.twimg.com/media/EQ4JP72XsAAzQSy.jpg'),
       (64, 'https://krovlyakryshi.ru/wp-content/uploads/dizajn-gostinoj-v-sovremennom-stile-foto-7.jpg'),
       (64, 'https://mykaleidoscope.ru/uploads/posts/2021-03/1615662907_56-p-bezhevaya-gostinaya-v-sovremennom-stile-ob-58.jpg'),
       (68, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (68, 'https://loftecomarket.ru/wp-content/uploads/1/7/b/17bf8b6d197e38af769a0a588c51b8bf.jpeg'),
       (74, 'https://images.cdn.inmyroom.ru/inmyroom/thumb/1240x796/jpg:60/uploads/post/teaser/7d/7d90/jpg_2000_7d90f6aa-b77f-4f8c-9a58-2e9fae7a910e.jpg?sign=a6582868d7b89a4941044b8d71d58c2e95ff50fb393a172b2e03cc1d3c793cb3'),
       (74, 'https://pbs.twimg.com/media/EQ4JP72XsAAzQSy.jpg'),
       (74, 'https://archidom.ru/upload/iblock/e8b/e8b001441882c88f59eb9362712a9ac6.jpg'),
       (74, 'https://pbs.twimg.com/media/C3Ggsv5XAAAXUYj?format=jpg&name=medium'),
       (79, 'https://mebel-go.ru/mebelgoer/2088Apartment-design-in-united-kingdom-unique-contemporary-apartment.jpg'),
       (79, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (83, 'https://loftecomarket.ru/wp-content/uploads/1/7/b/17bf8b6d197e38af769a0a588c51b8bf.jpeg'),
       (83, 'https://images.cdn.inmyroom.ru/inmyroom/thumb/1880x1200/jpg:60/uploads/post/teaser/df/df24/jpg_2000_df24ca8e-d187-4461-a23b-2fadff52d894.jpg?sign=008e6e0095e77125e7a41308749ada64784b3a86777d234116e148f04823d828'),
       (88, 'https://mykaleidoscope.ru/uploads/posts/2021-03/1615662907_56-p-bezhevaya-gostinaya-v-sovremennom-stile-ob-58.jpg'),
       (88, 'https://pbs.twimg.com/media/EQQoLayXYAAm9J0.jpg'),
       (88, 'https://varlamov.me/img/rigaapp/05.jpg'),
       (88, 'https://pbs.twimg.com/media/EQ4JP72XsAAzQSy.jpg'),
       (93, 'https://loftecomarket.ru/wp-content/uploads/1/7/b/17bf8b6d197e38af769a0a588c51b8bf.jpeg'),
       (93, 'https://i.pinimg.com/originals/b6/f8/5d/b6f85dbeeb5589ed033d42641e1202bb.jpg'),
       (99, 'https://mebel-go.ru/mebelgoer/2088Apartment-design-in-united-kingdom-unique-contemporary-apartment.jpg'),
       (99, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (99, 'https://archidom.ru/upload/iblock/e8b/e8b001441882c88f59eb9362712a9ac6.jpg'),
       (103, 'https://loftecomarket.ru/wp-content/uploads/1/7/b/17bf8b6d197e38af769a0a588c51b8bf.jpeg'),
       (103, 'https://varlamov.me/img/rigaapp/05.jpg'),
       (103, 'https://mykaleidoscope.ru/uploads/posts/2021-03/1615662907_56-p-bezhevaya-gostinaya-v-sovremennom-stile-ob-58.jpg'),
       (103, 'https://krovlyakryshi.ru/wp-content/uploads/dizajn-gostinoj-v-sovremennom-stile-foto-7.jpg'),
       (116, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (116, 'https://images.cdn.inmyroom.ru/inmyroom/thumb/1880x1200/jpg:60/uploads/post/teaser/df/df24/jpg_2000_df24ca8e-d187-4461-a23b-2fadff52d894.jpg?sign=008e6e0095e77125e7a41308749ada64784b3a86777d234116e148f04823d828'),
       (120, 'https://pbs.twimg.com/media/EQQoLayXYAAm9J0.jpg'),
       (120, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (120, 'https://pbs.twimg.com/media/C3Ggsv5XAAAXUYj?format=jpg&name=medium'),
       (122, 'https://mebel-go.ru/mebelgoer/2088Apartment-design-in-united-kingdom-unique-contemporary-apartment.jpg'),
       (122, 'https://pbs.twimg.com/media/EQ4JP72XsAAzQSy.jpg'),
       (122, 'https://loftecomarket.ru/wp-content/uploads/1/7/b/17bf8b6d197e38af769a0a588c51b8bf.jpeg'),
       (122, 'https://varlamov.me/img/rigaapp/05.jpg'),
       (128, 'https://interiorsmall.ru/wp-content/uploads/potryasayushchiy-dizayn-odnokomnatnoy-kvartiry-02.jpg'),
       (128, 'https://krovlyakryshi.ru/wp-content/uploads/dizajn-gostinoj-v-sovremennom-stile-foto-7.jpg'),
       (132, 'https://pbs.twimg.com/media/EQ4JP72XsAAzQSy.jpg'),
       (132, 'https://images.cdn.inmyroom.ru/inmyroom/thumb/1240x796/jpg:60/uploads/post/teaser/7d/7d90/jpg_2000_7d90f6aa-b77f-4f8c-9a58-2e9fae7a910e.jpg?sign=a6582868d7b89a4941044b8d71d58c2e95ff50fb393a172b2e03cc1d3c793cb3'),
       (132, 'https://archidom.ru/upload/iblock/e8b/e8b001441882c88f59eb9362712a9ac6.jpg'),
       (133, 'https://images.cdn.inmyroom.ru/inmyroom/thumb/1880x1200/jpg:60/uploads/post/teaser/df/df24/jpg_2000_df24ca8e-d187-4461-a23b-2fadff52d894.jpg?sign=008e6e0095e77125e7a41308749ada64784b3a86777d234116e148f04823d828'),
       (133, 'https://pbs.twimg.com/media/EQQoLayXYAAm9J0.jpg'),
       (133, 'https://i.pinimg.com/originals/b6/f8/5d/b6f85dbeeb5589ed033d42641e1202bb.jpg'),
       (133, 'https://pbs.twimg.com/media/C3Ggsv5XAAAXUYj?format=jpg&name=medium');

INSERT INTO Bookings(id, checkin, checkout, created_at, status, announcement_id, user_id)
VALUES ( 1, '2022-09-20', '2022-11-15', null, 0, 1, 2),
       ( 2, '2022-09-19', '2022-08-15', null, 0, 2, 2),
       ( 3, '2022-09-20', '2022-08-03', null, 0, 3, 3),
       ( 4, '2022-09-20', '2022-08-15', null, 0, 4, 3),
       ( 5, '2022-09-20', '2022-08-03', null, 0, 5, 3),
       ( 6, '2022-09-20', '2022-08-15', null, 0, 6, 3),
       ( 7, '2022-09-19', '2022-08-15', null, 0, 7, 3),
       ( 8, '2022-09-20', '2022-08-15', null, 0, 8, 3),
       ( 9, '2022-09-19', '2022-08-15', null, 0, 9, 3),
       ( 10, '2022-09-19', '2022-08-15', null, 1, 10, 3),
       ( 11, '2022-09-20', '2022-08-03', null, 1, 11, 3),
       ( 12, '2022-09-19', '2022-08-15', null, 1, 12, 3),
       ( 13, '2022-09-19', '2022-08-15', null, 1, 13, 3),
       ( 14, '2022-09-20', '2022-08-15', null, 1, 14, 5),
       ( 15, '2022-09-19', '2022-08-03', null, 1, 15, 3),
       ( 16, '2022-09-20', '2022-08-15', null, 1, 16, 4),
       ( 17, '2022-09-19', '2022-08-15', null, 1, 17, 3),
       ( 18, '2022-09-20', '2022-08-15', null, 1, 18, 4),
       ( 19, '2022-09-19', '2022-08-15', null, 1, 19, 3),
       ( 20, '2022-09-19', '2022-08-03', null, 2, 20, 5),
       ( 21, '2022-09-20', '2022-08-15', null, 2, 21, 5),
       ( 22, '2022-09-20', '2022-08-15', null, 2, 22, 4),
       ( 23, '2022-09-19', '2022-08-15', null, 2, 23, 3),
       ( 24, '2022-09-20', '2022-08-03', null, 2, 24, 3),
       ( 25, '2022-09-20', '2022-08-15', null, 2, 25, 3),
       ( 26, '2022-09-19', '2022-08-03', null, 2, 26, 3),
       ( 27, '2023-09-20', '2022-08-15', null, 2, 27, 3);
