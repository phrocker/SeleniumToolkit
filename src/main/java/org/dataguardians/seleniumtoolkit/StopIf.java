package org.dataguardians.seleniumtoolkit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class StopIf extends PageValidation{

    @NonNull
    String textContains;


    @NonNull
    String textNotContains;

    @Builder.Default
    Boolean fail = true;

    @Override
    public boolean validate(WebDriver driver) {
        if (StringUtils.isNotBlank(textContains) ) {
            log.debug("Checking if " + textContains + " exists");
            if (driver.getPageSource().contains(textContains)) {
                return false;
            }
        }

        if (StringUtils.isNotBlank(textNotContains) ) {
            log.debug("Checking if " + textNotContains + " does not exist");
            if (!driver.getPageSource().contains(textNotContains)) {
                return false;
            }
        }

        return true;
    }

    public boolean fail() {
        return fail;
    }

    public boolean stop() {
        return !fail;
    }
}
