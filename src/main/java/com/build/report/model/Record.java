package com.build.report.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Code cannot be null")
    private Long code;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Purchase time planned cannot be null")
    @Positive(message = "Purchase time planned must be a positive number")
    private Integer purchaseTimePlanned;

    @NotNull(message = "Cost cannot be null")
    @Positive(message = "Cost must be a positive number")
    private Double cost;

    @NotNull(message = "Demand forecast cannot be null")
    @Positive(message = "Demand forecast must be a positive number")
    private Long demandForecast;

    private Integer demandPLCDO;

    @NotNull(message = "Min cannot be null")
    @Positive(message = "Min must be a positive number")
    private Integer min;

    public void setCode(@NotNull(message = "Code cannot be null") Long code) {
        this.code = code;
    }

    public void setDescription(@NotNull(message = "Description cannot be null") String description) {
        this.description = description;
    }

    public void setPurchaseTimePlanned(@NotNull(message = "Purchase time planned cannot be null") @Positive(message = "Purchase time planned must be a positive number") Integer purchaseTimePlanned) {
        this.purchaseTimePlanned = purchaseTimePlanned;
    }

    public void setCost(@NotNull(message = "Cost cannot be null") @Positive(message = "Cost must be a positive number") Double cost) {
        this.cost = cost;
    }

    public void setDemandForecast(@NotNull(message = "Demand forecast cannot be null") @Positive(message = "Demand forecast must be a positive number") Long demandForecast) {
        this.demandForecast = demandForecast;
    }

    public void setDemandPLCDO(Integer demandPLCDO) {
        this.demandPLCDO = demandPLCDO;
    }

    public void setMin(@NotNull(message = "Min cannot be null") @Positive(message = "Min must be a positive number") Integer min) {
        this.min = min;
    }

    public Record() {
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", purchaseTimePlanned=" + purchaseTimePlanned +
                ", cost=" + cost +
                ", demandForecast=" + demandForecast +
                ", demandPLCDO=" + demandPLCDO +
                ", min=" + min +
                '}';
    }
}
