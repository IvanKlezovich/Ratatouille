CREATE TABLE IF NOT EXISTS pay
(
    id      BIGSERIAL PRIMARY KEY,
    orderId BIGINT       NOT NULL,
    cost    BIGINT       NOT NULL,
    status  VARCHAR(255) NOT NULL
);