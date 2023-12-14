package com.aplikasi.karyawan.repository;

import com.aplikasi.karyawan.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    //JPA Query
    @Query(value = "select c from Employee c WHERE c.id = :idEmployee", nativeQuery = false)
    public Employee getById(@Param("idEmployee") Long idEmployee);

    //Native Query : menggunakan JPAQL
    @Query(value = "select  e from employee e where  id = :idEmployee;",nativeQuery = true)
    public Object getByIdNativeQuery(@Param("idEmployee") Long idEmployee);

    @Query(value = "select e from Employee e where LOWER(e.name) like LOWER(:nameParam)")
    public Page<Employee> getByLikeName(@Param("nameParam") String nameParam, Pageable pageable);

    @Query(value = "select e from Employee e ")
    public Page<Employee> getALlPage(Pageable pageable);


}
