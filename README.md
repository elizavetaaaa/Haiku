# Haiku


## This Android app presents to the user a collection of Japanese poems on various topics.
## Haiku is wold famous style of japanese poetry.

## Design
Before starting app building the prototype was created in [Figma](https://www.figma.com/file/ScNJPKyoebUTDCSXItsvng/%D0%A5%D0%B0%D0%B9%D0%BA%D1%83-android?node-id=0%3A1).

## Coding

The app was implemnted with java.
Following dependencies were added:
```implementation 'androidx.recyclerview:recyclerview:1.1.0' ``` - for recView

```implementation 'com.mxn.soul:flowingdrawer-core:2.1.0' ```
```implementation 'com.nineoldandroids:library:2.4.0' ```-beautiful menu draging

### Data saving 
Instead of database I use *SharedPreferences*. All items  in each category are saved as a string with zeros. One zero for each item. ```"000000"```
When user clicks on like-inactive button, that becomes like-active and clicked item changes its zero to one. 
Loop goes through all categories and selects items with one, 
then program adds them to *Favourites*. The same happens when user wants to delete any item from favs.




## Result
Application icon was changed

![app icon](https://imgur.com/VcUtLMm.png)

When the app starts user is able to see all offering categories presented in left menu

![start page](https://imgur.com/BNyjpfW.png)

Choosing a category lets to fill the recView with appropriate items, it is possible to selects haiku that you like by pressing like button. After pressing it changes its style.

![start page](https://imgur.com/m6SbO6G.png) ![start page](https://imgur.com/XjKxvYH.png)


You can easily change the topic on drag -  the left menu opens.

![left menu](https://imgur.com/91y4lRk.png)

All selected items are immediately adding to FAVOURITES (избранное) category. You can also delete them from favourites by ppressing like-active button.
After that action they disappear from this category and also like-active button will be changed tol like-inactive in item's root category.

![fav1](https://imgur.com/zK83KpZ.png) ![fav2](https://imgur.com/Kj43x7T.png) ![fav3](https://imgur.com/s9jWuVw.png)



 
