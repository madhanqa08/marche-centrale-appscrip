package utilities.random;
import java.util.Random;
public class Randomnumber
{
    public static String getGeneratedBugId()
    {
        Random random = new Random();
        int number = 100000000 + random.nextInt(900000000);
        return "BUG" + number;
    }
}
