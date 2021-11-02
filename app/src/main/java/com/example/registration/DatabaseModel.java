package com.example.registration;


public class DatabaseModel {
    private int id;
    private String title;
    private String name;
    private String age;
    private String date;
    private String doctor;
    private String description;
    private String dob;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String  getAge() {
        return age;
    }

    public String getDate() {
        return date;
    }

    public String getDoc() {
        return doctor;
    }

    public String getDes() {
        return description;
    }

    public void setId(int id){
        this.id= id;
    }

    public void setTitle(String title) {
        this.title= title;
    }

    public void setName(String name)    {
        this.name= name;
    }

    public void setAge(String age)    {
        this.age= age;
    }

    public void setDate(String date) {
        this.date= date;
    }

    public void setDoc(String doctor) {
        this.doctor= doctor;
    }

    public void setDes(String description) {
        this.description= description;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "DatabaseModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", date='" + date + '\'' +
                ", doctor='" + doctor + '\'' +
                ", description='" + description + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
