 ALTER TABLE IF EXISTS user_messages_from_admin
    ADD CONSTRAINT user_messages_from_admin_user_fk FOREIGN KEY (user_id) REFERENCES users;

 ALTER TABLE IF EXISTS announcement_blocked_dates
     ADD CONSTRAINT announcement_blocked_dates_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements;

 ALTER TABLE IF EXISTS announcement_blocked_dates_by_user
     ADD CONSTRAINT announcement_blocked_dates_by_user_announcement_fk FOREIGN KEY (announcement_id) REFERENCES announcements;

