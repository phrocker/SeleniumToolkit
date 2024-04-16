package org.dataguardians.seleniumtoolkit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TableExtractor extends PageMapping {


    String tableContains;

    String idMatch;

    @Builder.Default
    String attribute = "id";

    @NonNull
    String extractionRegex;


    Integer extractionGroup;

    String extractionName;

    ArrayList<Integer> extractionGroups;

    ArrayList<String> extractionNames;

    // find table in which we'll perform a search.
    @Override
    public void performSearch(SearchContext container, WebDriver driver) {
        // find a string in a table. Then look for a string or xpath within that table.

        if (null != extractionGroups) {
            if (null != extractionNames) {
                if (extractionGroups.size() != extractionNames.size()) {
                    throw new IllegalArgumentException("extractionGroups and extractionNames must be the same size");
                }
                performMultiSearch(container,driver);

            }
        }
        else {
            log.debug("tableContains: " + tableContains);
            List<WebElement> allRows = driver.findElements(By.tagName("tr"));
            for (var row : allRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for (var cell : cells) {
                    if (cell.getText().contains(tableContains)) {
                        log.trace(cell.getText());
                        List<WebElement> elements = row.findElements(By.xpath(".//*[contains(@id, '" + idMatch + "')]"));

                        log.debug("elements " + elements.size());
                        for (var element : elements) {
                            log.debug(element.getCssValue("id"));
                            var rowtext = row.getText();
                            var celltext = cell.getText();
                            var etext = element.getText();
                            if (null != attribute) {
                                String attributeToExtract = element.getAttribute(attribute);
                                if (StringUtils.isNotEmpty(attributeToExtract) && null != extractionRegex) {
                                    Pattern pattern = Pattern.compile(extractionRegex);
                                    var matcher = pattern.matcher(attributeToExtract);
                                    if (matcher.find()) {
                                        attributeToExtract = matcher.group(extractionGroup);
                                        if (null != attributeToExtract) {
                                            attributeToExtract = attributeToExtract.trim();
                                            registeredData.put(extractionName, attributeToExtract);
                                            log.trace("Setting " + extractionName + " to " + attributeToExtract);
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                        break;
                    }
                }
            }
        }
    }

    private void performMultiSearch(SearchContext container, WebDriver driver) {
        log.debug("performMultiSearch tableContains: " + tableContains);
        List<WebElement> allRows = driver.findElements(By.tagName("tr"));
        for (var row : allRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (var cell : cells) {
                if (cell.getText().contains(tableContains)) {
                    log.trace(cell.getText());
                    List<WebElement> elements = row.findElements(By.xpath(".//*[contains(@id, '" + idMatch + "')]"));

                    log.debug("elements " + elements.size());
                    for (var element : elements) {
                        log.debug(element.getCssValue("id"));
                        var rowtext = row.getText();
                        var celltext = cell.getText();
                        var etext = element.getText();
                        if (null != attribute) {
                            String attributeToExtract = element.getAttribute(attribute);
                            if (StringUtils.isNotEmpty(attributeToExtract) && null != extractionRegex) {
                                Pattern pattern = Pattern.compile(extractionRegex);
                                var matcher = pattern.matcher(attributeToExtract);
                                if (matcher.find()) {
                                    for(int i=0; i < extractionGroups.size(); i++){
                                        attributeToExtract = matcher.group(extractionGroups.get(i));
                                        if (null != attributeToExtract) {
                                            attributeToExtract = attributeToExtract.trim();
                                            registeredData.put(extractionNames.get(i), attributeToExtract);
                                            log.trace("Setting " + extractionNames.get(i) + " to " + attributeToExtract);
                                        }
                                    }
                                }
                            }
                        }

                    }
                    break;
                }
            }
        }
    }

    private void extractFromRow(){

    }

    public boolean validateConfiguration() {
        if (null == extractionGroup ) {
                if (null == extractionGroups){
                    return false;
                }
                if (null == extractionNames){
                    return false;
                }
                if (null != extractionName) {
                    return false;
                }

        }else {
            if (null != extractionGroups){
                return false;
            }
            if (null == extractionName){
                return false;
            }
            if (null != extractionNames) {
                return false;
            }
        }
        return true;
    }
}
