Steps:

1. Display a screen similar to the provided wireframe. The screen should
   contain a video player at the top and a scrollable details section at the
   bottom.
2. Import and use the provided image assets in `assets/` for the media
   controls. For Android, use the provided SVG files. For iOS, use the provided PDF files.
3. Fetch a list of videos from the provided API (see instructions below for
   running the API).
4. Sort the received list of videos by date.
5. Load the first video into the UI by default.
6. Implement the play/pause button for the video player. The app should be
   paused on startup.
7. Implement next/previous buttons for the video player. Clicking next should
   update the UI with the next video and video details. Buttons should be
   insensitive when at the start/end of the list.
8. In the details section, show the returned description for the current video
   as rendered Markdown.
9. In the details section, also display the title and author of the current
   video.