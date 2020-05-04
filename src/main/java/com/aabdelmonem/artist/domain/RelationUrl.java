package com.aabdelmonem.artist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Artist relation URL domain
 * 
 * @author ahmed.abdelmonem
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class RelationUrl {

	private String id;

	private String resource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}
