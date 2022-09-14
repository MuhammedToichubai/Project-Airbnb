

 ALTER TABLE IF EXISTS user_messages_from_admin
    ADD CONSTRAINT user_messages_from_admin_user_fk FOREIGN KEY (user_id) REFERENCES users;