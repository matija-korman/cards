package com.example.cards.domain.converters;

import com.example.cards.domain.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public Status convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Status.values())
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
