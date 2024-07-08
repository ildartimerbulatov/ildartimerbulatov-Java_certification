import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LaptopDataGenerator {
    private static final String[] OS_OPTIONS = {"Windows 10", "Ubuntu", "macOS", "Windows 11"};
    private static final String[] COLOR_OPTIONS = {"Black", "Silver", "Gray", "White", "Red", "Blue"};

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("laptops.txt")) {
            Random random = new Random();
            int numberOfLaptops = 20; // Количество ноутбуков

            for (int i = 0; i < numberOfLaptops; i++) {
                int ram = (1 + random.nextInt(4)) * 8; // 8, 16, 24, 32 ГБ
                int hdd = (1 + random.nextInt(5)) * 256; // 256, 512, 768, 1024, 1280 ГБ
                String os = OS_OPTIONS[random.nextInt(OS_OPTIONS.length)];
                String color = COLOR_OPTIONS[random.nextInt(COLOR_OPTIONS.length)];

                writer.write(ram + "," + hdd + "," + os + "," + color + "\n");
            }

            System.out.println("Данные успешно сгенерированы и записаны в файл laptops.txt");
        } catch (IOException e) {
            System.err.println("Произошла ошибка при записи данных в файл: " + e.getMessage());
        }
    }
}
