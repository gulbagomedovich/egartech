package com.gulbagomedovich.egartech.controller;

import com.gulbagomedovich.egartech.model.Role;
import com.gulbagomedovich.egartech.model.User;
import com.gulbagomedovich.egartech.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

@Controller
public class SignupController {

    private final UserRepository userRepository;

    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        if (userRepository.getUserByUsername(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("user", "username", "Пользователь с таким именем уже существует"));
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepository.save(user);

        try {
            request.login(user.getUsername(), user.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/securities";
    }

}
