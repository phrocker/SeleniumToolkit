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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ValidateTerminal extends PageValidation{


    public String text;

    @Builder.Default
    Integer wait = 0;

    @Builder.Default
    public String regex = "";

    private boolean found;
    @Override
    public boolean validate(WebDriver driver) {
        var buttonName = "xterm-rows";

        found = false;
        Pattern pattern = Pattern.compile(regex);

        List<WebElement> elements = driver.findElements(By.className(buttonName));
        do {
            for (var element : elements) {
              if (StringUtils.isNotEmpty(text)) {
                    if (element.getText().contains(text)) {
                        log.debug("checking {}", element.getText());
                        found = true;
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(regex)) {
                    Matcher matcher = pattern.matcher(element.getText());
                    if (matcher.find()) {
                        log.debug(" * ** matches {}", element.getText());
                        found = true;
                        break;
                    } else {
                        log.debug("Did not match " + element.getText() + " to " + regex);
                    }
                }
            }
            if (wait > 0) {
                try {
                    log.debug("Waiting for " + text + " " + regex + " " + wait + "ms");
                    Thread.sleep(50);
                    wait -= 50;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }while (!found && wait > 0);
        return found;
        }


    @Override
    public boolean fail() {
        if (stopOnFailure)
            return !found;
        else
            return false;
    }
}
