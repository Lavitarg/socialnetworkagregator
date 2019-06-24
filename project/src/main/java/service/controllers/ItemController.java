package service.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import service.repo.ItemsRepo;


@Controller
public class ItemController {
    @Autowired
    ItemsRepo itemsRepo;

    @GetMapping("/item")
    public String getTestItem(ModelMap model)  {
        model.addAttribute("itemsFromServer",itemsRepo.findAll());
        return  "test";
    }
}
