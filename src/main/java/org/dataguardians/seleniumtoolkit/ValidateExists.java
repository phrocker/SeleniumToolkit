package org.dataguardians.seleniumtoolkit;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.WebDriver;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ValidateExists extends PageValidation{

    public String text;

    @Override
    public boolean validate(WebDriver driver) {
//        System.out.println(driver.getPageSource());
        var result = driver.getPageSource().contains(text);
        return result;
    }
}
