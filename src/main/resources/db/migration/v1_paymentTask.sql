CREATE TABLE pay_payment_task (
                       reward_id uuid PRIMARY KEY,
                       questionnaire_id uuid,
                       mdm_id text,
                       recipient_type integer,
                       amount double precision,
                       status integer,
                       created_at timestamp,
                       account_system text,
                       account integer,
                       source_qs text,
                       response_sent boolean
);

CREATE TABLE pay_task_status_history (
                                  id uuid PRIMARY KEY,
                                  task_id uuid,
                                  status_details integer,
                                  task_status integer,
                                  status_updated_at timestamp
);

CREATE TABLE pay_task_statuses (
                                 status integer PRIMARY KEY,
                                 description text
);

CREATE TABLE pay_status_details (
                                    code integer PRIMARY KEY,
                                    description text
);

INSERT INTO pay_task_statuses (status, description) VALUES (10, 'New');
INSERT INTO pay_task_statuses (status, description) VALUES (20, 'Payed');
INSERT INTO pay_task_statuses (status, description) VALUES (30, 'Rejected');
INSERT INTO pay_task_statuses (status, description) VALUES (40, 'Manual processing');
INSERT INTO pay_task_statuses (status, description) VALUES (50, 'Ready for payment');


INSERT INTO pay_status_details (code, description) VALUES (101, 'Продуктовый Профиль ФЛ: клиент не найден в МДМ');
INSERT INTO pay_status_details (code, description) VALUES (103, 'Продуктовый Профиль ФЛ: некорректный запрос к сервису');
INSERT INTO pay_status_details (code, description) VALUES (004, 'Продуктовый Профиль ФЛ: ошибка доступа');
INSERT INTO pay_status_details (code, description) VALUES (201, 'Продуктовый Профиль ФЛ: Мастер-счет не найден');
INSERT INTO pay_status_details (code, description) VALUES (202, 'Продуктовый Профиль ФЛ: Мастер-счет арестован');




