CREATE TABLE users(
	id BIGSERIAL PRIMARY KEY,
	username varchar UNIQUE NOT NULL,
	password varchar NOT NULL,
	contact_role varchar NOT NULL
);

CREATE TABLE contacts(
	id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	address VARCHAR(100) NOT NULL,
	phone VARCHAR(100) NOT NULL UNIQUE,
	VAT VARCHAR(20) NOT NULL,
	user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
