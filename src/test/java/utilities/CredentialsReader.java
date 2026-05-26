package utilities;
import java.io.FileInputStream;
import java.util.Properties;
public class CredentialsReader
{
    private static final Properties prop;
    static
    {
        try
        {
            prop = new Properties();
            FileInputStream fis =
                    new FileInputStream("src/test/java/utilities/credentials.properties");
            prop.load(fis);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key)
    {
        return prop.getProperty(key);
    }
}
