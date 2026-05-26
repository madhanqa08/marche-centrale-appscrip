package generation;
import java.util.Random;
public class EmailGeneration
{
    public static   String generateRandomEmail()
    {
        Random random = new Random();
        int number = 10000 + random.nextInt(90000);
        return "testuser" + number + "@gmail.com";
    }
}
