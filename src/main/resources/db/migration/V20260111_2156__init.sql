CREATE TABLE `libraries` (
    `library_id`	BIGINT	NOT NULL AUTO_INCREMENT,
    `name`	VARCHAR(255)	NOT NULL,
    `location`	VARCHAR(255)	NOT NULL,
    create_at datetime(6),
    update_at datetime(6),
    deleted_at datetime(6),
    primary key (`library_id`)
);

CREATE TABLE `users` (
    `user_id`	BIGINT	NOT NULL AUTO_INCREMENT,
    `username`	VARCHAR(255)	NOT NULL,
    `password`	VARCHAR(255)	NOT NULL,
    `email`	VARCHAR(255)	NULL,
    `mobile`	VARCHAR(255)	NULL,
    create_at datetime(6),
    update_at datetime(6),
    deleted_at datetime(6),
    primary key (`user_id`)
);

CREATE TABLE `books` (
    `book_id`	BIGINT	NOT NULL AUTO_INCREMENT,
    `name`	VARCHAR(255)	NOT NULL,
    `description`	VARCHAR(255)	NOT NULL,
    `isbn`	VARCHAR(255)	NOT NULL,
    create_at datetime(6),
    update_at datetime(6),
    deleted_at datetime(6),
    primary key (`book_id`)
);

CREATE TABLE `holdings` (
    `holding_id`	BIGINT	NOT NULL AUTO_INCREMENT,
    `library_id`	BIGINT	NOT NULL,
    `book_id`	BIGINT	NOT NULL,
    `call_number`	VARCHAR(255)	NOT NULL,
    `room`	VARCHAR(255)	NOT NULL,
    `status`	VARCHAR(255) NOT NULL	DEFAULT 'AVAILABLE' COMMENT '(AVAILABLE, LOANED)',
    create_at datetime(6),
    update_at datetime(6),
    deleted_at datetime(6),
    primary key (`holding_id`)
);

CREATE TABLE `loans` (
    `loan_id`	BIGINT	NOT NULL AUTO_INCREMENT,
    `loan_date`	DATETIME	NOT NULL,
    `due_date`	DATETIME	NOT NULL,
    `return_date`	DATETIME	NULL,
    `is_extended`	BOOLEAN	NOT NULL	DEFAULT FALSE,
    `status` VARCHAR(255) NOT NULL	DEFAULT 'LOANED' COMMENT '(LOANED,RETURNED)',
    `holding_id`	BIGINT	NOT NULL,
    `user_id`	BIGINT	NOT NULL,
    create_at datetime(6),
    update_at datetime(6),
    deleted_at datetime(6),
    primary key (`loan_id`)
);

CREATE TABLE `reservations` (
    `reservation_id`	BIGINT	NOT NULL AUTO_INCREMENT,
    `reservation_date`	DATETIME	NOT NULL,
    `status` VARCHAR(255) NOT NULL	DEFAULT 'WAITING' COMMENT '(WAITING, COMPLETED, CANCELED)',
    `user_id`	BIGINT	NOT NULL,
    `holding_id`	BIGINT	NOT NULL,
    create_at datetime(6),
    update_at datetime(6),
    deleted_at datetime(6),
    primary key (`reservation_id`)
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

ALTER TABLE users ADD CONSTRAINT UK_username UNIQUE (username);