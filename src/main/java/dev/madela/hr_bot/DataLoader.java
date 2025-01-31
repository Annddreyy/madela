package dev.madela.hr_bot;

import dev.madela.hr_bot.model.Role;
import dev.madela.hr_bot.model.User;
import dev.madela.hr_bot.repository.RoleRepository;
import dev.madela.hr_bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role hrRole = new Role();
        hrRole.setName("HR");
        roleRepository.save(hrRole);

        Role employeeRole = new Role();
        employeeRole.setName("EMPLOYEE");
        roleRepository.save(employeeRole);

        User hrUser = new User();
        hrUser.setUsername("hr");
        hrUser.setPassword(passwordEncoder.encode("hr"));
        Set<Role> hrRoles = new HashSet<>();
        hrRoles.add(hrRole);
        hrUser.setRoles(hrRoles);
        userRepository.save(hrUser);

        User employeeUser = new User();
        employeeUser.setUsername("employee");
        employeeUser.setPassword(passwordEncoder.encode("employee"));
        Set<Role> employeeRoles = new HashSet<>();
        employeeRoles.add(employeeRole);
        employeeUser.setRoles(employeeRoles);
        userRepository.save(employeeUser);
    }
}

