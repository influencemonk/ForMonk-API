package com.ForMonk2.utils;

import org.json.simple.JSONObject;


import org.json.simple.JSONArray;


@SuppressWarnings("unchecked")
public class CrawlerDataParser {

	
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
	
	
	private JSONObject getUserObj(JSONObject mainObj) {
		JSONObject userObj = new JSONObject();
		JSONObject entryData = (JSONObject) mainObj.get("entry_data");
		JSONArray profilePageArr = (JSONArray) entryData.get("ProfilePage");
		JSONObject profilePageObj = (JSONObject) profilePageArr.get(0);
		JSONObject graphQlObj = (JSONObject) profilePageObj.get("graphql");
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
	

}
