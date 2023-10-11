alter table rfrm_pay.ent_payment_task
    add column account_branch varchar(255);

comment on column rfrm_pay.ent_payment_task.account_branch is 'Код филиала банка ';