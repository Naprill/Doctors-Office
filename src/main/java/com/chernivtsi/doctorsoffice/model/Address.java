package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Address extends AbstractVersional {

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

}
