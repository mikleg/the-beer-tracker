package com.tbt.thebeertracker.models.data;


import com.tbt.thebeertracker.models.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LocationDao extends CrudRepository<Location, Integer> {
}