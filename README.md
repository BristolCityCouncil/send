# Overview

The Special Educational Needs application (SEND) was created so that Special Educational Needs Co-ordinators (SENDco's) in schools have a standardised way to apply to Bristol City Council for funding, for a pupil that has additional educational needs.

# Tech overview

The webapp is a maven based project, with Spring boot and Java 11. Pages are generated via freemarker templates. The database is a noSQL Azure Cosmos Db, and all data is held in .json format. Authentication to the app is through AD. 


# Description of user experience

Once the SENDco has a login to the app, they can create an assessment based on a unique UPN. This is the first page a logged in user will see, the home page (home.ftlh). The UPN is a Unique Pupil Reference number, the format of which must match this [central government criteria](https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/791190/UPNGenerator.xls). The assessment is also assigned a unique id by the database, although this is not generally visible to the user. 

The SENDco can view a summary page for the assessment (assessment.ftlh). From here are links to pupil needs on the needs page (needs.ftlh). Under one of four *area* headings are links to 10 *sub-areas*. On the sub-area page are several *sub-categories* which contain groups of statements that represent the needs of this pupil. SENDco's pick the statements most relevant to this pupil, and use the button at the foot of the page to save these to the assessment stored in the database. Need selections are exclusive within the group, as only one can be selected per group. We do not expect SENDco's to read all the need statements, as there are several hundred need statements in the database! Instead they should be guided by the sub-area and sub-category headings to find the statements that best describe the needs of each pupil. 

Once they have chosen the needs for a sub-area, the provisions links on the summary page become active. The SENDco can then use the provisions page (provisions.ftlh) to select requested provisions from a filtered list. Again these are saved to the assessment in the database by using the save button at the foot of the page. Provisions displayed are filtered according to which need statements have been selected, and which sub-area the needs belong to. A matrix of which provisions should be displayed is included in the file TBC confirmed-data/provisions-matrix.xlsx. This mapping between provisions and sub-areas is handled by the provisionsLookUp table (see Data structure heading below).

Note: all the need statements have a level, Expected, Targetted, A, B or C. Once the needs for a sub-category have been saved, the app finds the highest need level for this sub-category. This is a factor in determining which provisions will be available to the SENDco, as only A, B, or C levels attract additional funding. But this need level is not shown to the SENDco, to encourage them to chose the most appropriate need statements, rather than simply opting for the highest to maximise their funding level.

When at least one need and one provision have been selected, the SENDco can use the "download as pdf" button on the assessment summary page to get a pdf summary of their assessment. This contains all the selected needs and provision statements, as well as date of the assessment, school and UPN. TBC DESCRIBE FUNDING CALCULATION. Depending on the local authority workflow, this would then be sent to either the authorities' SEND admin team or finance team, with supporting documentation. This app avoids GDPR issues by only having the UPN and assessment id to identify each assessment - the child's name is not recorded and there are no free text boxes that would mean personal information about a child could ever be stored in the SEND database. 

More detailed information and guidance about how to use the Send application can be found here. [Send pages link TBC](https://www.bristol.gov.uk/resources-professionals/professionals-working-with-children-with-send)


# Data structure

There are 4 tables in the database in .json format; assessments, needs, provisions and provisionsLookUp. The assessments table will be empty until users create assessments. The needs and provisions tables contain several hundred text statements. The provisionLookUp table determines which provisions groups should be available to the user.

There are 10 .json files in the needs table, one for each of the sub-areas. Under which are several sub-categories, and under these are one or more statement groups. We query the database by sub-area not area, so area is really only a label to assist user navigation.  


### needs

```
Area x 4 
	|
Sub-Area x 10
	|
Sub-category x n
	|
Statement group x n
```

### provisions

There are 7 .json file in the provisions table, which represent a provision group. Under each are several provision types. In each provision type is a provision text statement.

```
Provision group x 7
	|
Provision type x n
	|
Provision x n	
```

### provisionsLookUp

The provisionslookup table has 10 items, one for each sub-area. These lists the provision groups (by id) that should be available to the user, depending on the needs the user has selected for this assessment.


# Developer considerations when using this repo

## Pull requests and developer collaboration

THIS REPO IS AVAILABLE TO BE CLONED, BUT YOU SHOULD CREATE YOUR OWN LOCAL AND REMOTE REPOS OF THIS PROJECT FOR DEVELOPMENT. CURRENTLY ALL PULL REQUESTS TO THIS REPO WILL BE REJECTED.
This may change in time, but it is expected that other local authorities will have different requirements when implementing this project, in terms of databases, authentication, workflow etc, which would create conflicts if we allow merges into the main code.

However, if you find spelling mistakes, bugs, etc it may be worth raising a PR anyway, so that Bristol City Council can consider raising a fix in our own bug tracking system, and applying the fix to the main branch, making the fix available to other users. 

## Developer skill set

As a spring boot app the SEND app runs in its own container, so should be fairly straightforward to set up. Developers implementing this app will need core skills in Java and Maven, and the ability to setup environments and basic databases (see the points below). The app also makes heavy use of javascript, which can be difficult to debug, although the scripts in the main branch should all be working correctly.


## Configuration for the SEND project

- Replace all Bristol City Council branding, i.e logos and headers.
- Refactor all the java package names away from uk.gov.bristol
- There are some TODOs in the pom.xml of both the main and test packages, which will need resolving before building and using this app.
- With some refactoring, authentication could be changed away from AD if required.
- With some refactoring of the java classes in the ../repo folder, another nosql database that takes .json files could be used instead of MS CosmosDB.
- Some config is required in the application.properties file. These four values will need to be replaced with actual values, or environment specific properties created where the application is hosted (in our case in the Azure app service). See [microsoft docos](https://docs.microsoft.com/en-us/azure/app-service/reference-app-settings?tabs=kudu%2Cdotnet)
```
azure.cosmos.uri=${CUSTOMCONNSTR_SEND_COSMOS_URI}
azure.cosmos.key=${CUSTOMCONNSTR_SEND_COSMOS_KEY}
azure.cosmos.database=${CUSTOMCONNSTR_SEND_COSMOS_DBNAME}
default.user.token=${CUSTOMCONNSTR_SEND_DEFAULT_USER_TOKEN}
```
The default.user.token is not wholly necessary. It was created to enable local development, to avoid having to login with 2 factor authentication each time we started the app on localhost.


## Set up Local database for testing (optional)

The Azure Cosmos DB Emulator provides a local environment that emulates the Azure Cosmos DB service for development purposes, without creating an Azure subscription or incurring any costs.

### Install Azure CosmosDB Emulator locally.
Go to https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator#installation 
Download the windows installation package and install Azure Cosmos DB Emulator.

### Export security certificates
Azure Cosmos DB Emulator uses a secure connection with our application.
We need to extract and register our certificate for the emulator.
The how to do it tutorial can be found at Microsoft Azure website, explaining how to export the Azure Cosmos DB SSL certificate. https://docs.microsoft.com/bs-latn-ba/azure/cosmos-db/local-emulator-export-ssl-certificates#how-to-export-the-azure-cosmos-db-ssl-certificate .
Follow the section under 'Export the Azure Cosmos DB TLS/SSL certificate'

### Upload certificate into the correct Java SDK location.
Check which version/ location Java our Spring Boot application uses or where SDK is located.
In Eclipse, go to Windows -> Preferences -> Java -> Installed JRE
In IntelliJ, you can go to File->Project Structure->SDKs and check which version
Copy certificate into /jre/lib/security location.

### Register certificate
We need to register the certificate into the system.
Open the Windows Command prompt as an administrator and navigate at \jre\lib\security.
Then run the following command to register the certificate into the keystore on your system:

`keytool -keystore cacerts -importcert -alias documentdbemulator -file documentdbemulatorcert.cer`

Note: Keytool is a tool located in your Java installation, so you need to have jdk/bin on your PATH for this to work.

### Open Azure Cosmos DB Emulator application
Once it started running, we can find it minimized in the system hidden in the running application system and either click Open Data Explorer …
or visit https://localhost:8081/_explorer/index.html  page. You can now get finally into the Azure CosmosDB Emulator.

### Create a New Database
Go to http://localhost:8081/
Click on the Explorer. 
Click New Database. 
Give SendCosmosLOCAL as Database id

### Point SEND application to the Local DB
Set the environment variable CUSTOMCONNSTR_SEND_COSMOS_URI to https://localhost:8081 
Set the environment variable CUSTOMCONNSTR_SEND_COSMOS_KEY to Primary key. 
(You can find the primary key on the homepage of the cosmos emulator in the browser, at localhost:8081)


### Upload static data
Once Send application started, the containers will be automatically created from the Java entities
Go to items under **assessments**, **needs** and **provisionsLookUp** and click on upload items 
Select all JSON files under respective folder in \send\confirmed data from the workspace and Upload


