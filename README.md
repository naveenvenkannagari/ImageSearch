Project has two screens 

1) Gallery Screen:- The Images are fetched from Flickr API  based on the search text which is shown in GRID and LIST view based on the user choice.Double tapping the image will get added to favorites

2) Favorites Screen: - It will display all the images[here the images are fetched from local cache ]  which are set to favorite. User can long press on any Image and can be removed from that list 

Technical Info 
Architecture:-  MVP
Libraries used:-
Picasso[For Image Downloading ASynchronously]
Retrofit [Rest Client]
Dagger [Dependency Injection]
ButterKnife[UI binding]
Realm[Local Database]
RxAndroid[Reactive Programming]
