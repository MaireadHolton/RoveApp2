# RoveApp

This is an Android app which allows users to rate businesses on accessibility. 

## **Structure**
The project is laid out according to MVVM design pattern

### **Design/ UX Approach**
The layout folder contains all of the xml files for designing the views in the app. There is a similar theme running throught the app and easy to use text boxes, rating bars, radio group and map view for users to enter details in the app. <br>
Login menu: <br>
Users can create an account and login using email and password authentication or use google signin <br>
Resport Fragment: <br>
On user login the user is presented with a view of visits they have added (Report Fragment), there is a toggle button to allow users to view all visits added by all users.
From this report fragment users can add a new visit by clicking the kabab menu. Users can delete visits from the list by swiping left and can update a visit by swiping right.
A favourit toggle switch is displayed to allow users add a visit to a favourites list (not implemented). <br>
Visit Fragment: <br>
When the user selects add visit they are brought to the visit fragment. This view allows the user to add the name of a business they have visited, select the type of visit from a radio group and add an accessibility rating for the visit. <br>
Map Fragment: <br>
User visits are displayed on a map fragment displaying the location the visit was added by the user. <br>
About Fragment: <br>
Displays information about the app to the user. <br>
User navigation Flow: <br>
![image](https://github.com/MaireadHolton/RoveApp2/assets/97246575/6e3d276e-7cbb-48ca-8506-2f6d61aeb261)


#### **Elements employed:**
I have used android studio to create the app with firebase real time database to store all visits in the app. I have created a login page with firebase authentication employed using email and password authentication and also google authentication.
I have added a rating bar to the RoveView to allow users to rate the loactions they have visited. A user image is 

##### **UML & Class Diagram: **

![image](https://github.com/MaireadHolton/RoveApp2/assets/97246575/5e4fae04-d3b7-4002-b2d4-116c8bca3390)

###### **References:**
Databinding: https://developer.android.com/topic/libraries/data-binding <br>
Recycler view: https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView <br>
Firebase docs: authentication: https://firebase.google.com/docs/auth <br>
              real time database: https://firebase.google.com/docs/database?hl=en&authuser=0 <br>
              Storage: https://firebase.google.com/docs/storage/android/start <br>
rating bar: https://developer.android.com/reference/kotlin/android/widget/RatingBar <br>
favourites: https://developer.android.com/reference/kotlin/android/widget/ToggleButton <br>
            https://stackoverflow.com/questions/35866370/implementing-add-to-favourite-mechanism-in-recyclerview <br>
maps: https://console.cloud.google.com/apis/library/maps-android-backend.googleapis.com?project=roveapp2-691bc <br>
