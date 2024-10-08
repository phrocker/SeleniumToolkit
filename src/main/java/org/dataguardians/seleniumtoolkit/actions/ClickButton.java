package org.dataguardians.seleniumtoolkit.actions;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Class to represent a click button
 */
@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class ClickButton extends PageAction{


    /**
     * The name of the button
     */
    public String buttonName;

    /**
     * The type of the button
     */
    @Builder.Default
    public String type = "id";

    /**
     * The text to check for
     */
    @Builder.Default
    public String withText = "";

    /**
     * Perform the action
     * @param container the search context
     * @param driver the web driver
     */
    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        if (isVariable(buttonName)) {
            buttonName = getVariableValue(buttonName,"");
        }

        if ("id".equals(type) && null != driver.findElement(By.id(buttonName))) {
            if (checkState( driver.findElement(By.id(buttonName))) ) {
                driver.findElement(By.id(buttonName)).click();
            }
        } else {
            List<WebElement> elements = driver.findElements(By.className(buttonName));
            for(var element : elements){
                if (element.getText().contains(withText)){
                    log.debug("ClickButton " + element.getText());
                    if (checkState(element)) {
                        element.click();
                    }
                    break;
                }

            }
        }

    }

    /**
     * Check the state of the element
     * @param element the element
     * @return true if the element is in the correct state
     */
    protected boolean checkState(WebElement element){
        return true;
    }
}
