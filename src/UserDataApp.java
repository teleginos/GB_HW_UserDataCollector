import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол):");
        String input = scanner.nextLine();

        String[] parts = input.split(" ");
        if (parts.length < 6) {
            System.out.println("Вы ввели меньше данных, чем требуется.");
            return;
        } else if (parts.length > 6) {
            System.out.println("Вы ввели больше данных, чем требуется.");
            return;
        }

        try {
            String surname = parts[0];
            String name = parts[1];
            String patronymic = parts[2];
            String birthDate = parts[3];
            if (!isValidDate(birthDate)) {
                throw new IllegalArgumentException("Неверный формат даты рождения.");
            }

            long phoneNumber = Long.parseLong(parts[4]);
            char gender = parts[5].charAt(0);
            if (gender != 'f' && gender != 'm') {
                throw new IllegalArgumentException("Неверный формат пола. Допустимые значения: f или m.");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(surname + ".txt", true))) {
                writer.write(String.format("%s %s %s %s %d %c", surname, name, patronymic, birthDate, phoneNumber, gender));
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            System.out.println("Неверный формат номера телефона.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
