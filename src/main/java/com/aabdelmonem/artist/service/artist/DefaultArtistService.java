package com.aabdelmonem.artist.service.artist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aabdelmonem.artist.domain.Artist;
import com.aabdelmonem.artist.domain.Relation;
import com.aabdelmonem.artist.exception.ArtistNotFoundException;
import com.aabdelmonem.artist.exception.ServiceNotAvailableException;
import com.aabdelmonem.artist.service.coverartarchive.CoverArtArchiveService;
import com.aabdelmonem.artist.service.discogs.DiscogsService;
import com.aabdelmonem.artist.service.musicbrainz.MusicBrainzService;

/**
 * Service that has the business logic for artist.
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class DefaultArtistService implements ArtistService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultArtistService.class);

	private MusicBrainzService musicBrainzService;

	private CoverArtArchiveService coverArtArchiveService;

	private DiscogsService discogsService;

	@Autowired
	public DefaultArtistService(MusicBrainzService musicBrainzService, CoverArtArchiveService coverArtArchiveService,
			DiscogsService discogsService) {
		this.musicBrainzService = musicBrainzService;
		this.coverArtArchiveService = coverArtArchiveService;
		this.discogsService = discogsService;
	}

	/**
	 * return artist info CompletableFuture that contains artist final object
	 * filled from MusicBrainz, Cover Art Archive and Discogs APIs 
	 * 
	 * 1- Create Artist object from MusicBrainz response. 
	 * 2- Create CompletableFuture object to get description from Discogs API 
	 * 3- Create CompletableFutures for Cover Art Archive calls for each release group. 
	 * 4- fill description, release groups images from getting the result of the created CompletableFutures.
	 * 
	 * This method will run in a separate thread using spring @Async with executor
	 * artistInfoAsyncExecutor
	 * 
	 * @throws ArtistNotFoundException
	 * @throws ServiceNotAvailableException
	 */
	@Async("artistInfoAsyncExecutor")
	@Override
	public CompletableFuture<Artist> getArtistInfo(String mbid) throws ArtistNotFoundException, ServiceNotAvailableException {

		logger.debug("Called DefaultArtistService.getArtistInfoFutureThread with MBID :{}", mbid);

		Artist artist = null;
		// fill artist from MusicBrainz response
		artist = musicBrainzService.getMusicBrainzArtistDetails(mbid);

		String discogsArtistId = getDiscogsArtistId(artist.getRelations());
		CompletableFuture<String> discogsProfileFuture = null;
		if (discogsArtistId != null && discogsArtistId.length() > 0) {
			// fire CompletableFuture thread to get profile from discogs
			discogsProfileFuture = discogsService.getArtistProfile(discogsArtistId);
		}

		// add albums cover images CompletableFutures to list
		List<CompletableFuture<String>> coverArtImageFuturesList = new ArrayList<>();
		// check that MusicBrainz response contains ReleaseGroups
		if (artist.getReleaseGroups() != null) {
			// add CompletableFuture returned from the service to coverArtImageFuturesList
			artist.getReleaseGroups().forEach(releaseGroup -> coverArtImageFuturesList
					.add(coverArtArchiveService.getAlbumCoverArtImage(releaseGroup.getId())));
		}

		try {
			// fill description (get() will wait for the thread to be done)
			if (discogsProfileFuture != null) {
				artist.setDescription(discogsProfileFuture.get());
			} else {
				artist.setDescription("");
			}

			// fill albums images (get() will wait for the thread to be done)
			for (int i = 0; i < coverArtImageFuturesList.size(); i++) {
				artist.getReleaseGroups().get(i).setImage(coverArtImageFuturesList.get(i).get());
			}
		} catch (Exception e) {
			// non blocking data - just log
			logger.error("Error while getting future thread result with message {}", e.getMessage());
		}
		return CompletableFuture.completedFuture(artist);

	}

	/**
	 * Search for relation type Dicogs and return the id from the URL
	 * 
	 * @param relations
	 * @return Discogs Artist ID
	 */
	private String getDiscogsArtistId(List<Relation> relations) {

		logger.debug("Called DefaultArtistService.getDiscogsArtistId");
		// check that MusicBrainz response contains relations
		if (relations == null)
			return null;

		Optional<Relation> discogsRelation = null;
		discogsRelation = relations.stream().filter(relation -> relation.getType().equalsIgnoreCase("discogs"))
				.findFirst();
		
		String discogsId = null;
		if (discogsRelation.isPresent()) {
			String url = discogsRelation.get().getUrl().getResource();
			// remove last / if found
			if (url.endsWith("/"))
				url = url.substring(0, url.length() - 1);
			// set id
			discogsId = url.substring(url.lastIndexOf('/') + 1);
		}

		return discogsId;
	}
}
