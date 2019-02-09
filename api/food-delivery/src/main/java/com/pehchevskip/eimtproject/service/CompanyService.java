package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.Company;
import com.pehchevskip.eimtproject.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public boolean addImage(Long id, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                return false;
            }
            Optional<Company> company = companyRepository.findById(id);
            if (company.isPresent()) {
                company.get().setImage(file.getBytes());
                companyRepository.save(company.get());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public byte[] getImage(Long id) throws IOException {
        Optional<Company> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            return new byte[]{};
        } else if (company.get().getImage() != null) {
            return company.get().getImage();
        } else {
            Resource resource = new ClassPathResource("static/images/placeholder.jpg");
            return Files.readAllBytes(resource.getFile().toPath());
        }
    }

}
