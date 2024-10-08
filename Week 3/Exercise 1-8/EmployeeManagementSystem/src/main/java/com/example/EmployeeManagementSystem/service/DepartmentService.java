package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.entity.secondary.Department;
import com.example.EmployeeManagementSystem.repository.secondary.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        return departmentRepository.findById(id).map(department -> {
            department.setName(departmentDetails.getName());
            return departmentRepository.save(department);
        }).orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public List<Department> findByNameNamedQuery(String name) {
        TypedQuery<Department> query = entityManager.createNamedQuery("Department.findByNameNamedQuery", Department.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public List<Department> findWithEmployeesMoreThan(int employeeCount) {
        TypedQuery<Department> query = entityManager.createNamedQuery("Department.findWithEmployeesMoreThan", Department.class);
        query.setParameter("employeeCount", employeeCount);
        return query.getResultList();
    }
}
