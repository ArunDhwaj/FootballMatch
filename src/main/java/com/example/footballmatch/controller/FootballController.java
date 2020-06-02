package com.example.footballmatch.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/standings")
public class FootballController
{
    @Autowired
    FootballService footballService;

    @RequestMapping("/status")
    public Response getMatchDetails(@RequestParam("unique_id") String uniqueId ) throws ResourceNotFoundException
    {
        return footballService.getMatchDetails(uniqueId);
    }
}
