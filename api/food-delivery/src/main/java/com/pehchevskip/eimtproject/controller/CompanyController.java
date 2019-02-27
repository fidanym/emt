package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.Company;
import com.pehchevskip.eimtproject.model.Role;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.service.CompanyService;
import com.pehchevskip.eimtproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(String name, String description) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Company company = new Company();
        company.setName(name);
        company.setDescription(description);
        return new ResponseEntity<>(companyService.save(company), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Company>> findAllCompanies() {
        return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add-image")
    public ResponseEntity<Boolean> addImage(@RequestParam Long id, @RequestParam("image") MultipartFile file) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!(user.get().getRole().equals(Role.SUPER_ADMIN)
                || (user.get().getRole().equals(Role.ADMIN)
                    && id.equals(user.get().getCompany().getId())))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
