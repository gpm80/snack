package ru.vkhackathon.snack.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;

/**
 * Утилита для ресурсов
 */
public class ResFileUtils {

    private static Logger logger = LoggerFactory.getLogger(ResFileUtils.class);

    public static File getFile(String path, String nameFile) {
        return getFile(path + "/" + nameFile);
    }

    public static File getFile(String nameRes) {
        try {
            ClassPathResource resource = new ClassPathResource(nameRes);
            return resource.getFile();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static <T> T loadFromFile(String res, Class<T> cls) throws Exception {
        return loadFromFile(getFile(res), cls);
    }

    public static <T> T loadFromFile(File jsonFile, Class<T> cls) throws Exception {
        try (FileInputStream stream = new FileInputStream(jsonFile)) {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(stream, cls);
        }
    }
}
