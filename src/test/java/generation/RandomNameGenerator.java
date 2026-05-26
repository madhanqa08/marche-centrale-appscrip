package generation;

import java.util.Random;

public class RandomNameGenerator
{
    static Random random = new Random();

    // Large First Name Pool
    static String[] firstNames =
            {
            "Madhan","Arun","Karthik","Vijay","Ajith","Rahul","Surya","Praveen","Rohit","Naveen",
            "Aditya","Akash","Amit","Anand","Ashwin","Balaji","Bharath","Chandan","Dinesh","Ganesh",
            "Hari","Harish","Jagan","Jeeva","Kiran","Lokesh","Manoj","Mukesh","Nithin","Prakash",
            "Rakesh","Sanjay","Sathish","Shiva","Sriram","Tarun","Vasanth","Vikram","Yogesh","Abhishek",
            "Aaryan","Deepak","Gokul","Hemant","Ishwar","Jatin","Kamal","Lalit","Mahesh","Naresh",
            "Omkar","Pavan","Rajesh","Sachin","Tejas","Uday","Varun","Yash","Zayan","Krish"
    };

    // Large Last Name Pool
    static String[] lastNames =
            {
            "Kumar","Raj","Sharma","Reddy","Singh","Varma","Krishna","Babu","Patel","Mohan",
            "Gupta","Agarwal","Nair","Menon","Iyer","Raman","Joshi","Kapoor","Malhotra","Chopra",
            "Saxena","Pandey","Yadav","Tiwari","Desai","Kulkarni","Shetty","Naidu","Pillai","Rao",
            "Chaudhary","Verma","Tripathi","Dubey","Soni","Mishra","Khan","Ali","Thomas","Fernandes",
            "Dsouza","Paul","Joseph","Mathew","Bose","Ghosh","Banerjee","Mukherjee","Das","Sen",
            "Bhardwaj","Parmar","Solanki","Jadeja","Thakur","Sinha","Rawat","Negi","Chavan","Pawar"
    };

    // Generate Random First Name
    public static String generateFirstName()
    {
        return firstNames[random.nextInt(firstNames.length)];
    }

    // Generate Random Last Name
    public static String generateLastName() {
        return lastNames[random.nextInt(lastNames.length)];
    }

    // Generate Full Name
    public static String generateFullName()
    {
        return generateFirstName() + " " + generateLastName();
    }
}
