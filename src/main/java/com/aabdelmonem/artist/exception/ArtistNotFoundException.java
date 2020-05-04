package com.aabdelmonem.artist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author ahmed.abdelmonem
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find artist with the provided MBID.")
public class ArtistNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 119732113034553353L;

	public ArtistNotFoundException() {
		super();
	}

}
