package com.springmvc.controller;

import com.springmvc.service.RunAsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RunAsController {
    
    @Autowired
    RunAsService runAsService;
    
    @Secured({"ROLE_ADMIN","RUN_AS_CUSTOM"})
    @RequestMapping("/runAs1")
    @ResponseBody
    public String runAs1(){
        runAsService.display();
        return "runAs1";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/runAs2")
    @ResponseBody
    public String runAs2(){
        runAsService.display();
        return "runAs2";
    }
}
