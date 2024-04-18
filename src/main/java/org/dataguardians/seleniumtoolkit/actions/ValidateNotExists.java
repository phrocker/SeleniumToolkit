package org.dataguardians.seleniumtoolkit.actions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.WebDriver;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class ValidateNotExists extends PageValidation{

    public String text;

    private boolean lastFound = false;

    @Override
    public boolean validate(WebDriver driver) {
        lastFound = !driver.getPageSource().contains(text);
        return lastFound;
    }


    @Override
    public boolean fail() {
        if (stopOnFailure)
            return lastFound;
        else
            return !lastFound;
    }
}
