package ru.vkhackathon.snack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.domain.BrandDAO;
import ru.vkhackathon.snack.repository.BrandRepository;

import java.io.File;
import java.util.Optional;

/**
 * Created by Petr Gusarov
 */
@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand findById(String id) {
        BrandDAO brandDAO = brandRepository.get(id);
        return Optional.ofNullable(brandDAO).orElse(new BrandDAO())
                .getBean();
    }

    public Brand save(Brand brand, File file) {
        return brandRepository.save(brand, file);
    }
}
