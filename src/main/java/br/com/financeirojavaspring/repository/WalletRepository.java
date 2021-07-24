package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.projection.VWWalletCreditorProjection;
import br.com.financeirojavaspring.projection.WalletProjection;
import br.com.financeirojavaspring.entity.Wallet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  @Query(value =
        "SELECT                                                     "
      + "     wallet.uuid,                                          "
      + "     wallet.title,                                         "
      + "     wallet.type_wallet as typeWallet,                     "
      + "     wallet.day_wallet as dayWallet,                       "
      + "     sum(record.value) as value,                           "
      + "     ( select                                              "
      + "           sum(value)                                      "
      + "       from                                                "
      + "           financeiro_record_debtor                        "
      + "       where                                               "
      + "           paid = true                                     "
      + "       and                                                 "
      + "           id_wallet = wallet.id_wallet                    "
      + "       and date_deadline                                   "
      + "       between :firstDate and :lastDate ) as valuePaid     "
      + "FROM                                                       "
      + "     financeiro_wallet wallet                              "
      + "inner join                                                 "
      + "     financeiro_record_debtor record                       "
      + "on                                                         "
      + "     wallet.id_wallet = record.id_wallet                   "
      + "where                                                      "
      + "     wallet.id_account = :idAccount                        "
      + "and                                                        "
      + "     record.date_deadline between :firstDate and :lastDate "
      + "group by                                                   "
      + "     wallet.id_wallet                                      "
      + "union                                                      "
      + "select                                                     "
      + "     wallet.uuid,                                          "
      + "     wallet.title,                                         "
      + "     wallet.type_wallet as typeWallet,                     "
      + "     wallet.day_wallet as dayWallet,                       "
      + "     null as value,                                        "
      + "     null as value_paid                                    "
      + "from                                                       "
      + "     financeiro_wallet wallet                              "
      + "where                                                      "
      + "     wallet.id_account = :idAccount                        "
      + "and                                                        "
      + "     wallet.type_wallet = 'DEBTOR'                         "
      + "and                                                        "
      + "     wallet.id_wallet                                      "
      + "not in                                                     "
      + "     ( select distinct                                     "
      + "           id_wallet                                       "
      + "       from                                                "
      + "           financeiro_record_debtor  record                "
      + "       where                                               "
      + "           record.date_deadline                            "
      + "       between :firstDate and :lastDate )                  ", nativeQuery = true)
  List<WalletProjection> findAllTypeDebtorWithTotalValueAndTotalPaidByMonthAndAccount(
      @Param(value = "firstDate") final LocalDate firstDate,
      @Param(value = "lastDate") final LocalDate lastDate,
      @Param(value = "idAccount") final Long idAccount);

  @Query(value = "select * from vw_wallet_creditor where idaccount = :idAccount", nativeQuery = true)
  List<VWWalletCreditorProjection> findAllTypeCreditorWithTotalValueByMonthAndAccount(
      @Param(value = "idAccount") final Long idAccount);

  @Query(value = "select * from vw_wallet_creditor where uuid = :uuid", nativeQuery = true)
  Optional<VWWalletCreditorProjection> findByUUID(@Param(value = "uuid") final String uuid);
}
