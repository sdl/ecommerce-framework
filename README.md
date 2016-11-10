SDL Web E-Commerce Framework v1.1
====================================

This is a framework to enable E-Commerce functionality for SDL Web.
It contains ECL providers and DXA modules for various E-Commerce systems.

Right now it provides connectors for:

* Fredhopper (http://www.fredhopper.com)
* SAP Hybris (http://www.hybris.com)
* Salesforce Commerce Cloud (Demandware) (http://www.demandware.com)

The framework has been verified both on SDL Tridion 2013 SP1 and SDL Web 8 (8.1.0) using DXA 1.5.
Support for DXA 1.6 will be released soon as an intermediate release.

New functionality in the v1.1 version:
* New OData based micro service for E-Commerce including clients for Java and .NET
* Decoupled E-Commerce connectors, either they can be plugged into the micro service or they can be co-located in DXA (java only)
* DXA modules for .NET providing the same functionality as the Java DXA modules
* Support for product variants
* Fredhopper ECL connector
* Support for latest Demandware version (v16.8)
* Separation of DXA module into two parts: one generic (controllers & models) and one example module (example HTML views)

Concepts
---------

The E-Commerce framework consists of:

* Generic E-Commerce APIs for product categories, product queries, product details, product variants, cart and in-context edit controls
* Generic DXA module for Java & .NET consuming above E-Commerce APIs
* DXA module with example views using the white label design
* Generic navigation DXA module for hybrid navigation support (right now only provides mega navigation)
* A number of connectors implementing the E-Commerce APIs (Fredhopper, Hybris, Demandware)
* OData based micro service for the E-Commerce APIs using the same setup as the standard SDL Web micro services
* Generic E-Commerce framework for ECL providers
* A number of ECL providers for retrieving categories and products (Hybris, Demandware)
* OData based micro service 

Currently there are the following connectors implementing fully or partly the E-Commerce APIs:

* Fredhopper (Categories, Search, Product Detail, Product Variants, Edit controls)
* Hybris (Categories, Search, Product Detail, Cart)
* Demandware (Categories, Search, Product Detail, Product Variants, Cart)
* Dummy for test & demo purposes (Cart)

The intention with the connectors is that you could mix&match what kind of combo you want, such as use Fredhopper for Categories&Search and Hybris for Detail&Cart.
As the the service interfaces are designed to be decoupled from eachother it is straight-forward to setup these kind of combinations.

The Fredhopper connector also provides some extensive support for inline editing (create new, edit and delete) of:

* Facets
* Promotions
* Modifications
* Rankings 
* Redirects
* Synonyms

The generic DXA module that consume the E-Commerce APIs. It contains right now the following:

* E-Com driven navigation items (in mega navigation). Can be mixed with content driven navigation items.
* Widgets for:
    * Product Listers for presenting products in category and search result pages
    * Facets 
    * Breadcrumbs
    * Promotions
    * Product details
    * Mega navigation (facets + promotions)
    * Search box (in the header)
    * Search spelling feedback
    * Cart (minimised in the header + detail)
* Page controllers for category and detail pages for SEO friendly pages
    * No 1:1 mapping between a page and a Tridion page. Tridion instead manage a set of template pages which are used for the different category/detail pages
    * The experience of those template pages can be overridden. The controllers uses a search pattern to find the best match template page for a certain category/detail pages
* Some widgets can be placed outside the control of the category/detail controllers such as facets, promotions and listers. The actual category & view type can be configured on widget level through either category paths or ECL links. This allows easy creating of campaign/landing pages etc.
* Localization support, i.e. the different language, currency and catalog mappings per publication

In addition there is a DXA module with a set of HTML views (in JSP or Razor) implementing the default E-Commerce templates. The views are
based on the White Label HTML design.

The ECL providers allows easy access to categories and products which makes it easy to associate references in for example the different E-Commerce widgets (listers, facets etc). 
In addition the ECL provider gives the possibility to drag & drop categories & products directly on pages as well. This allows the possibility to do both E-Commerce 1:1 (i.e. one Tridion page per category/product) and rule based (through the controllers).

Prerequisites
----------------

The framework requires DXA 1.5 which includes support for SDL Tridion 2013 SP1 and SDL Web 8.

The connectors to the E-Commerce systems has been verified against the following:

* SDL Fredhopper 7.5.x & 8.1.x
* Demandware OCAPI 16.8
* Hybris (Omni Commerce Connect) OCC v1 on hybris Commerce Suite 5.x


Getting started (Java)
------------------------

For Java you can either have the connectors co-located or use the OData micro service. Below is the steps
needed to have the connectors co-located:  

1. If you have not DXA webapp you can easily create one with a Maven archetype:
    * mvn archetype:generate
    * select dxa & v1.5.0
    * go into the newly created directory and type: mvn install
2. If you have not setup DXA in the CMS, please follow the following instructions: [Installing Digital Experience Accelerator](http://docs.sdl.com/LiveContent/content/en-US/SDL%20DXA-v3/GUID-8E88E5AF-4552-40F0-8DB2-FBDBDBA41A11) 
3. Clone this repository: `git clone https://github.com/sdl/ecommerce-framework`
4. Compile all code and install it into your local Maven repository by using any of the following alternatives: 
    * `mvn install -Pall-connectors` - compile the framework and all available connectors
    * `mvn install -Pfredhopper` - compile the framework and the Fredhopper connector (make sure to install the Fredhopper client libraries first, see [Installing Fredhopper libraries](./connectors/fredhopper-dxa-ecommerce-connector/lib/README.md)
    * `mvn install -Phybris` - compile the framework and the Hybris connector
    * `mvn install -Pdemandware` - compile the framework and the Demandware connector 
                             
5. Following instructions given in respective README for each connector to set it up:
    * [Fredhopper](./connectors/fredhopper-ecommerce-connector/README.md)
    * [Hybris](./connectors/hybris-ecommerce-connector/README.md)
    * [Demandware](./connectors/demandware-ecommerce-connector/README.md)
6. Add dependencies to the following framework modules in the POM.xml of your DXA web application:
    * ecommerce-framework-dxa-module
    * ecommerce-framework-dxa-module-example-views (optional)
    * navigation-dxa-module) 
7. In addition add dependencies to the selected connector(s) in your webapp's POM.xml
8. Setup CMS as described below


Getting Started with the OData micro service
----------------------------------------------

Follow the below steps to setup the the OData micro service (which can be run on Windows and Linux/Unix systems):

1. Clone this repository: `git clone https://github.com/sdl/ecommerce-framework`
2. Compile and package the OData micro service by doing the following in the project root:
     * mvn clean package -P[selected connector, e.g. 'fredhopper', 'hybris', or 'demandware']
3. Unzip the generated 'ecommerce-framework-odata-service-1.1.0-install.zip' file in an appropiate directory on your target environment
4. Configure the connector and locale settings in the config/application.yml file. Some examples are given in the file.
5. The service can be started by using the bin/start.sh or bin/start.ps1 scripts. If using Windows you can install the micro service as a Windows service by running the 'installService.ps1' script.
    * For more information about different deployment options, see [Installing Spring Boot applications](http://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html) 
6. Verify that the service up & running by typing the following in your browser: http://[server name]:8097/ecommerce.svc/Categories. It should list all top level categories in the current E-Commerce system.

Getting Started (.NET)
------------------------

Follow the below steps to setup the .NET version of the E-Commerce DXA modules: 
1. If you do not have a DXA.NET setup (for SDL Web 8) you can easily do this by doing the following:
    * git clone -b release/1.5 https://github.com/sdl/dxa-web-application-dotnet
    * Open the project in Visual Studio
    * Modify the Web.config to point to your local discovery service
    * Copy cd_ambient_conf.xml to Site/bin/config from your SDL Web 8.1.1 distribution: /Content Delivery/roles/api/rest/config/cd_ambient_conf.xml
    * Or just follow the instructions given here: http://docs.sdl.com/LiveContent/content/en-US/SDL%20DXA-v5/GUID-001D829E-1141-4B18-B696-894DF27B6DA1 
2. Clone this repository: `git clone https://github.com/sdl/ecommerce-framework`
3. Add the VS projects under dxa.net to your Visual Studio solution
4. Set the environment variable %DXA_SITE_DIR% to point to your DXA Site path (in visual studio or in your IIS instance) 
    * Alternatively you can include all E-Commerce VB projects into your site solution.
5. Restart Visual studio and rebuild the solution. Verify so E-Commerce Areas and DLLs are copied to your site folder
6. Configure E-Commerce micro service in Web.config of your site:

   ``` 
    !-- E-Commerce Framework -->
    <add key="ecommerce-service-uri" value="http://localhost:8097/ecommerce.svc"/>
   ```

7. Setup CMS as described below    

An overview of the different .NET projects are given here: [E-Commerce DXA.NET](./dxa.net/README.md).

Getting Started (CMS)
------------------------

1. Install the CMS packages by following instructions given in: [Install CMS packages](./cms/README.md) 
2. Publish out the settings page, HTML design + the header include page. 
3. Publish out pages under 'Categories' and 'Products'. And the 'Cart' and 'Search Results' pages.
4. Define the application properties needed for selected connectors in your web application. Refer to the README file for each connector.
4. Start up the server and verify that the E-Commerce main categories are visible in the mega navigation
5. In addition (optional) you can also install one of the ECL providers. See instructions given in:
    * [Hybris ECL](./ecl/hybris-ecl-provider/README.md)
    * [Demandware ECL](./ecl/demandware-ecl-provider/README.md) 
    * [Fredhopper ECL](./ecl/fredhopper-ecl-provider/README.md)

Not implemented
-----------------

Currently the E-Commerce framework does not implement the following:

* Support for checkouts
* Improved category experience such as sorting, slider facets etc
* Hybrid search (content/E-Commerce)
* Search suggest
* OAuth authentication on the E-Commerce micro service
* Generic ECL provider using the OData micro service (to minimize the integration points)

This is something to be consider in later version of the framework. 

Branching model
----------------

We intend to follow Gitflow (http://nvie.com/posts/a-successful-git-branching-model/) with the following main branches:

 - master - Stable 
 - develop - Unstable
 - release/x.y - Release version x.y

Please submit your pull requests on develop. In the near future we intend to push our changes to develop and master from our internal repositories, so you can follow our development process.


License
---------
Copyright (c) 2016 SDL Group.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.





