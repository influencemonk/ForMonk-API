package com.ForMonk2.utils;

import java.util.HashMap;

import org.bson.Document;

import com.ForMonk2.helpers.InstaCrawlerHelper;
import com.ForMonk2.utils.CollectionUtils.Collections;
import com.ForMonk2.utils.CollectionUtils.Operations;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

/*
 * Driver class to test other class functionalities
 */
public class TestDrive {
	
	public static void main(String[] args) {
		InstaCrawlerHelper helper = new InstaCrawlerHelper();
		helper.getProfileData("iamsrk");
	}

}
