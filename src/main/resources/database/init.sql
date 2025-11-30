CREATE SCHEMA silver.rating_engine;



CREATE TABLE silver.rating_engine.billing_line
(
    id          BIGINT,
    contract_id VARCHAR,
    start_date  TIMESTAMP(6) WITH TIME ZONE,
    end_date    TIMESTAMP(6) WITH TIME ZONE,
    product_id  VARCHAR,
    consumption DECIMAL(19, 4),
    status      VARCHAR
)
    WITH (
        format = 'PARQUET'
        );


CREATE TABLE gold.rating_engine.billing_line_gold
(
    id           BIGINT,
    contract_id  VARCHAR,
    start_date   TIMESTAMP(6) WITH TIME ZONE,
    end_date     TIMESTAMP(6) WITH TIME ZONE,
    product_id   VARCHAR,
    price        DECIMAL(19, 4),
    created_date TIMESTAMP WITH TIME ZONE
)
    WITH (
        format = 'PARQUET'
        );


CREATE TABLE rating_engine.product
(
    id          BIGINT,
    product_id  VARCHAR,
    price       DECIMAL(19, 2),
    coefficient DECIMAL(4, 2),
    type        VARCHAR,
    formula     VARCHAR,
    monthly_fee DECIMAL(19, 2)
)
    WITH (
        format = 'PARQUET'
        );


INSERT INTO rating_engine.product (id, product_id, price, type, coefficient, formula, monthly_fee)
VALUES (1, 'PRODUCT_1', 99.99, 'SUBSCRIPTION', 1.15, 'BASE_PRICE * COEFFICIENT', 9.99),
       (2, 'PRODUCT_2', 29.99, 'SUBSCRIPTION', 1.05, 'BASE_PRICE * COEFFICIENT + MONTHLY_FEE', 4.99),
       (3, 'PRODUCT_3', 199.99, 'PREMIUM', 1.25, 'BASE_PRICE * COEFFICIENT', 19.99),
       (4, 'PRODUCT_4', 49.99, 'BASIC', 1.00, 'BASE_PRICE', 0.00);