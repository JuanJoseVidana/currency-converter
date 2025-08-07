package service;

import contoler.CurrencyControler;
import service.util.CountryToCurrencyMap;

import java.util.Scanner;

public class Interface {
    private static CurrencyControler currencyControler;

    public static void showMenu() {

        Scanner scanner = new Scanner(System.in);
        String opcion = "";

        while (!opcion.equals("1")) {
            System.out.println("=== Conversor de Moneda ===");
            System.out.println("""
                Si deseas salir presiona 1
                Si deseas realizar una conversión presiona 2
                """);

            opcion = scanner.nextLine().trim();


            if (opcion.equals("2")) {
                System.out.print("Introduce el país de origen: ");
                String original = scanner.nextLine().trim();

                System.out.print("Introduce el país de destino: ");
                String converter = scanner.nextLine().trim();
                CurrencyControler currencyControler = new CurrencyControler();
                String result = currencyControler.getConversion(original, converter);

            }
            }

        scanner.close();
    }
}
