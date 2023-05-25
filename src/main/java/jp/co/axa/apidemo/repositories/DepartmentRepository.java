package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
