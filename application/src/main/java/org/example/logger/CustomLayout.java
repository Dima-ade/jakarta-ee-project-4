package org.example.logger;

import ch.qos.logback.classic.pattern.DateConverter;

public class CustomLayout extends ch.qos.logback.classic.PatternLayout{

    public static final String applicationVersion = "v1.0";

    public CustomLayout() {
        DEFAULT_CONVERTER_MAP.put("version", applicationVersion);
    }
}
