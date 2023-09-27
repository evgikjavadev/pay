# rfrm-pay - Сервис начисления вознаграждений
- Выплата НДФЛ за начисление вознаграждения рефереру и рефералу
- Отправка данных для отчетности (6-НДФЛ)
- Передача данных по выплатам в DAPP

Скрипт для сопровода который выбирает задания на оплату с статусом "К выплате"
SELECT reward_id FROM ent_payment_task WHERE status = 50

Список reward_id, по которым ДОПБ произвели оплату с помощью sql-скрипта "Payed"
При этом поля в ent-payment_task должны обновиться
UPDATE ent_payment_task SET processed = false,  status = 20 WHERE reward_id IN (SELECT reward_id FROM ent_payment_task WHERE status = 50)

Список reward_id, по которым ДОПБ отклонили оплату с помощью sql-скрипта "Rejected"
UPDATE ent_payment_task SET processed = false,  status = 30 WHERE reward_id IN (SELECT reward_id FROM ent_payment_task WHERE status = 50)

