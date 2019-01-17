package seichirino.Parts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import seichirino.Parts.controllers.PartController;
import seichirino.Parts.entity.Part;
import seichirino.Parts.repository.PartRepository;

import java.util.List;

@Service
public class PartServiceImpl implements PartService{

    private PartRepository repository;
    private static int currentPage = 0;

    public static void setCurrentPage(int currentPage) {
        PartServiceImpl.currentPage = currentPage;
    }

    public static int getCurrentPage() {
        return currentPage;
    }

    public int maxPage(){

        if (PartController.getSearchTerm().isEmpty() || PartController.getSearchTerm().equals("") || PartController.getSearchTerm() == null){
            if (PartController.getSort() == 1) {
                return (int)Math.ceil(((double) repository.findAllByRequired(true).size())/10d) - 1;
            } else if (PartController.getSort() == 2) {
                return (int)Math.ceil(((double) repository.findAllByRequired(false).size())/10d) - 1;
            }else {
                return (int)Math.ceil(((double) repository.findAll().size())/10d) - 1;
            }
        }else if (PartController.getSort() == 1) {

            return (int)Math.ceil(((double) repository.findAllByRequiredAndNameIgnoreCaseContaining(true,PartController.getSearchTerm()).size())/10d) - 1;
        } else if (PartController.getSort() == 2) {

            return (int)Math.ceil(((double) repository.findAllByRequiredAndNameIgnoreCaseContaining(false,PartController.getSearchTerm()).size())/10d) - 1;
        }else{

            return (int)Math.ceil(((double) repository.findAllByNameIgnoreCaseContaining(PartController.getSearchTerm()).size())/10d) - 1;
        }


    }

    public void incCurrentPage() {
        currentPage++;
        if(currentPage > maxPage()) currentPage = maxPage();
    }

    public void decCurrentPage() {
        currentPage--;
        if(currentPage < 0) currentPage = 0;
    }

    @Autowired
    public void setProductRepository(PartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Part getPartById(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public void savePart(Part part) {
        repository.save(part);
    }

    @Override
    public void updatePart(Integer id, String name, Integer amount, boolean required) {
        Part updated = repository.findOne(id);
        updated.setName(name);
        updated.setAmount(amount);
        updated.setRequired(required);
        repository.save(updated);
    }

    @Override
    public void deletePart(Integer id) {
        repository.delete(id);
    }

    @Override
    public List<Part> getByRequired() {
        return repository.findAllByRequired(true, gotoPage());
    }

    @Override
    public List<Part> getByRequiredNot() {
        return repository.findAllByRequired(false, gotoPage());
    }

    @Override
    public List<Part> getAll() {
        return repository.findAll(gotoPage()).getContent();
    }

    @Override
    public List<Part> getEverything() {
        return repository.findAll();
    }

    @Override
    public List<Part> findByName(String name) {
        return repository.findAllByNameIgnoreCaseContaining(name,gotoPage());
    }

    private PageRequest gotoPage(){
        PageRequest request = new PageRequest(currentPage,10);
        return request;
    }

    @Override
    public List<Part> findAllByRequiredAndName(boolean required, String name){
        return repository.findAllByRequiredAndNameIgnoreCaseContaining(required,name,gotoPage());
    }

}
