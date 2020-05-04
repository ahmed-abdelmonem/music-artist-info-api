package com.aabdelmonem.artist.service.musicbrainz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.aabdelmonem.artist.domain.Artist;
import com.aabdelmonem.artist.exception.ArtistNotFoundException;
import com.aabdelmonem.artist.exception.ServiceNotAvailableException;

/**
 * Service responsible for calling MusicBrainz API.
 * 
 * @author ahmed.abdelmonem
 */
@Service
public class DefaultMusicBrainzService implements MusicBrainzService{

	private static final Logger logger = LoggerFactory.getLogger(DefaultMusicBrainzService.class);

	@Value("${musicBrainz.api.url}")
	private String musicBrainzApiUrl;

	private RestTemplate restTemplate;

	@Autowired
	public DefaultMusicBrainzService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	/**
	 * This method will be called from the main artist info thread
	 * 
	 * @param mbid
	 * @return artist object filled from MusicBrainz response
	 * @throws ArtistNotFoundException
	 * @throws ServiceNotAvailableException
	 */
	@Override
	public Artist getMusicBrainzArtistDetails(String mbid)
			throws ArtistNotFoundException, ServiceNotAvailableException {
		try {
			logger.debug("Called MusicBrainzService.getMusicBrainzArtistDetails with MBID :{}", mbid);
			// query artist with relations, release groups and filter by album type
			String url = musicBrainzApiUrl + "artist/" + mbid + "?&fmt=json&inc=url-rels+release-groups&type=album";
			// call MusicBrainz API
			return restTemplate.getForObject(url, Artist.class);

		} catch (HttpClientErrorException ce) {
			logger.error("HttpClientErrorException while calling MusicBrainz API with message :{}", ce.getMessage());
			throw new ArtistNotFoundException();
		} catch (HttpServerErrorException se) {
			logger.error("HttpServerErrorException while calling MusicBrainz API with message :{}", se.getMessage());
			throw new ServiceNotAvailableException();
		}

	}
}
