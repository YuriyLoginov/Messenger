package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {

}
