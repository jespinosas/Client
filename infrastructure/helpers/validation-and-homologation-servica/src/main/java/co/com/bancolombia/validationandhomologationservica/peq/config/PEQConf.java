package co.com.bancolombia.validationandhomologationservica.peq.config;

import co.com.bancolombia.peq.EquivalenceParameterizer;
import co.com.bancolombia.peq.config.EquivalenceConfig;

public class PEQConf {

    public EquivalenceParameterizer createEquivalenceParameterizer(String url, int expireAfter, int timeout) {
        return new EquivalenceParameterizer(EquivalenceConfig.builder()
                .url(url)
                .cacheExpireAfter(expireAfter)
                .timeout(timeout)
                .build());
    }
}
