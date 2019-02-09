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
import java.util.Random;

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
        String[] descriptions = {"Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.", "" +
                "Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.", "" +
                "A small river named Duden flows by their place and supplies it with the necessary regelialia. ", "" +
                "It is a paradisematic country, in which roasted parts of sentences fly into your mouth."};
        String[] addresses = {"Marshal Tito 42, Berovo", "Partizanski Odredi 121, Skopje"};
        Long[] companiesIds = createCompanies(descriptions, "Restoran Bolero", "Restoran Mrs", "Restoran Dionis");
        Long fId = companiesIds[0];
        Long sId = companiesIds[1];
        Long tId = companiesIds[2];

        createUsers(addresses, "pehchevskip", "jovanovskij", "markovskim");

        createItems(new String[]{"Kebab", "Stek", "Pleskavica", "Raznic", "Pizza", "Pastramajlija", "Pasta Quattro Formaggi", "Pasta Carbonara"},
                    descriptions,
                    new double[]{ 10, 110, 120, 30, 130, 130, 150, 140 },
                    new Long[]{ fId, fId, fId, sId, sId, sId, tId, tId });
    }

    private Long[] createCompanies(String[] descriptions, String... companiesNames) {
        List<Long> result = new ArrayList<>();
        Random rand = new Random();
        for (String companyName : companiesNames) {
            Company company = new Company();
            company.setName(companyName);
            company.setDescription(descriptions[rand.nextInt(descriptions.length)]);
            Long tmpId = companyService.save(company).getId();
            result.add(tmpId);
        }
        return result.toArray(new Long[]{});
    }

    private void createUsers(String[] addresses, String... usersNames) {
        Random rand = new Random();
        for (String userName : usersNames) {
            String firstName = capitalize(String.valueOf(userName.charAt(userName.length()-1)));
            String lastName = capitalize(userName.substring(0, userName.length()-1));
            String password = passwordEncoder.encode("password");
            String address = addresses[rand.nextInt(addresses.length)];
            User user = userService.createUserWithRole(userName, userName + "@fd.com", password, firstName, lastName, address, Role.SUPER_ADMIN);
            userService.save(user);
        }
    }

    private void createItems(String[] itemsNames, String[] descriptions, double[] prices, Long[] companiesIds) {
        if (itemsNames.length != prices.length) {
            throw new IllegalArgumentException();
        }
        Random rand = new Random();
        for (int i = 0; i < itemsNames.length; ++i) {
            Item item = new Item();
            item.setName(itemsNames[i]);
            item.setDescription(descriptions[rand.nextInt(descriptions.length)]);
            item.setPrice(prices[i]);
            item.setCompany(companyService.findById(companiesIds[i]).orElse(null));
            itemService.save(item);
        }
    }

    private String capitalize(String string) {
        return (string != null && string.length() > 0)
                ? string.substring(0, 1).toUpperCase() + string.substring(1)
                : string;
    }

}
