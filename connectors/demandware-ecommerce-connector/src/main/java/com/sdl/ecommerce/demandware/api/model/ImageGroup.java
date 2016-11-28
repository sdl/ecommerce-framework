package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * ImageGroup
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageGroup {

    private List<Image> images;
    private String variation_value;
    private String view_type;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getVariation_value() {
        return variation_value;
    }

    public void setVariation_value(String variation_value) {
        this.variation_value = variation_value;
    }

    public String getView_type() {
        return view_type;
    }

    public void setView_type(String view_type) {
        this.view_type = view_type;
    }
}
