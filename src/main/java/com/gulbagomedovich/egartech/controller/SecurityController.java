package com.gulbagomedovich.egartech.controller;

import com.gulbagomedovich.egartech.model.Security;
import com.gulbagomedovich.egartech.model.User;
import com.gulbagomedovich.egartech.repository.SecurityRepository;
import com.gulbagomedovich.egartech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class SecurityController {

    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;

    public SecurityController(SecurityRepository securityRepository, UserRepository userRepository) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/securities"})
    public String index(Model model, Principal principal) {
        List<Security> securities = securityRepository.findAllByUserUsername(principal.getName());
        model.addAttribute("securities", securities);

        return "index";
    }

    @GetMapping("/securities/new")
    public String addSecurity(Model model) {
        model.addAttribute("security", new Security());

        return "add-security";
    }

    @PostMapping("/securities")
    public String addSecurity(@ModelAttribute("security") @Valid Security security, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "add-security";
        }

        User user = userRepository.getUserByUsername(principal.getName());
        security.setUser(user);

        securityRepository.save(security);

        return "redirect:/securities";
    }

    @GetMapping("/securities/{id}/edit")
    public String editSecurity(@PathVariable("id") Long id, Model model) {
        Security security = securityRepository.getSecurityById(id);
        model.addAttribute("security", security);

        return "edit-security";
    }

    @PutMapping("/securities")
    public String editSecurity(@ModelAttribute("security") @Valid Security security, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "edit-security";
        }

        User user = userRepository.getUserByUsername(principal.getName());
        security.setUser(user);

        securityRepository.save(security);

        return "redirect:/securities";
    }

    @DeleteMapping("/securities/{id}")
    public String deleteSecurity(@PathVariable("id") Long id) {
        securityRepository.deleteById(id);

        return "redirect:/securities";
    }

}
