package com.sikorakam.epidemicsimulation.dao.entity;

import javax.persistence.*;

@Entity
@Table(name="population")
public class Population {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;

    @Column(name = "infected_number")
    private Integer infectedNumber; //Pi - liczbie osób zarażonych

    @Column(name = "health_susceptible_number")
    private Integer healthSusceptibleNumber; // Pv - liczbie osób zdrowych, podatnych na infekcję

    @Column(name = "death_number")
    private Integer deathNumber; // Pm - liczbie osób zmarłych

    @Column(name = "healed_number")
    private Integer healedNumber; //Pr - liczbie osób, które wyzdrowiały i nabyły odporność

    @Column(name = "day_counter")
    private Integer dayCounter;

    public Population() {
    }

    public Population(Long id, Simulation simulation, Integer infectedNumber, Integer healthSusceptibleNumber, Integer deathNumber, Integer healedNumber, Integer dayCounter) {
        this.id = id;
        this.simulation = simulation;
        this.infectedNumber = infectedNumber;
        this.healthSusceptibleNumber = healthSusceptibleNumber;
        this.deathNumber = deathNumber;
        this.healedNumber = healedNumber;
        this.dayCounter = dayCounter;
    }

    public Population(Integer infectedNumber, Integer healthSusceptibleNumber, Integer deathNumber, Integer healedNumber) {
        this.infectedNumber = infectedNumber;
        this.healthSusceptibleNumber = healthSusceptibleNumber;
        this.deathNumber = deathNumber;
        this.healedNumber = healedNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Integer getInfectedNumber() {
        return infectedNumber;
    }

    public void setInfectedNumber(Integer infectedNumber) {
        this.infectedNumber = infectedNumber;
    }

    public Integer getHealthSusceptibleNumber() {
        return healthSusceptibleNumber;
    }

    public void setHealthSusceptibleNumber(Integer healthSusceptibleNumber) {
        this.healthSusceptibleNumber = healthSusceptibleNumber;
    }

    public Integer getDeathNumber() {
        return deathNumber;
    }

    public void setDeathNumber(Integer deathNumber) {
        this.deathNumber = deathNumber;
    }

    public Integer getHealedNumber() {
        return healedNumber;
    }

    public void setHealedNumber(Integer healedNumber) {
        this.healedNumber = healedNumber;
    }

    public Integer getDayCounter() {
        return dayCounter;
    }

    public void setDayCounter(Integer dayCounter) {
        this.dayCounter = dayCounter;
    }
}
