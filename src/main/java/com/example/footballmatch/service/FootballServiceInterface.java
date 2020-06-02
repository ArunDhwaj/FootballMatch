package com.example.footballmatch.service;

import org.json.JSONException;

public interface FootballServiceInterface
{
    public String getCountriesListings();

    public String getLeagueId(String countryId) throws JSONException;

    public String getStandings(String leagueId) throws JSONException;

}
