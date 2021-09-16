package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends CrudRepository<Group, Long> {

}
