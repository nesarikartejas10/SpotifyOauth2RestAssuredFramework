package com.spotify.oauth2.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.application_api.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;

import io.restassured.response.Response;

public class PlaylistTests {
	
	public Playlist playlistBuilder(String name, String description, boolean _public) {
		return new Playlist()
				.setName(name)
				.setDescription(description)
				.setPublic(_public);
	}
	
	public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
		assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
	}
	
	public void assertStatuesCode(int actualStatusCode, int expectedStatusCode) {
		assertThat(actualStatusCode, equalTo(expectedStatusCode));
	}
	
	public void assertError(Error responseErr, int expectedStatusCode, String expectedMessage) {
		assertThat(responseErr.getError().getStatus(),equalTo(expectedStatusCode));
		assertThat(responseErr.getError().getMessage(),equalTo(expectedMessage));
	}
	
	@Test
	public void shouldBeAbleToCreateAPlaylist() {
		Playlist requestPlaylist = playlistBuilder("Update Playlist", "Update playlist description", false);
		
		Response response = PlaylistApi.post(requestPlaylist);
		assertStatuesCode(response.statusCode(), 201);
		assertThat(response.header("Content-Type"), equalTo("application/json; charset=utf-8"));
		
		assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
	}
	
	@Test 
	public void shouldBeAbleToGetAPlaylist() {
		Playlist requestPlaylist = playlistBuilder("Update Playlist", "Update playlist description", true);
		
		Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
		assertStatuesCode(response.statusCode(), 200);
		assertThat(response.header("Content-Type"), equalTo("application/json; charset=utf-8"));
		
		assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
	}
	
	@Test
	public void shouldBeAbleToUpdateAPlaylist() {
		Playlist requestPlaylist = playlistBuilder("Update Playlist", "Update playlist description", false);
		
		Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
		assertStatuesCode(response.statusCode(), 200);
	}
	
	@Test
	public void shouldNotBeAbleToCreateAPlaylistWithoutName() {
		Playlist requestPlaylist = playlistBuilder("", "New playlist description", false);

		Response response = PlaylistApi.post(requestPlaylist);
		assertStatuesCode(response.statusCode(), 400);
		assertThat(response.header("Content-Type"), equalTo("application/json; charset=utf-8"));
		
		assertError(response.as(Error.class), 400, "Missing required field: name");	
	}
	
	@Test
	public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
		String invalid_token = "12345";
		Playlist requestPlaylist = playlistBuilder("New Playlist", "New playlist description", false);
		
		Response response = PlaylistApi.post(invalid_token, requestPlaylist);
		assertStatuesCode(response.statusCode(), 401);
		assertThat(response.header("Content-Type"), equalTo("application/json"));
		
		assertError(response.as(Error.class), 401, "Invalid access token");
	}
}
