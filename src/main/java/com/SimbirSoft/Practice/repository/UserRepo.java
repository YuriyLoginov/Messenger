package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

}
