package co.com.bancolombia.transformservice.config;

import co.com.bancolombia.model.validation.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PatternModelConfigTest {

    @TempDir
    Path tempDir;

    PatternModelConfig patternModelConfig;

    @BeforeEach
    void setUp() {
        patternModelConfig = new PatternModelConfig();
    }

    @Test
    void configModelsSuccess() throws IOException {
        Map<String, PatternModel> configModels = patternModelConfig
                .configModels("src/test/resources/config/configexamplesucces.yaml");

        assertNotNull(configModels);
        PatternModel model = configModels.get("PlainTextMQ");
        assertNotNull(model);
        assertFalse(model.requestModel().isEmpty());
    }

    @Test
    void configModelsFailInvalidName() throws IOException {
        Path invalidYaml = tempDir.resolve("invalid-pattern.yaml");
        Files.writeString(invalidYaml, """
            name:
            requestModel: []
        """);
        String configFiles = tempDir.toString();

        String expectedMessage = "Validation data error : Name is invalid";

        BusinessException exception = assertThrows(BusinessException.class, () ->
                patternModelConfig.configModels(configFiles));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void configModelsFailInvalidRequestModel() throws IOException {
        Path invalidYaml = tempDir.resolve("invalid-pattern.yaml");
        Files.writeString(invalidYaml, """
            name: InvalidPattern
            requestModel: []
        """);
        String configFiles = tempDir.toString();

        String expectedMessage = "Validation data error : Request model is invalid";

        BusinessException exception = assertThrows(BusinessException.class, () ->
                patternModelConfig.configModels(configFiles));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
