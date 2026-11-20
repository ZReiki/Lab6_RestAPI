package ua.edu.nuos.lab6_restapi.client.controller;

import ua.edu.nuos.lab6_restapi.client.WatchApiClient;
import ua.edu.nuos.lab6_restapi.client.model.WatchDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {
    private final WatchApiClient api;
    private final Scanner scanner;

    public ConsoleController() {
        api = new WatchApiClient();
        scanner = new Scanner(System.in);
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== КЛІЄНТ WATCH REST API ===");
            System.out.println("1. Показати всі годинники");
            System.out.println("2. Знайти годинник за ID");
            System.out.println("3. Додати новий годинник");
            System.out.println("4. Оновити годинник за ID");
            System.out.println("5. Видалити годинник за ID");
            System.out.println("0. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> {
                        List<WatchDto> watches = api.getAll();
                        if (watches.isEmpty()) {
                            System.out.println("Список порожній.");
                        } else {
                            watches.forEach(System.out::println);
                        }
                    }
                    case "2" -> {
                        long id = readLong("Введіть ID: ");
                        WatchDto watch = api.getById(id);
                        System.out.println(watch != null ? watch : "Запис не знайдено.");
                    }
                    case "3" -> {
                        WatchDto newWatch = readWatchFromConsole();
                        WatchDto created = api.create(newWatch);
                        System.out.println("Створено: " + created);
                    }
                    case "4" -> {
                        long id = readLong("Введіть ID для оновлення: ");
                        WatchDto existing = api.getById(id);
                        if (existing == null) {
                            System.out.println("Запис з таким ID не знайдено.");
                        } else {
                            WatchDto updatedData = readWatchFromConsole();
                            WatchDto updated = api.update(id, updatedData);
                            System.out.println("Оновлено: " + updated);
                        }
                    }
                    case "5" -> {
                        long id = readLong("Введіть ID для видалення: ");
                        boolean deleted = api.delete(id);
                        System.out.println(deleted ? "Видалено." : "Запис не знайдено або не видалено.");
                    }
                    case "0" -> {
                        System.out.println("Завершення роботи.");
                        return;
                    }
                    default -> System.out.println("Невідома команда.");
                }
            } catch (Exception e) {
                System.out.println("Помилка під час виконання запиту: " + e.getMessage());
            }
        }
    }

    private WatchDto readWatchFromConsole() {
        WatchDto w = new WatchDto();
        System.out.print("Марка (brand): ");
        w.setBrand(scanner.nextLine().trim());

        System.out.print("Дата релізу (YYYY-MM-DD): ");
        w.setReleaseDate(LocalDate.parse(scanner.nextLine().trim()));

        System.out.print("Тип механізму (type): ");
        w.setType(scanner.nextLine().trim());

        w.setPrice(readDouble("Ціна: "));
        w.setCaseDiameter(readDouble("Діаметр корпусу (мм): "));
        w.setQuantity(readInt("Кількість: "));
        w.setWarrantyMonths(readInt("Гарантія (міс.): "));
        w.setManufacturerId(readLong("ID виробника: "));
        return w;
    }

    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне ціле число.");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне ціле число.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().replace(',', '.').trim());
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне число.");
            }
        }
    }
}
