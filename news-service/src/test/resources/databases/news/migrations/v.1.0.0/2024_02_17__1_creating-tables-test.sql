CREATE TABLE news
(
    id               BIGSERIAL PRIMARY KEY,
    header           TEXT CHECK ( LENGTH(header) <= 100 )   NOT NULL,
    content          TEXT CHECK ( LENGTH(content) <= 2000 ) NOT NULL,
    published_by     VARCHAR(255),
    created_at       TIMESTAMP DEFAULT NOW()                NOT NULL,
    updated_at       TIMESTAMP
);

CREATE TABLE comments
(
    id               BIGSERIAL PRIMARY KEY,
    header           TEXT CHECK ( LENGTH(header) <= 100 )   NOT NULL,
    content          TEXT CHECK ( LENGTH(content) <= 2000 ) NOT NULL,
    published_by     VARCHAR(255),
    news_id          BIGINT,
    created_at       TIMESTAMP DEFAULT NOW()                NOT NULL,
    updated_at       TIMESTAMP,
    CONSTRAINT fk_news FOREIGN KEY (news_id) REFERENCES news (id)
);
