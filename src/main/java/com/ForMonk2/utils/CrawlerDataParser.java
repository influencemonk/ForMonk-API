package com.ForMonk2.utils;

import org.json.simple.JSONObject;

import com.ForMonk2.utils.Constants.INSTA_SCRAPER.DataSource;

import java.util.Collections;
import java.util.TreeMap;

import org.json.simple.JSONArray;


@SuppressWarnings("unchecked")
public class CrawlerDataParser {

	/**
	 * Method to parse profile info from scraper response data
	 */
	public JSONObject getProfileInfo(JSONObject mainObj) {
		JSONObject profileInfoObj = new JSONObject();
		
		JSONObject	userObj = getUserObj(mainObj);
		
		profileInfoObj.put("username", userObj.get("username"));
		profileInfoObj.put("business_email", userObj.get("business_email"));
		profileInfoObj.put("full_name", userObj.get("full_name"));
		profileInfoObj.put("is_joined_recently", userObj.get("is_joined_recently"));
		profileInfoObj.put("is_verified", userObj.get("is_verified"));
		profileInfoObj.put("is_business_account", userObj.get("is_business_account"));
		profileInfoObj.put("id", userObj.get("id"));
		profileInfoObj.put("external_url", userObj.get("external_url"));
		profileInfoObj.put("business_category_name", userObj.get("business_category_name"));
		profileInfoObj.put("business_phone_number", userObj.get("business_phone_number"));
		profileInfoObj.put("biography", userObj.get("biography"));
		profileInfoObj.put("connected_fb_page", userObj.get("connected_fb_page"));
		profileInfoObj.put("profile_pic_url", userObj.get("profile_pic_url"));
		profileInfoObj.put("profile_pic_url_hd", userObj.get("profile_pic_url_hd"));
		profileInfoObj.put("is_private", userObj.get("is_private"));
		
		JSONObject edgeFollowObj = (JSONObject) userObj.get("edge_follow");
		profileInfoObj.put("following", edgeFollowObj.get("count"));
		
		JSONObject edgeFollowedByObj = (JSONObject) userObj.get("edge_followed_by");
		profileInfoObj.put("followed_by", edgeFollowedByObj.get("count"));
		
		JSONObject edgeTimelineMediaObj = (JSONObject) userObj.get("edge_owner_to_timeline_media");
		profileInfoObj.put("media_count", edgeTimelineMediaObj.get("count"));
		
		return profileInfoObj;
	}
	
	
	/**
	 * Method to parse user object from scraper response data
	 */
	private JSONObject getUserObj(JSONObject mainObj) {
		JSONObject userObj = new JSONObject();
		JSONObject entryData = (JSONObject) mainObj.get("entry_data");
		JSONArray profilePageArr = (JSONArray) entryData.get("ProfilePage");
		JSONObject profilePageObj = (JSONObject) profilePageArr.get(0);
		JSONObject graphQlObj = (JSONObject) profilePageObj.get("graphql");
		userObj = (JSONObject) graphQlObj.get("user");
		return userObj;
	}
	
	
	/**
	 * Method to parse profile info from Instagram GraphQL API
	 */
	public JSONObject getProfileInfoGraphQl(JSONObject mainObj) {
		JSONObject profileInfoObj = new JSONObject();
		
		JSONObject	userObj = getUserObjGraphQl(mainObj);
		
		profileInfoObj.put("username", userObj.get("username"));
		profileInfoObj.put("id", userObj.get("id"));
		profileInfoObj.put("is_private", userObj.get("is_private"));
		
		JSONObject edgeFollowObj = (JSONObject) userObj.get("edge_follow");
		profileInfoObj.put("following", edgeFollowObj.get("count"));
		
		JSONObject edgeFollowedByObj = (JSONObject) userObj.get("edge_followed_by");
		profileInfoObj.put("followed_by", edgeFollowedByObj.get("count"));
		
		JSONObject edgeTimelineMediaObj = (JSONObject) userObj.get("edge_owner_to_timeline_media");
		profileInfoObj.put("media_count", edgeTimelineMediaObj.get("count"));
		
		return profileInfoObj;
	}
	
	/**
	 * Method to parse user object from Instagram GraphQL API
	 */
	private JSONObject getUserObjGraphQl(JSONObject mainObj) {
		JSONObject userObj = new JSONObject();
		JSONObject graphQlObj = (JSONObject) mainObj.get("graphql");
		userObj = (JSONObject) graphQlObj.get("user");
		return userObj;
	}


