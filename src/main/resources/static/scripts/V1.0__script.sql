create or replace view vw_wallet_creditor as
select
wallet.id_wallet as idWallet,
wallet.uuid,
wallet.title,
wallet.type_wallet as typeWallet,
wallet.id_account as idAccount,
wallet.day_wallet as dayWallet,
coalesce(sum(record.value), 0) as value
from financeiro_wallet wallet
left join financeiro_record_creditor record
on wallet.id_wallet  = record.id_wallet
where wallet.type_wallet = 'CREDITOR'
and record.date_receivement <= now()
group by wallet.id_wallet;