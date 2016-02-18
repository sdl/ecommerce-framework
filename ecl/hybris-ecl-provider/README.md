  Hybris ECL Provider
=======================

## Installation

* Create a directory called 'Hybris-ECL-Provider' under C:\ProgramData\SDL\SDL Tridion\External Content Library\AddInPipeline\Addins\
* Either use Visual Studio to compile the DLLs to this directory or copy the code manually to this location
* Copy the Themes directory to the created directory
* Open up your ExternalContentLibrary.xml in your Tridion config directory and add the following:

```
  <MountPoint type="Hybris-ECL-Provider" id="hybris" rootItemName="Hybris E-Commerce">
      <StubFolders>
        <StubFolder id="[STUB FOLDER TCM-URI]" />
      </StubFolders>
      <PrivilegedUserName>[TRIDION ADMIN USER]]</PrivilegedUserName>
      <ShopUrl xmlns="http://sdl.com/ecl/ecommerce">[Hybris OCC v1 URL, e.g. http://localhost:9001/rest/v1/electronics]</ShopUrl>
	  <Username xmlns="http://sdl.com/ecl/ecommerce">[Username]</Username>
	  <Password xmlns="http://sdl.com/ecl/ecommerce">[Password]</Password>
	  <ActiveCatalogVersion xmlns="http://sdl.com/ecl/ecommerce">[Active catalog version e.g. /catalogs/electronicsProductCatalog/Online]</ActiveCatalogVersion>
	  <MediaUrl xmlns="http://sdl.com/ecl/ecommerce">[Hybris media URL, e.g. http://localhost:9001]</MediaUrl>
  </MountPoint>
``` 

* Make sure that the id of the mountpoint is named 'hybris', otherwise the ECL mappings on the DXA side will not work.
* Restart SDL Tridion services and verify that you can browse the Hybris product catalog through SDL Tridion

  