	public JSONObject getPostsInfo(JSONObject mainPostsObj) {
		JSONObject postsInfoPageObj = new JSONObject();
		
		JSONArray postsInfoArr = new JSONArray();
		
		JSONObject dataObj = (JSONObject) mainPostsObj.get("data");
		JSONObject userObj = (JSONObject) dataObj.get("user");
		JSONObject edgeTimelineMedia = (JSONObject) userObj.get("edge_owner_to_timeline_media");
		
		JSONObject pageInfoObj = (JSONObject) edgeTimelineMedia.get("page_info");
		postsInfoPageObj.put("has_next_page", pageInfoObj.get("has_next_page"));
		postsInfoPageObj.put("end_cursor", pageInfoObj.get("end_cursor"));
		
		JSONArray edgesArray = (JSONArray) edgeTimelineMedia.get("edges");
		
		for(int i = 0; i < edgesArray.size(); i++) {
			JSONObject postObj = new JSONObject();
			JSONObject edge = (JSONObject) edgesArray.get(i);
			JSONObject nodeObj = (JSONObject) edge.get("node");
			
			postObj.put("id", nodeObj.get("id"));
			System.out.println("ID: "  + nodeObj.get("id"));
			postObj.put("display_url", nodeObj.get("display_url"));
			postObj.put("is_video", nodeObj.get("is_video"));
			postObj.put("shortcode", nodeObj.get("shortcode"));
			postObj.put("taken_at_timestamp", nodeObj.get("taken_at_timestamp"));
			postObj.put("thumbnail_src", nodeObj.get("thumbnail_src"));
			postObj.put("tracking_token", nodeObj.get("tracking_token"));
			
			JSONObject edgeMediaLikesObj = (JSONObject) nodeObj.get("edge_media_preview_like");
			postObj.put("likes", edgeMediaLikesObj.get("count"));
			
			JSONObject edgeMediaCommentsObj = (JSONObject) nodeObj.get("edge_media_to_comment");
			postObj.put("comments", edgeMediaCommentsObj.get("count"));
			
			JSONObject edgeMediaCaptionObj = (JSONObject) nodeObj.get("edge_media_to_caption");
			JSONArray captionEdgesArr = (JSONArray) edgeMediaCaptionObj.get("edges");
			if(captionEdgesArr.size() > 0) {
				JSONObject captionEdgeObj = (JSONObject) captionEdgesArr.get(0);
				JSONObject captionNodeObj = (JSONObject) captionEdgeObj.get("node");
				postObj.put("caption", captionNodeObj.get("text"));
			}
			else {
				postObj.put("caption", null);
			}
			
			postsInfoArr.add(postObj);
			
		}
		
		postsInfoPageObj.put("posts_data", postsInfoArr);
		
		return postsInfoPageObj;
	}
	
