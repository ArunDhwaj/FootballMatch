package com.example.footballmatch.controller;

import com.example.footballmatch.exceptionalhandling.ResourceNotFoundException;
import com.example.footballmatch.model.FinalStandingResponse;
import com.example.footballmatch.service.FootballServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/standings")
public class FootballController
{
    @Autowired
    FootballServiceInterface footballServiceInterface;

//    @GetMapping("v1/teamStandings")
//    public FinalStandingResponse getTeamStandings(@RequestParam("country") String countryName, @RequestParam("league") String leagueName, @RequestParam("team") String teamName) throws JsonProcessingException, ResourceNotFoundException
    @RequestMapping(path="v1/teamStandings")
    public FinalStandingResponse getTeamStandings() throws JsonProcessingException, ResourceNotFoundException
    {
        String countryName = "England";
        String leagueName = "Premier League";
        String teamName = "Manchester City";

        return footballServiceInterface.getTeamStandings(countryName, leagueName, teamName);
    }

    @RequestMapping(path="/")
    public String greeting() throws JsonProcessingException, ResourceNotFoundException
    {
        return "Hello Football";
    }
}
