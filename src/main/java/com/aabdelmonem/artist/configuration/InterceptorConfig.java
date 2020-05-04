package com.aabdelmonem.artist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aabdelmonem.artist.util.ArtistRequestInterceptor;


/**
 * @author ahmed.abdelmonem
 *
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
 
    @Autowired
    private ArtistRequestInterceptor artistRequestInterceptor;
 
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(artistRequestInterceptor).addPathPatterns("/**");
    }

}