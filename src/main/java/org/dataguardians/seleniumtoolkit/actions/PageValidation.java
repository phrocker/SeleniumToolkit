package org.dataguardians.seleniumtoolkit.actions;

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

    /**
     * Boolean to indicate we stop on failure.
     */
    @Builder.Default
    protected Boolean stopOnFailure = false;

    @Builder.Default
    protected Integer retryCount = 0;

    @Builder.Default
    protected Integer retryDelay = 0;

    /**
     * Validation base.
     * @param driver web driver.
     * @return boolean if successful.
     */
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
