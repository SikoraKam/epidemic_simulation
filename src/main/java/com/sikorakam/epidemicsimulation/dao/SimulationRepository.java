package com.sikorakam.epidemicsimulation.dao;

import com.sikorakam.epidemicsimulation.dao.entity.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {

    Simulation findByName(String name);

}
