package com.example.footballmatch.service;

import com.example.footballmatch.utils.ProgramConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class FootballService implements FootballServiceInterface
{
    @Override
    public String getCountriesListings() 
    {
        return getResultsFromAPI(ProgramConstants.GET_COUNTRIES);
    }

    @Override
    public String getLeagueId(String countryId) throws JSONException 
    {
        String countries = getCountriesListings();
        JSONArray jsonArray = new JSONArray(countries);
        JSONObject countryObj = jsonArray.getJSONObject(1);
        
        String countryId1 = countryObj.getString("country_id");
        String GET_LEAGUE_INFO = ProgramConstants.GET_LEAGUE + countryId1 + ProgramConstants.API_KEY;
        String leagueInformation =  getResultsFromAPI(GET_LEAGUE_INFO);
        
        JSONArray leagueArray = new JSONArray(leagueInformation);
        JSONObject leagueObj = leagueArray.getJSONObject(0);
        
        String leagueId = leagueObj.getString("league_id");
        return leagueId;
    }

    @Override
    public String getStandings(String leagueId) throws JSONException
    {
        String leagueID = this.getLeagueId("173");
        String GET_STANDINGS_INFO = GET_STANDINGS + leagueID + API_KEY;
        return getResultsFromAPI(GET_STANDINGS_INFO);
    }

    public String getResultsFromAPI(String URL) 
    {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
    
        try 
        {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            
            if (response.getStatusLine().getStatusCode() == 200) 
            {
                ResponseHandler<String> handler = new BasicResponseHandler();
                return handler.handleResponse(response);
            }
        } 
        catch (ClientProtocolException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}
