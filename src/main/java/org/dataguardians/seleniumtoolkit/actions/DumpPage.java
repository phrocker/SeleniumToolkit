package org.dataguardians.seleniumtoolkit.actions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class DumpPage extends PageAction{

    @Override
    public void performAction(SearchContext container, WebDriver driver) {

        System.out.println(driver.getPageSource());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
