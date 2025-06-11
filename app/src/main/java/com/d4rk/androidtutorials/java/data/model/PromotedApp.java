package com.d4rk.androidtutorials.java.data.model;

/**
 * Model representing a promoted application fetched from the remote API.
 */
public class PromotedApp {

    public final String name;
    public final String packageName;
    public final String iconUrl;

    public PromotedApp(String name, String packageName, String iconUrl) {
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
    }
}
