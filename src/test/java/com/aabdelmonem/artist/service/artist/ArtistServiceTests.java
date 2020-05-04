package com.aabdelmonem.artist.service.artist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.aabdelmonem.artist.MusicArtistInfoApplicationTests;
import com.aabdelmonem.artist.domain.Artist;
import com.aabdelmonem.artist.domain.ReleaseGroup;
import com.aabdelmonem.artist.exception.ArtistNotFoundException;

/**
 * Artist service test cases
 * 
 * @author ahmed.abdelmonem
 *
 */
public class ArtistServiceTests extends MusicArtistInfoApplicationTests {

	@Autowired
	private ArtistService artistService;

	/**
	 * 
	 * get Zara Larsson info and check response
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetZaraLarssonInfo_checkResponse() throws Exception {
		// call service for Zara Larsson
		Artist artist = artistService.getArtistInfo("134e6410-6954-45d1-bd4a-0f2d2ad5471d").get();

		assertNotNull(artist);
		// check id
		assertNotNull(artist.getId());
		assertFalse(artist.getId().isEmpty());
		// check description
		assertNotNull(artist.getDescription());
		assertFalse(artist.getDescription().isEmpty());
		assertTrue(artist.getDescription().contains("Sweden"));
		// check release groups
		List<ReleaseGroup> releaseGroups = artist.getReleaseGroups();
		assertNotNull(releaseGroups);
		assertFalse(releaseGroups.isEmpty());
		assertEquals(2, releaseGroups.size());

		releaseGroups.forEach(releaseGroup -> {
			// check id
			assertNotNull(releaseGroup.getId());
			assertFalse(releaseGroup.getId().isEmpty());
			// check title
			assertNotNull(releaseGroup.getTitle());
			assertFalse(releaseGroup.getTitle().isEmpty());
			// check image
			assertNotNull(releaseGroup.getImage());
			assertFalse(releaseGroup.getImage().isEmpty());

			// check values
			switch (releaseGroup.getTitle()) {
			case "Electronic Earth":
				assertEquals("http://coverartarchive.org/release/90e70a26-11cb-43a2-88f2-f49973a935ab/16086190492.jpg",
						releaseGroup.getImage());
				break;
			case "So Good":
				assertEquals("http://coverartarchive.org/release/1535d7f2-6465-4897-be4b-5919194a950a/22065982944.jpg",
						releaseGroup.getImage());
				break;
			default:
				break;
			}
		});
	}

	@Test
	public void testGetNotFoundArtist_checkResponse() throws Exception {
		// try to get not found artist
		try {
			// wait to finish and get result
			artistService.getArtistInfo("DFFFF").get();
			fail();
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof ArtistNotFoundException);
		}
	}
}
