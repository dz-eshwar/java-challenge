package jp.co.axa.apidemo.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name="DEPARTMENT")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Department model information")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "department id")
    private Long id;

    @ApiModelProperty(value = "department name")
    private String name;

    /*@OneToMany(mappedBy = "department")
    private List<Employee> employees;*/

}
