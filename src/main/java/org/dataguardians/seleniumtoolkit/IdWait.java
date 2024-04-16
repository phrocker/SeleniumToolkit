package org.dataguardians.seleniumtoolkit;


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

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class IdWait extends PageAction{

    public String id;

    @Builder.Default
    Long durationMs = 10000L;

    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        if (null != id && isVariable(id)){
            id = getVariableValue(id,id);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(durationMs)); // seconds
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }
}
