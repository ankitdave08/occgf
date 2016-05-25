package com.listertechnologies.occgf.web;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.listertechnologies.occgf.core.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService catgoryService;

    @RequestMapping("/upload-csv")
    public String uploadCsvForm() {
        return "category/upload-csv";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload-csv")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, 
            RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            try {
                String contents = IOUtils.toString(file.getInputStream(), "UTF-8");
                catgoryService.transformAndSend(contents);
                redirectAttributes.addFlashAttribute("message", "File successfully processed");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "Failed to process => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "File was empty");
        }

        return "redirect:/";

    }
}