	public JSONObject getCommentsInfo(JSONObject mainCommentsObj) {
		JSONObject commentsInfoPageObj = new JSONObject();
		
		JSONArray commentsArr = new JSONArray();
		
		JSONObject dataObj = (JSONObject) mainCommentsObj.get("data");
		JSONObject shortcodeMediaObj = (JSONObject) dataObj.get("shortcode_media");
		JSONObject edgeMediaCommentObj = (JSONObject) shortcodeMediaObj.get("edge_media_to_comment");
		JSONObject pageInfoObj = (JSONObject) edgeMediaCommentObj.get("page_info");
		
		commentsInfoPageObj.put("has_next_page", pageInfoObj.get("has_next_page"));
		commentsInfoPageObj.put("end_cursor", pageInfoObj.get("end_cursor"));
		
		JSONArray commentsEdgesArr = (JSONArray) edgeMediaCommentObj.get("edges");
		
		for(int i = 0; i< commentsEdgesArr.size(); i++) {
			JSONObject commentEdge = (JSONObject) commentsEdgesArr.get(i);
			JSONObject commentObj = new JSONObject();
			
			JSONObject commentNode = (JSONObject) commentEdge.get("node");
			
			commentObj.put("id", commentNode.get("id"));
			commentObj.put("text", commentNode.get("text"));
			commentObj.put("created_at", commentNode.get("created_at"));
			
			// Parse comment owner's data:
			JSONObject commentOwnerObj = (JSONObject) commentNode.get("owner");
			commentObj.put("owner_id", commentOwnerObj.get("id"));
			commentObj.put("owner_username", commentOwnerObj.get("username"));
			
			commentsArr.add(commentObj);
		}
		
		commentsInfoPageObj.put("comments_data", commentsArr);
		
		return commentsInfoPageObj;
	}
	
	
	/**
	 * Method to get Instant Profile Data of top 12 posts of a user
	 */
	public JSONObject getInstantProfileInfo(JSONObject mainObj) {
		
		JSONObject profileInfoObj = new JSONObject();
		
		JSONObject	userObj = getUserObj(mainObj);
		
		profileInfoObj.put("username", userObj.get("username"));
		profileInfoObj.put("business_email", userObj.get("business_email"));
		profileInfoObj.put("full_name", userObj.get("full_name"));
		profileInfoObj.put("is_joined_recently", userObj.get("is_joined_recently"));
		profileInfoObj.put("is_verified", userObj.get("is_verified"));
		profileInfoObj.put("is_business_account", userObj.get("is_business_account"));
		profileInfoObj.put("id", userObj.get("id"));
		profileInfoObj.put("external_url", userObj.get("external_url"));
		profileInfoObj.put("business_category_name", userObj.get("business_category_name"));
		profileInfoObj.put("business_phone_number", userObj.get("business_phone_number"));
		profileInfoObj.put("biography", userObj.get("biography"));
		profileInfoObj.put("connected_fb_page", userObj.get("connected_fb_page"));
		profileInfoObj.put("profile_pic_url", userObj.get("profile_pic_url"));
		profileInfoObj.put("profile_pic_url_hd", userObj.get("profile_pic_url_hd"));
		profileInfoObj.put("is_private", userObj.get("is_private"));
		
		JSONObject edgeFollowObj = (JSONObject) userObj.get("edge_follow");
		profileInfoObj.put("following", edgeFollowObj.get("count"));
		
		JSONObject edgeFollowedByObj = (JSONObject) userObj.get("edge_followed_by");
		profileInfoObj.put("followed_by", edgeFollowedByObj.get("count"));
		long followedBy = (long) edgeFollowedByObj.get("count");
		
		JSONObject edgeTimelineMediaObj = (JSONObject) userObj.get("edge_owner_to_timeline_media");
		profileInfoObj.put("media_count", edgeTimelineMediaObj.get("count"));
		
		boolean isPrivateProfile = (boolean) userObj.get("is_private");

		
		long totalLikes = 0;
		long totalComments = 0;
		
		float avgLikes = -1;
		float avgComments = -1;
		
		float engagementRate = -1;
		
		if(!isPrivateProfile) {
		
			JSONArray mediaEdgesArr = (JSONArray) edgeTimelineMediaObj.get("edges");
			
			JSONArray postDataArr = new JSONArray();
	
			
			for(int i = 0; i < mediaEdgesArr.size(); i++) {
				JSONObject mediaEdgeObj = (JSONObject) mediaEdgesArr.get(i);
				JSONObject postData = new JSONObject();
				
				JSONObject mediaNodeObj = (JSONObject) mediaEdgeObj.get("node");
				
				postData.put("id", mediaNodeObj.get("id"));
				postData.put("taken_at_timestamp", mediaNodeObj.get("taken_at_timestamp"));
				postData.put("thumbnail_src", mediaNodeObj.get("thumbnail_src"));
				postData.put("is_video", mediaNodeObj.get("is_video"));
				
				JSONObject edgeLikesObj = (JSONObject) mediaNodeObj.get("edge_liked_by");
				postData.put("liked_by", edgeLikesObj.get("count"));
				totalLikes += (long) edgeLikesObj.get("count");
				
				JSONObject edgeCommentsObj = (JSONObject) mediaNodeObj.get("edge_media_to_comment");
				postData.put("comments", edgeCommentsObj.get("count"));
				totalComments += (long) edgeCommentsObj.get("count");
				
				JSONObject edgeCaptionObj = (JSONObject) mediaNodeObj.get("edge_media_to_caption");
				JSONArray captionEdgesArr = (JSONArray) edgeCaptionObj.get("edges");
				
				if(captionEdgesArr.size() > 0) {
					JSONObject captionEdgeObj = (JSONObject) captionEdgesArr.get(0);
					JSONObject captionEdgeNodeObj = (JSONObject) captionEdgeObj.get("node");
					postData.put("caption", captionEdgeNodeObj.get("text"));
				}
				else {
					postData.put("caption", null);
				}
				
				if(mediaNodeObj.get("location") != null) {
					JSONObject locationObj = (JSONObject) mediaNodeObj.get("location");
					
					if(locationObj.get("name") != null) {
						postData.put("location", locationObj.get("name"));
					}
					else {
						postData.put("location", null);
					}
				}
				else {
					postData.put("location", null);
				}
				
				postDataArr.add(postData);
			}
			
			
			profileInfoObj.put("post_data", postDataArr);
			avgLikes = totalLikes / mediaEdgesArr.size();
			avgComments = totalComments / mediaEdgesArr.size();
			
			engagementRate = ((avgLikes + avgComments) / followedBy) * 100;
			
		}
		else {
			profileInfoObj.put("post_data", null);
			
		}
		
		
		profileInfoObj.put("avg_likes", avgLikes);
		profileInfoObj.put("avg_comments", avgComments);
		
		profileInfoObj.put("engagement_rate", engagementRate);
		
		
		return profileInfoObj;
	}
	
