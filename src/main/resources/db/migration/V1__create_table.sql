CREATE SEQUENCE address_seq START 28 INCREMENT 1;
CREATE SEQUENCE announcement_seq START 28 INCREMENT 1;
CREATE SEQUENCE booking_seq START 28 INCREMENT 1;
CREATE SEQUENCE feedback_seq START 137 INCREMENT 1;
CREATE SEQUENCE region_seq START 1 INCREMENT 1;
CREATE SEQUENCE user_seq START 7 INCREMENT 1;


CREATE TABLE addresses (
    id BIGSERIAL NOT NULL,
    address VARCHAR (255),
    city VARCHAR (255),
    region_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE announcements (
    id BIGSERIAL NOT NULL,
    bookmarks INTEGER,
    color_of_bookmark VARCHAR (255),
    color_of_like VARCHAR (255),
    created_at DATE,
    description VARCHAR (10000),
    house_type VARCHAR (255),
    likes INTEGER,
    max_guests INTEGER,
    price NUMERIC (19, 2),
    status INTEGER,
    title VARCHAR (255),
    message_from_admin VARCHAR (500),
    view_announcements INTEGER,
    location_id BIGINT,
    owner_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE announcement_images (
    announcement_id BIGINT NOT NULL,
    images VARCHAR(255)
);

CREATE TABLE announcement_blocked_dates(
           announcement_id BIGINT NOT NULL ,
           blocked_dates DATE
);

CREATE TABLE announcement_blocked_dates_by_user(
                                                   announcement_id BIGINT NOT NULL ,
                                                   blocked_dates_by_user DATE
);

CREATE TABLE announcements_guests (
    announcement_id BIGINT NOT NULL,
    guests_id BIGINT NOT NULL
);

CREATE TABLE bookings (
    id BIGSERIAL NOT NULL,
    checkin DATE,
    checkout DATE,
    status INTEGER,
    price_per_day NUMERIC (19, 2),
    announcement_id BIGINT,
    user_id BIGINT,
    created_at DATE,
    PRIMARY KEY (id)
);

CREATE TABLE feedbacks (
    id BIGSERIAL NOT NULL,
    color_of_dis_like VARCHAR (255),
    color_of_like varchar(255),
    created_at DATE,
    descriptions VARCHAR(10000),
    dis_likes INTEGER,
    likes INTEGER,
    rating INTEGER,
    announcement_id BIGINT,
    owner_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE feedback_images (
    feedback_id BIGINT NOT NULL,
    images VARCHAR (255)
);

CREATE TABLE regions (
    id BIGSERIAL NOT NULL,
    region_name VARCHAR (255),
    PRIMARY KEY (id)
);
CREATE TABLE user_bookmark_announcements (
    user_id BIGSERIAL NOT NULL,
    bookmark_announcements BIGINT
);
CREATE TABLE user_dis_liked_feedbacks (
    user_id BIGSERIAL NOT NULL,
    dis_liked_feedbacks BIGINT
);
CREATE TABLE user_liked_announcements (
    user_id BIGSERIAL NOT NULL,
    liked_announcements BIGINT
);
CREATE TABLE user_liked_feedbacks (
    user_id BIGSERIAL NOT NULL,
    liked_feedbacks BIGINT
);
CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) ,
    phone_number VARCHAR (14) UNIQUE ,
    image VARCHAR(255),
    password VARCHAR(255) NOT NULL ,
    role VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE user_messages_from_admin (
     user_id BIGINT NOT NULL ,
     messages_from_admin VARCHAR (500)
);

ALTER TABLE IF EXISTS addresses
    ADD CONSTRAINT address_region_fk FOREIGN KEY (region_id) REFERENCES regions;

ALTER TABLE IF EXISTS announcement_images
    ADD CONSTRAINT announcement_images_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS announcements
    ADD CONSTRAINT announcement_address_fk FOREIGN KEY (location_id) REFERENCES addresses;

ALTER TABLE IF EXISTS announcements
    ADD CONSTRAINT announcement_user_fk FOREIGN KEY (owner_id) REFERENCES users;

ALTER TABLE IF EXISTS announcements_guests
    ADD CONSTRAINT announcement_guests_user_fk FOREIGN KEY (guests_id) REFERENCES users;

ALTER TABLE IF EXISTS announcements_guests
    ADD CONSTRAINT announcement_guests_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS bookings
    ADD CONSTRAINT booking_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS bookings
    ADD CONSTRAINT booking_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS feedback_images
    ADD CONSTRAINT feedback_images_feedback_fk FOREIGN KEY (feedback_id) REFERENCES feedbacks;

ALTER TABLE IF EXISTS feedbacks
    ADD CONSTRAINT feedback_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS feedbacks
    ADD CONSTRAINT feedback_user_fk FOREIGN KEY (owner_id) REFERENCES users;

ALTER TABLE IF EXISTS user_bookmark_announcements
    ADD CONSTRAINT user_bookmark_announcements_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS user_dis_liked_feedbacks
    ADD CONSTRAINT user_dis_liked_feedbacks_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS user_liked_announcements
    ADD CONSTRAINT user_liked_announcements_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS user_liked_feedbacks
    ADD CONSTRAINT user_liked_feedbacks_user_fk FOREIGN KEY (user_id) REFERENCES users;























