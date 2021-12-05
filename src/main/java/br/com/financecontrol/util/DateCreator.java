package br.com.financecontrol.util;

import br.com.financecontrol.enums.TypeDay;

import java.time.LocalDate;

import static java.util.Objects.nonNull;

public class DateCreator {

    private DateCreator() {}

    public static LocalDate createLocalDate(
            final Integer month,
            final Integer year,
            final TypeDay typeDay) {
        switch (typeDay) {
            case FIRST_DAY_MONTH:
                return firstDayOfTheMonth(month, year);
            case LAST_DAY_MONTH:
                return lastDayOfTheMonth(month, year);
            default:
                return null;
        }
    }

    private static LocalDate firstDayOfTheMonth(final Integer month, final Integer year) {
        if (nonNull(month) && nonNull(year))
            return LocalDate.now()
                    .withMonth(month)
                    .withYear(year)
                    .withDayOfMonth(1);
        return null;
    }

    private static LocalDate lastDayOfTheMonth(final Integer month, final Integer year) {
        if (nonNull(month) && nonNull(year)) {
            final var firstDate = firstDayOfTheMonth(month, year);

            return LocalDate.now()
                    .withMonth(month)
                    .withYear(year)
                    .withDayOfMonth(firstDate.lengthOfMonth());
        }
        return null;
    }
}

