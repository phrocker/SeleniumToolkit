package org.dataguardians.seleniumtoolkit.actions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class SendTerminal extends SendText{



    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        type = "class";
        buttonName = "xterm-rows";
        super.performAction(container,driver);
    }
}
