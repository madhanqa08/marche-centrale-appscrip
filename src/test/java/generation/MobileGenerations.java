package generation;
import java.util.concurrent.ThreadLocalRandom;
public class MobileGenerations
{
    public static String generateMobileNumber()
    {
        StringBuilder mobile = new StringBuilder();

        mobile.append("  9");
        for(int i = 0; i < 9; i++)
        {
            mobile.append(ThreadLocalRandom.current().nextInt(10));
        }
        return mobile.toString();
    }
//    public static String generateMobileNumber()
//    {
//        StringBuilder mobile = new StringBuilder();
//        // Random first digit from 1 to 9
//        int firstDigit = ThreadLocalRandom.current().nextInt(1, 10);
//        mobile.append(firstDigit);
//        // Remaining 9 digits
//
//        for(int i = 0; i < 7; i++)// changed 7
//        {
//            mobile.append(ThreadLocalRandom.current().nextInt(10));
//        }
//        return mobile.toString();
//    }
}
