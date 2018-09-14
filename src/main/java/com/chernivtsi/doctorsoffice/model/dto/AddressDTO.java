package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Address;
import com.chernivtsi.doctorsoffice.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString(exclude = "patient")
@NoArgsConstructor
public class AddressDTO {

	private Long id;

	private Long patient;

	@Pattern(regexp = "[\\u0400-\\u04ff-\\s']*", message = "'Область' містить недопустимі символи. Дозволені кирилиця, дефіс, апостроф та пробіл")
	private String region;

	@Pattern(regexp = "[\\u0400-\\u04ff-\\s']*", message = "'Район' містить недопустимі символи. Дозволені кирилиця, дефіс, апостроф та пробіл")
	private String district;

	@Pattern(regexp = "[\\u0400-\\u04ff-\\s']*", message = "'Населений пункт' містить недопустимі символи. Дозволені кирилиця, дефіс, апостроф та пробіл")
	private String cityOrVillage;

	@Pattern(regexp = "[\\u0400-\\u04ff-\\s']*", message = "'Вулиця' містить недопустимі символи. Дозволені кирилиця, дефіс, апостроф та пробіл")
	private String street;

	@Pattern(regexp = "[\\u0400-\\u04ff-\\s0-9/]+", message = "'Номер будинку' містить недопустимі символи. Дозволені кирилиця, цифри, дефіс та пробіл")
	private String houseNumber;

	public AddressDTO(Address address) {
		this.id = address.getId();
		this.patient = address.getPatient().getId();
		this.region = address.getRegion();
		this.district = address.getDistrict();
		this.cityOrVillage = address.getCityOrVillage();
		this.street = address.getStreet();
		this.houseNumber = address.getHouseNumber();
	}

	public static Address dtoToEntity(AddressDTO address, User user) {
		return new Address(
				address.getId(),
				user,
				address.getRegion(),
				address.getDistrict(),
				address.getCityOrVillage(),
				address.getStreet(),
				address.getHouseNumber()
		);
	}
}
