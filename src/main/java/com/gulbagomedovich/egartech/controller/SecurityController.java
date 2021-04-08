package com.gulbagomedovich.egartech.controller;

import com.gulbagomedovich.egartech.model.Security;
import com.gulbagomedovich.egartech.repository.SecurityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SecurityController {

    private final SecurityRepository securityRepository;

    public SecurityController(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @GetMapping({"/", "/securities"})
    public String index(Model model) {
        List<Security> securities = securityRepository.findAll();
        model.addAttribute("securities", securities);

        return "index";
    }

    @GetMapping("/securities/new")
    public String addSecurity() {
        return "add-security";
    }

    @GetMapping("/securities/{id}/edit")
    public String editSecurity(@PathVariable("id") Long id, Model model) {
        Security security = securityRepository.getSecurityById(id);
        model.addAttribute("security", security);

        return "edit-security";
    }

    @PostMapping("/securities")
    public String addOrEditSecurity(Security security) {
        securityRepository.save(security);

        return "redirect:/securities";
    }

    @PostMapping("/securities/{id}")
    public String deleteSecurity(@PathVariable("id") Long id) {
        securityRepository.deleteById(id);

        return "redirect:/securities";
    }

}
