package ru.vkhackathon.snack.common;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.File;

/**
 * Created by Petr Gusarov
 */
public class ResFileUtilsTest {

    @Test
    public void testJsonParser() throws Exception {
        File jsonFile = ResFileUtils.getFile("json", "trc.json");
        JsonNode jsonNode = ResFileUtils.loadFromFile(jsonFile);
        jsonNode.isArray();
    }

}