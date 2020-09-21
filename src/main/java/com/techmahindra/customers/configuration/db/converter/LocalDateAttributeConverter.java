package com.techmahindra.customers.configuration.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp>{

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {

		if (attribute != null)
			return Timestamp.valueOf(attribute);
		
		return null;
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp sqlDate) {
		
		if (sqlDate != null)
			return sqlDate.toLocalDateTime();
		
		return null;
	}
}