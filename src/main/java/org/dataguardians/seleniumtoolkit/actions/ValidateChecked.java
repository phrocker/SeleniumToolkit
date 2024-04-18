package org.dataguardians.seleniumtoolkit.actions;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ValidateChecked extends PageValidation{

    public String buttonName;

    @Builder.Default
    public String type = "id";

    @Builder.Default
    public String withText = "";

    @Override
    public boolean validate(WebDriver driver) {
        if (isVariable(buttonName)) {
            buttonName = getVariableValue(buttonName,"");
            if (StringUtils.isEmpty(buttonName)){
                return false;
            }
        }
        if ("id".equals(type) && null != driver.findElement(By.id(buttonName))) {
            log.debug("Checking if {} is selected {}", buttonName, driver.findElement(By.id(buttonName)).isSelected());
            return driver.findElement(By.id(buttonName)).isSelected();
        } else {
            boolean checked = false;
            List<WebElement> elements = driver.findElements(By.className(buttonName));
            for(var element : elements){
                if (element.getText().contains(withText)){
                    log.debug("ClickButton " + element.getText());
                    if ( element.isSelected() ) {
                        checked = true;
                        break;
                    }
                }

            }
            return checked;
        }
    }
}
