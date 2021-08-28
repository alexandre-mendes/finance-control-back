create or replace view vw_wallet_creditor as
select
wallet.id_wallet as idWallet,
wallet.uuid,
wallet.title,
wallet.type_wallet as typeWallet,
wallet.id_account as idAccount,
wallet.day_wallet as dayWallet,
coalesce(sum(CASE WHEN record.date_transaction <= now() THEN record.value ELSE 0 END), 0) as value
from financeiro_wallet wallet
left join financeiro_record_creditor record
on wallet.id_wallet  = record.id_wallet
where wallet.type_wallet = 'CREDITOR'
group by wallet.id_wallet
union
select
wallet.id_wallet as idWallet,
wallet.uuid,
wallet.title,
wallet.type_wallet as typeWallet,
wallet.id_account as idAccount,
wallet.day_wallet as dayWallet,
0 as value
from financeiro_wallet wallet where type_wallet = 'CREDITOR' and id_wallet not in (select distinct id_wallet from financeiro_record_creditor);
