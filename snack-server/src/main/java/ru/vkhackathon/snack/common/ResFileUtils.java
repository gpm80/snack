package ru.vkhackathon.snack.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Petr Gusarov
 */
public class ResFileUtils {

    private static Logger logger = LoggerFactory.getLogger(ResFileUtils.class);

    public static File getFile(String path, String nameFile) {
        try {
            ClassPathResource resource = new ClassPathResource(path + "/" + nameFile);
            return resource.getFile();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public static JsonNode loadFromFile(File jsonFile) throws Exception {
        try (FileInputStream stream = new FileInputStream(jsonFile)) {
//        try (FileReader reader = new FileReader(jsonFile)) {
//            JsonParser parser = new JsonSimpleJsonParser();
//            List<Object> objects = parser.parseList(IOUtils.toString(reader));
//            JSONParser jsonParser = new JSONParser();
//            Object parse = jsonParser.parse(reader);


            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(stream);

//            jsonNode.findParents()
//            return jsonNode;
        }
    }
}
