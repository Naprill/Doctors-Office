package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Article extends AbstractIdentifiable{

    @NotBlank(message = "Назва обов'язкова")
    private String title;

    @NotBlank(message = "Посилання обов'язкове")
    private String link;

    @NotBlank(message = "Опис обов'язковий")
    @Column(columnDefinition="text", length=10485760)
    private String description;

}
