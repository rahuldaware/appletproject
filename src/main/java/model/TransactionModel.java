package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TransactionModel {
    private String bookingId;
    private ArrayList<String> firstName;
    private ArrayList<String> lastName;
    private ArrayList<String> age;
    private ArrayList<String> gender;
    private String cc_no;
    private String price;

    public TransactionModel() {
        firstName = new ArrayList<>();
        lastName = new ArrayList<>();
        age = new ArrayList<>();
        gender = new ArrayList<>();
    }
    public TransactionModel(String bookingId, ArrayList<String> firstName, ArrayList<String> lastName, ArrayList<String> age, ArrayList<String> gender, String cc_no, String price) {
        this.bookingId = bookingId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.cc_no = cc_no;
        this.price = price;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public ArrayList<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(ArrayList<String> firstName) {
        this.firstName = firstName;
    }

    public void addFirstName(String firstName) {
        this.firstName.add(firstName);
    }

    public ArrayList<String> getLastName() {
        return lastName;
    }

    public void setLastName(ArrayList<String> lastName) {
        this.lastName = lastName;
    }

    public void addLastName(String lastName) {
        this.lastName.add(lastName);
    }

    public ArrayList<String> getAge() {
        return age;
    }

    public void setAge(ArrayList<String> age) {
        this.age = age;
    }

    public void addAge(String age) {
        this.age.add(age);
    }

    public ArrayList<String> getGender() {
        return gender;
    }

    public void setGender(ArrayList<String> gender) {
        this.gender = gender;
    }

    public void addGender(String gender) {
        this.gender.add(gender);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCc_no() {
        return cc_no;
    }

    public void setCc_no(String cc_no) {
        this.cc_no = cc_no;
    }
}
