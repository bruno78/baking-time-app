# Baking Time App

<p align="center"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/a90fd194/app/src/main/ic_launcher-web.png" width="200" alt="image of icon"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/c6b0f4d8/screenshots/mobile-detail-land.png" width="400" alt="mobile step detail landscape view"> </p>
<p align="center"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/c6b0f4d8/screenshots/mobile-open-screen-port.png" width="200" alt="mobile app opening screen"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/c6b0f4d8/screenshots/mobile-ing-recipe.png" width="200" alt="mobile ingredients step detail"> <image src="https://cdn.rawgit.com/bruno78/baking-time-app/c6b0f4d8/screenshots/mobile-detail-port.png" width="200" alt="recipe detail view"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/c6b0f4d8/screenshots/widget.png" width="200" alt="app widget"> </p>
<p align="center"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/c6b0f4d8/screenshots/tablet-open-sceen-land.png" width="300" alt="tablet opening screen"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/c6b0f4d8/screenshots/tablet-step-detail-land-2.png" width="300" alt="tablet master flow detail"> </p>

## Project Summary

Baking Time App allows Udacity’s resident baker-in-chief, Miriam, to share her recipes with the world. 
It allows a user to select a recipe and see video-guided steps for how to complete it.

## Pre-requisites

* Android SDK v26
* Android min SDK v17

## Tools Used 

* [ExoPlayer](https://github.com/google/ExoPlayer)
* [Glide](https://github.com/bumptech/glide)
* [Timber](https://github.com/JakeWharton/timber)
* [App Widgets](https://developer.android.com/guide/topics/appwidgets/overview)
* [GSON](https://github.com/google/gson)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) 
* [Retrofit](http://square.github.io/retrofit/)
* [ButterKnife](http://jakewharton.github.io/butterknife/)
* [CardView](https://developer.android.com/reference/android/support/v7/widget/CardView)
* [Icons8](https://icons8.com/material-icons/)
* [Espresso](https://developer.android.com/training/testing/espresso/)

## Instructions

Download or clone this repo on your machine, open the project using Android Studio. Once Gradle builds
the project, click "run" and choose an emulator.

## User Experience

* Users can select a recipe and view the ingredients and the steps.
* Users can select a step and see a video with a written instructions below. If the video is not available, it will display a thumbnail a message stating video is not available. 
* Users can use _"Next"_ and _"Previous"_ buttons to navigate through the steps without having to go back to the Ingredients and Steps screen. 
* Users can watch video on full screen on landscape mode on mobile devices.
* Users can see recipe steps and navigation between them via a Master Detail Flow.
* Users can use app widget to display their favorite recipe's ingredients on device's home screen. 

## Next Steps

- [ ] Click on the widget to get to Recipe Details
- [ ] Click on the widget to switch recipe ingredients
- [ ] Add tests to click on next and previous button 
- [ ] Loading icon 
- [ ] Improve UI looks

## License

The content of this repository is licensed under a **[Creative Commons Attribution License.](https://creativecommons.org/licenses/by/3.0/us/)**

## Notes about the project: 

This project is part of [Udacity's Android Developer Nanodegree](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801) 
together with [Grow With Google Scholarship.](https://www.udacity.com/grow-with-google)

The rubric for this project can be found [here](https://github.com/bruno78/baking-time-app/blob/master/Rubric.md)
The mock guidelines for this project can be found [here.](https://github.com/bruno78/baking-time-app/tree/master/screenshots/bakingapp-mocks.pdf)