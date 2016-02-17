E-Commerce Framework for SDL Web
====================================

This is framework to enable E-Commerce functionality for SDL Web.
It will contain ECL providers and DXA modules for various E-Commerce systems.

Right now it provides connectors for:

* Fredhopper (http://www.sdl.com/cxc/digital-experience/ecommerce-optimization/fredhopper.html)
* SAP Hybris (http://www.hybris.com)
* DemandWare (http://www.demandware.com)

Concepts
---------

The E-Commerce framework consists of:

* Generic E-Commerce APIs for product categories, product queries, product details, cart and in-context edit controls
* Generic DXA module consuming above E-Commerce APIs
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

The ECL providers allows easy access to categories and products which makes it easy to associate references in for example the different E-Commerce widgets (listers, facets etc). 
In addition the ECL provider gives the possibility to drag & drop categories & products directly on pages as well. This allows the possibility to do both E-Commerce 1:1 (i.e. one Tridion page per category/product) and rule based (through the controllers).

Getting started
-----------------
T.B.D



Documentation
---------------
T.B.D.


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





