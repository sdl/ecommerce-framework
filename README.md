E-Commerce Framework for SDL Web
====================================

This is a framework to enable E-Commerce functionality for SDL Web.
It contains ECL providers and DXA modules for various E-Commerce systems.

Right now it provides connectors for:

* Fredhopper (http://www.sdl.com/cxc/digital-experience/ecommerce-optimization/fredhopper.html)
* SAP Hybris (http://www.hybris.com)
* Demandware (http://www.demandware.com)

The framework has been verified both on SDL Tridion 2013 SP1 and SDL Web 8 (8.1.0) using DXA 1.3.

Concepts
---------

The E-Commerce framework consists of:

* Generic E-Commerce APIs for product categories, product queries, product details, cart and in-context edit controls
* Generic DXA module consuming above E-Commerce APIs
* Generic navigation DXA module for hybrid navigation support (right now only provides mega navigation)
* A number of connectors implementing the E-Commerce APIs (Fredhopper, Hybris, Demandware)
* Generic E-Commerce framework for ECL providers
* A number of ECL providers for retrieving categories and products (Hybris, Demandware)

Currently there are the following connectors implementing fully or partly the E-Commerce APIs:

* Fredhopper (Categories, Search, Product Detail, Edit controls)
* Hybris (Categories, Search, Product Detail, Cart)
* Demandware (Categories, Search, Product Detail, Cart)
* Dummy for test & demo purposes (Cart)

The intension with the connectors is that you could mix&match what kind of combo you want, such as use Fredhopper for Categories&Search and Hybris for Detail&Cart.
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
    * Product Listers
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
* ADF claim processor for carts (so cart data can be exposed to for example SmartTarget)
* Localization support, i.e. the different language, currency and catalog mappings per publication

The ECL providers allows easy access to categories and products which makes it easy to associate references in for example the different E-Commerce widgets (listers, facets etc). 
In addition the ECL provider gives the possibility to drag & drop categories & products directly on pages as well. This allows the possibility to do both E-Commerce 1:1 (i.e. one Tridion page per category/product) and rule based (through the controllers).

Prerequisites
----------------

The framework is requiring DXA 1.3 which includes support for SDL Tridion 2013 SP1 and SDL Web 8.

The connectors to the E-Commerce systems has been verified against the following:

* SDL Fredhopper 7.5.x & 8.1.x
* Demandware OCAPI 16.1 & 16.2
* Hybris (Omni Commerce Connect) OCC v1 on hybris Commerce Suite 5.x


Getting started
-----------------

To get started with the E-Commerce framework you need to do the following steps:

1. If you have not DXA v1.3 in your repo you need to clone that first: `git clone -b release/1.3 https://github.com/sdl/dxa-web-application-java`. Then you do 'mvn install' and all DXA and needed non-public 3PPs are installed into your local Maven repository
2. If you have not setup DXA in the CMS, please follow the following instructions: [Installing Digital Experience Accelerator](http://docs.sdl.com/LiveContent/content/en-US/SDL%20DXA-v3/GUID-8E88E5AF-4552-40F0-8DB2-FBDBDBA41A11) 
3. Clone this repository: `git clone https://github.com/sdl/ecommerce-framework`
4. Compile all code and install it into your local Maven repository by using any of the following alternatives: 
    * `mvn install -Pall-connectors` - compile the framework and all available connectors
    * `mvn install -Pfredhopper` - compile the framework and the Fredhopper connector (make sure to install the Fredhopper client libraries first, see [Installing Fredhopper libraries](./connectors/fredhopper-dxa-ecommerce-connector/lib/README.md)
    * `mvn install -Phybris` - compile the framework and the Hybris connector
    * `mvn install -Pdemandware` - compile the framework and the Demandware connector 
5. If you get these type of error: "[WARNING] The POM for com.tridion:cd_ambient:jar:7.1.0 is missing, no dependency information available"
    * Go into your local Maven repository and modify the following file: ($HOME/.m2/repository/com/tridion/cd_ambient/7.1.0/_remote.repositories and make sure that it contains the following:
    
        ```
        cd_ambient-7.1.0.pom>=
        cd_ambient-7.1.0.jar>=
        ```
                                  
6. Following instructions given in respective README for each connector to set it up:
    * [Fredhopper](./connectors/fredhopper-dxa-ecommerce-connector/README.md)
    * [Hybris](./connectors/hybris-dxa-ecommerce-connector/README.md)
    * [Demandware](./connectors/demandware-dxa-ecommerce-connector/README.md)
7. Add dependencies to the framework modules (ecommerce-framework-api, ecommerce-framework-dxa-module, navigation-dxa-module) in the POM.xml of your DXA web application
8. In addition add dependencies to the selected connector(s) in your webapp's POM.xml
9. Install the CMS packages by following instructions given in: [Install CMS packages](./cms/README.md) 
10. Publish out the settings page, HTML design + the header include page. 
11. Publish out pages under 'Categories' and 'Products'. And the 'Cart' and 'Search Results' pages.
12. Define the application properties needed for selected connectors in your web application. Refer to the README file for each connector.
13. Start up the server and verify that the E-Commerce main categories are visible in the mega navigation
14. In addition (optional) you can also install one of the ECL providers (Hybris or Demandware). See instructions given in:
    * [Hybris ECL](./ecl/hybris-ecl-provider/README.md)
    * [Demandware ECL](./ecl/demandware-ecl-provider/README.md) 


Not implemented
-----------------

Currently the E-Commerce framework does not implement the following:

* Support for checkouts
* Product variants
* Improved category experience such as sorting, slider facets etc
* Hybrid search (content/E-Commerce)
* Search suggest

This is something to be consider in later version of the framework.  We also consider to move the E-Commerce connectors to the
SDL Web micro service infrastructure instead so it can easily be reused for .NET web applications.

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





