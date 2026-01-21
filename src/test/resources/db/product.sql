CREATE TABLE silver.rating_engine.product
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