ALTER TABLE news
    ADD COLUMN search tsvector
        GENERATED ALWAYS AS (to_tsvector('russian', replace(
                        coalesce(news.header, '') || ' ' || coalesce(news.content, ''),
                        'ё',
                        'е')))
            STORED;

CREATE INDEX news_search_index ON news USING GIN (search);

ALTER TABLE comments
    ADD COLUMN search tsvector
        GENERATED ALWAYS AS (to_tsvector('russian', replace(
                        coalesce(comments.header, '') || ' ' || coalesce(comments.content, ''),
                        'ё',
                        'е')))
            STORED;

CREATE INDEX comments_search_index ON comments USING GIN (search);
