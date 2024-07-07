create TABLE IF NOT EXISTS pay
(
    id       SERIAL         NOT NULL PRIMARY KEY,
    order_id TEXT           NOT NULL,
    cost     DECIMAL(10, 2) NOT NULL,
    status   VARCHAR(50)    NOT NULL
)