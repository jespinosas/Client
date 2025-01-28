package co.com.bancolombia.config;

import co.com.bancolombia.peq.EquivalenceParameterizer;
import co.com.bancolombia.validationandhomologationservica.peq.PEQServiceDisabled;
import co.com.bancolombia.validationandhomologationservica.peq.PEQServiceEnabled;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQConf;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQConfigProperties;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PEQConfig {
    @Bean
    public PEQConf peqConf() {
        return new PEQConf();
    }

    @Bean
    public EquivalenceParameterizer equivalenceParameterizer(
            @Value("${adapter.peq.url}") String url,
            @Value("${adapter.peq.cache.expireAfter}") int expireAfter,
            @Value("${adapter.peq.timeout}") int timeout,
            PEQConf peqConf) {
        return peqConf.createEquivalenceParameterizer(url, expireAfter, timeout);
    }

    @Bean
    @ConfigurationProperties(prefix = "adapter.peq.header")
    public PEQConfigProperties peqConfigProperties() {
        return new PEQConfigProperties();
    }

    @Bean
    public PEQService peqCaseAllowed(EquivalenceParameterizer equivalenceParameterizer,
                                     PEQConfigProperties properties) {

        return new PEQServiceEnabled(equivalenceParameterizer,properties);
    }


   /* public PEQService peqCaseNotAllowed() {

        return new PEQServiceDisabled();
    }*/
}
