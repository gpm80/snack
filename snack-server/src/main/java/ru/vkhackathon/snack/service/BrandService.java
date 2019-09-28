package ru.vkhackathon.snack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.domain.BrandDAO;
import ru.vkhackathon.snack.repository.BrandRepository;

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
}
