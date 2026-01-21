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