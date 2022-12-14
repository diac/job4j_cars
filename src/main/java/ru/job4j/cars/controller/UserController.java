package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/register")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute User user, @RequestParam("driverName") String driverName) {
        userService.register(user, driverName);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req, RedirectAttributes redirectAttributes) {
        Optional<User> userInDb = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userInDb.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка авторизации");
            return "redirect:/login";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userInDb.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}