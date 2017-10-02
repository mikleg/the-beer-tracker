package com.tbt.thebeertracker.models.data;

import com.tbt.thebeertracker.models.BeerDrink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BeerDrinkDao extends CrudRepository<BeerDrink, Integer> {
}