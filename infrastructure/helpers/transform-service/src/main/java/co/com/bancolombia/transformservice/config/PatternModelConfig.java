package co.com.bancolombia.transformservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class PatternModelConfig {

    private static final int MAX_DEEP = 3;

    @Bean
    public Map<String, PatternModel> configModels(
            @Value("${plain-text-pattern.config.files}") String path) throws IOException{

        var mapper = new ObjectMapper(new YAMLFactory())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (Stream<Path> stream = Files.walk(Paths.get(path), MAX_DEEP)){
            return stream
                    .parallel()
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".yaml"))
                    .map(p -> getConfigModel(p, mapper))
                    .collect(Collectors.toUnmodifiableMap(PatternModel::name, Function.identity()));
        }
    }

    @SneakyThrows
    private static PatternModel getConfigModel(Path path, ObjectMapper mapper) {

        PatternModel rawModel = mapper.readValue(Files.readAllBytes(path), PatternModel.class);
        System.out.println("map: " + mapper.readValue(Files.readAllBytes(path), PatternModel.class));
        System.out.println("Reading config file: " + path.getFileName());
        System.out.println("Raw model: " + rawModel.name());
        System.out.println("Raw model: " + rawModel.request());
        System.out.println("Raw model: " + rawModel.requestModel());
        // Validación explícita
        return PatternModel.createValid(
                rawModel.name(),
                rawModel.request(),
                rawModel.requestModel()
        );
    }
}
