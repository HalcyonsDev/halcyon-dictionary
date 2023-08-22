package com.halcyon.blog.controller;

import com.halcyon.blog.entity.Package;
import com.halcyon.blog.service.IPackageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/package")
public class PackageController {

    private IPackageService packageService;

    @GetMapping("/view")
    public String packageView(@RequestParam int id, Model model) {
        Package wordsPackage = packageService.findPackageById(id);

        model.addAttribute("package", wordsPackage);

        if (wordsPackage.getWords() != null) {
            model.addAttribute("packageMap", getWordsPackageMap(wordsPackage));
        }

        return "package_view";
    }

    @GetMapping("/create")
    public String createPackage() {
        return "create_package";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name, @RequestParam String description,
                       @RequestParam String complexity, Model model) {

        Package wordsPackage = new Package(name, description, complexity);
        packageService.save(wordsPackage);

        model.addAttribute("package", wordsPackage);

        return "create_words";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Package wordsPackage = packageService.findPackageById(id);
        model.addAttribute("package", wordsPackage);

        return "package_edit";
    }

    @PostMapping("/save-edits")
    public String saveEdits(@RequestParam int id, @RequestParam String name, @RequestParam String description,
                            @RequestParam String complexity) {

        Package wordsPackage = packageService.findPackageById(id);

        wordsPackage.setName(name);
        wordsPackage.setDescription(description);
        wordsPackage.setComplexity(complexity);

        packageService.save(wordsPackage);

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        packageService.deletePackageById(id);

        return "redirect:/";
    }

    private Map<String, String> getWordsPackageMap(Package wordsPackage) {
        return getWordsMap(wordsPackage);
    }

    public static Map<String, String> getWordsMap(Package wordsPackage) {
        String wordsPackageJson = wordsPackage.getWords();

        Map<String, String> wordsPackageMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject(wordsPackageJson);
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            String value = jsonObject.getString(key);

            wordsPackageMap.put(key, value);
        }

        return wordsPackageMap;
    }

    @Autowired
    public void setPackageService(IPackageService packageService) {
        this.packageService = packageService;
    }
}
