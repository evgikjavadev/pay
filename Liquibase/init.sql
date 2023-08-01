CREATE TABLE IF NOT EXISTS ent_payment_task (
                       reward_id uuid PRIMARY KEY NOT NULL,
                       questionnaire_id uuid NOT NULL,
                       mdm_id VARCHAR(255) NOT NULL,
                       recipient_type integer NOT NULL,
                       amount double precision NOT NULL,
                       status integer NOT NULL,
                       created_at timestamp NOT NULL,
                       account_system VARCHAR(255),
                       account VARCHAR(255),
                       source_qs VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ent_task_status_history (
                      status_history_id BIGSERIAL PRIMARY KEY NOT NULL,
                      reward_id uuid NOT NULL,
                      status_details_code integer,
                      task_status integer NOT NULL,
                      status_updated_at timestamp NOT NULL,
                      CONSTRAINT fk_ent_payment_task
                          FOREIGN KEY (reward_id)
                              REFERENCES ent_payment_task(reward_id)
);

CREATE TABLE IF NOT EXISTS ent_payed_list (
                       reward_id uuid PRIMARY KEY NOT NULL,
                       created_at timestamp NOT NULL,
                       processed boolean NOT NULL,
                       CONSTRAINT fk_ent_payed_list
                           FOREIGN KEY (reward_id)
                               REFERENCES ent_payment_task(reward_id)
                                    ON UPDATE SET NULL
);

CREATE TABLE IF NOT EXISTS dct_task_statuses (
                                 status integer PRIMARY KEY NOT NULL ,
                                 status_business_description VARCHAR(255) NOT NULL,
                                 status_system_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS dct_status_details (
                                    status_details_code integer PRIMARY KEY NOT NULL,
                                    related_status integer NOT NULL,
                                    description VARCHAR(255) NOT NULL
);

INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (10, 'Новое', 'New');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (20, 'Выплачено', 'Payed');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (30, 'Отклонено', 'Rejected');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (40, 'Ручной разбор', 'Manual processing');
INSERT INTO dct_task_statuses (status, status_business_description, status_system_name) VALUES (50, 'К выплате', 'Ready for payment');


INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (101, 40, 'Продуктовый Профиль ФЛ: клиент не найден в МДМ');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (103, 40, 'Продуктовый Профиль ФЛ: некорректный запрос к сервису');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (104, 40, 'Продуктовый Профиль ФЛ: ошибка доступа');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (201, 30, 'Продуктовый Профиль ФЛ: Мастер-счет не найден');
INSERT INTO dct_status_details (status_details_code, related_status, description) VALUES (202, 30, 'Продуктовый Профиль ФЛ: Мастер-счет арестован');




