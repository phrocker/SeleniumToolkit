package org.dataguardians.seleniumtoolkit;


import com.google.common.base.Joiner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class NavigateUrl extends PageAction{

    public String baseurl;

    @Builder.Default
    public LinkedHashMap<String,String> parameterNames = new LinkedHashMap<>();

    @Builder.Default
    Boolean includeCSRF=true;

    @Override
    public void performAction(SearchContext container, WebDriver driver) {
        String url = driver.getCurrentUrl();
        List<String> parameters = new ArrayList<>();
        try {
            if (includeCSRF) {
                List<NameValuePair> keyvaluePairs = URLEncodedUtils.parse(new URI(url), StandardCharsets.UTF_8);
                System.out.println("current url is " + url);
                for (NameValuePair nvp : keyvaluePairs) {
                    log.debug(nvp.getName() + " " + nvp.getValue());
                    System.out.println(nvp.getName() + " " + nvp.getValue());
                    if ("_csrf".equalsIgnoreCase(nvp.getName())){
                        parameters.add("_csrf=" + nvp.getValue());
                        break;
                    }
                }

            }
            for(Map.Entry<String,String> parameter  : parameterNames.entrySet() ){
                String value = PageMapping.registeredData.get(parameter.getValue());
                if (StringUtils.isNotEmpty(value)) {
                    parameters.add(parameter.getKey() + "=" + value);
                }
            }
            String newurl = baseurl;
            if (!parameters.isEmpty()) {
                newurl += "?" + Joiner.on('&').join(parameters);
            }
            log.trace("Navigating to " + newurl);
            System.out.println("Navigating to " + newurl);
            driver.get(newurl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
