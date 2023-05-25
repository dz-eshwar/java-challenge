package jp.co.axa.apidemo.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Data
@Entity
@Table(name="DEPARTMENT")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /*@OneToMany(mappedBy = "department")
    private List<Employee> employees;*/

}
