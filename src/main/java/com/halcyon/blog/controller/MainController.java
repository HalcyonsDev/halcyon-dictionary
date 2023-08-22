package com.halcyon.blog.controller;

import com.halcyon.blog.entity.Package;
import com.halcyon.blog.service.IPackageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private IPackageService packageService;

    @GetMapping("/")
    public String home(Model model) {

        List<Package> packagesList = packageService.findAllPackages();

        model.addAttribute("packagesList", packagesList);

        return "home";
    }

    @Autowired
    public void setPackageService(IPackageService packageService) {
        this.packageService = packageService;
    }
}
