package org.example.utils;

public class PriceAnalyzer {

    public static double parse(String priceText) {

        if (priceText == null || priceText.isEmpty()) return 0.0;

        try {
            priceText = priceText.replace("RUB", "").trim();

            String[] parts = priceText.split("\\+");

            double total = 0.0;

            for (String part : parts) {
                total += Double.parseDouble(part.trim());
            }

            return total;

        } catch (Exception e) {
            return 0.0;
        }
    }
}