package org.dataguardians.seleniumtoolkit.actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public abstract class PageAction extends PageExecutable {

    String modalContainer;
    public abstract void performAction(SearchContext container, WebDriver driver);


    protected void clearAndType(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    public void performAction(WebDriver driver) {
        performAction(driver,driver);
    }

}
