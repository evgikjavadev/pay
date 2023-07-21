CREATE TABLE pay_payment_task (
                       reward_id uuid PRIMARY KEY,
                       questionnaire_id uuid,
                       mdm_id uuid,
                       recipient_type integer,
                       amount double precision,
                       status integer,
                       created_at timestamp,
                       account_system text,
                       account integer,
                       source_qs text,
                       response_sent bool
);

CREATE TABLE pay_task_status_history (
                                  id uuid PRIMARY KEY,
                                  task_id uuid,
                                  status_details integer,
                                  task_status integer,
                                  status_updated_at timestamp
);

