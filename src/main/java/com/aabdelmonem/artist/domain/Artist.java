package com.aabdelmonem.artist.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Artist domain
 * 
 * @author ahmed.abdelmonem
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Artist {

	private String id;

	private String name;
	
	// will be filled from Discogs
	private String description;

	@JsonProperty("release-groups")
	private List<ReleaseGroup> releaseGroups;

	private List<Relation> relations;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ReleaseGroup> getReleaseGroups() {
		return releaseGroups;
	}

	public void setReleaseGroups(List<ReleaseGroup> releaseGroups) {
		this.releaseGroups = releaseGroups;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}
}
