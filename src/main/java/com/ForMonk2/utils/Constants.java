
package com.ForMonk2.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Constants {
	
	public final static String DB_HOST = "ds143603.mlab.com";
	public final static int DB_PORT = 43603;
	public final static String MAIN_ENDPOINT = "https://influencemonk.com";
	
	public final static class DB_CREDENTIALS {
		
		public final static String 	USERNAME = "android23235616";
		public final static String PASSWORD = "wart414ways465";
		public final static String DB_NAME = "infodb";
		public final static String USER1_CRED = "mongodb://android23235616:wart414ways465@ds143603.mlab.com:43603/infodb";
	}
	
	public final static class INSTAGRAM_CONSTANTS{
		
		public final static String BASE_URL = "https://api.instagram.com/oauth/authorize";
		
		public final static String ACCESS_TOKEN_BASE_URL = "https://api.instagram.com/oauth/access_token";
		
		public final static String CLIENT_ID = "3e7317c616f74b89840af96f97e7a56b";
		
		public final static String REDIRECT_URL = "http://localhost:8080/instaCallbackUrl";
		
		public final static String CLIENT_SECRET = "4057a80cc984462fbe47049b2a99fdd1";
		
		public final static String RESPONSE_TYPE = "code";
		
		public final static String SCOPE = "basic+comments+relationships+likes+follower_list";
		
		public final static String GRANT_TYPE = "authorization_code";
		
		public static Map<String, Object> getAuthTokenQuery() {

	        return Collections.unmodifiableMap(new HashMap<String, String>() {
	         
				private static final long serialVersionUID = 1L;

				{
	               put("client_id" , CLIENT_ID);
	               put("redirect_uri" , REDIRECT_URL);
	               put("response_type" , RESPONSE_TYPE);
	               put("scope" , SCOPE) ;
	               
	            }
	        });
		
	
		}	
	
	
	}
	
	public final static class CRAWLER_CONSTANTS {
		public static final String CRAWLER_URL = "http://influencemonk.com/insta-data/parser.php";
		
	}
	
	public final static class INSTA_SCRAPER {
		
		public enum ApiUser {
			imWeb, diet, getics
		}
		
		public static enum DataSource {
			graphql, im_parser
		}
		
		public static int COOKIE_VAR_INDEX = 0;
		
		public static String[] cookieVarArr = {"7XCbIaBY6FhSfhg6k_2hPQSyIajpzqdQdidTc_mukXM", "UuP4LfQMkEsW3laqrut4T77wS8RcpShS2EMle2o0YT0",
												"zamDmstMfIZ_S9mCaO7iMGL0Z-df7ErNdLTIJSVAE-k", "fK3QvsHIrx1EwvW8RKGMK-DKbtKQc5escIgbJrUDtBw",
												"OE086zprwPtnYbIbWedtmUX10dt2_Uepl96E7O6p3cE", "4LhXor-o9RUzzk_QZK3joa91JrzauNaR__S8Ua22FqY",
												"OdcI6KU6PUHVba3N1sJnrDzYNunVN5EUGhipuARC0L4", "Jfj6Rknjd14ivPgoxD1A1yLqheY6ZQipyvv4_Uw9QZU",
												"2WEYXho5eoRPgVG-INnn9YSVvPpswCPWfiYu-GEPeao", "X8m6VGRva6pY9H__ZhmbpzZG29RNiqovcTY4l-VLX3E"};
		
		public static String[] cookie = {
			"mid=XBkOEAALAAFL-jDQiryAv8m_5Gma; mcd=3; csrftoken=hQN3Bzpf4BTFckhB82qK16MFpjNzD1rL; ds_user_id=9869424602; sessionid=9869424602%3AvyEK6gxpKdm0S7%3A10; rur=ATN; urlgen=\"{\"139.59.17.129\": 14061}:1gZH7Z:Z0T5DGNOc8Jj_kHthiJBrEZsp-s\"",	
			"rur=FTW; mid=XBkPvgALAAFApfayylO7VnudORov; mcd=3; csrftoken=gStWihVsjKbQRJdUbsgRik3Z3wrHsHhf; ds_user_id=9743360601; sessionid=9743360601%3AJO2UBMWDVVKXaN%3A9; urlgen=\"{\"139.59.17.129\": 14061}:1gZHBL:W0shYTHMfiYdspzRqMI72EFA6Ig\",",
			"ig_cb=1; mcd=3; mid=XBkV_wALAAEnYCw5bZpMiX5M5ZDW; csrftoken=pwoKvtaV1sSBhTtp382eMJXnQPQkpwJ4; ds_user_id=9867696899; sessionid=9867696899%3AXGuezOYHwn7f6h%3A3; rur=FRC; urlgen=\"{\"62.151.183.189\": 8560}:1gZHZX:8p8HGFrq5pOK1HKbKmTOG9kDiDA\"",
			"mcd=3; mid=XBkTBgALAAGJ0ULgtXA7To8B8vbR; csrftoken=Wwji7WxjOWu4WnzyF4Ibqpmgn1EI6O4V; ds_user_id=9643087890; sessionid=9643087890%3AFrhvmpFxSiognK%3A29; rur=FRC; urlgen=\"{\"159.203.40.248\": 14061}:1gZHO4:7qNX4NrFIRIychZ0N0XdlglFthc\"",
			"mid=XBkUVwALAAF_AcqzWqEWRTdMjUKQ; ig_cb=1; rur=PRN; mcd=3; csrftoken=LXUPRVigO2UClWTR7huiqayqe7GrW6nq; ds_user_id=9711258815; sessionid=9711258815%3AxbvPuhxrrfbFbn%3A22; urlgen=\"{\"88.208.244.29\": 8560}:1gZHSN:staKRjHhtBF_cx0oxS5gOlNvpfE\""
		};
		
		public static final String BASE_URL = "https://www.instagram.com/graphql/query/";
		
		public static final String QUERY_HASH_POSTS = "f412a8bfd8332a76950fefc1da5785ef";
		public static final String QUERY_HASH_COMMENTS = "f0986789a5c5d17c2400faebf16efd0d";
		
		public static String getCookieVar() {
			
			String COOKIE_VAR = cookieVarArr[COOKIE_VAR_INDEX];
			String HEADER_COOKIE = "mcd=3; mid=W56NywALAAGbK-Cwoc07F1_xzcqT; fbm_124024574287414=base_domain=.instagram.com; csrftoken=2zouD42UCgJClNPyxLUhiUJ79HPEL9OF; ds_user_id=3560802821; csrftoken=2zouD42UCgJClNPyxLUhiUJ79HPEL9OF; shbid=7908; rur=FRC; shbts=1541912998.6216357; sessionid=IGSC140782378d86daaaf4add2af9162675070396726a3aa69ec7287a41b9cf41936%3Ag5F5qn2O8xEpfRw1x9O30aik3N93rtpQ%3A%7B%22_auth_user_id%22%3A3560802821%2C%22_auth_user_backend%22%3A%22accounts.backends.CaseInsensitiveModelBackend%22%2C%22_auth_user_hash%22%3A%22%22%2C%22_platform%22%3A4%2C%22_token_ver%22%3A2%2C%22_token%22%3A%223560802821%3AWok0LiYpRtdyATZZEVzqUqVRUpTSHkDu%3Aedae05b2803d6108199ca80a18a83a2d6a410e8cc56fcd0415e87e6e62980c38%22%2C%22last_refreshed%22%3A1542005528.4967029095%7D; fbsr_124024574287414="+COOKIE_VAR+".eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImNvZGUiOiJBUUEwSFRqazVLMG05T1g4MGRrQ3lTbWFqVDN4VjVMYzdwTEJPOTdMRTdNSFEwbnBPRUdzUkZSRUhsVXhKeGVXb2ExQTE4T1dzT3VPb1NaTWZlem5BLUNVZTJqX0tWcXF1TVpzMXpIcVZkNVNEN2ZBRXoyU2xDMDF1azFCLVZPdE1jTjFURWlWSnVBTm9sNWsyaFY2eFprdjd4a1lxTUF5UE9STXQ2X0d5bzVPRnRKU1dkdDdOSTliMURWZTFQQ2E0MlRsMkJfRVJmRS03dGVUcXBFdGxESVQ5S2U4Nm1VME1Ib0hvUHNaUlViS2lBcDNDZmVENUFoREVxbW4wRkV2Y3A4d3V2aGlVWWVfbjR2eUo2QVZQV1JyYTNORmlYYXJpd1NSMHIwU0hxSkVCajBmOThqTWFFUTBoX005MmJyTURqRDZwWnhLaC0tZUQ3VWJVbWVCdVM0OGx2RXA4WXpydHlfRldoMjRNNFlrNkpNMERZN2lGbFp3UHRYbEx1bWE3NjgiLCJpc3N1ZWRfYXQiOjE1NDIwMDgyNDAsInVzZXJfaWQiOiIxMDAwMDEwMjA3MTg5NDAifQ; urlgen=\"{}:1gM6mp:-y8-gR9TbC3oZu-wxwuI20L7Haw\"";
			
			return HEADER_COOKIE;
		}
		
		public static String getCookieVar2() {
			
			return cookie[COOKIE_VAR_INDEX];
		}
		
		public static final String getPostsJsonVariables(String id, int first, String after) {
			String result = "";
			
			StringBuilder variablesBuilder = new StringBuilder("{");
			variablesBuilder.append("\"id\":\""+id+"\"");
			variablesBuilder.append(",\"first\":"+String.valueOf(first));
			if(after !=null) {
				variablesBuilder.append(",\"after\":\""+after+"\"");
			}
			variablesBuilder.append("}");
			
			result = variablesBuilder.toString();
			
			return result;
		}
		
		public static final String getCommentsJsonVariables(String shortcode, int first, String after) {
			String result = "";
			
			StringBuilder variablesBuilder = new StringBuilder("{");
			variablesBuilder.append("\"shortcode\":\""+shortcode+"\"");
			variablesBuilder.append(",\"first\":"+String.valueOf(first));
			if(after !=null) {
				variablesBuilder.append(",\"after\":\""+after+"\"");
			}
			variablesBuilder.append("}");
			
			result = variablesBuilder.toString();
			
			return result;
		}
		
		public static final String getUserProfileURL(String username) {
			return "https://www.instagram.com/"+username+"/";
		}
		
		public static Map<Integer, Float> getAvgEngagements(){
			
			Map<Integer, Float> avgEngagements = new TreeMap<Integer, Float>();
			
			avgEngagements.put(1999, 10.7f);
			avgEngagements.put(4999, 6.0f);
			avgEngagements.put(9999, 4.9f);
			avgEngagements.put(24999, 3.6f);
			avgEngagements.put(49999, 3.1f);
			avgEngagements.put(74999, 2.6f);
			avgEngagements.put(99999, 2.5f);
			avgEngagements.put(149999, 2.5f);
			avgEngagements.put(249999, 2.4f);
			avgEngagements.put(499999, 2.4f);
			avgEngagements.put(999999, 1.3f);
			avgEngagements.put(9999999, 1.5f);
			
			return avgEngagements;
			
		}
		
		public static Map<Float, Float> getRatingMap(){
			
			Map<Float, Float> ratingMap = new TreeMap<Float, Float>();
			
			ratingMap.put(-49.0f, 1.0f);
			ratingMap.put(-14.0f, 2.0f);
			ratingMap.put(14.0f, 3.0f);
			ratingMap.put(39.0f, 3.5f);
			ratingMap.put(69.0f, 4.0f);
			ratingMap.put(99.0f, 4.5f);
			ratingMap.put(249.0f, 5.0f);
			ratingMap.put(999.0f, 6.0f);
			
			return ratingMap;
			
		}
		
	}
	
	public static class SOCIAL_CLIENTS {
		public static String INSTAGRAM = "CMCCULQOUVBFRZFFEWSGRHDZYDSTRUNVIPMFMITSPRQHHNNWWHFOBPYYDABA";
	}

}

