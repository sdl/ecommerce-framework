E-Commerce Framework for SDL Web
====================================

This is framework to enable E-Commerce functionality for SDL Web.
It will contain ECL providers and DXA modules for various E-Commerce systems.

Right now it provides connectors for:

* Fredhopper (http://www.sdl.com/cxc/digital-experience/ecommerce-optimization/fredhopper.html)
* SAP Hybris (http://www.hybris.com)
* Demandware (http://www.demandware.com)

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
    * Item Listers
    * Facets 
    * Breadcrumbs
    * Promotions
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

Getting started
-----------------

To get started with the E-Commerce framework you need to do the following steps:

[TODO: Extend this description]

1. If you have not DXA v1.3 in your repo you need to clone that first: `git clone https://github.com/sdl/dxa-web-application-java`. Then you do 'mvn install' and all DXA and needed non-public 3PPs are installed into your local Maven repository
2. Clone this repository: `git clone https://github.com/sdl/ecommerce-framework`
3. Compile all code and install it into your local Maven repository: `mvn install` (TODO: Add info about different profiles)
4. Following instructions given in respective README for each connector (under /connectors) to compile and set it up
5. Add dependencies to the framework modules (ecommerce-framework-api, ecommerce-framework-dxa-module, navigation-dxa-module) in the POM.xml of your DXA web application
6. In addition add dependencies to the selected connector(s) in your webapp's POM.xml
7. Install the CMS package by doing the following: [TO BE DEFINED]
8. As optional step you can install the example pages & config for each connector (see README for the connector)
9. Publish out the settings page, HTML design + the header include page. 
10. Publish out pages under 'Categories' and 'Products'. And the 'Cart' page.
11. Define the application properties needed for selected connectors in your webapps' Spring config or properties file
12. Start up the server and verify that the E-Commerce main categories are visible in the mega navigation


In addition (optional) you can also install one of the ECL providers (Hybris or Demandware). Follow the instructions given in the respective README for those providers.

(TODO: Add reference to a community.sdl.com blog post here)

Not implemented
-----------------

Currently the E-Commerce framework does not implement the following:

* Support for checkouts
* Product variants
* Improved category experience such as sorting, slider facets etc
* Hybrid search (content/E-Commerce)
* Search suggest

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





