package com.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
public class Story {
    public Integer sid;
    public String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer count;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String audio;
}
