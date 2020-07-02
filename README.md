# time-tracking
This repo contains configuration information and addon tools for time tracking with [ActivityWatch](https://activitywatch.net/) tailored for Breeding Insight.

![Summary](images/summary.jpg?raw=true)

## Settings

![Settings](images/categorization.jpg?raw=true)

The ActivityWatch dashboard or [aw-webui](https://github.com/ActivityWatch/aw-webui) has a settings page that allows custom specification of categories and rules to classify whether an event belongs to a category. I have configured categories and regex rules for my environment which can be used as a starting point for anyone else. Currently, there is no way to export or load these settings within the app. They are kept in the browser local storage under the classes key. You can copy and paste the `classes.json` contents included in this repo into the local storage using the browser dev tools.

The regex rules currently only support the application name or window title from the events. 

## Watchers

ActivityWatch comes with two default watchers, [aw-watcher-window](https://github.com/ActivityWatch/aw-watcher-window) which monitors application name and window title and [aw-watcher-afk](https://github.com/ActivityWatch/aw-watcher-afk) which monitors mouse and keyboard activity to determine if the user is active.

ActivityWatch also supports custom watchers that can give more event information for specific application types. For example, there is [aw-watcher-web](https://github.com/ActivityWatch/aw-watcher-web) which will give you url information of the browser. There is also a [aw-watcher-jetbrains](https://github.com/OlivierMary/aw-watcher-jetbrains) which will give more detailed IntelliJ editor information. Note that the information from these watchers would have to be accessed via the ActivityWatch API.

## API

ActivityWatch has an API and a custom query language. Using this, it should be possible to estimate time spent per Trello card by running a program at the end of the sprint.

### Task Time Tracking Idea

Each of our Trello cards has a unique id that is shown in the window title bar in the following cases:

- Trello website
- Github pull request page
- Github branch page
- IntelliJ project (can display git branch in title)
- Terminal (can display git branch in title)

Over the duration of a sprint, you will have worked with a number of cards whether implementing or reviewing. This known list would be one of the inputs to the program. For example for the last sprint:

- ONT-49
- PRO-53
- INF-81
- ONT-32
- INF-163

The program could go through the sprint days from start to finish looking for these identifiers. When it sees one, say you opened trello to look at the requirements, it will start adding time under that task. This would include time that's hard to track otherwise such as research or testing. It would exclude communications time because a lot of the time will not be specific to the task being worked on due to general meetings, etc. When a different card identifier is seen in one of the window titles, time will switch to being added under that card. If one or more of the specified card identifiers are not found in the sprint analysis, an error message will be produced to indicate which were missing so that it can be fixed and time can be summed correctly.

## Configuring applications to put git branch in window title

To improve the accuracy of the automated task time tracking program, it is beneficial to have the git branch be identified in the title of as many windows as possible. The git branch is tied to the trello card for the task which allows us to calculate the time. Here are applications I have configured to show the current git branch in the window title bar.

### IntelliJ

Install the [Branch in Window Title](https://plugins.jetbrains.com/plugin/9675-branch-in-window-title) plugin to have the git branch displayed in the IntelliJ window title.

## Issues

### Full screen (Zoom screen sharing) not tracking time
Need to investigate and possibly open an issue in the [aw-watcher-window](https://github.com/ActivityWatch/aw-watcher-window) repo. Looks like time in a fullscreen screensharing zoom call is not being tracked.
