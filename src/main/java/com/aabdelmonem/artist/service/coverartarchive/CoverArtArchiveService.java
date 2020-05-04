package com.aabdelmonem.artist.service.coverartarchive;

import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author ahmed.abdelmonem
 */
public interface CoverArtArchiveService {

	CompletableFuture<String> getAlbumCoverArtImage(String releaseGroupMbid);
}
