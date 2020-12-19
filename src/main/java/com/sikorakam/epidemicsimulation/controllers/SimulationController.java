package com.sikorakam.epidemicsimulation.controllers;

import com.sikorakam.epidemicsimulation.SimulationProcess;
import com.sikorakam.epidemicsimulation.dao.PopulationRepository;
import com.sikorakam.epidemicsimulation.dao.SimulationRepository;
import com.sikorakam.epidemicsimulation.dao.entity.Simulation;
import com.sikorakam.epidemicsimulation.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SimulationController {

    @Autowired
    private SimulationRepository simulationRepository;
    @Autowired
    private PopulationRepository populationRepository;

    @Autowired
    SimulationProcess simulationProcess;

    @GetMapping("/simulations")
    public Iterable<Simulation> findAllSimulations() {
        return simulationRepository.findAll();
    }

    @GetMapping("/simulations/{id}")
    public Optional<Simulation> findSimulationById(@PathVariable Long id) {
        return simulationRepository.findById(id);
    }

    @PostMapping("/simulations")
    public Simulation createSimulation(@Valid @RequestBody Simulation simulation){
        simulationRepository.save(simulation);
//        SimulationProcess simulationProcess = new SimulationProcess(simulation);
        simulationProcess.simulate(simulation);
        return simulation;

    }

    @PutMapping("simulations/{id}")
    public Simulation updateSimulation(@PathVariable Long id, @Valid @RequestBody Simulation newSimulation) throws NotFoundException {
        return simulationRepository.findById(id).map(simulation -> {
            simulation.setName(newSimulation.getName());
            simulation.setPopulationAmount(newSimulation.getPopulationAmount());
            simulation.setInitialInfectedNumber(newSimulation.getInitialInfectedNumber());
            simulation.setIndicatorR(newSimulation.getIndicatorR());
            simulation.setMortality(newSimulation.getMortality());
            simulation.setRecoverTime(newSimulation.getRecoverTime());
            simulation.setDeathTime(newSimulation.getDeathTime());
            simulation.setSimulationTime(newSimulation.getSimulationTime());
            return simulationRepository.save(simulation);
        }).orElseThrow(() -> new NotFoundException("simulation not found"));
    }

    @DeleteMapping("simulations/{id}")
    public String deleteSimulation(@PathVariable Long id) throws NotFoundException {
        return simulationRepository.findById(id).map(simulation -> {
            simulationRepository.delete(simulation);
            return "simulation deleted";
        }).orElseThrow(() -> new NotFoundException("simulation not found"));
    }

}
