package com.tbt.thebeertracker.models.data;


import com.tbt.thebeertracker.models.BeerTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BeerTagDao extends CrudRepository<BeerTag, Integer> {
}