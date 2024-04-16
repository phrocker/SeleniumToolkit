package org.dataguardians.seleniumtoolkit;

import lombok.Builder;
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
public abstract class PageValidation extends PageExecutable {

    @Builder.Default
    protected Boolean stopOnFailure = false;

    public abstract boolean validate(WebDriver driver);

    public boolean fail() {
        return false;
    }

    public boolean stop() {
        return false;
    }

    public String getConditions(){
        return toString();
    }

}
