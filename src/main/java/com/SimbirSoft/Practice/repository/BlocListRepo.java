package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.model.BlockList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocListRepo extends JpaRepository<BlockList, Long> {

}