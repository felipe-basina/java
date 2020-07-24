package com.sample.spark.models;

import java.io.Serializable;

public class Battle implements Serializable {

    private Integer battle_number;

    private Integer year;

    private String attacker_king;

    private String defender_king;

    public Battle(Integer battle_number, Integer year, String attacker_king, String defender_king) {
        this.battle_number = battle_number;
        this.year = year;
        this.attacker_king = attacker_king;
        this.defender_king = defender_king;
    }

    public Integer getBattle_number() {
        return battle_number;
    }

    public void setBattle_number(Integer battle_number) {
        this.battle_number = battle_number;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAttacker_king() {
        return attacker_king;
    }

    public void setAttacker_king(String attacker_king) {
        this.attacker_king = attacker_king;
    }

    public String getDefender_king() {
        return defender_king;
    }

    public void setDefender_king(String defender_king) {
        this.defender_king = defender_king;
    }

    @Override
    public String toString() {
        return "Battle{" +
                "battle_number=" + battle_number +
                ", year=" + year +
                ", attacker_king='" + attacker_king + '\'' +
                ", defender_king='" + defender_king + '\'' +
                '}';
    }

}