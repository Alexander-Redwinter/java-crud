package seichirino.Parts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import seichirino.Parts.entity.Part;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {

    List<Part> findAllByRequired(boolean required, Pageable pageRequest);
    List<Part> findAllByRequired(boolean required);
    List<Part> findAllByNameIgnoreCaseContaining(String name, Pageable pageRequest);
    List<Part> findAllByNameIgnoreCaseContaining(String name);
    List<Part> findAllByRequiredAndNameIgnoreCaseContaining(boolean required, String name, Pageable pageRequest);
    List<Part> findAllByRequiredAndNameIgnoreCaseContaining(boolean required, String name);
}
