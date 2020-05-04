package com.aabdelmonem.artist.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.aabdelmonem.artist.datatransferobject.AlbumDTO;
import com.aabdelmonem.artist.datatransferobject.ArtistDTO;
import com.aabdelmonem.artist.domain.Artist;
import com.aabdelmonem.artist.domain.ReleaseGroup;

/**
 * Mapper to map artist domain to artist data transfer object
 * 
 * @author ahmed.abdelmonem
 *
 */
public class ArtistMapper {

	/**
	 * private constructor to hide the implicit public one
	 */
	private ArtistMapper() {
	}

	public static ArtistDTO makeArtistDTO(Artist artist) {

		return new ArtistDTO(artist.getId(), artist.getName(), artist.getDescription(),
				ArtistMapper.makeAlbumDTOList(artist.getReleaseGroups()));
	}

	public static AlbumDTO makeAlbumDTO(ReleaseGroup releaseGroup) {

		return new AlbumDTO(releaseGroup.getId(), releaseGroup.getTitle(), releaseGroup.getImage());
	}

	public static List<AlbumDTO> makeAlbumDTOList(List<ReleaseGroup> releaseGroups) {

		return releaseGroups.stream().map(ArtistMapper::makeAlbumDTO).collect(Collectors.toList());
	}
}
