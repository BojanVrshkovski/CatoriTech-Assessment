CREATE TABLE users(
	id BIGSERIAL PRIMARY KEY,
	username varchar UNIQUE NOT NULL,
	password varchar NOT NULL,
	user_role varchar NOT NULL
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

ALTER TABLE contacts
ADD COLUMN business_id BIGINT,
ADD CONSTRAINT fk_business_id FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE;


CREATE TABLE businesses (
    id BIGSERIAL PRIMARY KEY,
    business_name VARCHAR(255) NOT NULL
);

SELECT * FROM users
SELECT * FROM contacts
