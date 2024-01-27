BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('user3','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user4','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user5','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

INSERT INTO forums (user_id, title) VALUES (1, 'Movies Forum');
INSERT INTO forum_moderator (moderator_id, forum_id) VALUES (1, 1);
INSERT INTO forums (user_id, title) VALUES (3, 'Video Games Forum');
INSERT INTO forum_moderator (moderator_id, forum_id) VALUES(1, 2);
INSERT INTO forums (user_id, title) VALUES (4, 'Programming Forum');
INSERT INTO forum_moderator (moderator_id, forum_id) VALUES (1, 3);

INSERT INTO posts (forum_id, title) VALUES (1, 'I really hated Star Wars: The Rise of Skywalker');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(1, 3, 'It Sucked. I miss the prequels.', CURRENT_TIMESTAMP, true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 1, 1);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(2, 1, 1);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(3, 1, 1);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(4, 1, -1);
INSERT INTO posts (forum_id, title) VALUES (1, 'The Godfather is such a timeless classic.');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(2, 4, 'Truly one of the movies of all time.', CURRENT_TIMESTAMP, true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 2, 1);
INSERT INTO posts (forum_id, title) VALUES (1, 'Lord of The Rings is still the greatest trilogy ever created in cinema.');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(3, 5, $$'I loved when Sauron said "I am the Lord of the Rings". Masterpiece.'$$, CURRENT_TIMESTAMP, true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 3, 1);

INSERT INTO posts (forum_id, title) VALUES (2, 'League of Legends has ruined my life.');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(4, 3, 'Release me from this torment.', CURRENT_TIMESTAMP, true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 4, 1);
INSERT INTO posts (forum_id, title) VALUES (2, 'Video games were better in the 90s!!!!');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(5, 4, 'I am just so sick of these new video games, it just is not the same!!', CURRENT_TIMESTAMP, true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 5, 1);
INSERT INTO posts (forum_id, title) VALUES (2, 'I am so hyped for Tetris 2.');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(6, 5, 'What if we had microtransactions in Tetris? That would be awesome I think.', CURRENT_TIMESTAMP, true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 6, 1);

INSERT INTO posts (forum_id, title) VALUES (3, 'Java or C#?');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(7, 3, 'I cannot decide. Somebody please decide for me.', CURRENT_TIMESTAMP, true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 7, 1);
INSERT INTO posts (forum_id, title) VALUES (3, 'Where do I declare variables in CSS');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(8, 4, 'Somebody please help me, I cannot find anything on the internet about it and it is driving me crazy.', '2024-01-17 11:30:30', true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 8, 1);
INSERT INTO posts (forum_id, title) VALUES (3, 'What does the C in C# stand for?');
INSERT INTO  comments (post_id, user_id, body, post_date, is_active)
VALUES(9, 5, 'Seriously, this one does not make sense.', '2024-01-18 2:00:00', true);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 9, 1);

INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (1, 1, 'I agree that movie sucked!', CURRENT_TIMESTAMP, true, 1);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 10, 1);

INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (1, 4, 'I thought it was ok', CURRENT_TIMESTAMP, true, 1);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 11, 1);

INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (2, 3, 'I loved that movie too!', CURRENT_TIMESTAMP, true, 2);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 12, 1);


INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (3, 4, 'Meh, I hate them. They completely changed from the books!', CURRENT_TIMESTAMP, true, 3);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 13, 1);


INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (4, 5, 'It truly is a prison of our own making.', CURRENT_TIMESTAMP, true, 4);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 14, 1);


INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (5, 1, 'Agreed. I really miss games like Super Mario 64.', CURRENT_TIMESTAMP, true, 5);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 15, 1);


INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (6, 3, 'Stop hyping this game up, it is going to be trash', CURRENT_TIMESTAMP, true, 6);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 16, 1);


INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (7, 4, 'Make your own decisions.', CURRENT_TIMESTAMP, true, 7);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 17, 1);


INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (8, 5, 'You have to find the answer within yourself. Do some meditation.', '2024-01-17 12:00:00', true, 8);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 18, 1);


INSERT INTO comments (post_id, user_id, body, post_date, is_active, reply_id)
VALUES (9, 1, 'I do not know. I think it is some musical term. Weird.', '2024-01-18 2:30:00', true, 9);
INSERT INTO vote_comment (user_id, comment_id, vote) VALUES(1, 19, 1);






COMMIT TRANSACTION;
