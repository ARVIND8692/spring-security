package com.springmvc.config;


import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected RunAsManager runAsManager() {
        RunAsManagerImpl runAsManager=new RunAsManagerImpl();
        runAsManager.setKey("abc");
        return runAsManager;
    }
}
