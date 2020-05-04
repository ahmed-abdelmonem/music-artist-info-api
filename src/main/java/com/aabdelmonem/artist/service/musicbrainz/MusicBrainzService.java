package com.aabdelmonem.artist.service.musicbrainz;

import com.aabdelmonem.artist.domain.Artist;
import com.aabdelmonem.artist.exception.ArtistNotFoundException;
import com.aabdelmonem.artist.exception.ServiceNotAvailableException;

/**
 * 
 * @author ahmed.abdelmonem
 */
public interface MusicBrainzService {

	Artist getMusicBrainzArtistDetails(String mbid) throws ArtistNotFoundException, ServiceNotAvailableException;

}
