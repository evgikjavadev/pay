
BEGIN;
CREATE TABLE IF NOT EXISTS ent_payment_task (
                       reward_id bigint PRIMARY KEY NOT NULL,
                       questionnaire_id uuid NOT NULL,
                       mdm_id bigint NOT NULL,
                       recipient_type integer NOT NULL,
                       amount numeric NOT NULL,
                       status integer NOT NULL,
                       created_at timestamp NOT NULL,
                       account_system VARCHAR(255),
                       account VARCHAR(255),
                       source_qs VARCHAR(255) NOT NULL,
                       processed BOOLEAN,
                       blocked integer NOT NULL,
                       blocked_at timestamp
);
COMMIT;

comment on table ent_payment_task is 'Хранение данных о заданиях на оплату реферальных вознаграждений и их результатах';

comment on column ent_payment_task.reward_id is 'ID задачи на оплату (генерируется ядром 2155 rfrm-core)';

comment on column ent_payment_task.questionnaire_id is 'ID заявки на участие в реферальной программе';

comment on column ent_payment_task.mdm_id is 'ID получателя вознаграждения в MDM';

comment on column ent_payment_task.recipient_type is 'Тип получателя вознаграждения';

comment on column ent_payment_task.amount is 'Сумма вознаграждения (определяется ядром 2155 rfrm-core)';

comment on column ent_payment_task.status is 'Cтатус задания на оплату';

comment on column ent_payment_task.created_at is 'Время создания задания в БД';

comment on column ent_payment_task.account_system is 'Система, в которой расположен счет клиента для оплаты вознаграждения';

comment on column ent_payment_task.account is 'Счет клиента для оплаты вознаграждения';

comment on column ent_payment_task.source_qs is 'ID продукта, по которому должно быть выплачено вознаграждение за участие в реферальной программе';

comment on column ent_payment_task.processed is 'Признак передачи события смены финального статуса в kafka (необходим для ручного процесса оплаты)';

comment on column ent_payment_task.blocked is 'Отметка о блокировке задания (сигнализирует, что задание сейчас находится в обработке каким-то из подов микросервиса)';

comment on column ent_payment_task.blocked_at is 'Время последней установки blocked в 1';

alter table ent_payment_task owner to "rfrm_pay_admin";

create sequence status_history_sq as integer;

alter sequence status_history_sq owner to "rfrm_pay_admin";

BEGIN;
CREATE TABLE IF NOT EXISTS ent_task_status_history (
                      status_history_id bigint default nextval('rfrm_pay.status_history_sq'::regclass) not null
                          constraint ent_task_status_history_pk
                              primary key,
                      reward_id bigint NOT NULL,
                      status_details_code integer,
                      task_status integer NOT NULL,
                      status_updated_at timestamp NOT NULL,
                      CONSTRAINT fk_ent_payment_task
                          FOREIGN KEY (reward_id)
                              REFERENCES ent_payment_task(reward_id)
);
COMMIT;

comment on table ent_task_status_history is 'Хранение истории изменения статусов заданий на оплату';

comment on column ent_task_status_history.status_history_id is 'id события (ключ)';

comment on column ent_task_status_history.reward_id is 'id задания, по которому изменился статус';

comment on column ent_task_status_history.status_details_code is 'Код комментария к статусу заявки';

comment on column ent_task_status_history.task_status is 'Статус, присвоенный заданию';

comment on column ent_task_status_history.status_updated_at is 'Дата и время присвоения заданию вышеуказанного статуса';

alter table ent_task_status_history owner to "rfrm_pay_admin";

BEGIN;
CREATE TABLE IF NOT EXISTS dct_task_statuses (
                         status integer PRIMARY KEY NOT NULL ,
                         status_business_description VARCHAR(255) NOT NULL,
                         status_system_name VARCHAR(255) NOT NULL
);
COMMIT;

BEGIN;
CREATE TABLE IF NOT EXISTS dct_status_details (
                        status_details_code integer PRIMARY KEY NOT NULL,
                        description VARCHAR(255) NOT NULL
);
COMMIT;

BEGIN;
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (10, 'Новое', 'New');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (20, 'Выплачено', 'Payed');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (30, 'Отклонено', 'Rejected');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (40, 'Ручной разбор', 'Manual processing');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (50, 'К выплате', 'Ready for payment');
COMMIT;

BEGIN;
INSERT INTO dct_status_details (status_details_code, description) VALUES (101, 'Продуктовый Профиль ФЛ: клиент не найден в МДМ');
INSERT INTO dct_status_details (status_details_code, description) VALUES (103, 'Продуктовый Профиль ФЛ: некорректный запрос к сервису');
INSERT INTO dct_status_details (status_details_code, description) VALUES (104, 'Продуктовый Профиль ФЛ: ошибка доступа');
INSERT INTO dct_status_details (status_details_code, description) VALUES (201, 'Продуктовый Профиль ФЛ: Мастер-счет не найден');
INSERT INTO dct_status_details (status_details_code, description) VALUES (202, 'Продуктовый Профиль ФЛ: Мастер-счет арестован');
INSERT INTO dct_status_details (status_details_code, description) VALUES (203, 'Продуктовый Профиль ФЛ: Не соблюдены требования к участию в акции');
COMMIT;
