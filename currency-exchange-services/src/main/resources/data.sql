
CREATE TABLE CURRENCY_EXCHANGE (
    id INT,
    currency_from VARCHAR(3),
    currency_to VARCHAR(3),
    conversion_multiple DECIMAL(10,2),
    environment VARCHAR(255),
    PRIMARY KEY (id)
);

insert into currency_exchange
(id,currency_from,currency_to,conversion_Multiple,environment)
values (1081,'USD','INR',65,'');


insert into currency_exchange
(id,currency_from,currency_to,conversion_Multiple,environment)
values (1082,'EUR','INR',70,'');

insert into currency_exchange
(id,currency_from,currency_to,conversion_Multiple,environment)
values (1083,'AUD','INR',80,'');