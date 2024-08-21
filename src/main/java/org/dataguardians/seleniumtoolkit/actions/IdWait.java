package org.dataguardians.seleniumtoolkit.actions;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Class to represent a wait for content
 */
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class IdWait extends PageAction{

    /**
     * The id of the element
     */
    public String id;

    /**
     * The duration in milliseconds
     */
    @Builder.Default
    Long durationMs = 10000L;

    /**
     * Perform the action, in this case wait for the element to be visible.
     * @param container the search context
     * @param driver the web driver
     */
    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        if (null != id && isVariable(id)){
            id = getVariableValue(id,id);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(durationMs)); // seconds
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }
}
