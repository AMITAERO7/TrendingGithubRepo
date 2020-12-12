# TrendingGithubRepo
An Android App that lists the most trending repositories in Android from Github API.

The app includes the following main components:
A local database that servers as a single source of truth for data presented to the user.
A web api service.
A ViewModel that provides data specific for the UI.
The UI, which shows a visual representation of the data in the ViewModel.

App Architecture
The View — that informs the ViewModel about the user’s actions
The ViewModel — exposes streams of data relevant to the View
The DataModel — abstracts the data source. The ViewModel works with the DataModel to get and save the data.

App Specs
Kotlin (in kotlin_support branch)
MVVM Architecture
Android Architecture Components (LiveData, Lifecycle, ViewModel, Room Persistence Library, ConstraintLayout)
RxJava2 for implementing Observable pattern.
Retrofit for API integration.
Gson for serialisation.
Shared Preferences for saving key value pair data i.e time 
