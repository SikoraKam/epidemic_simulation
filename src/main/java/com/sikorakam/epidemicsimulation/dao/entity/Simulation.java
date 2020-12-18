package com.sikorakam.epidemicsimulation.dao.entity;

import com.sikorakam.epidemicsimulation.dao.PopulationRepository;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="simulation")
public class Simulation {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Population> populations;

    @NonNull
    @Column(name = "name")
    private String name; // N - nazwa symulacji

    @NonNull
    @Column(name = "population_amount")
    private Integer populationAmount; //P - wielkość populacji

    @NonNull
    @Column(name = "initial_infected_number")
    private Integer initialInfectedNumber; // I - początkowa liczba osób zarażonych

    @NonNull
    @Column(name = "indicator_R")
    private Integer indicatorR; //R - wskaźnik określający ile osób zaraża jedna zarażona osoba, czyli znany z newsów covidowych wskaźnik R

    @NonNull
    @Column(name = "mortality")
    private Integer mortality; //M - wskaźnik śmiertelności, określający ilu spośród zarażonych umiera

    @NonNull
    @Column(name = "recover_time")
    private Integer recoverTime; // Ti - ilość dni, która upływa od momentu zarażenia do wyzdrowienia chorego

    @NonNull
    @Column(name = "death_time")
    private Integer deathTime; //Tm - ilość dni, która upływa od momentu zarażenia do śmierci chorego

    @NonNull
    @Column(name = "simulation_time")
    private Integer simulationTime; // Ts - Ilość dni, dla których ma być przeprowadzona symulacja


    public Simulation() {
    }

    public Simulation(Long id, Set<Population> populations, String name, Integer populationAmount, Integer initialInfectedNumber, Integer indicatorR, Integer mortality, Integer recoverTime, Integer deathTime, Integer simulationTime) {
        this.id = id;
        this.populations = populations;
        this.name = name;
        this.populationAmount = populationAmount;
        this.initialInfectedNumber = initialInfectedNumber;
        this.indicatorR = indicatorR;
        this.mortality = mortality;
        this.recoverTime = recoverTime;
        this.deathTime = deathTime;
        this.simulationTime = simulationTime;
    }

    public Simulation(String name, Integer populationAmount, Integer initialInfectedNumber, Integer indicatorR, Integer mortality, Integer recoverTime, Integer deathTime, Integer simulationTime) {
        this.name = name;
        this.populationAmount = populationAmount;
        this.initialInfectedNumber = initialInfectedNumber;
        this.indicatorR = indicatorR;
        this.mortality = mortality;
        this.recoverTime = recoverTime;
        this.deathTime = deathTime;
        this.simulationTime = simulationTime;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Population> getPopulations() {
        return populations;
    }

    public void setPopulations(Set<Population> populations) {
        this.populations = populations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulationAmount() {
        return populationAmount;
    }

    public void setPopulationAmount(Integer populationAmount) {
        this.populationAmount = populationAmount;
    }

    public Integer getInitialInfectedNumber() {
        return initialInfectedNumber;
    }

    public void setInitialInfectedNumber(Integer initialInfectedNumber) {
        this.initialInfectedNumber = initialInfectedNumber;
    }

    public Integer getIndicatorR() {
        return indicatorR;
    }

    public void setIndicatorR(Integer indicatorR) {
        this.indicatorR = indicatorR;
    }

    public Integer getMortality() {
        return mortality;
    }

    public void setMortality(Integer mortality) {
        this.mortality = mortality;
    }

    public Integer getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Integer recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(Integer deathTime) {
        this.deathTime = deathTime;
    }

    public Integer getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(Integer simulationTime) {
        this.simulationTime = simulationTime;
    }
}
