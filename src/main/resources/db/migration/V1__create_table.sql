CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    password VARCHAR(255) NOT NULL ,
    role VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE addresses (
    id BIGSERIAL NOT NULL,
    address VARCHAR (255),
    city VARCHAR (255),
    region_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE announcements (
    id BIGSERIAL NOT NULL,
    created_at DATE,
    description VARCHAR (2048),
    house_type VARCHAR (255),
    max_guests INTEGER,
    price NUMERIC (19, 2),
    status INTEGER,
    title VARCHAR (255),
    location_id BIGINT,
    owner_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE announcement_images (
    announcement_id BIGINT NOT NULL,
    images VARCHAR(255)
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
    announcement_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE feedbacks (
    id BIGSERIAL NOT NULL,
    created_at DATE,
    description VARCHAR(2048),
    dislike INTEGER,
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
























