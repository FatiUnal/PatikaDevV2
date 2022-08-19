package com.PatikaDev.Helper.Model;

public class Student extends User{
    private String type;
    public Student() {
    }

    public Student(int id, String name, String username, String pass, String type) {
         this.type = "student";
    }
}
