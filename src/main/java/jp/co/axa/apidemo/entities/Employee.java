package jp.co.axa.apidemo.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="EMPLOYEE")
@NoArgsConstructor
@ApiModel(description = "Employee model information")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "employee id auto generated")
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    @ApiModelProperty(value = "employee name")
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    @ApiModelProperty(value = "employee salary")
    private Double salary;


    @ManyToOne
    @JoinColumn(name = "department_id")
    @ApiModelProperty(value = "employee department")
    private Department department;

    public Employee(Long id) {
        this.id = id;
    }
}
