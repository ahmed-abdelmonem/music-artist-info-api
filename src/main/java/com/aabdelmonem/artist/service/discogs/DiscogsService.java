package com.aabdelmonem.artist.service.discogs;

import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author ahmed.abdelmonem
 */
public interface DiscogsService {

	CompletableFuture<String> getArtistProfile(String artistId);
}
