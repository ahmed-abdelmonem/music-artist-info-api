package com.aabdelmonem.artist.datatransferobject;

/**
 * Album data transfer object
 * 
 * @author ahmed.abdelmonem
 *
 */
public class AlbumDTO {

	private String id;

	private String title;

	private String image;
	
	/**
	 * default constructor used by JUnit test classes.
	 */
	public AlbumDTO() {

	}

	public AlbumDTO(String id, String title, String image) {
		this.id = id;
		this.title = title;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}
}
