package com.example.footballmatch.utils;

public class ProgramConstants
{
    public static String BASE_URL = "https://apifootball.com/api/?action=";
    public static String API_KEY = "&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";

    public static final String GET_COUNTRIES = BASE_URL +  "get_countries" + API_KEY;

    public static final String GET_LEAGUE_INFO = BASE_URL +  "get_leagues&country_id=173" + API_KEY;

//  public static final String GET_STANDINGS = BASE_URL +  "get_standings&league_id=128" + API_KEY;

    public static String GET_LEAGUE = BASE_URL + "get_leagues&country_id=";

    public static String GET_STANDINGS = BASE_URL + "get_standings&league_id=";

}
