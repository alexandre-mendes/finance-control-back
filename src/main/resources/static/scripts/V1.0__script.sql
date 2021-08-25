create or replace view financeiro_wallet_vw as
select
wallet.id_wallet,
wallet.uuid,
wallet.title,
wallet.type_wallet,
wallet.id_account,
wallet.day_wallet,
coalesce(sum(record.value), 0) as value,
coalesce(sum(CASE WHEN record.paid is true THEN record.value ELSE 0 END), 0) as value_paid,
coalesce(extract(month from record.date_deadline), 0) as month_wallet
from financeiro_wallet wallet
left join financeiro_record_debtor record on wallet.id_wallet = record.id_wallet
where
wallet.type_wallet = 'DEBTOR'
group  by
wallet.id_wallet,
wallet.uuid,
wallet.title,
wallet.type_wallet,
wallet.day_wallet,
extract(month from record.date_deadline)
union
select
wallet.id_wallet,
wallet.uuid,
wallet.title,
wallet.type_wallet,
wallet.id_account,
wallet.day_wallet,
coalesce(sum(CASE WHEN record.date_transaction <= now() THEN record.value ELSE 0 END), 0) as value,
0 as value_paid,
0 as month_wallet
from financeiro_wallet wallet
left join financeiro_record_creditor record
on wallet.id_wallet  = record.id_wallet
where wallet.type_wallet = 'CREDITOR'
group by wallet.id_wallet
union
select
wallet.id_wallet,
wallet.uuid,
wallet.title,
wallet.type_wallet,
wallet.id_account,
wallet.day_wallet,
0 as value,
0 as value_paid,
0 as month_wallet
from financeiro_wallet wallet where type_wallet = 'CREDITOR' and id_wallet not in (select distinct id_wallet from financeiro_record_creditor)
