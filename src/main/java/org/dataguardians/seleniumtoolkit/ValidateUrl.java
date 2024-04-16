package org.dataguardians.seleniumtoolkit;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ValidateUrl extends PageValidation{

    public String url;

    @Override
    public boolean validate(WebDriver driver) {
        log.debug("validating " + driver.getCurrentUrl() + " " + url);
        return driver.getCurrentUrl().contains(url);
    }
}
