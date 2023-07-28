package ru.vtb.msa.rfrm.service;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class BeforeConverIvent extends ApplicationEvent {
    public BeforeConverIvent(Object source) {
        super(source);
    }

    public BeforeConverIvent(Object source, Clock clock) {
        super(source, clock);
    }
}
