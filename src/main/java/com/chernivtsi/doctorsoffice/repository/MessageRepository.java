package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
