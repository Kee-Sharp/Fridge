# Fridge
Project repo for Junior Design 2021

Developed by Miranda Bisson, Abigail Gutierrez-Ray, Spencer Kee, Tori Kraj, and Deepti Vaidyanathan

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Install Guide

Prerequisites: 
  - Android device running Android 11 **OR** proper emulator in Android Studio (Pixel 4 API 30)
  - Android Studio version that supports Android 11 & level 30 API development

Dependent Database:
  - Junior Design Team 300 Fridge MongoDB Atlas Database (local copy of database included with install)

Download Instructions:
  - GitHub
    - Visit https://github.com/skee21/Fridge and clone or download .zip file of the code

Run Instructions (Android Studio):
  - Locate project folder (ensure it has been extracted properly from the .zip if downloaded)
  - Open Android Studio --> File --> Open --> Project Folder Location
  - Install any missing requirements to run (such as the Android 11/API level 30 SDK requirements)
  - Create a new device (Pixel 4 API 30) in the Android Virtual Device (AVD) manager
  - Click the green run arrow on the toolbar next to the device selection pulldown menu or press Shift + F10 on Windows

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Fridge Release Notes

### Version 1.0.5 (4/23/21)
New features:
  - Users can now login and switch between users on the same device
  - In-app sync between local app food data and database food data implemented 
  - UI updates: 
    - Fridge logo on login page updated to match original logo spacing
    - Page icons for Fridge, Lists, and User added
    - Decreased space between login and register buttons on login page
  - Fridge options updated from Delete All and Delete One to One Eaten, All Eaten, One Thrown Away, and All Thrown away to facilitate cost tracking

Bug fixes:
  - Local login authentication sync with database fixed

Known bugs:
  - Cost tracking is not fully implemented/integrated with database
  - Registering new users is not fully implemented/integrated with database
