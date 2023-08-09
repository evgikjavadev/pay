package ru.vtb.msa.rfrm.integration.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorCounter {
    private final String name;
    private final String description;
    private final String dynamicTagName;
    private final MeterRegistry registry;
    private final String classNameValue;
    private final String requestMethodValue;
    private final Map<String, Counter> counters = new HashMap<>();

    public ErrorCounter(String name, String description, String dynamicTagName,
                        MeterRegistry registry, String className, String requestMethod) {
        this.name = name;
        this.description = description;
        this.dynamicTagName = dynamicTagName;
        this.registry = registry;
        this.classNameValue = className;
        this.requestMethodValue = requestMethod;
    }

    public void increment(String dynamicTagValue) {
        Counter counter = counters.get(dynamicTagValue);
        if (counter == null) {
            counter = Counter.builder(name).description(description).tag(dynamicTagName, dynamicTagValue)
                    .tag("className", classNameValue).tag("requestMethod", requestMethodValue).register(registry);
            counters.put(dynamicTagValue, counter);
        }
        counter.increment();
    }
}
