package com.ForMonk2.utils;

import com.ForMonk2.helpers.InstaCrawlerHelper;

/*
 * Driver class to test other class functionalities
 */
public class TestDrive {
	
	public static void main(String[] args) {
		InstaCrawlerHelper helper = new InstaCrawlerHelper();
		helper.getProfileData("iishortgirlii");
	}

}
