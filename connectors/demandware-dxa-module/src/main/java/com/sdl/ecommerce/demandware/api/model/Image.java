package com.sdl.ecommerce.demandware.api.model;

/**
 * Image
 *
 * @author nic
 */
public class Image {

    private String link;
    private String title;
    private String alt;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
