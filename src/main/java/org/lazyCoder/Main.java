package org.lazyCoder;

import org.lazyCoder.service.Services;

public class Main {
    public static void main(String[] args) {
        Services services = new Services();
        services.createEmployee("Aman",30000);
        services.createLap("HP",40000);
        services.assignLap(2,2);
    }
}