package com.spotify.oauth2.api.application_api;
import static com.spotify.oauth2.api.TokenManager.*;
import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;

import static com.spotify.oauth2.api.Route.*;

import io.restassured.response.Response;

public class PlaylistApi {
	
	public static Response post(Playlist requestPlaylist) {

		return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken(), requestPlaylist);
	}
	
	public static Response post(String token, Playlist requestPlaylist) {

		return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, token, requestPlaylist);
	}

	public static Response get(String playlistId) {
		
		return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
	}

	public static Response update(String playlistId, Playlist requestPlaylist) {
		
		return RestResource.update(PLAYLISTS + "/" + playlistId, getToken(), requestPlaylist);
	}

}
