package com.aabdelmonem.artist.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.aabdelmonem.artist.MusicArtistInfoApplicationTests;
import com.aabdelmonem.artist.datatransferobject.AlbumDTO;
import com.aabdelmonem.artist.datatransferobject.ArtistDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Artist controller test cases
 * @author ahmed.abdelmonem
 *
 */
public class ArtistControllerTests extends MusicArtistInfoApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * System test
	 * 
	 * get Labrinth info and check response
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetLabrinthInfo_checkResponse() throws Exception {
		// call endpoint for Labrinth
		ArtistDTO artist = getArtistInfoAndCheckHttpResponse("dc99e6fd-c710-4f79-b74b-127b4d0b7849", HttpStatus.OK);

		assertNotNull(artist);
		// check id
		assertNotNull(artist.getMbid());
		assertFalse(artist.getMbid().isEmpty());
		// check description
		assertNotNull(artist.getDescription());
		assertFalse(artist.getDescription().isEmpty());
		assertTrue(artist.getDescription().contains("Labrinth"));
		// check albums
		List<AlbumDTO> albums = artist.getAlbums();
		assertNotNull(albums);
		assertFalse(albums.isEmpty());
		assertEquals(4, albums.size());

		albums.forEach(album -> {
			// check id
			assertNotNull(album.getId());
			assertFalse(album.getId().isEmpty());
			// check title
			assertNotNull(album.getTitle());
			assertFalse(album.getTitle().isEmpty());
			// check image
			assertNotNull(album.getImage());
			assertFalse(album.getImage().isEmpty());

			// check values
			switch (album.getTitle()) {
			case "Electronic Earth":
				assertEquals("http://coverartarchive.org/release/53ab0143-f891-4a38-99c8-53db5bfc4ce8/5892357054.png",
						album.getImage());
				break;
			case "iTunes Festival: London 2012":
				assertEquals("http://coverartarchive.org/release/76ec94af-457c-4f19-b2fe-57be5ea0e6d5/3254080123.jpg",
						album.getImage());
				break;
			default:
				break;
			}
		});
	}

	@Test
	public void testGetNotFoundArtist_checkResponse() throws Exception {
		// call endpoint with not found MBID
		getArtistInfoAndCheckHttpResponse("MJJJJ", HttpStatus.NOT_FOUND);
	}

	/**
	 * call get artist info end point and check the HTTP status response and return
	 * the ArtistDTO object
	 * 
	 * @param mbid
	 * @param expectedStatus
	 * @return ArtistDTO object
	 * @throws Exception
	 */
	private ArtistDTO getArtistInfoAndCheckHttpResponse(String mbid, HttpStatus expectedStatus) throws Exception {
		String uri = "/artist/" + mbid;
		// call endpoint
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get(uri.toString()).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		// check HTTP status
		assertEquals(expectedStatus.value(), response.getStatus());

		if (expectedStatus.equals(HttpStatus.OK)) {
			assertNotNull(response.getContentAsString());
			return stringToJson(response.getContentAsString(), ArtistDTO.class);
		}

		return null;
	}

	protected <T> T stringToJson(String json, Class<T> classToConvert) throws JsonParseException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.readValue(json, classToConvert);
	}

}
