# rfrm-pay - Сервис начисления вознаграждений
- Выплата НДФЛ за начисление вознаграждения рефереру и рефералу
- Отправка данных для отчетности (6-НДФЛ)
- Передача данных по выплатам в DAPP

Скрипт для сопровода который выбирает задания на оплату с статусом "К выплате"
SELECT reward_id FROM ent_payment_task WHERE status = 50

Скрипт смены статуса поля processed в таблице ent_payment_task после отправки списка сопроводу
UPDATE ent_payment_task SET processed = false WHERE reward_id IN (SELECT reward_id FROM ent_payment_task WHERE status = 50)

