package org.dataguardians.seleniumtoolkit.actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.ConcurrentHashMap;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public abstract class PageMapping extends PageExecutable {

    String modalContainer;

    String register;

    public static ConcurrentHashMap<String,String> registeredData = new ConcurrentHashMap<>();

    public abstract void performSearch(SearchContext container, WebDriver driver);


    public boolean validateConfiguration() {
        return true;
    }
}
