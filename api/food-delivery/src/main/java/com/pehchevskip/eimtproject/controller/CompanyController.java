package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.Company;
import com.pehchevskip.eimtproject.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/create")
    public Company createCompany(String name) {
        Company company = new Company();
        company.setName(name);
        return companyService.save(company);
    }

    @GetMapping("/all")
    public List<Company> findAllCompanies() {
        return companyService.findAll();
    }

}
