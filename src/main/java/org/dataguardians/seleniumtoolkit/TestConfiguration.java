package org.dataguardians.seleniumtoolkit;


import lombok.extern.slf4j.Slf4j;
import org.dataguardians.seleniumtoolkit.actions.PageExecutable;
import org.dataguardians.seleniumtoolkit.actions.capabilities.Variables;
import org.dataguardians.seleniumtoolkit.actions.configuration.PageExecutions;
import org.yaml.snakeyaml.Yaml;


import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TestConfiguration {

    Map<String, PageExecutions> jobs = new HashMap<>();

    List<String> order = new ArrayList<>();

    Map<String,String> variables = new HashMap<>();


    public static TestConfiguration yamlParser(InputStream inputStream){
            Yaml yaml = new Yaml();

            HashMap<String,Object> yamlMap = yaml.load(inputStream);

            TestConfiguration configuration = new TestConfiguration();

            List<String> excludedKeys = new ArrayList<>();
            excludedKeys.add("RunOrder");
            excludedKeys.add("variables");
                    Set<String> testNames = yamlMap.keySet().stream().filter(name -> !excludedKeys.contains(name)).collect(Collectors.toSet());
                    Map<String, PageExecutions> pageExecutions = new HashMap<>();
                    if (yamlMap.containsKey("RunOrder")){
                        configuration.order = yamlMap.get("RunOrder") instanceof ArrayList ? (ArrayList) yamlMap.get("RunOrder") : new ArrayList<String>();
                     //   configuration.order = configuration.order.stream().collect(Collectors.toList());
                    }
                    if (yamlMap.containsKey("variables")){
                        configuration.variables = yamlMap.get("variables") instanceof LinkedHashMap ? (LinkedHashMap) yamlMap.get("variables") : new LinkedHashMap<String, String>();
                        //   configuration.order = configuration.order.stream().collect(Collectors.toList());
                    }
                    for(String testName : testNames) {

                        log.info("Test name: " + testName);
                        LinkedHashMap<String, Object> testMap = (LinkedHashMap) yamlMap.get(testName);

                        if (testMap.containsKey("actions")){
                            var actions = testMap.get("actions");
                            if (actions instanceof ArrayList) {
                                ArrayList<Object> actionList = (ArrayList) actions;
                                PageExecutions.PageExecutionsBuilder peBuilder = PageExecutions.builder();
                                List<PageExecutable> pageActions = new ArrayList<>();
                                for(Object action : actionList){
                                    if (action instanceof LinkedHashMap){
                                        Map<String,Object> actionMap = (LinkedHashMap) action;
                                        for(String key : actionMap.keySet()){
                                            try {

                                                System.out.println("key is " + key);
                                                Class<?> paClazz = Class.forName("org.dataguardians.seleniumtoolkit.actions." + key);
                                                Object actionObj = paClazz.getConstructor().newInstance();
                                                Map<String,Object> options = (LinkedHashMap<String, Object>) actionMap.get(key);
                                                if (null != options ) {
                                                    for (String optionKey : options.keySet()) {
                                                        var value = options.get(optionKey);
                                                        if (value instanceof String) {
                                                            value = Variables.replaceVariables(value.toString(),configuration.variables);
                                                        }
                                                        actionObj.getClass().getMethod("set" + optionKey.substring(0, 1).toUpperCase() + optionKey.substring(1), options.get(optionKey).getClass()).invoke(actionObj, value);
                                                    }
                                                }
                                                pageActions.add((PageExecutable) actionObj);
                                            } catch (ClassNotFoundException e) {
                                                throw new RuntimeException(e);
                                            } catch (InvocationTargetException e) {
                                                throw new RuntimeException(e);
                                            } catch (InstantiationException e) {
                                                throw new RuntimeException(e);
                                            } catch (IllegalAccessException e) {
                                                throw new RuntimeException(e);
                                            } catch (NoSuchMethodException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }

                                }


                                String startUrl = "";
                                if (testMap.containsKey("startUrl")){
                                    startUrl = testMap.get("startUrl").toString();
                                    startUrl = Variables.replaceVariables(startUrl,configuration.variables);
                                }

                                String stopUrl = "";
                                if (testMap.containsKey("stopUrl")){
                                    stopUrl = testMap.get("stopUrl").toString();
                                    stopUrl = Variables.replaceVariables(stopUrl,configuration.variables);
                                }

                                pageExecutions.put(testName, peBuilder.actions(pageActions).startUrl(startUrl).stopUrl(stopUrl).name(testName).build());
                            }
                        }
                    }

            for(String testName : testNames) {

                LinkedHashMap<String, Object> testMap = (LinkedHashMap) yamlMap.get(testName);

                if (testMap.containsKey("dependsOn")) {
                    var dependencies = testMap.get("dependsOn");
                    PageExecutions pe = pageExecutions.get(testName);

                    if (dependencies instanceof ArrayList) {
                        ArrayList<String> dependencyList = (ArrayList) dependencies;
                        List<PageExecutions> pageExecutionsList = new ArrayList<>();
                        for(String name : dependencyList){
                            PageExecutions otherPe = pageExecutions.get(name);
                            pageExecutionsList.add(otherPe);
                        }
                        pe.setDependsOn(pageExecutionsList);
                    }
                }
            }

            for(Map.Entry<String,PageExecutions> pe : pageExecutions.entrySet()){
                configuration.jobs.put(pe.getKey(), pe.getValue());
            }

            return configuration;
        }
}

