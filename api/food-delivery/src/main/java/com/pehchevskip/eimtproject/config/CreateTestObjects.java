package com.pehchevskip.eimtproject.config;

import com.pehchevskip.eimtproject.model.Company;
import com.pehchevskip.eimtproject.model.Item;
import com.pehchevskip.eimtproject.model.Role;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.service.CompanyService;
import com.pehchevskip.eimtproject.service.ItemService;
import com.pehchevskip.eimtproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateTestObjects implements CommandLineRunner {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Long[] companiesIds = createCompanies("Restoran Kaj Zvonko", "Restoran Mrs");
        Long fId = companiesIds[0];
        Long sId = companiesIds[1];

        createUsers("pehchevskip", "jovanovskij", "markovskim");

        createItems(new String[]{"Kebab", "Stek", "Pleskavica", "Raznic", "Pizza", "Pastramajlija"},
                    new double[]{ 10, 110, 120, 30, 130, 130 },
                    new Long[]{ fId, fId, fId, sId, sId, sId });
    }

    private Long[] createCompanies(String... companiesNames) {
        List<Long> result = new ArrayList<>();
        for (String companyName : companiesNames) {
            Company company = new Company();
            company.setName(companyName);
            Long tmpId = companyService.save(company).getId();
            result.add(tmpId);
        }
        return result.toArray(new Long[]{});
    }

    private void createUsers(String... usersNames) {
        for (String userName : usersNames) {
            String firstName = String.valueOf(userName.charAt(userName.length()-1));
            String lastName = userName.substring(0, userName.length()-1);
            String password = passwordEncoder.encode("password");
            User user = userService.createUserWithRole(userName, userName + "@fd.com", password, firstName, lastName, Role.SUPER_ADMIN);
            userService.save(user);
        }
    }

    private void createItems(String[] itemsNames, double[] prices, Long[] companiesIds) {
        if (itemsNames.length != prices.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < itemsNames.length; ++i) {
            Item item = new Item();
            item.setName(itemsNames[i]);
            item.setPrice(prices[i]);
            item.setCompany(companyService.findById(companiesIds[i]).orElse(null));
            itemService.save(item);
        }
    }

}
