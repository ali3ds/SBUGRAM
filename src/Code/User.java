package Code;

import java.util.*;

public class User {
    int id;
    String username;
    String password;
    String first_name;
    String last_name;
    String city;
    String country;
    int posts_count;
    String month;
    int day,year;
    int post_count;
    List<Integer> followings;
    String avatar_path;

    public User(List<String> list){
        followings=new ArrayList<Integer>();
        this.username=list.get(0);
        this.password=list.get(1);
        this.first_name=list.get(2);
        this.last_name=list.get(3);
        this.country=list.get(4);
        this.city=list.get(5);
        this.day=Integer.parseInt(list.get(6));
        this.month=list.get(7);
        this.year=Integer.parseInt(list.get(8));
        this.avatar_path=list.get(9);
    }

    public User(Map<String,String> data){
        followings=new ArrayList<Integer>();
        Set<String> set = data.keySet();
        for(String s:set){
            switch (s.trim()){
                case "id":
                    this.id=Integer.parseInt(data.get(s));
                    break;
                case "username":
                    this.username=data.get(s);
                    break;

                case "password":
                    this.password=data.get(s);
                    break;
                case "first":
                    this.first_name=data.get(s);
                    break;
                case "last":
                    this.last_name=data.get(s);
                    break;
                case "day":
                    this.day=Integer.parseInt(data.get(s));
                    break;
                case "month":
                    this.month=data.get(s);
                    break;
                case "year":
                    this.year=Integer.parseInt(data.get(s));
                    break;
                case "Country":
                    this.country=data.get(s);
                    break;
                case "city":
                    this.city=data.get(s);
                    break;
                case "following":
                    this.followings.add(Integer.parseInt(data.get(s)));
                    break;
                case "avatar":
                    this.avatar_path=data.get(s);
                    break;

            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", posts_count=" + posts_count +
                ", month='" + month + '\'' +
                ", day=" + day +
                ", year=" + year +
                ", post_count=" + post_count +
                ", followings=" + followings +
                ", avatar='" + avatar_path + '\'' +
                '}';
    }
}
