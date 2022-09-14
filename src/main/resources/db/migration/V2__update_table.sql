ALTER TABLE IF EXISTS announcement_messages_from_admin
    ADD CONSTRAINT announcement_messages_from_admin_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements;

 ALTER TABLE IF EXISTS user_messages_from_admin
    ADD CONSTRAINT user_messages_from_admin_user_fk FOREIGN KEY (user_id) REFERENCES users;