package org.dataguardians.seleniumtoolkit;

import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dataguardians.seleniumtoolkit.actions.PageAction;
import org.dataguardians.seleniumtoolkit.actions.PageMapping;
import org.dataguardians.seleniumtoolkit.actions.PageValidation;
import org.dataguardians.seleniumtoolkit.actions.configuration.PageExecutions;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class SeleniumTests {


    /**
     * Execute a test
     * @param job the name of the job
     * @param driver the web driver
     * @param pe the page executions
     * @return true if the test passed
     */
    public static boolean execute(String job, @NonNull WebDriver driver, @NonNull PageExecutions pe ){
        if (StringUtils.isNotEmpty(pe.getStartUrl())){
            log.debug("Setting url to " + pe.getStartUrl());
            driver.get(pe.getStartUrl());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(var subPe : pe.getDependsOn()) {
            log.debug("Running dependency:" + subPe.getName());
            execute(job, driver, subPe);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        AtomicBoolean valid = new AtomicBoolean(true);
        for(var action : pe.getActions()){
            log.debug("Running " + action);
                SearchContext container = driver;
                if (action instanceof PageAction) {
                    PageAction pa = (PageAction) action;

                    if (StringUtils.isNotEmpty(pa.getModalContainer())) {

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(pa.getModalContainer())));
                        container = driver.findElement(By.id(pa.getModalContainer()));
                        container = container.findElement(By.xpath(".//div[@class='modal-body']"));

                    }
                    pa.performAction(container, driver);
                } else if (action instanceof PageValidation) {
                    PageValidation pv = (PageValidation) action;
                    boolean result = pv.validate(driver);
                    var conditions = pv.getConditions();
                    valid.set(valid.get() & result);
                    if (!result ){
                        System.out.println("***********************************");
                        System.out.println("Failed " + job);
                        System.out.println("Failed " + result + " " + action.getClass() + " " + conditions);
                        System.out.println("***********************************");
                        if (pv.fail()) {
                            throw new RuntimeException( action + " Failed ");
                        }
                        else if (pv.stop()){
                            log.error("Stopping on " + action);
                            valid.set(true);
                            break;
                        }
                    }
                } else if (action instanceof PageMapping) {
                    PageMapping pv = (PageMapping) action;
                    if (!pv.validateConfiguration()){
                        throw new RuntimeException("Invalid configuration for " + action);
                    }
                    pv.performSearch(driver, driver);
                } else {
                    throw new RuntimeException("Unknown action " + action);
                }
        }

        if (StringUtils.isNotEmpty(pe.getStopUrl())){
            log.debug("Setting stop url to " + pe.getStopUrl());
            driver.get(pe.getStopUrl());
        }

        log.debug(pe.getName() + " is " + (valid.get() ? "valid" : "invalid"));
        return valid.get();
    }

    public static void runTest(WebDriver driver, List<Map.Entry<String,Boolean>> executionResults, Path yamlFile) throws IOException {
        // Open an InputStream for each file
        try (InputStream inputStream = Files.newInputStream(yamlFile)) {
            // Handle the input stream (read content, etc.)
            TestConfiguration test = TestConfiguration.yamlParser(inputStream);
            for(String job : test.order) {

                log.trace("Running " + job);
                PageExecutions pe = test.jobs.get(job);
                try {
                    executionResults.add(Maps.immutableEntry(job,execute(job, driver, pe) ));
                    //executionResults.add( "\t" + job + "\t" + execute(job, driver, pe) );
                } catch (Exception e) {
                    executionResults.add(Maps.immutableEntry(job,false ));
                    //executionResults.add("\t" + job + "\t failed");
                    log.error("Error running " + job, e);
                }
            }
        }
    }

    public static void runTests() throws URISyntaxException {
        var resourceDirUrl = Thread.currentThread().getContextClassLoader().getResource("yaml/");

        // Convert URL to a Path
        Path resourceDirPath = Paths.get(resourceDirUrl.toURI());

        runTestsDirectory(resourceDirPath,true);
    }

    public static void runTestsDirectory(Path yamlDirectory, boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        WebDriver driver = new ChromeDriver(options);

        //Map<String, Boolean> successMetrics = new HashMap<>();
        List<Map.Entry<String,Boolean>> executionResults = new ArrayList<>();
        try {

            // Get the URL of the directory (relative to the classpath)

            // List .yaml files only
            List<Path> yamlFiles = Files.list(yamlDirectory)
                    .filter(path -> path.toString().endsWith(".yaml") || path.toString().endsWith(".yml"))
                    .collect(Collectors.toList());

            // Read each file serially
            yamlFiles.forEach(file -> {
                try {
                    runTest(driver,executionResults,file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            driver.quit();
        }
        executionResults.forEach(entry ->{
            System.out.println("\t" + entry.getKey() + "\t " + entry.getValue());
        });
    }

    public static List<Map.Entry<String,Boolean>> testTestFile(Path testFile, boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        WebDriver driver = new ChromeDriver(options);

        //Map<String, Boolean> successMetrics = new HashMap<>();
        List<Map.Entry<String,Boolean>> executionResults = new ArrayList<>();
        try {

            // Get the URL of the directory (relative to the classpath)

            // Read each file serially

            try {
                runTest(driver,executionResults,testFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            driver.quit();
        }

        return executionResults;

    }

    static {
        var log4jprops = Thread.currentThread().getContextClassLoader().getResource("log4j.properties");

        org.apache.logging.log4j.core.config.Configurator.initialize(null, log4jprops.getPath().toString());

    }

    public static void main(String [] args) throws URISyntaxException {
        runTests();
    }
}
