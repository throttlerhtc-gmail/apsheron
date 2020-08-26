package com.restaurant.apsheron.web;

import com.restaurant.apsheron.service.FoodService;
import com.restaurant.apsheron.service.ManagerService;
import com.restaurant.apsheron.util.FoodsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private FoodService foodService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/managers")
    public String getManagers(Model model) {
        model.addAttribute("managers", managerService.getAll());
        return "managers";
    }

    @PostMapping("/managers")
    public String setManager(HttpServletRequest request) {
        int managerId = Integer.parseInt(request.getParameter("managerId"));
        SecurityUtil.setAuthManagerId(managerId);
        return "redirect:foods";
    }

    @GetMapping("/foods")
    public String getFoods(Model model) {
        model.addAttribute("foods",
                FoodsUtil.getTos(foodService.getAll(SecurityUtil.authManagerId()), SecurityUtil.authManagerCaloriesPerDay()));
        return "foods";
    }
}
