package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/all")
    public String showAllUsers(Model model) {
        List<User> allUsers = service.getAllUsers();
        model.addAttribute("allUsers",allUsers);
        return "all-Users";
    }

    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user",new User());
        return "add-User";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        service.saveUser(user);
        return "redirect:/users/all";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user",service.getUserById(id));
        return "edit-User";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        service.updateUser(id,user);
        return "redirect:/users/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        service.deleteUser(id);
        return "redirect:/users/all";
    }
}
