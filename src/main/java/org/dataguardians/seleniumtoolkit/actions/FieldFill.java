package org.dataguardians.seleniumtoolkit.actions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Class to represent a field fill
 */
@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class FieldFill extends PageAction{

    public String field;
    public String value;

    /**
     * Fill the field with the value.
     * @param container the search context
     * @param driver the web driver
     */
    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        log.debug("action on " + field);
        WebElement usernameField = container.findElement(By.name(field));
        log.debug(usernameField.getText());
        log.debug(container.toString());
        clearAndType(usernameField, value);
    }

    public void setValue(String value ) {
        this.value = value;
    }
    public void setValue(Integer value) {
        this.value = value.toString();
    }
}
