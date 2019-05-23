package com.davidwang.githubsearch;

public class Repository {
    private String name;
    private String description;
    private String language;
//    public String avatarImage;            // Note: this is an URL
//    public String favoriteImage;

    public Repository() {
        name = "";
        description = "";
        language = "";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }
}
