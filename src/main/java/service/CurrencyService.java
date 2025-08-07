package service;

import service.util.CountryToCurrencyMap;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyService {


    public List<String> getCurrency(String originalInput, String converterInput) {
        String original =  sanitize(originalInput);
        String converter = sanitize(converterInput);

        List<String> result = new ArrayList<>();

        if (CountryToCurrencyMap.isSupported(original)) {
            result.add(CountryToCurrencyMap.getCurrencyCode(original));
        } else {
            result.add(null);
        }

        if (CountryToCurrencyMap.isSupported(converter)) {
            result.add(CountryToCurrencyMap.getCurrencyCode(converter));
        } else {
            result.add(null);
        }

        return result;
    }

    private String sanitize(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .trim();
    }
}
