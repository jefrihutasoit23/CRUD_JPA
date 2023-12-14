package com.aplikasi.karyawan.controller;

import com.aplikasi.karyawan.entity.Employee;
import com.aplikasi.karyawan.repository.EmployeeRepository;
import com.aplikasi.karyawan.service.EmployeeService;
import com.aplikasi.karyawan.utils.SimpleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/employee")
public class EmployeeController {
    @Autowired
    public EmployeeService employeeService;

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();

    @Autowired
    public EmployeeRepository employeeRepository;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody Employee request) {
        return new ResponseEntity<Map>(employeeService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Employee request) {
        return new ResponseEntity<Map>(employeeService.update(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody Employee request) {
        return new ResponseEntity<Map>(employeeService.delete(request.getId()), HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<Map>(employeeService.delete(id), HttpStatus.OK);
    }

    @GetMapping(value = {"/list", "/list/"})
    public ResponseEntity<Map> listQuizHeader(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {

        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);
        Page<Employee> list = null;

        if (name != null && !name.isEmpty()) {
            list = employeeRepository.getByLikeName("%" + name + "%", show_data);
        } else {
            list = employeeRepository.getALlPage(show_data);
        }
        Map map = new HashMap();
        map.put("data",list);
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }

}
