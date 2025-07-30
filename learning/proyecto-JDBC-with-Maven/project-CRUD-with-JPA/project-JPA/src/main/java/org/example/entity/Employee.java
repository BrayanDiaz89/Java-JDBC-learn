package org.example.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "pa_surname")
    private String paSurname;
    @Column(name = "ma_surname")
    private String maSurname;
    private String email;
    private Double salary;
    private Boolean active;

    public Employee() {}

    public Employee(String firstName, String paSurname, String maSurname, String email, Double salary) {
        this.firstName = firstName;
        this.paSurname = paSurname;
        this.maSurname = maSurname;
        this.email = email;
        this.salary = salary;
        this.active = true;
    }

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getPaSurname() {
        return paSurname;
    }

    public String getMaSurname() {
        return maSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getActive() {
        return active;
    }

    public void desactivarEmployee(){
        active = false;
    }

    @Override
    public String toString() {
        return String.format("""
                Employee Data:
                Id: %d
                First Name: %s
                Last Name: %s %s
                Email: %s
                Salary: %.2f
                Active: %b                
                """,
                id, firstName, paSurname, maSurname, email, salary, active);
    }
}
