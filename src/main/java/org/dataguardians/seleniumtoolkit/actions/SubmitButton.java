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
public class SubmitButton extends PageAction{


    public String buttonName;

    @Builder.Default
    public String type = "id";

    @Builder.Default
    public String withText = "";

    @Override
    public void performAction(SearchContext container, WebDriver driver) {

        if ("id".equals(type) && null != driver.findElement(By.id(buttonName))) {
            driver.findElement(By.id(buttonName)).submit();
        } else {
            List<WebElement> elements = driver.findElements(By.className(buttonName));
            for(var element : elements){
                if (element.getText().contains(withText)){
                    element.submit();
                }
                log.debug("SubmitButton " + element.getText());
            }
        }

    }
}
