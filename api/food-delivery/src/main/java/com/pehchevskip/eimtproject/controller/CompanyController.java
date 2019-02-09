package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.Company;
import com.pehchevskip.eimtproject.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @PostMapping("/add-image")
    public ResponseEntity<Boolean> addImage(@RequestParam Long id, @RequestParam("image")MultipartFile file) {
        boolean success = companyService.addImage(id, file);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-image")
    public void getImage(@RequestParam Long id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(companyService.getImage(id));
        response.getOutputStream().close();
    }

}
