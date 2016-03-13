  Demandware ECL Provider
==========================

## Installation

* Create a directory called 'Demandware-ECL-Provider' under C:\ProgramData\SDL\SDL Tridion\External Content Library\AddInPipeline\Addins\
* Either use Visual Studio to compile the DLLs to this directory or copy the code manually to this location
* Copy the Themes directory to the created directory
* Open up your ExternalContentLibrary.xml in your SDL Tridion config directory and add the following:

```
  <MountPoint type="Demandware-ECL-Provider" id="demandware" rootItemName="Demandware E-Commerce">
      <StubFolders>
        <StubFolder id="[STUB FOLDER TCM-URI]" />
      </StubFolders>
      <PrivilegedUserName>[TRIDION ADMIN USER]]</PrivilegedUserName>
      <ShopUrl xmlns="http://sdl.com/ecl/ecommerce">[Demandware shop URL, e.g. http://yourside.demandware.net/s/SiteGenesis]</ShopUrl>
      <ClientId xmlns="http://sdl.com/ecl/ecommerce">[Demandware client ID]</ClientId>  
  </MountPoint>
``` 

* The Demandware client ID can be generated on https://account.demandware.com. For more information, see [Adding a client ID for the Open Commerce API](https://documentation.demandware.com/DOC2/topic/com.demandware.dochelp/AccountManager/AccountManagerAddAPIClientID.htm).
* Make sure that the id of the mountpoint is named 'demandware', otherwise the ECL mappings on the DXA side will not work.
* Verify that the multimedia type has been created for ECL with MIME type = 'application/externalcontentlibrary' and extension = 'ecl'
* Restart SDL Tridion services and verify that you can browse the Hybris product catalog through SDL Tridion

  
