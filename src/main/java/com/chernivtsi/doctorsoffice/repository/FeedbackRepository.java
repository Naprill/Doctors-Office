package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Long>{
}
