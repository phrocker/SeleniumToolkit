package org.dataguardians.seleniumtoolkit.actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Abstract Class to represent a page action
 */
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public abstract class PageAction extends PageExecutable {

    /**
     * The modal container
     */
    String modalContainer;

    /**
     * Perform the action
     * @param container the search context
     * @param driver the web driver
     */
    public abstract void performAction(SearchContext container, WebDriver driver);


    /**
     * Clear and type text into a field
     * @param field the field
     * @param text the text
     */
    protected void clearAndType(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    /**
     * Perform the action
     * @param driver the web driver
     */
    public void performAction(WebDriver driver) {
        performAction(driver,driver);
    }

}
