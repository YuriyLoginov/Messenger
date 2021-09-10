package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepo extends CrudRepository<Chat, Long> {
}
