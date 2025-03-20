package com.nhnacademy.day04reflection.user;

public class User{
    private String userName;
    private int userAge;


    public User(){
    }
    public User(String userName, int userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }


    public String getUserName() {
        return userName;
    }
    public int getUserAge() {
        return userAge;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
