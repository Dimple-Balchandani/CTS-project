package com.vs.ConsignmentTrackingSystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Controller
@Api(value = "/main", description = "Api for home")
public class MainController {

	@RequestMapping(value = "/")
	public String hello() {
		return "index";
	}

}
