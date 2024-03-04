WITH series AS (SELECT generate_series(1, 200) as sery)

INSERT
INTO comments(header, content, published_by, news_id)
SELECT CASE
           WHEN s.sery % 5 = 0 then 'Комментарий 1' || s.sery
           WHEN s.sery % 4 = 0 then 'Комментарий 2' || s.sery
           WHEN s.sery % 3 = 0 then 'Комментарий 3' || s.sery
           WHEN s.sery % 2 = 0 then 'Комментарий 4' || s.sery
           ELSE 'Комментарий 5' || s.sery
           END,
       CASE
           WHEN s.sery % 5 = 0 then 'ВАААУУУ ' || s.sery
           WHEN s.sery % 4 = 0 then 'Это было круто :) ' || s.sery
           WHEN s.sery % 3 = 0 then 'Восхитительно. ' || s.sery
           WHEN s.sery % 2 = 0 then 'Хорошая работа! ' || s.sery
           ELSE 'Жаль я этим не интересуюсь(( ' || s.sery
           END,
       CASE
           WHEN s.sery % 5 = 0 then 'kate.kam'
           WHEN s.sery % 3 = 0 then 'yaroslav'
           WHEN s.sery % 2 = 0 then 'drozdova'
           ELSE 'starostenko'
           END,
       (CEIL(s.sery / 34.0::FLOAT)::INTEGER)
FROM series s
