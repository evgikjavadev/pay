CREATE TABLE IF NOT EXISTS ent_payment_task (
                       reward_id uuid PRIMARY KEY,
                       questionnaire_id uuid,
                       mdm_id text,
                       recipient_type integer,
                       amount double precision,
                       status integer,
                       created_at timestamp,
                       account_system text,
                       account text,
                       source_qs text,
                       response_sent boolean
);

CREATE TABLE IF NOT EXISTS ent_task_status_history (
                                  status_history_id BIGSERIAL PRIMARY KEY,
                                  task_id uuid,
                                  status_details_code integer,
                                  task_status integer,
                                  status_updated_at timestamp
);

CREATE TABLE IF NOT EXISTS dct_task_statuses (
                                 status integer PRIMARY KEY,
                                 status_business_description text,
                                 status_system_name text
);

CREATE TABLE IF NOT EXISTS dct_status_details (
                                    status_details_code integer PRIMARY KEY,
                                    related_status integer,
                                    description text
);

INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (10, 'Новое', 'New');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (20, 'Выплачено', 'Payed');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (30, 'Отклонено', 'Rejected');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (40, 'Ручной разбор', 'Manual processing');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (50, 'К выплате', 'Ready for payment');


INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (101, 4, 'Продуктовый Профиль ФЛ: клиент не найден в МДМ');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (103, 4, 'Продуктовый Профиль ФЛ: некорректный запрос к сервису');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (104, 4, 'Продуктовый Профиль ФЛ: ошибка доступа');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (201, 3, 'Продуктовый Профиль ФЛ: Мастер-счет не найден');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (202, 3, 'Продуктовый Профиль ФЛ: Мастер-счет арестован');




