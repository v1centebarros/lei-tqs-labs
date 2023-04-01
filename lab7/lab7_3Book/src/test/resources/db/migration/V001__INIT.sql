
CREATE TABLE books
(

    id        SERIAL         NOT NULL PRIMARY KEY,
    title     VARCHAR(255)   NOT NULL,
    author    VARCHAR(255)   NOT NULL,
    isbn      VARCHAR(255)   NOT NULL,
    publisher VARCHAR(255)   NOT NULL,
    year      INT            NOT NULL,
    price     DECIMAL(10, 2) NOT NULL
)
