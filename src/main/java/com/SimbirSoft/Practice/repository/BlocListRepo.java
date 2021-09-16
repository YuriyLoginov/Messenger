package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.domain.util.BlockList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocListRepo extends CrudRepository<BlockList, Long> {

}