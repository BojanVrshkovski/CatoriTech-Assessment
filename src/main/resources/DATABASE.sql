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

ALTER TABLE users
ADD COLUMN business_id BIGINT,
ADD CONSTRAINT fk_user_business FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE;


CREATE TABLE businesses (
    id BIGSERIAL PRIMARY KEY,
    business_name VARCHAR(255) NOT NULL
);

SELECT * FROM users
SELECT * FROM contacts
SELECT * FROM businesses


INSERT INTO businesses (business_name) VALUES
    ('Business A'),
    ('Business B'),
    ('Business C'),
    ('Business D'),
    ('Business E'),
    ('Business F'),
    ('Business G'),
    ('Business H'),
    ('Business I'),
    ('Business J');


INSERT INTO users (username, password, user_role, business_id) VALUES
    ('business_user1', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'BUSINESS', 1),
    ('business_user2', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'BUSINESS', 2),
    ('business_user3', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'BUSINESS', 3),
    ('business_user4', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'BUSINESS', 4),
    ('business_user5', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'BUSINESS', 5);

-- Individual Users
INSERT INTO users (username, password, user_role) VALUES
    ('individual_user1', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'INDIVIDUAL'),
    ('individual_user2', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'INDIVIDUAL'),
    ('individual_user3', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'INDIVIDUAL'),
    ('individual_user4', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'INDIVIDUAL'),
    ('individual_user5', '$2a$04$m1UxvY0tlAXaChnYK0KGv.PlY3gFKjdYyV0zydq6q.QJ4SgSq2BqK', 'INDIVIDUAL');


-- Business Contacts
INSERT INTO contacts (first_name, last_name, address, phone, VAT, user_id, business_id) VALUES
    ('Business Contact 1', 'Lastname', 'Business Address 1', '1234567890', 'VAT123', 21, 1),
    ('Business Contact 2', 'Lastname', 'Business Address 2', '2345678901', 'VAT234', 22, 2),
    ('Business Contact 3', 'Lastname', 'Business Address 3', '3456789012', 'VAT345', 23, 3),
    ('Business Contact 4', 'Lastname', 'Business Address 4', '4567890123', 'VAT456', 24, 4),
    ('Business Contact 5', 'Lastname', 'Business Address 5', '5678901234', 'VAT567', 25, 5);

-- Individual Contacts
INSERT INTO contacts (first_name, last_name, address, phone, VAT, user_id) VALUES
    ('Individual Contact 1', 'Lastname', 'Individual Address 1', '9876543210', 'VAT654', 26),
    ('Individual Contact 2', 'Lastname', 'Individual Address 2', '8765432109', 'VAT765', 27),
    ('Individual Contact 3', 'Lastname', 'Individual Address 3', '7654321098', 'VAT876', 28),
    ('Individual Contact 4', 'Lastname', 'Individual Address 4', '6543210987', 'VAT987', 29),
    ('Individual Contact 5', 'Lastname', 'Individual Address 5', '5432109876', 'VAT098', 30);


INSERT INTO contacts (first_name, last_name, address, phone, VAT, user_id, business_id) VALUES
('Business Contact BV', 'Lastname', 'Business Address 1', '1214567890', 'VAT123', 21, 1);












