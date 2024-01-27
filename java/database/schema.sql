BEGIN TRANSACTION;

DROP TABLE IF EXISTS vote_comment;
DROP TABLE IF EXISTS forum_moderator;
DROP TABLE IF EXISTS favorite_forum;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS forums;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE forums (
    forum_id SERIAL PRIMARY KEY,
    user_id int REFERENCES users(user_id) NOT NULL,
    title varchar(200) NOT NULL
);

CREATE TABLE posts (
    post_id SERIAL PRIMARY KEY,
    forum_id int REFERENCES forums(forum_id) NOT NULL,
    title varchar(200) NOT NULL,
    url varchar(200)
);

CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    post_id int REFERENCES posts(post_id) NOT NULL,
    user_id int REFERENCES users(user_id) NOT NULL,
    body varchar(1000) NOT NULL,
    post_date TIMESTAMP NOT NULL,
    is_active boolean NOT NULL,
    reply_id int REFERENCES comments(comment_id)
);

CREATE TABLE favorite_forum (
    user_id int REFERENCES users(user_id),
    forum_id int REFERENCES forums(forum_id),
    CONSTRAINT pk_favorite_forum PRIMARY KEY (user_id, forum_id)
);

CREATE TABLE forum_moderator (
    moderator_id int REFERENCES users(user_id),
    forum_id int REFERENCES forums(forum_id),
    CONSTRAINT pk_forum_moderator PRIMARY KEY (moderator_id, forum_id)
);

CREATE TABLE vote_comment (
    user_id int REFERENCES users(user_id),
    comment_id int REFERENCES comments(comment_id),
    vote int NOT NULL,
    CONSTRAINT pk_vote_comment PRIMARY KEY (user_id, comment_id)
);

COMMIT TRANSACTION;
