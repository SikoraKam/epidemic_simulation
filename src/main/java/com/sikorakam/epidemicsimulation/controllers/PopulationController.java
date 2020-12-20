package com.sikorakam.epidemicsimulation.controllers;

import com.sikorakam.epidemicsimulation.dao.PopulationRepository;
import com.sikorakam.epidemicsimulation.dao.entity.Population;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PopulationController {

    @Autowired
    private PopulationRepository populationRepository;

    @GetMapping("/populations")
    private Iterable<Population> findAllPopulations() {
        return populationRepository.findAll();
    }

    @GetMapping("/populations/{id}")
    private Optional<Population> findPopulationById(@PathVariable Long id) {
        return populationRepository.findById(id);
    }

    @GetMapping("/simulations/{simulationId}/populations")
    private Iterable<Population> findAllPopulationsBySimulationId(@PathVariable(value = "simulationId") Long simulationId){
        return populationRepository.findAllBySimulationId(simulationId);
    }

}
