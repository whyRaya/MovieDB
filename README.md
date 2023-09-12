# MovieDB

## Overview
A mobile native application to show movies using API from https://api.themoviedb.org

- Built with MVVM architecture, Jetpack Compose, Retrofit, Glide, and Hilt.
- Support Dark and Light Mode, has toggle to change it dynamically
- Screen Flow:
     - `Genre Screen` -> `Movie Screen` -> `Movie Detail Screen` -> `Movie video`
- `Genre Screen` will fetch the genre list from the server. Click one of the genres to get a movie list based on the selected genre. 
- `Movie Screen` has pagination to load movies based on `genreId`
- `Movie Detail Screen` has a video list and pagination to load the user's review
     - If the user clicks the video thumbnail, it will play the video on Webview 
- `Movie video` uses Webview to play Youtube Url
- Each screen has Loading, Error, and Content View to handle state changes


## Screen Capture
| Genres Screen (Light Mode) | Movies Screen (Light Mode) | Movie Detail Screen (Light Mode) |
| ------ | ------ | ------ |
| ![Screenshot_20230911_142940](https://gitlab.com/raya.wahyu.anggara/MovieDB/uploads/274f8213553222e3c705214b2129569f/Screenshot_20230911_143009.png) |  ![Screenshot_20230911_142959](https://gitlab.com/raya.wahyu.anggara/MovieDB/uploads/72ac8842976d695630943d0fdd3c8fb4/Screenshot_20230911_142959.png)      | ![Screenshot_20230911_143009](https://gitlab.com/raya.wahyu.anggara/MovieDB/uploads/6be5864c7902cb399186705816a243db/Screenshot_20230911_142940.png) |

| Genres Screen (Dark Mode) | Movies Screen (Dark Mode) | Movie Detail Screen (Dark Mode) |
| ------ | ------ | ------ |
| ![Screenshot_20230911_143020](https://gitlab.com/raya.wahyu.anggara/MovieDB/uploads/89f760fe54247286ec6cbe40676d1c92/Screenshot_20230911_143020.png) | ![Screenshot_20230911_143033](https://gitlab.com/raya.wahyu.anggara/MovieDB/uploads/fc64a6d4f0d561a16f42bb4373dbd83b/Screenshot_20230911_143033.png) | ![Screenshot_20230911_143054](https://gitlab.com/raya.wahyu.anggara/MovieDB/uploads/0fbd50b7f64fc425b0bc3638c6553056/Screenshot_20230911_143054.png)|

## Screen Recording
[![IMG](https://img.youtube.com/vi/EH8WX1jP0sU/0.jpg)](https://youtu.be/EH8WX1jP0sU)
