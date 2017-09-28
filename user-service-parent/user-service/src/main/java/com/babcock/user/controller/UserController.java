package com.babcock.user.controller;

import com.babcock.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/securityadmin")
public class UserController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    UserService securityAdminService;

}