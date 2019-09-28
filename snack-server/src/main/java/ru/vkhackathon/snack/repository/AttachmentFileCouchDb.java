package ru.vkhackathon.snack.repository;

import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.MimeTypeUtils;
import ru.vkhackathon.snack.ImageUri;
import ru.vkhackathon.snack.domain.CouchGeneric;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * Created by Petr Gusarov
 */
@Repository
public class AttachmentFileCouchDb {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentFileCouchDb.class);
    @Value("${url.image.header}")
    private String header;
    @Value("${spring.ektorp.defaultDatabase}")
    private String nameDb;
    private CouchDbConnector dbConn;

    public AttachmentFileCouchDb(CouchDbConnector db) {
        this.dbConn = db;
    }

    /**
     * @param file
     * @param type
     * @param imageUri
     * @param generic
     * @return
     */
    public String saveAttach(File file, String type, ImageUri imageUri, CouchGeneric generic) {
        if (file != null) {
            if (type == null) {
                type = MimeTypeUtils.IMAGE_JPEG_VALUE;
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                AttachmentInputStream attach = new AttachmentInputStream(UUID.randomUUID().toString(), fis, type);
                String uri = String.format("%s/%s/%s/%s", header, nameDb, generic.getId(), attach.getId());
                imageUri.setImageUri(uri);
                dbConn.createAttachment(generic.getId(), generic.getRevision(), attach);
                return uri;
            } catch (Exception e) {
                logger.warn("An error attach file image logo", e);
            }
        }
        return "";
    }

    /**
     * Подгружаем ссылку на лету с аатача
     *
     * @param couchGeneric
     * @return
     */
    public void lookImageUri(CouchGeneric couchGeneric) {
        if (couchGeneric.getAttachments() != null) {
            couchGeneric.getAttachments().keySet().stream().findFirst().ifPresent(s -> {
                Object bean = couchGeneric.getBean();
                if (bean instanceof ImageUri) {
                    ((ImageUri) bean).setImageUri(String.format("%s/%s/%s/%s", header, nameDb, couchGeneric.getId(), s));
                }
            });
        }
    }
}
