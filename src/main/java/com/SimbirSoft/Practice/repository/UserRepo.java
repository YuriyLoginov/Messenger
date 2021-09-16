package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
User findByUsername(String username);
}
