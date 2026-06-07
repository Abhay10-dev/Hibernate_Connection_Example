package org.lazyCoder.model;


import jakarta.persistence.*;

@Table(name = "laptop")
@Entity
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lapId;
    private String lapName;
    private double lapPrice;

    @OneToOne(mappedBy = "laptop", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Employee employee;

    public Laptop() {
    }

    public Laptop(String lapName, double lapPrice) {
        this.lapName = lapName;
        this.lapPrice = lapPrice;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getLapName() {
        return lapName;
    }

    public void setLapName(String lapName) {
        this.lapName = lapName;
    }

    public double getLapPrice() {
        return lapPrice;
    }

    public void setLapPrice(double lapPrice) {
        this.lapPrice = lapPrice;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "lapId=" + lapId +
                ", lapName='" + lapName + '\'' +
                ", lapPrice=" + lapPrice +
                ", employee=" + employee +
                '}';
    }
}
