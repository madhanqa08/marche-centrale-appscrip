package generation;
import java.util.Random;
public class PasswordGenerator
{
    public static String generatePassword()
    {

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "@#$%&*!";

        String allChars = upper + lower + digits + symbols;

        Random random = new Random();

        StringBuilder password = new StringBuilder();

        // Mandatory characters
        password.append(upper.charAt(random.nextInt(upper.length())));     // 1 Uppercase
        password.append(lower.charAt(random.nextInt(lower.length())));     // 1 Lowercase
        password.append(digits.charAt(random.nextInt(digits.length())));   // 1 Digit
        password.append(symbols.charAt(random.nextInt(symbols.length()))); // 1 Symbol

        // Remaining characters (Total length = 10)
        for (int i = 0; i < 6; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle password characters
        char[] pwdArray = password.toString().toCharArray();

        for (int i = 0; i < pwdArray.length; i++) {

            int randomIndex = random.nextInt(pwdArray.length);

            char temp = pwdArray[i];
            pwdArray[i] = pwdArray[randomIndex];
            pwdArray[randomIndex] = temp;
        }

        return new String(pwdArray);
    }

}
