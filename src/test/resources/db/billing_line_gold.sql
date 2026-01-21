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