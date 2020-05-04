package com.aabdelmonem.artist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ahmed.abdelmonem
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CoverArtArchiveReleaseGroupImage {

	private String id;

	private String image;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
