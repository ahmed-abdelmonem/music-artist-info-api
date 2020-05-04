package com.aabdelmonem.artist.service.artist;

import java.util.concurrent.CompletableFuture;

import com.aabdelmonem.artist.domain.Artist;
import com.aabdelmonem.artist.exception.ArtistNotFoundException;
import com.aabdelmonem.artist.exception.ServiceNotAvailableException;

/**
 * @author ahmed.abdelmonem
 *
 */
public interface ArtistService {

	CompletableFuture<Artist> getArtistInfo(String mbid) throws ArtistNotFoundException, ServiceNotAvailableException;

}
