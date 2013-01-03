package ch.ownz.s4m.sonos.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an immutable, playable entry in the zone player library.
 * 
 * @author altery
 */
public class Entry implements Serializable, Comparable<Entry> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Entry.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1987394879345l;

	/** The id. */
	private final String id;

	/** The title. */
	private final String title;

	/** The parent id. */
	private final String parentId;

	/** The upnp class. */
	private final String upnpClass;

	/** The res. */
	private final String res;

	/** The album. */
	private final String album;

	/** The album art uri. */
	private final String albumArtUri;

	/** The album art uri. */
	private final String albumArtist;

	/** The creator. */
	private final String creator;

	/** The original track number. */
	private final int originalTrackNumber;

	/**
	 * Instantiates a new entry.
	 * 
	 * @param id
	 *            the id
	 * @param title
	 *            the title
	 * @param parentId
	 *            the parent id
	 * @param album
	 *            the album
	 * @param albumArtUri
	 *            the album art uri
	 * @param creator
	 *            the creator
	 * @param upnpClass
	 *            the upnp class
	 * @param res
	 *            the res
	 * @param originalTrackNumber
	 *            the original track number
	 */
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

	/**
	 * Instantiates a new entry.
	 * 
	 * @param id
	 *            the id
	 * @param title
	 *            the title
	 * @param parentId
	 *            the parent id
	 * @param album
	 *            the album
	 * @param albumArtUri
	 *            the album art uri
	 * @param creator
	 *            the creator
	 * @param upnpClass
	 *            the upnp class
	 * @param res
	 *            the res
	 */
	public Entry(final String id, final String title, final String parentId, final String album,
			final String albumArtUri, final String creator, final String upnpClass, final String res,
			final String albumArtist) {
		this(id, title, parentId, album, albumArtUri, creator, upnpClass, res, -1, albumArtist);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final Entry o) {
		return new CompareToBuilder().append(getTitle(), o.getTitle()).append(getAlbum(), o.getAlbum())
				.append(getCreator(), o.getCreator()).append(getOriginalTrackNumber(), o.getOriginalTrackNumber())
				.append(getAlbumArtUri(), o.getAlbumArtUri()).build();
	}

	/**
	 * Gets the album.
	 * 
	 * @return the name of the album.
	 */
	public String getAlbum() {
		return this.album;
	}

	/**
	 * Gets the album artist.
	 * 
	 * @return the album artist
	 */
	public String getAlbumArtist() {
		return this.albumArtist;
	}

	/**
	 * Gets the album art uri.
	 * 
	 * @return the URI for the album art.
	 */
	public String getAlbumArtUri() {
		return StringEscapeUtils.unescapeXml(this.albumArtUri);
	}

	/**
	 * Gets the creator.
	 * 
	 * @return the name of the artist who created the entry.
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the unique identifier of this entry.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Gets the original track number.
	 * 
	 * @return the original track number
	 */
	public int getOriginalTrackNumber() {
		return this.originalTrackNumber;
	}

	/**
	 * Gets the parent id.
	 * 
	 * @return the unique identifier of the parent of this entry.
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * Gets the res.
	 * 
	 * @return a URI of this entry.
	 */
	public String getRes() {
		return this.res;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title of the entry.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Gets the upnp class.
	 * 
	 * @return the UPnP classname for this entry.
	 */
	public String getUpnpClass() {
		return this.upnpClass;
	}

	/**
	 * To string.
	 * 
	 * @return the title of the entry.
	 */
	@Override
	public String toString() {
		return this.title;
	}

}
