package com.aabdelmonem.artist.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ahmed.abdelmonem
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CoverArtArchiveReleaseGroup {

	private String id;

	private List<CoverArtArchiveReleaseGroupImage> images;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CoverArtArchiveReleaseGroupImage> getImages() {
		return images;
	}

	public void setImages(List<CoverArtArchiveReleaseGroupImage> images) {
		this.images = images;
	}
}
