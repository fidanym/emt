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

    private static String[] descriptions = {"Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.", "" +
            "Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.", "" +
            "A small river named Duden flows by their place and supplies it with the necessary regelialia. ", "" +
            "It is a paradisematic country, in which roasted parts of sentences fly into your mouth."};

    private static String[] addresses = {"Marshal Tito 42, Berovo", "Partizanski Odredi 121, Skopje"};

    private static Random rand = new Random();


    @Override
    public void run(String... args) {
        Long[] companiesIds = createCompanies(descriptions, "Restoran Bolero", "Restoran Mrs", "Restoran Dionis");
        Long comp1 = companiesIds[0];
        Long comp2 = companiesIds[1];
        Long comp3 = companiesIds[2];

        createUsers(new String[] { "pehchevskip", "jovanovskij", "markovskim" },
                    new Role[] { Role.SUPER_ADMIN, Role.ADMIN, Role.CLIENT });

        createItems(new String[]{ "Kebab", "Stek", "Pleskavica", "Raznic", "Pizza", "Pastramajlija", "Pasta Quattro Formaggi", "Pasta Carbonara" },
                    new double[]{ 10, 110, 120, 30, 130, 130, 150, 140 },
                    new Long[]{ comp1, comp1, comp1, comp2, comp2, comp2, comp3, comp3 });
    }

    private Long[] createCompanies(String[] descriptions, String... companiesNames) {
        List<Long> result = new ArrayList<>();
        for (String companyName : companiesNames) {
            Company company = new Company();
            company.setName(companyName);
            company.setDescription(descriptions[rand.nextInt(descriptions.length)]);
            Long tmpId = companyService.save(company).getId();
            result.add(tmpId);
        }
        return result.toArray(new Long[]{});
    }

    private void createUsers(String[] userNames, Role[] roles) {
        for (int i = 0; i < userNames.length; ++i) {
            String firstName = capitalize(String.valueOf(userNames[i].charAt(userNames[i].length()-1)));
            String lastName = capitalize(userNames[i].substring(0, userNames[i].length()-1));
            String password = passwordEncoder.encode("password");
            String address = addresses[rand.nextInt(addresses.length)];
            User user = userService.createUserWithRole(userNames[i], userNames[i] + "@fd.com", password, firstName, lastName, address, "071234567", roles[i]);
            if (Role.ADMIN.equals(roles[i])) {
                user.setCompany(companyService.findById(1l).get());
            }
            userService.save(user);
        }
    }

    private void createItems(String[] itemsNames, double[] prices, Long[] companiecomp2s) {
        if (itemsNames.length != prices.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < itemsNames.length; ++i) {
            Item item = new Item();
            item.setName(itemsNames[i]);
            item.setDescription(descriptions[rand.nextInt(descriptions.length)]);
            item.setPrice(prices[i]);
            item.setCompany(companyService.findById(companiecomp2s[i]).orElse(null));
            itemService.save(item);
        }
    }

    private String capitalize(String string) {
        return (string != null && string.length() > 0)
                ? string.substring(0, 1).toUpperCase() + string.substring(1)
                : string;
    }

}
