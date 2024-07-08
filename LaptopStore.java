import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class LaptopStore {

    public static void main(String[] args) {
        Set<Laptop> laptops = loadLaptopsFromFile("laptops.txt");

        // Вызов метода для фильтрации
        filterLaptops(laptops);
    }

    private static Set<Laptop> loadLaptopsFromFile(String fileName) {
        Set<Laptop> laptops = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int ram = Integer.parseInt(parts[0].trim());
                int hdd = Integer.parseInt(parts[1].trim());
                String os = parts[2].trim();
                String color = parts[3].trim();
                laptops.add(new Laptop(ram, hdd, os, color));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return laptops;
    }

    public static void filterLaptops(Set<Laptop> laptops) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> filterCriteria = new HashMap<>();

        while (true) {
            System.out.println("Введите цифру, соответствующую необходимому критерию:");
            System.out.println("1 - ОЗУ");
            System.out.println("2 - Объем ЖД");
            System.out.println("3 - Операционная система");
            System.out.println("4 - Цвет");
            System.out.println("5 - Завершить фильтрацию");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Считываем символ новой строки

            if (choice == 5) break;

            switch (choice) {
                case 1:
                    System.out.println("Введите минимальное значение ОЗУ (в ГБ):");
                    filterCriteria.put("ram", scanner.nextInt());
                    break;
                case 2:
                    System.out.println("Введите минимальный объем жесткого диска (в ГБ):");
                    filterCriteria.put("hdd", scanner.nextInt());
                    break;
                case 3:
                    System.out.println("Введите операционную систему:");
                    filterCriteria.put("os", scanner.nextLine());
                    break;
                case 4:
                    System.out.println("Введите цвет:");
                    filterCriteria.put("color", scanner.nextLine());
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }

        Set<Laptop> filteredLaptops = laptops.stream()
                .filter(laptop -> {
                    if (filterCriteria.containsKey("ram") && laptop.getRam() < (int) filterCriteria.get("ram")) {
                        return false;
                    }
                    if (filterCriteria.containsKey("hdd") && laptop.getHdd() < (int) filterCriteria.get("hdd")) {
                        return false;
                    }
                    if (filterCriteria.containsKey("os") && !laptop.getOs().equalsIgnoreCase((String) filterCriteria.get("os"))) {
                        return false;
                    }
                    if (filterCriteria.containsKey("color") && !laptop.getColor().equalsIgnoreCase((String) filterCriteria.get("color"))) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toSet());

        if (filteredLaptops.isEmpty()) {
            System.out.println("Нет ноутбуков, соответствующих выбранным критериям.");
        } else {
            System.out.println("Ноутбуки, соответствующие выбранным критериям:");
            filteredLaptops.forEach(System.out::println);
        }
    }
}
