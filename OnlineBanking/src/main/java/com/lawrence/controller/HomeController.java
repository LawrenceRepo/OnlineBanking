package com.lawrence.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lawrence.dto.UserDto;
import com.lawrence.model.PrimaryAccount;
import com.lawrence.model.SavingsAccount;
import com.lawrence.model.User;
import com.lawrence.repository.RoleRepository;
import com.lawrence.security.UserRole;
import com.lawrence.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") UserDto userDto, Model model) {
        
    User user = new User();
    
        if (userService.checkUserExists(userDto.getUsername(), userDto.getEmail())) {

            if (userService.checkEmailExists(userDto.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(userDto.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } 
        else {
            BeanUtils.copyProperties(userDto, user);
            
            Set<UserRole> userRoles = new HashSet<>();
            
            userRoles.add(new UserRole(user, roleRepository.findByName("ROLE_USER")));

            userService.createUser(user, userRoles);

            return "redirect:/";
        }
    }

    @RequestMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        
        User user = userService.findByUsername(principal.getName());
        
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }
}
