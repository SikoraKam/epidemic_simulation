package com.sikorakam.epidemicsimulation;

import com.sikorakam.epidemicsimulation.dao.PopulationRepository;
import com.sikorakam.epidemicsimulation.dao.SimulationRepository;
import com.sikorakam.epidemicsimulation.dao.entity.Population;
import com.sikorakam.epidemicsimulation.dao.entity.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SimulationProcess {

    private Simulation simulation;
    private List<Population> populations = new ArrayList<>();
    private List<Integer> infectedDaily = new ArrayList<>();
    private List<Integer> infectedDailyAndStillAlive = new ArrayList<>();
    private SimulationRepository simulationRepository;
    private PopulationRepository populationRepository;


    public SimulationProcess(Simulation simulation) {
        this.simulation = simulation;
    }

    public void simulate(){

        Integer infectedNumber = simulation.getInitialInfectedNumber();
        Integer healthSusceptibleNumber = simulation.getPopulationAmount() - infectedNumber;
        Integer deathNumber = 0;
        Integer healedNumber = 0;
        Population population = new Population(infectedNumber, healthSusceptibleNumber, deathNumber, healedNumber);
        populations.add(population);
        System.out.println("SYMULUJE SIĘ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        populationRepository.save(population);
        System.out.println("SYMULUJE SIĘ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        infectedDaily.add(infectedNumber);
        infectedDailyAndStillAlive.add(infectedNumber);



        for (int i = 0; i < simulation.getSimulationTime(); i++) {

            System.out.println("dzień: " + i);

            deathNumber = calculateDeaths();
            population.setDeathNumber(deathNumber);
            population.setInfectedNumber(population.getInfectedNumber() - deathNumber);

            infectedDailyAndStillAlive.set(i, infectedDailyAndStillAlive.get(i) - deathNumber);
            healedNumber = calculateHealed();
            population.setHealedNumber(healedNumber);
            population.setInfectedNumber(population.getInfectedNumber() - healedNumber);

            population.setInfectedNumber(calculateInfection(population));

            population.setDayCounter(population.getDayCounter() + 1);
            populations.add(population);
            populationRepository.save(population);

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

    Integer calculateDeaths(){
        if (infectedDaily.size() < simulation.getDeathTime()){
            return 0;
        }
        Integer temp = infectedDaily.get(simulation.getPopulations().size() - simulation.getDeathTime());
        return temp <= simulation.getMortality() ? temp : simulation.getMortality();
    }
    Integer calculateHealed(){
        if (infectedDailyAndStillAlive.size() < simulation.getRecoverTime()) {
            return 0;
        }
        Integer healed = infectedDailyAndStillAlive.get(simulation.getPopulations().size() - simulation.getRecoverTime());
        return healed <= 0 ? 0 : healed;
    }
    Integer calculateInfection(Population population){
        Integer infectionNumber = population.getInfectedNumber() * simulation.getIndicatorR();
        Integer lastNotInfected = population.getHealthSusceptibleNumber();
        if(lastNotInfected < infectionNumber) {
            population.setHealthSusceptibleNumber(0);
            return lastNotInfected;
        }else {
            population.setHealthSusceptibleNumber(lastNotInfected - infectionNumber);
            return infectionNumber;
        }
    }

}
