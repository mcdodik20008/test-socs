--liquibase formatted sql
--changeset author:kaspshitskii-18-12-24-add-sock
CREATE TABLE sock(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    color VARCHAR(255) NOT NULL,
    cotton_percentage INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0
);

CREATE INDEX idx_sock_color_cotton_percentage ON sock (color, cotton_percentage);
