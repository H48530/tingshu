package com.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 */
public class Album {
    public Integer aid;
    public String name;
    public Integer uid;
    public String cover;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Story> storyList;
}
