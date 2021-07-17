package br.com.financeirojavaspring.repository;

import br.com.financeirojavaspring.projection.WalletProjection;
import br.com.financeirojavaspring.model.Wallet;
import java.time.LocalDate;
import java.util.List;
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
      + "           id_wallet = wallet.id_wallet  ) as valuePaid    "
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
      + "       between :firtDate and :lastDate )                   ", nativeQuery = true)
  List<WalletProjection> findAllTypeDebtorWithTotalValueAndTotalPaidByMonthAndAccount(
      final @Param(value = "firstDate") LocalDate firstDate,
      final @Param(value = "lastDate") LocalDate lastDate,
      final @Param(value = "idAccount") Long idAccount);

  @Query(value =
        "SELECT                                                         "
      + "     wallet.uuid,                                              "
      + "     wallet.title,                                             "
      + "     wallet.type_wallet as typeWallet,                         "
      + "     wallet.day_wallet as dayWallet,                           "
      + "     sum(record.value) as value,                               "
      + "     null                                                      "
      + "FROM                                                           "
      + "     financeiro_wallet wallet                                  "
      + "inner join                                                     "
      + "     financeiro_record_creditor record                         "
      + "on                                                             "
      + "     wallet.id_wallet = record.id_wallet                       "
      + "where                                                          "
      + "     wallet.id_account = :idAccount                            "
      + "and                                                            "
      + "     record.date_receivement between :firstDate and :lastDate  "
      + "group by                                                       "
      + "     wallet.id_wallet                                          "
      + "union                                                          "
      + "select                                                         "
      + "     wallet.uuid,                                              "
      + "     wallet.title,                                             "
      + "     wallet.type_wallet as typeWallet,                         "
      + "     wallet.day_wallet as dayWallet,                           "
      + "     null as value,                                            "
      + "     null                                                      "
      + "from                                                           "
      + "     financeiro_wallet wallet                                  "
      + "where                                                          "
      + "     wallet.id_account = :idAccount                            "
      + "and                                                            "
      + "     wallet.type_wallet = 'CREDITOR'                           "
      + "and                                                            "
      + "     wallet.id_wallet                                          "
      + "not in                                                         "
      + "     ( select distinct                                         "
      + "           id_wallet                                           "
      + "       from                                                    "
      + "           financeiro_record_creditor record                   "
      + "       where                                                   "
      + "           record.date_deadline                                "
      + "       between :firstDate and :lastDate )                      ", nativeQuery = true)
  List<WalletProjection> findAllTypeCreditorWithTotalValueByMonthAndAccount(
      final @Param(value = "firstDate") LocalDate firstDate,
      final @Param(value = "lastDate") LocalDate lastDate,
      final @Param(value = "idAccount") Long idAccount);
}
