package Code;

import java.util.Date;
import java.util.List;

public class User {
    String id;
    String username;
    String password;
    String first_name;
    String last_name;
    String city;
    String country;
    int phone;
    String month;
    int day,year;

    public User(List<String> list){
        this.username=list.get(0);
        this.password=list.get(1);
        this.first_name=list.get(2);
        this.last_name=list.get(3);
        this.country=list.get(4);
        this.city=list.get(5);
        this.day=Integer.parseInt(list.get(6));
        this.month=list.get(7);
        this.year=Integer.parseInt(list.get(8));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
