package utilities.random;
import java.util.Random;
public class Randomnumber
{
    public static String getEmployeeId()
    {
        System.out.println();
        Random obj = new Random();
        return String.valueOf(obj.nextInt(90000)+10000);
    }
}
