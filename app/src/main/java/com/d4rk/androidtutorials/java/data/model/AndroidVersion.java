package com.d4rk.androidtutorials.java.data.model;

public class AndroidVersion {
    public final String version;
    public final String api;
    public final String codeName;
    public final String codenameLiteral;
    public final String year;

    public AndroidVersion(String version, String api, String codeName, String codenameLiteral, String year) {
        this.version = version;
        this.api = api;
        this.codeName = codeName;
        this.codenameLiteral = codenameLiteral;
        this.year = year;
    }
}