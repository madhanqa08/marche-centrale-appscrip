package utilities;
import java.util.HashMap;
public class TestdataTravel
{
    private static final HashMap<String,String> map = new HashMap<>();

    public static void set(String key,String value)
    {
        map.put(key,value);
    }

    public static String get(String key)
    {
        return map.get(key);
    }
}
