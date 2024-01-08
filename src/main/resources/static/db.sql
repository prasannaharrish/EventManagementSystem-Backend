-- creating sample users;

INSERT INTO User (username, password, email, first_name, last_name,created_at, updated_at) VALUES
    ('user1', 'pass1', 'user1@example.com', 'John', 'Doe',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user2', 'pass2', 'user2@example.com', 'Alice', 'Smith',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user3', 'pass3', 'user3@example.com', 'Bob', 'Johnson',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user4', 'pass4', 'user4@example.com', 'Eva', 'Anderson',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user5', 'pass5', 'user5@example.com', 'Michael', 'Williams',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user6', 'pass6', 'user6@example.com', 'Samantha', 'Jones',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user7', 'pass7', 'user7@example.com', 'David', 'Brown',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user8', 'pass8', 'user8@example.com', 'Emma', 'Miller',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user9', 'pass9', 'user9@example.com', 'Christopher', 'White',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user10', 'pass10', 'user10@example.com', 'Olivia', 'Johnson',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ;

--creating sample events

INSERT INTO event (title, location, description, date, time, userId, category, createdAt, updatedAt)
VALUES
('Technical Event 1', 'Location 1', 'Technical Event Description', '2022-01-01', '10:00', 1, 'Technical', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cultural Event 1', 'Location 2', 'Cultural Event Description', '2022-02-01', '14:30', 2, 'Cultural', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Concert Event 1', 'Location 3', 'Concert Event Description', '2022-02-15', '18:00', 1, 'Concert', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Public Event 1', 'Location 4', 'Public Event Description', '2022-03-10', '20:00', 5, 'Public', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Workshop Event 1', 'Location 5', 'Workshop Event Description', '2022-04-05', '15:30', 4, 'Workshop', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Non-Technical Event 1', 'Location 6', 'Non-Technical Event Description', '2022-01-10', '22:00', 6, 'Non Technical', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Workshop Event 2', 'Location 7', 'Workshop Event Description', '2022-02-05', '12:30', 3, 'Workshop', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cultural Event 2', 'Location 8', 'Cultural Event Description', '2022-03-01', '16:45', 2, 'Cultural', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Concert Event 2', 'Location 9', 'Concert Event Description', '2022-03-20', '19:30', 7, 'Concert', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Public Event 2', 'Location 10', 'Public Event Description', '2022-04-15', '11:00', 9, 'Public', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Public Event 3', 'Location 46', 'Public Event Description', '2022-03-25', '12:30', 10, 'Public', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Private Event 1', 'Location 47', 'Private Event Description', '2022-04-05', '19:00', 8, 'Private', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Workshop Event 3', 'Location 48', 'Workshop Event Description', '2022-05-01', '14:00', 10, 'Workshop', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bootcamp Event 1', 'Location 49', 'Bootcamp Event Description', '2022-05-15', '10:30', 4, 'Bootcamp', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Concert Event 3', 'Location 50', 'Concert Event Description ', '2022-11-30', '14:45', 2, 'Concert', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



