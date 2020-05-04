package com.aabdelmonem.artist.service.discogs;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aabdelmonem.artist.domain.DiscogsArtist;

/**
 * Service responsible for calling Discogs API and return needed info.
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class DefaultDiscogsService implements DiscogsService{

	private static final Logger logger = LoggerFactory.getLogger(DefaultDiscogsService.class);

	@Value("${discogs.api.url}")
	private String discogsApiUrl;

	private RestTemplate restTemplate;

	@Autowired
	public DefaultDiscogsService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	/**
	 * This method will run in a separate thread using spring @Async (RestTemplate
	 * is thread safe)
	 * 
	 * @param artistId
	 * @return CompletableFuture of string (artist description)
	 */
	@Async("apiAsyncExecutor")
	@Override
	public CompletableFuture<String> getArtistProfile(String artistId) {
		logger.debug("Called DiscogsService.getArtistProfile with artistId :{}", artistId);
		String profile = "";
		try {
			// call Discogs API
			profile = restTemplate.getForObject(discogsApiUrl + "artists/" + artistId, DiscogsArtist.class).getProfile();

		} catch (Exception e) {
			// non blocking - just log and return empty description
			logger.error("Error while calling Discogs API with message :{}", e.getMessage());
		}
		return CompletableFuture.completedFuture(profile);
	}
}
