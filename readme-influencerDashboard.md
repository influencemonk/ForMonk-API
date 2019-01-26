#Influencer Dashboard

A tool for the brand to view/explore all the influencers that he can choose. 
There would be a search bar , which would provide search options. When the brand selects , one of the options, he will be forwarded to the profile of the influencer.

##Collections required

Collections have been designed keeping the UI in mind.

### ISC : 1. Influencer Search Collection/View 

```
InfluencerSearchWidget :
{
	fullName: 
	{
		firstName: "Soumya",
		lastName: "Deb"
	},
	username: "_soumyadeb_",
	userId: 12324,
}

```


``` userId ``` is the main id with which every user is identified (both brands and influencers) .

The main intent of this collection is to assist in fetching search suggestions.

### 2. ISDC: Influencer Search Details Collection/View

```
	{
	fullName: 
	{
		fisrtName: "Soumya",
		lastName: "Deb"
	},
	username: "_soumyadeb_",
	userId: 12324,
	category: [],
	elvaluationDetails:
	{
		er: 34.5,
		followers: 143,
		avgLikes: 56.2,
		totalLikes: 2331,
		avgComments: 12.1,
		totalComments: 123
	}
	}

```
	
``` er ``` is the engagement rate.

The main intent of this collection is to display all the search results .

### 3. IMC: Influencer Master Collection

```
{
_id: "5j34jfjoif38",
socialHandle: "_soumyadeb_",
"clientId": "CMCCULQOUVBFRZFFEWSGRHDZYDSTRUNVIPMFMITSPRQHHNNWWHFOBPYYDABA"
}

```

This collection will be used to map influencer's social address username with social network site and also get its id ;

### 4. IDC: Influencer Details Collection

```
{	
_id: "5kdfnkwer32",
userId: "5j34jfjoif38",
profileSummary:
{	
	fullName: 
	{
		firstName: "Soumya",
		lastName: "Deb"
	},
	contactDetails:
	[
		{
			type: "email",
			value: "soumyadeb1809@gmail.com",
			tags: "personal"
		}
	]
	username: "_soumyadeb_",
	category: [],
	evaluationDetails:
	{
		er: 34.5,
		followers: 143,
		avgLikes: 56.2,
		totalLikes: 2331,
		avgComments: 12.1,
		totalComments: 123
	},
	profilePicture: "http://instagram.com/dask/askdkjnjks/dash.jpg"
},
socialIds: 
[
	{
		type: "instagram",
		socialUsername: "_soumyadeb_",
		socialProfilePicture: "http://instagram.com/dask/askdkjnjks/dash.jpg"
	}
],
socialMasterId: "5kwjfjdwjfjd",
	
}

```

```socialMasterId``` is the id to reference ```Social Master Collection``` .

The intent of this collection is to display all the details of the influencer on a single page.

### 5. SMC: Social Master Collection

```
{
_id: "5kwjfjdwjfjd",
userId: "5j34jfjoif38",
credentials: 
[
	{
		accessToken: "sdkfjknwhebuyfguyg",
		refreshToken: "324bwhqdeu3278mfd",
		type: "instagram"
		id: "23454352345",
	}
],
followerTrendMasterId: "567djhfdf3"

}

```

The intent of this collection is to store all the required oauth credentials (this  credentials would be later used in python serviced , to update ER ).

``` followerTrendMasterId ``` is to reference ``` Follower Trend Master Collection ``` 

### 6. FTMC: Follower Trend Master Collection

```
{
_id: "567djhfdf3",
IMCId : "",
data: 
[
	{
		timestamp: 15387237342,
		followers: 1234
	}
]
}

```

This collection will store all the followers of an influencer per day.  










