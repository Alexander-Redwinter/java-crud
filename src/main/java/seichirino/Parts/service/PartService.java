package seichirino.Parts.service;

import seichirino.Parts.entity.Part;

import java.util.List;

public interface PartService {
    Part getPartById(Integer id);
    void savePart(Part part);
    void updatePart(Integer id, String name, Integer amount, boolean required);
    void deletePart(Integer id);
    List<Part> getByRequired();
    List<Part> getByRequiredNot();
    List<Part> getAll();
    List<Part> findByName(String name);
    List<Part> getEverything();
    public void incCurrentPage();
    public void decCurrentPage();
    public int maxPage();
    List<Part> findAllByRequiredAndName(boolean required, String name);
}
