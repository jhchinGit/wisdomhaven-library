**Prerequisites**

1. Make sure MySQL is installed.
   - You can download the installer from [MySQL Installer](https://dev.mysql.com/downloads/installer/)
   - Refer to the [MySQL Installation Guide for Windows](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/mysql-installer.html) for detailed installation instructions.
2. Create a MySQL database schema.
   - Refer to the [How to create a database schema in MySQL](https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/How-to-create-a-database-schema-with-the-MySQL-Workbench) for detailed installation instructions.
3. Make sure Java EE 17 is installed) for detailed installation instructions.
   - Download the Installer from [Oracle Java 17 Downloads](https://www.oracle.com/java/technologies/downloads/#java17)
   - Verify that JAVA_HOME is set. You can find guidance on setting JAVA_HOME at [Set JAVA_HOME Variable in Windows, Mac OS X, and Linux](https://www.baeldung.com/java-home-on-windows-mac-os-x-linux)
4. Make sure Apache Maven is installed.
   - Download the Installer from [Apache Maven](https://maven.apache.org/download.cgi)
   - Refer to the [Installing Apache Maven](https://maven.apache.org/install.html) for detailed installation instructions.

**How to run the application**

1. Unzip the SourceCode.zip.
2. Create a configuration file for the application:
   - Use the provided template file "application.properties.template" located in the SourceCode folder.
   - Rename the file "application.properties.template" to "application.properties".
   - Move the file into SourceCode folder.
3. Open the command prompt and navigate to the SourceCode folder
4. Execute the following command: `mvn spring-boot:run -Dserver.port=8080 -Dspring.config.location=application.properties -DskipTests`
   - You can customize the port as needed.
5. Once the service has started, it is ready to be used.

**API Specification**

1. Visit [Swagger Editor](https://editor.swagger.io).
2. Click on File -> Import file -> Choose "openapi.yaml" from the same directory as this file -> Click Open.
3. The API specification will be displayed on the right-hand side.
