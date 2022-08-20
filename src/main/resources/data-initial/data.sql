INSERT INTO users (id, full_name, email, password, image, role)
VALUES (1, 'Admin', 'airbnb@gmail.com', '$2a$10$UG2qe9cH4iB00B9dZvLp0exUt5WyQrN/eMOboNE3.tHV4hFXD9lr2',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'ADMIN'),
       (2, 'Bill Gates', 'billgates@gmail.com', '$2a$10$.YQm2c3IhgaqYVzeh3REFeMpCswkZ99bFznWwNLG7l0TRmji/YvRi',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'USER'),
       (3, 'Steve Jobs,', 'steve@gmail.com', '$2a$10$.YQm2c3IhgaqYVzeh3REFeMpCswkZ99bFznWwNLG7l0TRmji/YvRi',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'USER');

INSERT INTO regions(id, region_name)
VALUES (1, 'Batken'),
       (2, 'Jalalabat'),
       (3, 'Naryn'),
       (4, 'Issyk-Kul'),
       (5, 'Talas'),
       (6, 'Osh'),
       (7, 'Chui'),
       (8, 'Bishkek');


INSERT INTO addresses(id, city, address, region_id)
VALUES (1, 'Bishkek', 'Grazhdanskyi 119', 8),
       (2, 'Nookat', 'Kok-jar 39', 6),
       (3, 'Cholponata', 'Manas 1', 4),
       (4, 'Kadamjai', 'Kurmanjandatka 110', 1);

INSERT INTO announcements(id, created_at, title, description, price, max_guests, house_type, status,
                          location_id, owner_id)
VALUES (1, '2021-01-04', 'Peaksoft', '4 спальни, 5 кроватей, 4 ванные', 2500, 10, 'HOUSE', 0, 1, 2),
       (2, '2022-04-10', 'Отдельная комната в жилье типа домик на природе', '8 спален ,8 кроватей, 8 ванных комнат',
        1500, 3, 'HOUSE', 0, 2, 2),
       (3, '2022-07-27', 'Makris Gialos Suites на пляже', '1 спальня, 1 кровать, 1 ванная', 3500, 4, 'APARTMENT', 0, 3,
        2),
       (4, '2022-08-04', 'Маяк', '4 спальни, 5 кроватей, 4 ванные', 1700, 8, 'HOUSE', 0, 4, 2);

-- INSERT INTO feedbacks(id, created_at, description,  likes, dislike, rating, announcement_id, owner_id)
-- VALUES
--        (1, '2021-05-05', 'Peaksoft is an architectural marvel. Breathtaking views from all over the house and the hospitality of the Peaksoft staff ensure that you leave Peaksoft looking forward to returning again and again. A truly unique experience. Highly recommend!',
--         true, , 5, 1, 3  );
--        (2, '2022-06-10', 'Үй-бүлө менен эс алуу үчүн идеалдуу жер. Бизге Ноокаттын атмосферасы абдан жакты.',
--         0, 1, 3, 2, 3  ),
--        (3, '2022-08-04', 'Простое жилье в заливе мечты. Отличная еда в ресторане, а также в соседних ресторанах. Очень хорошие владельцы гостиниц. До других небольших пляжей можно дойти пешком.',
--         0, 1, 2, 3, 3  ),
--        (4, '2022-08-05', 'But I can answer any questions on the phone, of course. The lighthouse is completely at your disposal. I''m staying elsewhere',
--         0, 0, 0, 4, 2  );



