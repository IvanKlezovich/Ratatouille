create TABLE IF NOT EXISTS delivery
(
    id       SERIAL      NOT NULL PRIMARY KEY,
    order_id TEXT        NOT NULL,
    status   VARCHAR(50) NOT NULL,
    message  TEXT
)