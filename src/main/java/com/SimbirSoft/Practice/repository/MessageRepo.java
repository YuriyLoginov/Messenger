package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {

}
