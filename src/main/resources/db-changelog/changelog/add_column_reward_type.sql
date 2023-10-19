alter table rfrm_pay.ent_payment_task
    add column reward_type integer NOT NULL;

comment on column rfrm_pay.ent_payment_task.reward_type is 'Тип вознаграждения: 1 - Выплата (рубли), 2 - Выплата (бонусы)';