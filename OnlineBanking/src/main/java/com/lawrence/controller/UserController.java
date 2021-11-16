package com.lawrence.controller;

import java.security.Principal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lawrence.dto.UserDto;
import com.lawrence.model.User;
import com.lawrence.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName());

        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile")
    public String profilePost(@ModelAttribute("user") UserDto newUserDto, Model model) {

        User user = userService.findByUsername(newUserDto.getUsername());
     
        BeanUtils.copyProperties(newUserDto, user);

        model.addAttribute("user", user);

        userService.saveUser(user);

        return "profile";
    }


}

