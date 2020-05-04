package com.aabdelmonem.artist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Artist relation domain
 * 
 * @author ahmed.abdelmonem
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Relation {

	private String type;

	private RelationUrl url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RelationUrl getUrl() {
		return url;
	}

	public void setUrl(RelationUrl url) {
		this.url = url;
	}
}
