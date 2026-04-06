package org.example.utils;

import java.util.List;

public class PriceCalculator {

    public static double sum(List<Double> prices) {

        double total = 0.0;

        for (double price : prices) {
            total += price;
        }

        return total;
    }

    public static double average(List<Double> prices) {

        if (prices.isEmpty()) return 0.0;

        return sum(prices) / prices.size();
    }
}