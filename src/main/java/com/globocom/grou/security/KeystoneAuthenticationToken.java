package com.globocom.grou.security;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.Optional;

public class KeystoneAuthenticationToken extends AbstractAuthenticationToken {

    private static final String KEYSTONE_URL = Optional.ofNullable(System.getenv("KEYSTONE_URL")).orElse("http://controller:5000/v3");
    private static final String KEYSTONE_DOMAIN_CONTEXT = Optional.ofNullable(System.getenv("KEYSTONE_DOMAIN_CONTEXT")).orElse("grou");

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String token;
    private final String project;

    private Object principal = null;

    public KeystoneAuthenticationToken(String token, String project) {
        super(Collections.emptyList());
        this.token = token;
        this.project = project;
        log.info("Openstack Keystone url: " + KEYSTONE_URL + " (domain scope: " + KEYSTONE_DOMAIN_CONTEXT + ")");
    }

    @Override
    public Object getCredentials() {
        return Collections.emptyList();
    }

    @Override
    public Object getPrincipal() {
        if (principal == null) {
            try {
                OSClient.OSClientV3 os = OSFactory.builderV3()
                        .endpoint(KEYSTONE_URL)
                        .token(token)
                        .scopeToProject(Identifier.byName(project), Identifier.byName(KEYSTONE_DOMAIN_CONTEXT))
                        .authenticate();

                this.principal = os.getToken().getUser();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return principal;
    }
}
