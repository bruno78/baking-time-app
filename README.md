# Baking Time App _in progress..._

<p align="center"> <img src="https://cdn.rawgit.com/bruno78/baking-time-app/a90fd194/app/src/main/ic_launcher-web.png" width="200" alt="image of icon"></p>

## Project Summary

Baking Time App allows Udacity’s resident baker-in-chief, Miriam, to share her recipes with the world. 
It allows a user to select a recipe and see video-guided steps for how to complete it.

## Pre-requisites

* Android SDK v26
* Android min SDK v17

## Tools Used 

* [ExoPlayer](https://github.com/google/ExoPlayer)
* [Timber](https://github.com/JakeWharton/timber)
* [GSON](https://github.com/google/gson)
* [LeakCanary](https://github.com/square/leakcanary)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) 
* [Retrofit](http://square.github.io/retrofit/)
* [ButterKnife](http://jakewharton.github.io/butterknife/) 
* [Icons8](https://icons8.com/material-icons/)

## Instructions

Download or clone this repo on your machine, open the project using Android Studio. Once Gradle builds
the project, click "run" and choose an emulator.

## Progress tasks

- [x] Implement Retrofit / GSON
- [x] Implement Repository
- [x] Implement ViewModel to manage Recipe
- [x] Implement ViewModel to manage communication between fragments (IngredientAndStep, StepDetail)
- [x] Implement LeakCanary to monitor Memory leaks
- [x] Implement ExoPlayer
- [x] Fix back button 
- [x] Improve Exoplayer buffering
- [x] Implement ExoPlayer full screen in landscape mode
- [x] Fix issue where app crashes when you select a step after rotation
- [x] Implement Widget
- [x] Start Espresso Tests
- [x] Implement Multiple views
- [x] Code de dimensions properly - define at values/dimens resource
- [x] Polish Views

Optionals

- [ ] Click on the widget to get to Recipe Details
- [ ] Add tests to click on next and previous button 

## License

The content of this repository is licensed under a **[Creative Commons Attribution License.](https://creativecommons.org/licenses/by/3.0/us/)**

## Notes about the project: 

This project is part of [Udacity's Android Developer Nanodegree](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801) 
together with [Grow With Google Scholarship.](https://www.udacity.com/grow-with-google)

The rubric for this project can be found [here](https://github.com/bruno78/baking-time-app/blob/master/Rubric.md)
