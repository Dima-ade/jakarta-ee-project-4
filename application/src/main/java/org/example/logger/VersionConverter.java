package org.example.logger;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class VersionConverter extends ClassicConverter {
    public VersionConverter() {
    }

    @Override
    public String convert(ILoggingEvent event) {
        //return "versionToAdd";
        return event.getLoggerContextVO().getPropertyMap().get("build-version");
//        return event.getLoggerContextVO().getPropertyMap().get("application.version");
//        return event.getLoggerContextVO().getPropertyMap().get("logversion");
    }
}