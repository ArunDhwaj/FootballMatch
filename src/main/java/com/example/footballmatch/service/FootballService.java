package com.example.footballmatch.service;

import com.example.footballmatch.exceptionalhandling.ResourceNotFoundException;
import com.example.footballmatch.model.*;
import com.example.footballmatch.utils.ProgramConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class FootballService implements FootballServiceInterface
{
    @Autowired
    RestTemplate restTemplate;

    private String footballServiceURL = ProgramConstants.BASE_URL;
    private String API_KEY = ProgramConstants.API_KEY;


    @Override
    public FinalStandingResponse getTeamStandings(String countryName, String leagueName, String teamName) throws JsonProcessingException, ResourceNotFoundException
    {
        System.out.println("1. countryName: " + countryName + ", leagueName: " + leagueName + ", teamName: " + teamName);

        Country countryData = new Country();
        League leagueData = new League();
        Teams teamData = new Teams();
        Standings rankingData = new Standings();

        //call Country API to get league ID
        try
        {
            //Step-1: call countries API to get countryData
            String getCountries_url = footballServiceURL + "?action=get_countries" + API_KEY;
            System.out.println("2.1 getCountries_url: " + getCountries_url );

            ResponseEntity<Country[]> countryResponse =
                    restTemplate.getForEntity(getCountries_url, Country[].class);

//            System.out.println("2.1 countryResponse: " + countryResponse.getBody().length );

            List<Country> countries = Arrays.stream(countryResponse.getBody()).filter(c -> c.getCountryName().equals(countryName)).collect(Collectors.toList());
//            System.out.println("2.2 countries: " + countries );

            countryData = countries.stream().findFirst().get();
            System.out.println("2.3 countryData: " + countryData );

            //Step-2: call League API to get leagueData
            ResponseEntity<League[]> leagueResponse =
                    restTemplate.exchange(footballServiceURL + "?action=get_leagues&country_id=" + countryData.getCountryId() + API_KEY, HttpMethod.GET, getObjectHttpEntity(), League[].class);
            List<League> leagues = Arrays.stream(leagueResponse.getBody()).filter(c -> c.getLeagueName().equals(leagueName)).collect(Collectors.toList());

            leagueData = leagues.stream().findFirst().get();
            System.out.println("3. leagueData: " + leagueData );

            //Step-3: call teams API to get teamData
            ResponseEntity<Teams[]> teamsResponse =
                    restTemplate.exchange(footballServiceURL + "?action=get_teams&league_id=" + leagueData.getLeagueId() + API_KEY, HttpMethod.GET, getObjectHttpEntity(), Teams[].class);

            List<Teams> teams = Arrays.stream(teamsResponse.getBody()).filter(t -> t.getTeamName().equals(teamName)).collect(Collectors.toList());

            teamData = teams.stream().findFirst().get();
            System.out.println("4. teamData: " + teamData );

            //Step-4: call standings API to get rankingData
            ResponseEntity<Standings[]> standingsResponse =
                    restTemplate.exchange(footballServiceURL + "?action=get_standings&league_id=" + leagueData.getLeagueId() + API_KEY , HttpMethod.GET, getObjectHttpEntity(), Standings[].class);

            List<Standings> rankings = Arrays.stream(standingsResponse.getBody()).filter(s -> s.getTeamName().equals(teamName)).collect(Collectors.toList());
            rankingData = rankings.stream().findFirst().get();
            System.out.println("5. rankingData: " + rankingData );

        }
        catch (NoSuchElementException e)
        {
            throw new ResourceNotFoundException("The Country Name/League Name or the team name are incorrect");
        }

        return findRankOfTeam(countryData, leagueData, teamData, rankingData);
    }

    private HttpEntity<Object> getObjectHttpEntity()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    private FinalStandingResponse findRankOfTeam(Country countryResponse, League leagueResponse, Teams teamsResponse, Standings standingsResponse) throws JsonProcessingException
    {
        FinalStandingResponse response = new FinalStandingResponse();

        response.setCountryId(countryResponse.getCountryId());
        response.setCountryName(countryResponse.getCountryName());
        response.setLeagueId(leagueResponse.getLeagueId());
        response.setLeagueName(leagueResponse.getLeagueName());
        response.setTeamId(teamsResponse.getTeamKey());
        response.setTeamName(teamsResponse.getTeamName());
        response.setOverAllLeaguePosition(standingsResponse.getOverallLeaguePosition());

        return response;
    }

}