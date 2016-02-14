package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Category
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private String id;
    private String parent_category_id;
    private String name;
    private String description;
    private String image;
    private String thumbnail;

    private String page_title;
    private String page_description;
    private String page_keywords;
    private List<Category> categories;
    private List<ProductSearchRefinement> refinements;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(String parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getPage_description() {
        return page_description;
    }

    public void setPage_description(String page_description) {
        this.page_description = page_description;
    }

    public String getPage_keywords() {
        return page_keywords;
    }

    public void setPage_keywords(String page_keywords) {
        this.page_keywords = page_keywords;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<ProductSearchRefinement> getRefinements() {
        return refinements;
    }

    public void setRefinements(List<ProductSearchRefinement> refinements) {
        this.refinements = refinements;
    }
}
