package ru.vtb.msa.rfrm.integration.personcs.entity;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;


@Builder
@Getter
public class Priority implements Comparable {
    private int priority;
    private String cod;
    private String name;

    @Override
    public int compareTo(@NotNull Object o) {
        return getPriority() > ((Priority)o).getPriority() ? 1 : -1 ;
    }
}
