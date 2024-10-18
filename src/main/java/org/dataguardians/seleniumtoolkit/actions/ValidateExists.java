package org.dataguardians.seleniumtoolkit.actions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class ValidateExists extends PageValidation{

    public String text;

    @Override
    public boolean validate(WebDriver driver) {
        var result = driver.getPageSource().contains(text);
        while(!result && retryCount > 0){
            try {
                Thread.sleep(retryDelay);
            } catch (InterruptedException e) {
            }
            result = driver.getPageSource().contains(text);
            retryCount--;
            log.debug("retrying " + retryCount);
        }
        return result;
    }
}
