package com.chernivtsi.doctorsoffice.model.converter;

import com.chernivtsi.doctorsoffice.model.Role;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class StringToRoleListConverter implements AttributeConverter<List<Role>, String> {

	@Override
	public String convertToDatabaseColumn(List<Role> list) {
		return list.stream().map(Role::toString).collect(joining(","));
	}

	@Override
	public List<Role> convertToEntityAttribute(String joined) {
		return Arrays.stream(joined.split(",\\s*"))
				.map(Role::valueOf)
				.collect(Collectors.toList());
	}

}