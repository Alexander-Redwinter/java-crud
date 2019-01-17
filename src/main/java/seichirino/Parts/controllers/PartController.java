package seichirino.Parts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seichirino.Parts.entity.Part;
import seichirino.Parts.service.PartService;
import seichirino.Parts.service.PartServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PartController {

    private PartService service;
    private static int sort = 0;
    private static String searchTerm = "";

    public static String getSearchTerm() {
        return searchTerm;
    }

    public static int getSort() {
        return sort;
    }

    @Autowired
    public void setPartService(PartService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<Part> partsTable;


        if (searchTerm.isEmpty() || searchTerm.equals("") || searchTerm == null){
            if (sort == 1) {
                partsTable = service.getByRequired();
            } else if (sort == 2) {
                partsTable = service.getByRequiredNot();
            }else {
                partsTable = service.getAll();
            }
        }else if (sort == 1) {
            partsTable = service.findAllByRequiredAndName(true,searchTerm);
        } else if (sort == 2) {
            partsTable = service.findAllByRequiredAndName(false,searchTerm);
        }else{
            partsTable = service.findByName(searchTerm);
        }

        model.addAttribute("parts", partsTable);
        int toCreate = toCreate();
        int maxPage = service.maxPage() +1;
        int currentPage = PartServiceImpl.getCurrentPage() +1;
        model.addAttribute("toCreate", toCreate);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("sortType",sort);
        return "index";
    }

    private int toCreate() {
        List<Part> partsTable = service.getEverything();

        int min = -1;
        for (Part p : partsTable) {
            if(p.isRequired()){
                int i = p.getAmount();
                if (min == -1) {
                    min = i;
                } else if (i < min) {
                    min = i;
                }
            }
        }
        return min;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Part part = service.getPartById(id);
        model.addAttribute("part", part);
        return "operations/edit";
    }

    @PostMapping("/update")
    public String savePart(@RequestParam Integer id, @RequestParam(required = false, defaultValue = "defaultPartName") String name,
                           @RequestParam(required = false, defaultValue = "0") Integer amount, @RequestParam(required = false, defaultValue = "false") boolean required) {
        service.updatePart(id, name, amount, required);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newPart() {
        return "operations/new";
    }

    @PostMapping("/save")
    public String updatePart(@RequestParam(required = false, defaultValue = "defaultPartName") String name,
                             @RequestParam(required = false, defaultValue = "0") Integer amount, @RequestParam(required = false, defaultValue = "false") boolean required) {
        service.savePart(new Part(name, amount, required));
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deletePart(id);
        PartServiceImpl.setCurrentPage(0);
        return "redirect:/";
    }

    @GetMapping("/sort")
    public String sort() {
        sort++;
        if (sort > 2) sort = 0;
        PartServiceImpl.setCurrentPage(0);
        return "redirect:/";
    }

    @GetMapping("/pageup")
    public String pageup() {
        service.incCurrentPage();
        return "redirect:/";
    }

    @GetMapping("/pagedown")
    public String pagedown() {
        service.decCurrentPage();
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "") String searchParam) {
        searchTerm = searchParam;
        PartServiceImpl.setCurrentPage(0);
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home() {
        PartServiceImpl.setCurrentPage(0);
        sort = 0;
        searchTerm = "";
        return "redirect:/";
    }

}
