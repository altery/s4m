package ch.ownz.s4m.sonos.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * Represents an immutable, playable entry in the zone player library.
 * 
 * @author altery
 */
public class Entry implements Serializable, Comparable<Entry> {

	private static final long serialVersionUID = -6210589841294627006L;

	private final String id;

	private final String title;

	private final String parentId;

	private final String upnpClass;

	private final String res;

	private final String album;

	private final String albumArtUri;

	private final String albumArtist;

	private final String creator;

	private final int originalTrackNumber;

	public Entry(final String id, final String title, final String parentId, final String album,
			final String albumArtUri, final String creator, final String upnpClass, final String res,
			final int originalTrackNumber, final String albumArtist) {
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.album = album;
		this.albumArtist = albumArtist;
		this.albumArtUri = albumArtUri;
		this.creator = creator;
		this.upnpClass = upnpClass;
		this.res = res;
		this.originalTrackNumber = originalTrackNumber;
	}

	public Entry(final String id, final String title, final String parentId, final String album,
			final String albumArtUri, final String creator, final String upnpClass, final String res,
			final String albumArtist) {
		this(id, title, parentId, album, albumArtUri, creator, upnpClass, res, -1, albumArtist);
	}

	@Override
	public int compareTo(final Entry o) {
		return new CompareToBuilder().append(getTitle(), o.getTitle()).append(getAlbum(), o.getAlbum())
				.append(getCreator(), o.getCreator()).append(getOriginalTrackNumber(), o.getOriginalTrackNumber())
				.append(getAlbumArtUri(), o.getAlbumArtUri()).build();
	}

	public String getAlbum() {
		return this.album;
	}

	public String getAlbumArtist() {
		return this.albumArtist;
	}

	public String getAlbumArtUri() {
		return StringEscapeUtils.unescapeXml(this.albumArtUri);
	}

	public String getCreator() {
		return this.creator;
	}

	public String getId() {
		return this.id;
	}

	public int getOriginalTrackNumber() {
		return this.originalTrackNumber;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getRes() {
		return this.res;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUpnpClass() {
		return this.upnpClass;
	}

}
