package com.sdl.ecommerce.odata.client;

import com.sdl.odata.client.BasicODataClientQuery;

/**
 * ECommerceODataClientQuery
 *
 * @author nic
 */
public class ECommerceODataClientQuery extends BasicODataClientQuery {

    private String property;

    public ECommerceODataClientQuery(Builder builder) {
        super(builder);
        this.property = builder.property;
    }

    @Override
    public String getQuery() {
        String query = super.getQuery();
        if ( this.property != null ) {
            query = query + "/" + this.property;
        }
        return query;
    }

    public String getSelectedProperty() {
        return this.property;
    }

    public static class Builder extends BasicODataClientQuery.Builder {
        private String property;

        public Builder withProperty(String property) {
            this.property = property;
            return this;
        }

        public BasicODataClientQuery build() {
            return new ECommerceODataClientQuery(this);
        }
    }

}
