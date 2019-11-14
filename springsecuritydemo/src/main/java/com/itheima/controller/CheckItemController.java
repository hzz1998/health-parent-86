package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public String add(){
        return "add";
    }

    @RequestMapping("/del")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public String del(){
        return "del";
    }
}
