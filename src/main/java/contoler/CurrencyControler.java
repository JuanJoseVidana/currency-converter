package contoler;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import service.CurrencyService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class CurrencyControler {

    private static CurrencyService currencyService;

    public CurrencyControler() {
        currencyService = new CurrencyService();
    }

    private static final String API_LINK = Constants.ROOT + Constants.API_KEY + "/pair/{original}/{converter}";

    public String getConversion(String original, String converter) {
        List<String> codes = currencyService.getCurrency(original, converter);

        String originalCode = codes.get(0);
        String converterCode = codes.get(1);

        if (originalCode == null && converterCode == null) {
            return "Ninguno de los países ingresados existe en la base de datos.";
        }

        if (originalCode == null) {
            return "El país de origen \"" + original + "\" no existe en la base de datos.";
        }

        if (converterCode == null) {
            return "El país de destino \"" + converter + "\" no existe en la base de datos.";
        }

        try {
            String urlStr = API_LINK
                    .replace("{original}", originalCode)
                    .replace("{converter}", converterCode);

            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("User-Agent", "Java-Currency-Converter");

            int status = con.getResponseCode();
            if (status != 200) {
                return "Error en la llamada a la API: código " + status;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            String jsonResponse = content.toString();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

            if (jsonObject == null) {
                return "Error: Respuesta JSON vacía o inválida";
            }

            if (!jsonObject.has("conversion_rate")) {
                return "Error: La respuesta no contiene 'conversion_rate'. Respuesta: " + jsonResponse;
            }

            if (jsonObject.has("error-type")) {
                String errorType = jsonObject.get("error-type").getAsString();
                return "Error de la API: " + errorType;
            }

            if (jsonObject.has("result")) {
                String result = jsonObject.get("result").getAsString();
                if ("success".equalsIgnoreCase(result)) {
                    double conversionRate = jsonObject.get("conversion_rate").getAsDouble();
                    return performUserConversion(originalCode, converterCode, conversionRate);
                } else {
                    return "La API no devolvió una respuesta exitosa. Resultado: " + result;
                }
            }

            double conversionRate = jsonObject.get("conversion_rate").getAsDouble();
            return performUserConversion(originalCode, converterCode, conversionRate);

        } catch (JsonSyntaxException e) {
            return "Error al parsear JSON: " + e.getMessage();
        } catch (IOException e) {
            return "Error de conexión: " + e.getMessage();
        } catch (Exception e) {
            return "Error inesperado: " + e.getMessage();
        }
    }

    private String performUserConversion(String originalCode, String converterCode, double conversionRate) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\n=== CONVERSIÓN DE MONEDA ===");
            System.out.println("Tasa de conversión: 1 " + originalCode + " = " + conversionRate + " " + converterCode);
            System.out.print("¿Cuántos " + originalCode + " quiere convertir a " + converterCode + "? ");

            String userInput = scanner.nextLine().trim();

            double amount = Double.parseDouble(userInput);

            if (amount < 0) {
                return "Error: No se puede convertir una cantidad negativa.";
            }

            double convertedAmount = amount * conversionRate;

            String result = String.format("%.2f %s = %.2f %s",
                    amount, originalCode, convertedAmount, converterCode);

            System.out.println("\n=== RESULTADO ===");
            System.out.println(result);

            return result;

        } catch (NumberFormatException e) {
            return "Error: Debe ingresar un número válido.";
        } catch (Exception e) {
            return "Error al procesar la conversión: " + e.getMessage();
        }
    }
}
