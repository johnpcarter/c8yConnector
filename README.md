##c8yConnector

Provides a set of services to allow access to your c8y tenant. Use in conjunction with your webMethods based agents to integrate any device type with Cumulocity. 
The services are documented in Designer under the comments section of each service and from the packages [home directory /c8yConnector](http://localhost:5555/c8yConnectory). You can also view [online](https://raw.githack.com/johnpcarter/c8yConnector/main/pub/index.html).

**Installation**

This source code is a webMethods Micro Service Runtime package and you will need to first install the Software AG Micro Service Runtime or download a docker image.

**local installation**

If you have an Integration Server or Micro Service Runtime running locally for development purposes, first navigate to your packages directory;

$ cd /${SAG_HOME}/IntegrationServer/packages
or
$ cd /${SAG_HOME}/IntegrationServer/instances/${INSTANCE}/packages

If your packages directory is already under version control

$ git submodule add https://github.com/johnpcarter/c8yConnector.git c8yConnector.git

or if you are not, then simply clone the repository

$ git clone https://github.com/johnpcarter/c8yConnector.git

Then download dependent packages

$ git clone https://github.com/johnpcarter/JcPublicTools.git

Then restart your runtime server and refresh your package browser in Designer.

**Docker Installation**

A predefined Dockerfile template has been provided in the resources directory. It is recommended that you copy the directory
and then update Dockerfile and aclmap_sm.cnf file appropriately.

cd into your directory and download the latest source code

*$ cd ${WORKING_DIR}*  

*$ git clone https://github.com/johnpcarter/c8yConnector.git*  
*$ git clone https://github.com/johnpcarter/JcPublicTools.git*  

You will also need to add your own packages and configuration as part of the build by copying the following line into the section 'add YOUR packages here'
e.g.

*ADD --chown=sagadmin ./c8yPhilipsHueAgent /opt/softwareag/IntegrationServer/packages/c8yPhilipsHueAgent*  

and also update the ./resources/aclmap_sm.cnf file to include permissions for any the services that you are exposing through an API.

You could also choose to include your MSR license file by adding the following line

*ADD --chown=sagadmin .licenseKey.xml /opt/softwareag/IntegrationServer/config/licenseKey.xml*  

You can now build your image

*$ docker build -t ${YOUR DOCKER IMAGE} .*  

**Preparing the Micro Service zip file for your tenant**

You will need to export the newly build image as a tar file and then zip it with the provided cumulocity.json file before uploading to your tenant

*$ docker save -o image.tar ${YOUR DOCKER IMAGE}*  
*$ zip ${c8y_app_name} cumulocity.json image.tar*

**Uploading the Micro Service agent to your Cumulocity tenant**

Login in to your Cumulocity tenant and select the "Administration" app, then from the left hand side menu "Applications", "Own Applications" and then click "Add application". Choose "Upload microservice"and select the zip that you created before.

Alternatively you can upload the image from the command line using the 'cumulocity-microservice.sh' script with

*$ ./cumulocity-microservice.sh deploy -n philips-hue-agent -u <YOUR_LOGIN> -p <YOUR_PASSWORD> -d https://www.cumulocity.com -te <TENANT_ID>*  

Once uploaded, make sure that you subscribe to the app in order to start it up and activate it.

For more information refer to the Cumulocity documentation on [Micro Service Runtime](https://cumulocity.com/guides/microservice-sdk/concept/#microservice-runtime)

**Starting up the agent remotely**

You can also use the agent from your own development environment or run the docker image outside of your tenant if you wish.
You will need to set the bootstrap credentials or set the c8y user in your local environment.

You can obtain your bootstrap credentials via the following api call

*$ curl "https://<TENTANT_NAME>.cumulocity.com/application/applications/<APP_ID>/bootstrapUser" \
 -u '<YOUR_USER>:<YOUR_PASSWORD>'*

From your local environment, run the service

*jc.cumulocity.tools.app:setCredentials*  

and either provide your own credentials via the C8Y_TENANT, C8Y_USER and C8Y_PASSWORD inputs, or you want to use the app credential provide 
the 'C8Y_BOOSTRAP_TENANT', 'C8Y_BOOTSTRAP_USER' and 'C8Y_BOOTSTRAP_PASSWORD' inputs. Reload the package afterwards if you want the package to automatically 
send metrics and process operations for your lights.

If you want to run the docker image independently then set the following environment variables when starting up the container

*-e C8Y_BOOTSTRAP_TENANT=''*
*-e C8Y_BOOTSTRAP_USER=''*
*-e C8Y_BOOTSTRAP_PASSWORD=''*
