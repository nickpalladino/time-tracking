# time-tracking
This repo contains configuration information and addon tools for time tracking with ActivityWatch tailored for Breeding Insight.

## Settings

The ActivityWatch dashboard or [aw-webui](https://github.com/ActivityWatch/aw-webui) has a settings page that allows custom specification of categories and rules to classify whether an event belongs to a category. I have configured categories and regex rules for my environment which can be used as a starting point for anyone else. Currently, there is no way to export or load these settings within the app. They are kept in the browser local storage under the classes key. You can copy and paste the `classes.json` contents included in this repo into the local storage using the browser dev tools.

The regex rules currently only support the application name or window title from the events. 

## Watchers

ActivityWatch comes with two default watchers, [aw-watcher-window](https://github.com/ActivityWatch/aw-watcher-window) which monitors application name and window title and [aw-watcher-afk](https://github.com/ActivityWatch/aw-watcher-afk) which monitors mouse and keyboard activity to determine if the user is active.

ActivityWatch also supports custom watchers that can give more event information for specific application types. For example, there is [aw-watcher-web](https://github.com/ActivityWatch/aw-watcher-web) which will give you url information of the browser. There is also a [aw-watcher-jetbrains](https://github.com/OlivierMary/aw-watcher-jetbrains) which will give more detailed IntelliJ editor information. Note that the information from these watchers would have to be accessed via the ActivityWatch API.
