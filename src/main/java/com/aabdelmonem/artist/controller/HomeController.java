package com.aabdelmonem.artist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * All requests to root directory will be routed by this controller.
 * 
 * @author ahmed.abdelmonem
 *
 */
@Controller
@ApiIgnore
public class HomeController
{

    @GetMapping("/")
    public String home()
    {
        return "redirect:swagger-ui.html";
    }

}
