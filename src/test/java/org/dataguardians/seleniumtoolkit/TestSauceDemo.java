package org.dataguardians.seleniumtoolkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSauceDemo {


    @Test
    public void runLogin() throws URISyntaxException {

        var resourceDirUrl = Thread.currentThread().getContextClassLoader().getResource("saucedemo/TestLogin.yml");

        // Convert URL to a Path
        Path resourceDirPath = Paths.get(resourceDirUrl.toURI());

        SeleniumTests.testTestFile(resourceDirPath,true).forEach( test -> assertTrue(test.getValue(), "Entry " + test.getKey() + " failed"));

    }

}
