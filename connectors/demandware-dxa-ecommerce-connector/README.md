Demandware Connector
=====================

The Demandware connector implements the following E-Commerce Framework capabilities:

* Product Categories
* Facets
* Product Lister
* Breadcrumb
* Product Detail
* Cart

The connector has been verified against Demandware OCAPI 16.1 and 16.2

Configuration
---------------

To configure the connector you need to have the following properties in your Spring config or external application config.
Example of a application.properties file:

```
# Demandware Application Configuration
#

demandware.url = [Demandware API base URL, e.g. http://something.demandware.net/s/]
demandware.clientId = [API client ID]
demandware.overriddenOrigin=[Optional if origin header needs to be overriden due to Demandware settings]
demandware.trustAllSSLCerts=true
demandware.categoryExpiryTimeout = 600000
```