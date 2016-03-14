Fredhopper Connector
======================

The Fredhopper connector implements the following capabilities of the E-Commerce Framework:
   
* Product Categories
* Facets
* Product Lister
* Breadcrumb
* Promotions (product, image maps and content promotions)
* Product Detail
* Search spell feedback
* XPM In-Context Edit controls for facets and promotions
* XPM controls on category pages to create new facets, promotions, modifications, rankings, redirects and search synonyms

The connector has been verified against SDL Fredhopper 7.5.x and 8.1.x.

Build
---------

To be able to compile this module you need install the Fredhopper client libraries.
This you can do by executing the `lib/mvn-install.sh` or `lib/mvn-install.bat` scripts which will install these client libraries
into your local Maven repository.

Configuration
---------------

To configure the connector you need to have the following properties in your Spring config or external application config.
Example of a application.properties file:

```
# Fredhopper Application Configuration
#

fredhopper.queryserver.url=[Fredhopper query server URL, e.g. http://localhost:8180]
fredhopper.access.username=[Fredhopper access username - optional]
fredhopper.access.password=[Fredhopper access password - optional]
fredhopper.adminserver.url=[Fredhopper admin server URL, e.g. http://localhost:8180]
fredhopper.admin.username=[Fredhopper admin username]
fredhopper.admin.password=[Fredhopper admin password]
fredhopper.imageurl.mappings = [Optional, if mapping of Fredhopper image URLs to alternative URLs, e.g. http://demo.fredhopper.com/images=/fredhopper-images]

```
