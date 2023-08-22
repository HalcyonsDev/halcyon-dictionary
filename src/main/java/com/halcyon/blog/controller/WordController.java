package com.halcyon.blog.controller;

import com.halcyon.blog.entity.Package;
import com.halcyon.blog.service.IPackageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static com.halcyon.blog.controller.PackageController.getWordsMap;

@Controller
@RequestMapping("/package/word")
public class WordController {

    private IPackageService packageService;

    @PostMapping("/add")
    public String add(@RequestParam String keyWord, @RequestParam String valueWord,
                       @RequestParam int id, Model model) {

        addWord(id, keyWord, valueWord, model);

        return "create_words";
    }

    @PostMapping("/save")
    public String save() {
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String entryKey, @RequestParam int id, Model model) {
        deleteWord(id, entryKey, model);

        return "create_words";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Package wordsPackage = packageService.findPackageById(id);
        Map<String, String> packageMap = getWordsMap(wordsPackage);

        model.addAttribute("package", wordsPackage);
        model.addAttribute("packageMap", packageMap);

        return "words_edit";
    }

    @PostMapping("/add-one")
    public String addOne(@RequestParam int id, @RequestParam String keyWord,
                         @RequestParam String valueWord, Model model) {
        addWord(id, keyWord, valueWord, model);

        return "words_edit";
    }

    @PostMapping("/edit-one")
    public String editOne(@RequestParam int id, @RequestParam String entryKey, Model model) {
        Package wordsPackage = packageService.findPackageById(id);

        String wordsPackageJson = wordsPackage.getWords();
        JSONObject wordsJsonObject = new JSONObject(wordsPackageJson);

        String entryValue = wordsJsonObject.getString(entryKey);

        model.addAttribute("package", wordsPackage);
        model.addAttribute("entryKey", entryKey);
        model.addAttribute("entryValue", entryValue);

        return "edit_one_word";
    }

    @PostMapping("/save-one")
    public String saveOne(@RequestParam int id, @RequestParam String keyWord,
                          String valueWord, String oldKeyWord, Model model) {

        Package wordsPackage = packageService.findPackageById(id);

        String wordsPackageJson = wordsPackage.getWords();
        JSONObject wordsJsonObject = new JSONObject(wordsPackageJson);

        if (!wordsJsonObject.has(keyWord)) {
            wordsJsonObject.remove(oldKeyWord);
        }

        wordsJsonObject.put(keyWord, valueWord);

        wordsPackage.setWords(wordsJsonObject.toString());
        packageService.save(wordsPackage);

        model.addAttribute("package", wordsPackage);
        model.addAttribute("packageMap", getWordsMap(wordsPackage));

        return "words_edit";

    }

    @PostMapping("/delete-one")
    public String deleteOne(@RequestParam int id, @RequestParam String entryKey, Model model) {
        deleteWord(id, entryKey, model);

        return "words_edit";
    }

    private void addWord(int id, String keyWord, String valueWord, Model model) {
        Package wordsPackage = packageService.findPackageById(id);

        setWordsToPackage(wordsPackage, keyWord, valueWord);
        packageService.save(wordsPackage);

        Map<String, String> packageMap = getWordsMap(wordsPackage);

        model.addAttribute("package", wordsPackage);
        model.addAttribute("packageMap", packageMap);
    }

    private void deleteWord(int id, String entryKey, Model model) {
        Package wordsPackage = packageService.findPackageById(id);

        String wordsPackageJson = wordsPackage.getWords();
        JSONObject wordsJsonObject = new JSONObject(wordsPackageJson);
        wordsJsonObject.remove(entryKey);

        wordsPackage.setWords(wordsJsonObject.toString());
        packageService.save(wordsPackage);

        model.addAttribute("package", wordsPackage);
        model.addAttribute("packageMap", getWordsMap(wordsPackage));
    }

    private void setWordsToPackage(Package wordsPackage, String keyWord, String valueWord) {
        JSONObject wordsJsonObject = new JSONObject();
        String wordsPackageJson = wordsPackage.getWords();

        if (wordsPackage.getWords() != null) {
            wordsJsonObject = new JSONObject(wordsPackageJson);
        }

        wordsJsonObject.put(keyWord, valueWord);
        wordsPackage.setWords(wordsJsonObject.toString());

    }

    @Autowired
    public void setPackageService(IPackageService packageService) {
        this.packageService = packageService;
    }
}
