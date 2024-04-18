package org.dataguardians.seleniumtoolkit.actions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicBoolean;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class ValidateTag extends PageValidation{

    public String tagName;
    public String textContains;

    @Override
    public boolean validate(WebDriver driver) {
        AtomicBoolean containsText = new AtomicBoolean(false);
        driver.findElements(By.tagName(tagName)).forEach(webElement -> {
            if ( webElement.getText().toLowerCase().contains(textContains.toLowerCase()) ) {
                containsText.set(true);
            }
        });
        return containsText.get();
    }
}
