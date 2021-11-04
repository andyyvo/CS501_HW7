# CS501_HW7
CS 501 Mobile App Development - Fragment Transactions, Implicit Intents, Menus, Animations 

## W7_P1 What's wrong with this code?
Exploring Fragment Transactions and how they interact with each other.

## W7_P2 Maintaining State, Bundles and SharedPreferences
A (very) simple App, with one Button, one imageview and one EditText. Clicking on the button will load a random image into the imageview. This app allows us to practice SharedPreferences when it comes to onDestroy and onCreate to "remember" the state of the app.

## W7_P3 Spring Break Chooser
This app works with intents with Return Values, Google Maps, and an accelerometer. The app allows the user to select a language from a ListView and speak into the device with a Speech-To-Text intent. Upon shaking the phone using an accelerometer, a geo URI intent will open up a Maps app and display a "Spring Break" location based on the language selected. Simultaneously, the Spring Break Chooser app will say "Hello" in the respective language.

## W7_P4 Animations
An app that has two XML-based animations:
1. A 10-second animation that results in a centered View object fading to 50% for the first 5 seconds, rotate counter clockwise 2 times in total and pivot from the center for seconds 1-3, and slide down then back to center for the remainder of the animation.
2. An 8-second animation that results in a View object growing from the center up to double in size for seconds 0-2, rotate 3 times in total and pivot in the bottom left corner for seconds 1-4, bounce for seconds 4-5, slide down then back to the center for seconds 5-6, slide left then back to the center for seconds 6-7, then slide up and then back to the center while reverting back to the original size and pivoting from the center for seconds 7-8.
