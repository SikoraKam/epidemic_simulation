package com.sikorakam.epidemicsimulation.dao;

import com.sikorakam.epidemicsimulation.dao.entity.Population;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopulationRepository extends JpaRepository<Population, Long> {

    Iterable<Population> findAllBySimulationId(Long simulationId);
}
