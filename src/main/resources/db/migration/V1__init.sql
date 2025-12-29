CREATE TABLE `libraries` (
                             `library_id`	BIGINT	NOT NULL,
                             `name`	VARCHAR(255)	NOT NULL,
                             `location`	VARCHAR(255)	NOT NULL,
                             `website`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `users` (
                         `user_id`	BIGINT	NOT NULL,
                         `username`	VARCHAR(255)	NOT NULL,
                         `password`	VARCHAR(255)	NOT NULL	COMMENT 'password 인코딩',
                         `email`	VARCHAR(255)	NULL,
                         `mobile`	VARCHAR(255)	NULL
);

CREATE TABLE `books` (
                         `book_id`	BIGINT	NOT NULL,
                         `name`	VARCHAR(255)	NOT NULL,
                         `description`	VARCHAR(255)	NOT NULL,
                         `isbn`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `holdings` (
                            `holding_id`	BIGINT	NOT NULL,
                            `library_id`	BIGINT	NOT NULL,
                            `book_id`	BIGINT	NOT NULL,
                            `call_number`	VARCHAR(255)	NOT NULL,
                            `room`	VARCHAR(255)	NOT NULL,
                            `status`	enum('AVAILABLE', 'LOANED')	NOT NULL	DEFAULT 'AVAILABLE'
);

CREATE TABLE `loans` (
                         `loan_id`	BIGINT	NOT NULL,
                         `loan_date`	DATETIME	NOT NULL,
                         `due_date`	DATETIME	NOT NULL,
                         `return_date`	DATETIME	NULL,
                         `is_extended`	BOOLEAN	NOT NULL	DEFAULT FALSE,
                         `status`	enum('LOANED','RETURNED')	NOT NULL	DEFAULT 'LOANED',
                         `holding_id`	BIGINT	NOT NULL,
                         `user_id`	BIGINT	NOT NULL
);

CREATE TABLE `reservations` (
                                `reservation_id`	BIGINT	NOT NULL,
                                `reservation_date`	DATETIME	NOT NULL,
                                `status`	enum('WAITING', 'COMPLETED', 'CANCELED')	NOT NULL	DEFAULT 'WAITING',
                                `user_id`	BIGINT	NOT NULL,
                                `holding_id`	BIGINT	NOT NULL
);

ALTER TABLE `libraries` ADD PRIMARY KEY (
    `library_id`
);

ALTER TABLE `users` ADD PRIMARY KEY (
    `user_id`
);

ALTER TABLE `books` ADD PRIMARY KEY (
    `book_id`
);

ALTER TABLE `holdings` ADD PRIMARY KEY (
    `holding_id`
);

ALTER TABLE `loans` ADD PRIMARY KEY (
    `loan_id`
);

ALTER TABLE `reservations` ADD PRIMARY KEY (
    `reservation_id`
);

-- 1. 소장 도서(Holdings) 관계 설정
ALTER TABLE `holdings` ADD CONSTRAINT `FK_libraries_TO_holdings` FOREIGN KEY (
    `library_id`
) REFERENCES `libraries` (
    `library_id`
);

ALTER TABLE `holdings` ADD CONSTRAINT `FK_books_TO_holdings` FOREIGN KEY (
    `book_id`
) REFERENCES `books` (
    `book_id`
);

-- 2. 대출(Loans) 관계 설정
ALTER TABLE `loans` ADD CONSTRAINT `FK_holdings_TO_loans` FOREIGN KEY (
    `holding_id`
) REFERENCES `holdings` (
    `holding_id`
);

ALTER TABLE `loans` ADD CONSTRAINT `FK_users_TO_loans` FOREIGN KEY (
    `user_id`
) REFERENCES `users` (
    `user_id`
);

-- 3. 예약(Reservations) 관계 설정
ALTER TABLE `reservations` ADD CONSTRAINT `FK_users_TO_reservations` FOREIGN KEY (
    `user_id`
) REFERENCES `users` (
    `user_id`
);

ALTER TABLE `reservations` ADD CONSTRAINT `FK_holdings_TO_reservations` FOREIGN KEY (
    `holding_id`
) REFERENCES `holdings` (
    `holding_id`
);

