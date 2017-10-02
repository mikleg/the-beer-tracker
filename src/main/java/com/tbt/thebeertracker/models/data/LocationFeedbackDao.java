package com.tbt.thebeertracker.models.data;

import com.tbt.thebeertracker.models.Beer;
import com.tbt.thebeertracker.models.LocationFeedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LocationFeedbackDao extends CrudRepository<LocationFeedback, Integer> {
}