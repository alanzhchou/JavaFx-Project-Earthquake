package com.alanchou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * __Author__: Alan
 * __date__: 2017/12/16
 * __version__: 1.0
 */

@Controller
public class IndexController implements MappingController {
    @GetMapping(value = "/")
    public String index(){
        return "index";
    }
}