	/**
	 * Method to parse profile summary from Instagram GraphQL API
	 */
	public JSONObject getPostSummaryInfo(JSONObject mainPostsObj, int maxPosts, DataSource source) {
		
		JSONObject profileInfoObj = new JSONObject();
		JSONObject	userObj = null;
		if(source == Constants.INSTA_SCRAPER.DataSource.graphql) {
			userObj = getUserObjGraphQl(mainPostsObj);
		}
		else {
			userObj = getUserObj(mainPostsObj);
		}
		
		profileInfoObj.put("username", userObj.get("username"));
		profileInfoObj.put("business_email", userObj.get("business_email"));
		profileInfoObj.put("full_name", userObj.get("full_name"));
		profileInfoObj.put("is_joined_recently", userObj.get("is_joined_recently"));
		profileInfoObj.put("is_verified", userObj.get("is_verified"));
		profileInfoObj.put("is_business_account", userObj.get("is_business_account"));
		profileInfoObj.put("id", userObj.get("id"));
		profileInfoObj.put("external_url", userObj.get("external_url"));
		profileInfoObj.put("business_category_name", userObj.get("business_category_name"));
		profileInfoObj.put("business_phone_number", userObj.get("business_phone_number"));
		profileInfoObj.put("biography", userObj.get("biography"));
		profileInfoObj.put("connected_fb_page", userObj.get("connected_fb_page"));
		profileInfoObj.put("profile_pic_url", userObj.get("profile_pic_url"));
		//profileInfoObj.put("profile_pic_url_hd", userObj.get("profile_pic_url_hd"));
		profileInfoObj.put("is_private", userObj.get("is_private"));
		
		JSONObject edgeFollowObj = (JSONObject) userObj.get("edge_follow");
		profileInfoObj.put("following", edgeFollowObj.get("count"));
		
		JSONObject edgeFollowedByObj = (JSONObject) userObj.get("edge_followed_by");
		profileInfoObj.put("followed_by", edgeFollowedByObj.get("count"));
		long followedBy = (long) edgeFollowedByObj.get("count");
		
		JSONObject edgeTimelineMediaObj = (JSONObject) userObj.get("edge_owner_to_timeline_media");
		profileInfoObj.put("media_count", edgeTimelineMediaObj.get("count"));
		
		boolean isPrivateProfile = (boolean) userObj.get("is_private");

		
		long totalLikes = 0;
		long totalComments = 0;
		
		float avgLikes = -1;
		float avgComments = -1;
		
		float engagementRate = -1;
		
		if(!isPrivateProfile) {
			
			JSONArray postDataArr = new JSONArray();
		
			JSONArray mediaEdgesArr = (JSONArray) edgeTimelineMediaObj.get("edges");
			
			int loopLimit = maxPosts < mediaEdgesArr.size() ? maxPosts : mediaEdgesArr.size();
			
			System.out.println("Loop Limit: "+loopLimit);
			if(loopLimit > 0) {
				for(int i = 0; i < loopLimit; i++) {
					JSONObject mediaEdgeObj = (JSONObject) mediaEdgesArr.get(i);
					JSONObject postData = new JSONObject();
					
					JSONObject mediaNodeObj = (JSONObject) mediaEdgeObj.get("node");
					
					postData.put("taken_at_timestamp", mediaNodeObj.get("taken_at_timestamp"));
	
					JSONObject edgeLikesObj = (JSONObject) mediaNodeObj.get("edge_liked_by");
					postData.put("liked_by", edgeLikesObj.get("count"));
					totalLikes += (long) edgeLikesObj.get("count");
					
					JSONObject edgeCommentsObj = (JSONObject) mediaNodeObj.get("edge_media_to_comment");
					postData.put("comments", edgeCommentsObj.get("count"));
					totalComments += (long) edgeCommentsObj.get("count");
					
					postDataArr.add(postData);
	
				}
			
				profileInfoObj.put("post_data", postDataArr);
				
				avgLikes = totalLikes / loopLimit;
				avgComments = totalComments / loopLimit;
				
				engagementRate = ((avgLikes + avgComments) / followedBy) * 100;
				
			}
			
			
		}
		else {
			profileInfoObj.put("post_data", null);
			
		}
		
		
		profileInfoObj.put("avg_likes", avgLikes);
		profileInfoObj.put("avg_comments", avgComments);
		
		profileInfoObj.put("engagement_rate", engagementRate);
		
		
		return profileInfoObj;
		
	}
	
	
	/**
	 * Method to parse full profile analytics data from Instagram GraphQL API
	 */
	public JSONObject getFullProfileAnalytics(JSONObject mainPostsObj) {
		JSONObject postsInfoPageObj = new JSONObject();
		
		JSONArray postsInfoArr = new JSONArray();
		
		JSONObject dataObj = (JSONObject) mainPostsObj.get("data");
		JSONObject userObj = (JSONObject) dataObj.get("user");
		JSONObject edgeTimelineMedia = (JSONObject) userObj.get("edge_owner_to_timeline_media");
		//postsInfoPageObj.put("media_count", edgeTimelineMedia.get("count"));
		
		JSONObject pageInfoObj = (JSONObject) edgeTimelineMedia.get("page_info");
		postsInfoPageObj.put("has_next_page", pageInfoObj.get("has_next_page"));
		postsInfoPageObj.put("end_cursor", pageInfoObj.get("end_cursor"));
		
		JSONArray edgesArray = (JSONArray) edgeTimelineMedia.get("edges");
		
		long totalLikes = 0;
		long totalComments = 0;
		
		TreeMap<Long, JSONObject> postsMap = new TreeMap<Long, JSONObject>(Collections.reverseOrder()); 
		
		for(int i = 0; i < edgesArray.size(); i++) {
			JSONObject postObj = new JSONObject();
			JSONObject edge = (JSONObject) edgesArray.get(i);
			JSONObject nodeObj = (JSONObject) edge.get("node");
			
			//postObj.put("id", nodeObj.get("id"));
			//System.out.println("ID: "  + nodeObj.get("id"));
			//postObj.put("display_url", nodeObj.get("display_url"));
			//postObj.put("is_video", nodeObj.get("is_video"));
			//postObj.put("shortcode", nodeObj.get("shortcode"));
			postObj.put("taken_at_timestamp", nodeObj.get("taken_at_timestamp"));
			//postObj.put("thumbnail_src", nodeObj.get("thumbnail_src"));
			//postObj.put("tracking_token", nodeObj.get("tracking_token"));
			
			JSONObject edgeMediaLikesObj = (JSONObject) nodeObj.get("edge_media_preview_like");
			long likes = (long) edgeMediaLikesObj.get("count");
			postObj.put("likes", likes);
			totalLikes += likes;
			
			JSONObject edgeMediaCommentsObj = (JSONObject) nodeObj.get("edge_media_to_comment");
			long comments = (long) edgeMediaCommentsObj.get("count");
			postObj.put("comments", comments);
			totalComments += comments;
			
			// Add limited data for regular analytics
			postsInfoArr.add(postObj);
			
			
			// Add other post related data for top posts
			postObj.put("display_url", nodeObj.get("display_url"));
			postObj.put("is_video", nodeObj.get("is_video"));
			
			boolean isVideo = (boolean) nodeObj.get("is_video");
			
			JSONObject edgeMediaCaptionObj = (JSONObject) nodeObj.get("edge_media_to_caption");
			JSONArray captionEdgesArr = (JSONArray) edgeMediaCaptionObj.get("edges");
			if(captionEdgesArr.size() > 0) {
				JSONObject captionEdgeObj = (JSONObject) captionEdgesArr.get(0);
				
				if(null != captionEdgeObj.get("node")) {
					JSONObject captionNodeObj = (JSONObject) captionEdgeObj.get("node");
					
					if(null != captionNodeObj.get("text")) {
						postObj.put("caption", captionNodeObj.get("text"));
					}
				}
			}
			else {
				postObj.put("caption", null);
			}
			
			if(!isVideo) {
				postsMap.put(likes + comments, postObj);
			}
			
		}
		
		postsInfoPageObj.put("posts_data", postsInfoArr);
		postsInfoPageObj.put("page_total_likes", totalLikes);
		postsInfoPageObj.put("page_total_comments", totalComments);
		postsInfoPageObj.put("page_posts_map", postsMap);
		
		return postsInfoPageObj;
	}

	

}
