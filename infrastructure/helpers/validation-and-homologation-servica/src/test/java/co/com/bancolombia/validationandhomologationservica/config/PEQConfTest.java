package co.com.bancolombia.validationandhomologationservica.config;

import co.com.bancolombia.validationandhomologationservica.peq.config.PEQConf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PEQConfTest {

    PEQConf peqConf;

    @BeforeEach
    void setUp() {

        peqConf = new PEQConf();

    }

    @Test
    void createEquivalenceParameterizer() {

        assertNotNull(
                peqConf.createEquivalenceParameterizer
                        ("url",3600, 5000)
        );



    }
}
