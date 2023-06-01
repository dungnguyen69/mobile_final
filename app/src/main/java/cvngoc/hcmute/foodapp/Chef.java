package cvngoc.hcmute.foodapp;

public class Chef {

    private String Area, City, ConfirmPassword, EmailID, Fname, House, Lname, Mobile, Password, District;

    public Chef(String confirmPassword, String emailID, String fname, String house, String lname, String mobile, String password, String district, String city) {
        this.Area = Area;
        City = city;
        ConfirmPassword = confirmPassword;
        EmailID = emailID;
        Fname = fname;
        House = house;
        Lname = lname;
        Mobile = mobile;
        Password = password;
        District = district;
    }

    public Chef() {
    }


    public String getCity() {
        return City;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public String getEmailID() {
        return EmailID;
    }

    public String getFname() {
        return Fname;
    }

    public String getHouse() {
        return House;
    }

    public String getLname() {
        return Lname;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getPassword() {
        return Password;
    }

    public String getDistrict() {
        return District;
    }

}
