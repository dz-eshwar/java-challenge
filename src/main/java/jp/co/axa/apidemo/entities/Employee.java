package jp.co.axa.apidemo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="EMPLOYEE")
@NoArgsConstructor
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    private Double salary;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee(Long id) {
        this.id = id;
    }
}
