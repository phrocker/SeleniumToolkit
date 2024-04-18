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
import org.openqa.selenium.interactions.Actions;

import java.util.List;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class SendText extends PageAction{


    public String buttonName;

    @Builder.Default
    public String type = "id";

    @Builder.Default
    public String text = "";

    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        log.trace("button name is " + buttonName);
        if (isVariable(buttonName)) {
            log.trace("36");
            buttonName = getVariableValue(buttonName,"");
        }


        if ("id".equals(type) && null != driver.findElement(By.id(buttonName))) {
            log.trace("Clicking " + buttonName + " on " + driver.getCurrentUrl());
            driver.findElement(By.id(buttonName)).sendKeys(text );

        } else {
            log.trace("aww yeah'");

            List<WebElement> elements = driver.findElements(By.className(buttonName));
            for(var element : elements){
                //if (element.getText().contains(withText)){
                    log.debug("ClickButton " + element.getText());
                log.trace("ClickButton " + element.getText());
                    //element
                log.trace(element.getText());
                try {
                    var action =new Actions(driver);
                    action.moveToElement(element).click().perform();
                    action.sendKeys(text + "\r\n").perform();
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }

                //}

            }
        }

    }
}
