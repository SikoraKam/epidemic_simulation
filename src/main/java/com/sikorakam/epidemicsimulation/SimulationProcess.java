package com.sikorakam.epidemicsimulation;

import com.sikorakam.epidemicsimulation.dao.PopulationRepository;
import com.sikorakam.epidemicsimulation.dao.SimulationRepository;
import com.sikorakam.epidemicsimulation.dao.entity.Population;
import com.sikorakam.epidemicsimulation.dao.entity.Simulation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@Service
public class SimulationProcess {

    private List<Population> populations = new ArrayList<>();
    private List<Integer> infectedDaily = new ArrayList<>();
    private List<Integer> infectedDailyAndStillAlive = new ArrayList<>();

    @Autowired(required = true)
    private SimulationRepository simulationRepository;
    @Autowired(required = true)
    private PopulationRepository populationRepository;



    public SimulationProcess() {
    }

    @GetMapping("populations")
    public Iterable<Population> findAllPopulations() {
        return populationRepository.findAll();
    }


    public void simulate(Simulation simulation){
        System.out.println(simulation);
        Integer infectedNumber = simulation.getInitialInfectedNumber();
        System.out.println(infectedNumber);
        Integer healthSusceptibleNumber = simulation.getPopulationAmount();
        System.out.println(healthSusceptibleNumber);
        Integer deathNumber = 0;
        Integer healedNumber = 0;
        Population population = new Population(simulation, infectedNumber, healthSusceptibleNumber, deathNumber, healedNumber, 0);
        populations.add(population);
        populationRepository.save(population);

        infectedDaily.add(infectedNumber);
        infectedDailyAndStillAlive.add(infectedNumber);
        Integer infectedNumberPreviousDay = 0;
        Integer deathNumberPreviousDay = 0;
        Integer healedNumberPreviousDay = 0;



        for (int i = 0; i < simulation.getSimulationTime(); i++) {

            System.out.println("dzień: " + i);

            deathNumber = calculateDeaths(simulation, population);
            population.setDeathNumber(population.getDeathNumber() + deathNumber);
            population.setInfectedNumber(population.getInfectedNumber() - deathNumber);
            Integer xd = population.getInfectedNumber();
            deathNumberPreviousDay = deathNumber;



            healedNumber = calculateHealed(simulation,population);
            population.setHealedNumber(healedNumber + population.getHealedNumber());
            population.setInfectedNumber(population.getInfectedNumber() - healedNumber);
            xd = population.getInfectedNumber();
            healedNumberPreviousDay = healedNumber;


            if (population.getHealthSusceptibleNumber() > 0) {
                infectedNumber = calculateInfection(population, simulation, infectedNumberPreviousDay);
                population.setInfectedNumber(infectedNumber);
                xd = population.getInfectedNumber();
            }
            else {
                infectedDaily.add(0);
                infectedDailyAndStillAlive.add(0);
            }
            infectedNumberPreviousDay = population.getInfectedNumber();



            population.setDayCounter(population.getDayCounter() + 1);
            populations.add(population);
            populationRepository.saveAndFlush(population);

            System.out.println("uleczeni: " + population.getHealedNumber());
            System.out.println("zmarli: " + population.getDeathNumber());
            System.out.println("podatni: " + population.getHealthSusceptibleNumber());
            System.out.println("zarazeni: " + population.getInfectedNumber());
            System.out.println("wszyscy z symulacji: " + simulation.getPopulationAmount());

            if (population.getHealedNumber() + population.getHealthSusceptibleNumber() + population.getDeathNumber()
                    + population.getInfectedNumber() != simulation.getPopulationAmount()){
                System.out.println("===============================> DANE SIĘ NIE ZGADZAJĄ");

            }
            System.out.println();

        }
    }

    Integer calculateDeaths(Simulation simulation, Population population){
        if (infectedDaily.size() < simulation.getDeathTime()){
            return 0;
        }
        int deathPeriod = populations.size() - simulation.getDeathTime();
        Double temp = Double.valueOf(infectedDaily.get(deathPeriod));

        Integer deathNumber = (temp <= simulation.getMortality() * infectedDaily.get(deathPeriod))
                ? (int) Math.round(temp)
                : (int) Math.round(simulation.getMortality() * population.getInfectedNumber());

        if(infectedDaily.get(deathPeriod) - deathNumber <= 0){
            infectedDailyAndStillAlive.set(deathPeriod, 0);
        }
        else {
            infectedDailyAndStillAlive.set(deathPeriod,infectedDaily.get(deathPeriod) - deathNumber);
        }

        return deathNumber;

    }
    Integer calculateHealed(Simulation simulation, Population population){
        if (infectedDailyAndStillAlive.size() < simulation.getRecoverTime()) {
            return 0;
        }
        Integer healed = infectedDailyAndStillAlive.get(populations.size() - simulation.getRecoverTime());
        if(healed > population.getInfectedNumber() - healed)
            return population.getInfectedNumber();
        return healed <= 0 ? 0 : healed;
    }
    Integer calculateInfection(Population population, Simulation simulation, Integer infectedNumberPreviousDay){
        Integer infectionNumber = population.getInfectedNumber() + (int) Math.round(population.getInfectedNumber() * simulation.getIndicatorR());
        Integer lastNotInfected = population.getHealthSusceptibleNumber();


        if(lastNotInfected < (int) Math.round(population.getInfectedNumber() * simulation.getIndicatorR())) {
            lastNotInfected = simulation.getPopulationAmount() - infectedNumberPreviousDay - population.getDeathNumber() - population.getHealedNumber();
            population.setHealthSusceptibleNumber(0);
            System.out.println("ddd" + lastNotInfected);
            System.out.println("oooo" + infectedNumberPreviousDay);
            infectedDaily.add(lastNotInfected);
            infectedDailyAndStillAlive.add(lastNotInfected);
            return lastNotInfected + infectedNumberPreviousDay;
        }else {
            infectedDaily.add((int) Math.round(population.getInfectedNumber() * simulation.getIndicatorR()));
            infectedDailyAndStillAlive.add((int) Math.round(population.getInfectedNumber() * simulation.getIndicatorR()));
            population.setHealthSusceptibleNumber(simulation.getPopulationAmount() - infectionNumber - population.getDeathNumber() - population.getHealedNumber());

            return infectionNumber;
        }
    }

}
