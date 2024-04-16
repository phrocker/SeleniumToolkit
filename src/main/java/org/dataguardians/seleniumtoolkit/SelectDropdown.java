package org.dataguardians.seleniumtoolkit;


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
import org.openqa.selenium.support.ui.Select;

import java.util.List;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class SelectDropdown extends PageAction{


    public String id;

    @Builder.Default
    public String type = "id";

    @Builder.Default
    public String selectString = "";

    @Override
    public void performAction(SearchContext container, WebDriver driver) {

        if ("id".equals(type) && null != driver.findElement(By.id(id))) {
            Select selector = new Select(driver.findElement(By.id(id)));
            selector.selectByVisibleText(selectString);
        } else if ("name".equals(type) && null != driver.findElement(By.name(id))) {
            Select selector = new Select(driver.findElement(By.name(id)));
            selector.selectByVisibleText(selectString);
        } else {
            List<WebElement> elements = driver.findElements(By.className(id));
            for(var element : elements){
                if (element.getText().contains(selectString)){
                    log.debug("SelectDropdown " + element.getText());
                    Select selector = new Select(element);
                    selector.selectByVisibleText(selectString);
                    break;
                }

            }
        }

    }
}
