
INSERT INTO users (id, full_name, email, password, image, role)
VALUES (2, 'Admin', 'airbnb@mail.com', '$2a$10$UG2qe9cH4iB00B9dZvLp0exUt5WyQrN/eMOboNE3.tHV4hFXD9lr2',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'ADMIN'),
       (3, 'Bill Gates', 'billgates@mail.com', '$2a$10$.YQm2c3IhgaqYVzeh3REFeMpCswkZ99bFznWwNLG7l0TRmji/YvRi',
        'https://thumbs.dreamstime.com/b/admin-sign-laptop-icon-stock-vector-166205404.jpg', 'USER');

INSERT INTO regions(id, region_name)
VALUES
       (1, 'Баткен'),
       (2, 'Жалал-Абад'),
       (3, 'Нарын'),
       (4, 'Ыссык-Кол'),
       (5, 'Талас'),
       (6, 'Ош'),
       (7, 'Чуй'),
       (8, 'Бишкек');


INSERT INTO addresses(id, city, address, region_id)
VALUES (1, 'Бишкек', 'Гражданская 119', 6),
       (2, 'Ноокат', 'Кок-жар 39', 8),
       (3, 'Чолпоната', 'Манас 1', 4),
       (4, 'Кадамжай', 'Курманжандатка 110', 1);

INSERT INTO announcements(id, created_at, title, description, price, max_guests, house_type, status,
                          location_id, owner_id)
VALUES
    (1, '2021-01-04', 'Peaksoft', '4 спальни, 5 кроватей, 4 ванные', 2500, 10, 'HOUSE', 0, 1, 2 ),
    (2, '2022-04-10', 'Отдельная комната в жилье типа домик на природе', '8 спален ,8 кроватей, 8 ванных комнат', 1500, 3, 'HOUSE', 0, 2, 2 ),
    (3, '2022-07-27', 'Makris Gialos Suites на пляже', '1 спальня, 1 кровать, 1 ванная', 3500, 4, 'APARTMENT', 0, 3, 2 ),
    (4, '2022-08-04', 'Маяк', '4 спальни, 5 кроватей, 4 ванные', 1700, 8, 'HOUSE', 0, 4, 2 );






