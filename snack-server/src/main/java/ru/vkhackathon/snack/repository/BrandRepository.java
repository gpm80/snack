package ru.vkhackathon.snack.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.domain.BrandDAO;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petr Gusarov
 */
@Repository
@Views({
        @View(name = BrandRepository.VIEW_BRAND_ALL,
                map = "function(doc) { if (doc.type=='BRAND') { emit(doc._id, doc) } }")
})
public class BrandRepository extends CouchDbRepositorySupport<BrandDAO> {

    private static Logger logger = LoggerFactory.getLogger(BrandRepository.class);
    public static final String VIEW_BRAND_ALL = "viewBrandAll";
    @Value("${url.image.header}")
    private String header;
    @Value("${spring.ektorp.defaultDatabase}")
    private String nameDb;
    @Autowired
    private AttachmentFileCouchDb attachmentFileCouchDb;

    /**
     * @param db
     */
    public BrandRepository(CouchDbConnector db) {
        super(BrandDAO.class, db);
        initStandardDesignDocument();
    }

    /**
     * Возвращает все бренды
     *
     * @return
     */
    public List<BrandDAO> findAll() {
        ViewQuery query = createQuery(VIEW_BRAND_ALL);
        List<BrandDAO> brandDAOS = db.queryView(query, BrandDAO.class);
        return brandDAOS.stream()
                .peek(dao -> attachmentFileCouchDb.lookImageUri(dao))
                .collect(Collectors.toList());
    }

    /**
     * Сохраняет бренд с логотипом
     *
     * @param brand бренд
     * @param file  логотип
     * @return
     */
    public Brand save(Brand brand, File file) {
        BrandDAO brandDAO = new BrandDAO();
        brandDAO.setBean(brand);
        if (brand.getId() == null) {
            add(brandDAO);
        } else {
            update(brandDAO);
        }
        attachmentFileCouchDb.saveAttach(file, null, brandDAO.getBean(), brandDAO);
        return brandDAO.syncGetBean();
    }

}
