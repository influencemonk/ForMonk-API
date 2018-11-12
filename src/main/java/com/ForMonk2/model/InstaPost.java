package com.ForMonk2.model;

import java.util.List;

public class InstaPost {
	
	private String id;
	private String shortcode;
	private String displayUrl;
	private boolean isVideo;
	private String trackingToken;
	private long timestamp;
	private String thumbnailSrc;
	private long mediaLikes;
	private long mediaComments;
	private String caption;
	private boolean isCommentsDisabled;
	private List<InstaComment> comments;
	
	
	public InstaPost(String id, String shortcode, String displayUrl, boolean isVideo, String trackingToken,
			long timestamp, String thumbnailSrc, long mediaLikes, long mediaComments, String caption,
			boolean isCommentsDisabled, List<InstaComment> comments) {
		super();
		this.id = id;
		this.shortcode = shortcode;
		this.displayUrl = displayUrl;
		this.isVideo = isVideo;
		this.trackingToken = trackingToken;
		this.timestamp = timestamp;
		this.thumbnailSrc = thumbnailSrc;
		this.mediaLikes = mediaLikes;
		this.mediaComments = mediaComments;
		this.caption = caption;
		this.isCommentsDisabled = isCommentsDisabled;
		this.comments = comments;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getShortcode() {
		return shortcode;
	}


	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}


	public String getDisplayUrl() {
		return displayUrl;
	}


	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}


	public boolean isVideo() {
		return isVideo;
	}


	public void setVideo(boolean isVideo) {
		this.isVideo = isVideo;
	}


	public String getTrackingToken() {
		return trackingToken;
	}


	public void setTrackingToken(String trackingToken) {
		this.trackingToken = trackingToken;
	}


	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public String getThumbnailSrc() {
		return thumbnailSrc;
	}


	public void setThumbnailSrc(String thumbnailSrc) {
		this.thumbnailSrc = thumbnailSrc;
	}


	public long getMediaLikes() {
		return mediaLikes;
	}


	public void setMediaLikes(long mediaLikes) {
		this.mediaLikes = mediaLikes;
	}


	public long getMediaComments() {
		return mediaComments;
	}


	public void setMediaComments(long mediaComments) {
		this.mediaComments = mediaComments;
	}


	public String getCaption() {
		return caption;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}


	public boolean isCommentsDisabled() {
		return isCommentsDisabled;
	}


	public void setCommentsDisabled(boolean isCommentsDisabled) {
		this.isCommentsDisabled = isCommentsDisabled;
	}


	public List<InstaComment> getComments() {
		return comments;
	}


	public void setComments(List<InstaComment> comments) {
		this.comments = comments;
	}
	

}
