package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Analysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysesRepository extends CrudRepository<Analysis,Long>{
}
