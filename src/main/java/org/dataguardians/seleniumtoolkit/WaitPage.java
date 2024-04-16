package org.dataguardians.seleniumtoolkit;


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
public class WaitPage extends PageAction{

    @Override
    public void performAction(SearchContext container, WebDriver driver) {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
