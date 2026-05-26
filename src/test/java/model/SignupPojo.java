package model;
import generation.EmailGeneration;
import generation.MobileGenerations;
import generation.PasswordGenerator;
import generation.RandomNameGenerator;
public class SignupPojo
{
    private String firstname;
    private String lastname;
    private String mobile;
    private String email;
    private String passowrd;

    public void getAllDatas()
    {
        setPassowrd(PasswordGenerator.generatePassword());
        setFirstname(RandomNameGenerator.generateFirstName());
        setLastname(RandomNameGenerator.generateLastName());
        setMobile(MobileGenerations.generateMobileNumber());
        setEmail(EmailGeneration.generateRandomEmail());
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }
}
