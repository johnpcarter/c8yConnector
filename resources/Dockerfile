#
# Template to customise msc based image
#

FROM store/softwareag/webmethods-microservicesruntime:10.5.0.3-alpine
LABEL MAINTAINER="jcart@mac.com" \
	DESCRIPTION="Micro Service for Cumulocity" \
	COMMENT="first release" \
	BUILD-TEMPLATE="c8y Agent" \
	TYPE="Micro Service" 

EXPOSE 80

USER root

# Install apache 2 proxy in order to reroute MSR 5555 port to 80
	
	RUN apk update			
			
	RUN apk add apache2			
			
	RUN apk add apache2-proxy			
			
	RUN apk add apache2-ctl		
	
	ADD --chown=sagadmin ./vhost.conf /etc/apache2/conf.d/vhost.conf			

USER sagadmin	

# Add dependent packages
	
	ADD --chown=sagadmin ./aclmap_sm.cnf /opt/softwareag/IntegrationServer/config/aclmap_sm.cnf	
		
	ADD --chown=sagadmin ./c8yConnector /opt/softwareag/IntegrationServer/packages/c8yConnector			
	ADD --chown=sagadmin ./JcPublicTools /opt/softwareag/IntegrationServer/packages/JcPublicTools			

# add YOUR packages here -->


# ensures that apache proxy gets started before MSR

	ADD --chown=sagadmin ./msr-docker-init.sh /docker-init.sh

# Add heath checker and fix volume directory issue 

	ADD  --chown=sagadmin:sagadmin ./docker-health-check.sh /docker-health-check.sh
	
# Need to run as root so that apache proxy can use port 80!
	
USER root

ENTRYPOINT ["/docker-init.sh"]

HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=6 \
	CMD /docker-health-check.sh
			
			
			

