CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    email VARCHAR(255),
    full_name VARCHAR(255),
    image VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE addresses (
    id BIGSERIAL NOT NULL,
    address VARCHAR (255),
    city VARCHAR (255),
    region_id INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE announcements (
    id BIGSERIAL NOT NULL ,
    created_at DATE,
    description VARCHAR (255),
    house_type VARCHAR (255),
    max_guests INTEGER,
    price NUMERIC (19, 2),
    status INTEGER,
    title VARCHAR (255),
    location_id INTEGER,
    owner_id INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE announcement_images (
    announcement_id INTEGER NOT NULL,
    images VARCHAR(255)
);

CREATE TABLE announcements_guests (
    announcement_id INTEGER NOT NULL,
    guests_id INTEGER NOT NULL
);

CREATE TABLE bookings (
    id BIGSERIAL NOT NULL,
    checkin DATE,
    checkout DATE,
    status INTEGER ,
    announcement_id INTEGER,
    user_id INTEGER ,
    PRIMARY KEY (id)
);

CREATE TABLE feedbacks (
    id BIGSERIAL NOT NULL,
    created_at DATE,
    description VARCHAR(255),
    dislike INTEGER,
    likes INTEGER,
    rating INTEGER,
    announcement_id INTEGER,
    owner_id INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE feedback_images (
    feedback_id INTEGER NOT NULL,
    images VARCHAR (255)
);

CREATE TABLE regions (
    id BIGSERIAL NOT NULL,
    region_name VARCHAR (255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS addresses
    ADD CONSTRAINT fk_address_region FOREIGN KEY (region_id) REFERENCES regions;

ALTER TABLE IF EXISTS announcement_images
    ADD CONSTRAINT fk_announcement_images_announcement FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS announcements
    ADD CONSTRAINT fk_announcement_address FOREIGN KEY (location_id) REFERENCES addresses;

ALTER TABLE IF EXISTS announcements
    ADD CONSTRAINT fk_announcement_user FOREIGN KEY (owner_id) REFERENCES users;

ALTER TABLE IF EXISTS announcements_guests
    ADD CONSTRAINT fk_announcement_guests_user FOREIGN KEY (guests_id) REFERENCES users;

ALTER TABLE IF EXISTS announcements_guests
    ADD CONSTRAINT fk_announcement_guests_announcement FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS bookings
    ADD CONSTRAINT fk_booking_announcement FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS bookings
    ADD CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS feedback_images
    ADD CONSTRAINT fk_feedback_images_feedback FOREIGN KEY (feedback_id) REFERENCES feedbacks;

ALTER TABLE IF EXISTS feedbacks
    ADD CONSTRAINT fk_feedback_announcement FOREIGN KEY (announcement_id) REFERENCES announcements;

ALTER TABLE IF EXISTS feedbacks
    ADD CONSTRAINT fk_feedback_user FOREIGN KEY (owner_id) REFERENCES users;
























