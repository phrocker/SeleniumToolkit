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

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class ClickButton extends PageAction{


    public String buttonName;

    @Builder.Default
    public String type = "id";

    @Builder.Default
    public String withText = "";

    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        System.out.println("button name is " + buttonName);
        if (isVariable(buttonName)) {
            System.out.println("36");
            buttonName = getVariableValue(buttonName,"");
        }

        System.out.println("Clicking " + buttonName + " on " + driver.getCurrentUrl());
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

    protected boolean checkState(WebElement element){
        return true;
    }
}
