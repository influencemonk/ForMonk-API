#Instagram Profile Links

A tool for instagram users to add multiple external links to their profile.
Users will connect their instagram business account and will be able to add links with titles for different posts of his/her profile.

## ISC: Profile Links Collection

```
LinksCollectionModel:
{
	_id: "5kwjfjdwjfjd",
	IMCId: "5j34jfjoif38",
	postLink: "bit.ly/lauda_lasun",
	postImage: "http://instgram.com/posts/lauda_lasun.png",
	linkTitle: "Some title given by user with a maximum character limit of 100 characters",
	isHidden: false,
	isPinned: true,
	clickCount: 123,
	clientId: "CMCCULQOUVBFRZFFEWSGRHDZYDSTRUNVIPMFMITSPRQHHNNWWHFOBPYYDABA",
	createdOn: 1512732746843,
	updatedOn: 1512732746823
}

```

The main intent of this collection is to store all the profile links of the users.

### 1. getLinks (GET)
This API will return the list of all links added by the logged in user with ``` IMCId ``` as input parameter.


### 2. editLink (POST)
This API will be used to edit the deatils of the links added by the user with input parameters: ``` postTitle ```, ``` postPic ```, ``` postLink ``` and ``` LCMId ```, ``` IMCId ```.


### 3. addLink (POST)
This API will be used to add a new link with input parameters: ``` postTitle ```, ``` postPic ```, ``` postLink ``` and ``` LCMId ```, ``` IMCId ```.


### 4. deleteLink (POST)
This API will be used to delete existing link with ``` LCMId ``` and , ``` IMCId ``` as input parameter.