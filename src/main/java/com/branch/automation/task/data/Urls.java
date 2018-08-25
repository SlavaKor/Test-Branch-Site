package com.branch.automation.task.data;

/**
 * Urls used for testing are stored in this enum
 */
public enum Urls {
    GOOGLE_SEARCH("http://www.google.com"),
    BRANCH_IO("http://www.branch.io");

    private String url;

    Urls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
