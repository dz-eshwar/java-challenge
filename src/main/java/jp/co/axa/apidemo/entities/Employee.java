package jp.co.axa.apidemo.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="EMPLOYEE")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    private Double salary;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
