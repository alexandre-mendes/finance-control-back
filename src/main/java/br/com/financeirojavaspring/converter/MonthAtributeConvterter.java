package br.com.financeirojavaspring.converter;

import br.com.financeirojavaspring.enums.Month;
import javax.persistence.AttributeConverter;
import java.util.Arrays;

import static java.util.Objects.nonNull;

public class MonthAtributeConvterter implements AttributeConverter<Month, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Month attribute) {
        return nonNull(attribute) ? attribute.getValue() : null;
    }

    @Override
    public Month convertToEntityAttribute(Integer dbData) {
        return nonNull(dbData) ? Arrays.stream(Month.values()).filter(month -> month.getValue() == dbData).findFirst().orElse(null) : null;
    }
}
