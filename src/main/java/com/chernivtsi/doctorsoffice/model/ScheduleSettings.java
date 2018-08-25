package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleSettings extends AbstractIdentifiable{

	/** початок робочого дня у лікаря, година
	 */
	private Float workStart;

	/** кінець робочого дня, година
	 */
	private Float workEnd;

	/** тривалість прийому в хвилинах
	 */
	private Float receptionTimeRange;
}

