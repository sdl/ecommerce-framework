package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.hybris.api.model.Price;

/**
 * HybrisPrice
 *
 * @author nic
 */
public class HybrisPrice implements ProductPrice {

    private Price hybrisPrice;

    public HybrisPrice(Price hybrisPrice) {
        this.hybrisPrice = hybrisPrice;
    }

    @Override
    public String getCurrency() {
        return this.hybrisPrice.getCurrencyIso();
    }

    @Override
    public float getPrice() {
        return Float.parseFloat(this.hybrisPrice.getValue());
    }

    @Override
    public String getFormattedPrice() {
        return this.hybrisPrice.getFormattedValue();
    }
}
