Using the server on localhost:4000
Real device - make sure you insert your PC internal IP in viewModel file
Emulator - set 10.0.2.2 proxy in its settings

Steps:

1. Display a screen similar to the provided wireframe. The screen should
   contain a video player at the top and a scrollable details section at the
   bottom. - DONE
2. Import and use the provided image assets in `assets/` for the media
   controls. For Android, use the provided SVG files. For iOS, use the provided PDF files. - Provided by ExoPlayer
3. Fetch a list of videos from the provided API (see instructions below for
   running the API). - DONE
4. Sort the received list of videos by date. - DONE
5. Load the first video into the UI by default. - DONE
6. Implement the play/pause button for the video player. The app should be
   paused on startup. - DONE
7. Implement next/previous buttons for the video player. Clicking next should
   update the UI with the next video and video details. Buttons should be
   insensitive when at the start/end of the list. - DONE
8. In the details section, show the returned description for the current video
   as rendered Markdown. - DONE
9. In the details section, also display the title and author of the current
   video. - TODO

Problems solved:

Accessing localhost:4000 server - 
1. usesCleartextTraffic property in Manifest file
2. use local IP for host machine