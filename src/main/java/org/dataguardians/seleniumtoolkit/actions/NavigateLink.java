package org.dataguardians.seleniumtoolkit.actions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Class to represent a navigate link
 */
@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NavigateLink extends PageAction{

    /**
     * The partial URL
     */
    public String partialUrl;

    /**
     * The id of the element
     */
    public String id;

    /**
     * The duration in milliseconds
     */
    Integer waitMs = 125;

    /**
     * Navigate to the partial URL
     * @param container the search context
     * @param driver the web driver
     */
    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        if (StringUtils.isNotBlank(partialUrl)) {
            List<WebElement> elements = driver.findElements(By.xpath("//a[contains(@href,'" + partialUrl + "')]"));
            log.debug("Navigating to partialUrl " + partialUrl + " " + elements.size());
            if (elements.size() == 1) {
                log.trace("clicking " + elements.get(0).getText());
                elements.get(0).click();
            } else {
                boolean clicked = findClicked(elements);
                if (!clicked) {
                    /**
                     * Institute a wait. If we are in headless mode it may take some time for elements
                     * to render. 
                     */
                    try {
                        log.debug("Could not click {}, waiting {} ms", partialUrl, waitMs);
                        Thread.sleep(waitMs);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    clicked = findClicked(elements);
                    if (!clicked) {
                        throw new RuntimeException("Unable to click " + partialUrl);
                    }
                }
            }
        }
        else if (StringUtils.isNotEmpty(id)){
            List<WebElement> elements = driver.findElements(By.xpath("//a[contains(@id,'" + id + "')]"));
            log.debug("Navigating to partialUrl " + id + " " + elements.size());
            if (elements.size() == 1) {
                log.trace("clicking " + elements.get(0).getText());
                elements.get(0).click();
            } else {
                boolean clicked = findClicked(elements);
                if (!clicked) {
                    /**
                     * Institute a wait. If we are in headless mode it may take some time for elements
                     * to render.
                     */
                    try {
                        log.debug("Could not click {}, waiting {} ms", id, waitMs);
                        Thread.sleep(waitMs);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    clicked = findClicked(elements);
                    if (!clicked) {
                        throw new RuntimeException("Unable to click " + partialUrl);
                    }
                }
            }
        }
    }

        boolean findClicked(List<WebElement> elements) {
            boolean clicked = false;
            for(var element : elements) {
                if (!element.getText().isEmpty()) {
                    log.trace("clicking " + element.getText());
                    try
                    {
                        element.click();
                        clicked = true;
                        break;
                    }catch(Exception e){
                        log.error("Error clicking " + element.getText(),e);
                    }
                } else {
                    log.debug("Not clicking link without text " + element.getAttribute("href"));
                }
            }
            return clicked;
        }
}
