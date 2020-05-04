package com.aabdelmonem.artist.datatransferobject;

import java.util.List;

/**
 * Artist data transfer object
 * 
 * @author ahmed.abdelmonem
 *
 */
public class ArtistDTO {

	// MBID (MusicBrainz Identifier)
	private String mbid;

	private String name;

	private String description;

	private List<AlbumDTO> albums;
	
	/**
	 * default constructor used by JUnit test classes.
	 */
	public ArtistDTO() {

	}
	
	public ArtistDTO(String mbid, String name, String description, List<AlbumDTO> albums) {
		this.mbid = mbid;
		this.name = name;
		this.description = description;
		this.albums = albums;
	}

	public String getMbid() {
		return mbid;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<AlbumDTO> getAlbums() {
		return albums;
	}
}
