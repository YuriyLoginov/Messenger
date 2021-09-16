package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends CrudRepository<Chat, Long> {
}
