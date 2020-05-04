package com.aabdelmonem.artist.service.coverartarchive;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aabdelmonem.artist.domain.CoverArtArchiveReleaseGroup;

/**
 * Service responsible for calling Cover Art Archive API and return needed info.
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class DefaultCoverArtArchiveService implements CoverArtArchiveService{

	private static final Logger logger = LoggerFactory.getLogger(DefaultCoverArtArchiveService.class);

	@Value("${coverArtArchive.api.url}")
	private String coverArtArchiveApiUrl;

	private RestTemplate restTemplate;

	@Autowired
	public DefaultCoverArtArchiveService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	/**
	 * This method will run in a separate thread using spring @Async with executor apiAsyncExecutor (RestTemplate
	 * is thread safe)
	 * 
	 * @param releaseGroupMbid
	 * @return CompletableFuture of string (image URL)
	 */
	@Async("apiAsyncExecutor")
	@Override
	public CompletableFuture<String> getAlbumCoverArtImage(String releaseGroupMbid) {
		logger.debug("Called CoverArtArchiveService.getAlbumCoverArtImage with albumId :{}", releaseGroupMbid);
		try {
			// call Cover Art Archive API
			CoverArtArchiveReleaseGroup releaseGroup = null;
			releaseGroup = restTemplate.getForObject(coverArtArchiveApiUrl + "release-group/" + releaseGroupMbid,
					CoverArtArchiveReleaseGroup.class);

			if (releaseGroup.getImages() != null && !releaseGroup.getImages().isEmpty()) {
				return CompletableFuture.completedFuture(releaseGroup.getImages().get(0).getImage());
			}
		} catch (Exception e) {
			// non blocking service - just logging and return the default value
			logger.error("Error while calling Cover Art Archive API with message :{}", e.getMessage());
		}
		// default return
		return CompletableFuture.completedFuture("No-Cover-Image");
	}
}
