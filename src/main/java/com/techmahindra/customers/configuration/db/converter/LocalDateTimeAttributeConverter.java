package com.techmahindra.customers.configuration.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDate, Date>{

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {

		if (attribute != null)
			return Date.valueOf(attribute);
		
		return null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		
		if (sqlDate != null)
			return sqlDate.toLocalDate();
		
		return null;
	}
}