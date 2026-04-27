CREATE TABLE activity
(
    id            UUID NOT NULL,
    client_id     UUID,
    order_id      UUID,
    type          VARCHAR(255),
    activity_date TIMESTAMP,
    amount        DECIMAL(10, 2),
    CONSTRAINT pk_activity PRIMARY KEY (id)
);

CREATE TABLE client
(
    id        UUID    NOT NULL,
    name      VARCHAR(255),
    surname   VARCHAR(255),
    email     VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE client_order
(
    id            UUID    NOT NULL,
    client_id     UUID,
    address       VARCHAR(255),
    price         DECIMAL(10, 2),
    delivery_date TIMESTAMP,
    is_paid       BOOLEAN NOT NULL,
    is_active     BOOLEAN NOT NULL,
    created_at    TIMESTAMP,
    is_delivered  BOOLEAN NOT NULL,
    reminder_sent BOOLEAN NOT NULL,
    CONSTRAINT pk_clientorder PRIMARY KEY (id)
);

CREATE TABLE offer
(
    id        UUID    NOT NULL,
    name      VARCHAR(255),
    price     DECIMAL(10, 2),
    meal_type VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    CONSTRAINT pk_offer PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id       UUID NOT NULL,
    order_id UUID,
    offer_id UUID,
    quantity INT,
    CONSTRAINT pk_orderitem PRIMARY KEY (id)
);

ALTER TABLE activity
    ADD CONSTRAINT FK_ACTIVITY_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE activity
    ADD CONSTRAINT FK_ACTIVITY_ON_ORDER FOREIGN KEY (order_id) REFERENCES client_order (id);

ALTER TABLE client_order
    ADD CONSTRAINT FK_CLIENTORDER_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_OFFER FOREIGN KEY (offer_id) REFERENCES offer (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES client_order (id);