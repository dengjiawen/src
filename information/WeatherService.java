/**
 * Copyright 2018 (C) Jiawen Deng. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng.
 *
 * All programmers are playwrights, and all computers are lousy actors.
 *
 *-----------------------------------------------------------------------------
 * WeatherService.java
 *-----------------------------------------------------------------------------
 * This class gets weather info from OpenWeatherMap API and relays them to the
 * UI.
 *-----------------------------------------------------------------------------
 */

package information;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import ui.RenderingService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

public class WeatherService {

    // various API elements and XPATH Expressions for parsing XML
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String CITY_ID = "id=6094817";
    private static final String SUFFIX = "&mode=xml";

    private static final String WEATHER = "weather?";
    private static final String FORECAST = "forecast?";

    private static final String XPATH_EXPR_TEMP = "//temperature/@value";
    private static final String XPATH_EXPR_HIGH = "//temperature/@max";
    private static final String XPATH_EXPR_LOW = "//temperature/@min";
    private static final String XPATH_EXPR_HUM = "//humidity/@value";
    private static final String XPATH_EXPR_PSR = "//pressure/@value";
    private static final String XPATH_EXPR_RISE = "//sun/@rise";
    private static final String XPATH_EXPR_SET = "//sun/@set";

    private static final String[] XPATH_EXPR_FORE_TIME = new String[]{
            "//forecast/time[1]/@from",
            "//forecast/time[2]/@from",
            "//forecast/time[3]/@from"
    };
    private static final String[] XPATH_EXPR_FORE_TEMP = new String[]{
            "//forecast/time[1]/temperature/@value",
            "//forecast/time[2]/temperature/@value",
            "//forecast/time[3]/temperature/@value"
    };

    // current temp, high and low
    public static int temperature;
    public static int high_temp;
    public static int low_temp;

    // current humidity & pressure
    public static int humidity;
    public static int pressure;

    // sun rise time and sun set time
    public static String sun_rise;
    public static String sun_set;

    // forecast info: time/temp/prep
    public static String[] forecast_times;
    public static int[] forecast_temperatures;
    public static int[] forecast_precipitations;

    // timer that updates weather info periodically
    private static Timer weather_information_service;

    // XML document retrieved from API
    private static Document latest_weather_from_api;
    private static Document latest_forecast_from_api;

    /**
     * WARNING:
     * ACCESS TO THE SOURCE CODE FOR THIS SOFTWARE DOES NOT GRANT YOU AUTOMATIC
     * PERMISSION TO UTILIZE THE FOLLOWING API KEY. YOU HAVE NO RIGHT TO USE,
     * DISTRIBUTE, OR SHARE THIS API KEY UNDER ANY CIRCUMSTANCES.
     *
     * THIS API KEY IS FOR THE OPEN WEATHER MAP API,
     * AND IT BELONGS TO JIAWEN (FRED) DENG.
     */
    private static final String API_KEY = "&APPID=ac8e58a5a0c08c34492528a0328443cd";

    /**
     * Initialize the WeatherService
     */
    public static void init () {

        Console.printGeneralMessage("Initializing WeatherService");

        // update weather every minute,
        // request document from API, get data
        weather_information_service = new Timer(60 * 1000, e -> {

            Console.printGeneralMessage("Weather daemon, periodic update event");

            requestDocumentsFromAPI();
            getWeatherDataFromAPI();
            getForecastDataFromAPI();

            RenderingService.invokeRepaint();
        });

        // start information service
        weather_information_service.setInitialDelay(60 * 1000);
        weather_information_service.start();

        Console.printGeneralMessage("Weather daemon, initial update");
        // request the first round of data
        requestDocumentsFromAPI();
        getWeatherDataFromAPI();
        getForecastDataFromAPI();
    }

    /**
     * Downloads XML file from the API, and save it in a Document object.
     */
    private static void requestDocumentsFromAPI() {

        // downloads XML file from the API, and save it in a Document object.

        Console.printGeneralMessage("Weather daemon, requesting weather XML data from API " + BASE_URL + WEATHER + CITY_ID + API_KEY + SUFFIX);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            latest_weather_from_api = builder.parse(BASE_URL + WEATHER + CITY_ID + API_KEY + SUFFIX);
            latest_weather_from_api.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Console.printGeneralMessage("GET request successful.");

        Console.printGeneralMessage("Weather daemon, requesting forecast XML data from API " + BASE_URL + FORECAST + CITY_ID + API_KEY + SUFFIX);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            latest_forecast_from_api = builder.parse(BASE_URL + FORECAST + CITY_ID + API_KEY + SUFFIX);
            latest_forecast_from_api.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Console.printGeneralMessage("GET request successful.");
    }

    /**
     * Parses the XML using XPath expressions
     * @param document      target document
     * @param XPATH_EXPR    target XPath expressions
     * @return              requested String
     */
    private static String getDataFromXML(Document document, String XPATH_EXPR) {

        // parse using XPathFactory instance

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        try {
            XPathExpression expr = xpath.compile(XPATH_EXPR);

            Console.printGeneralMessage("Result for XPath Expression " + XPATH_EXPR + " is " + expr.evaluate(document));

            return expr.evaluate(document);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method that converts Kelvin to Celcius
     * @param kelvin    Kelvin
     * @return      Celcius
     */
    private static int kelvinToCelcius(Float kelvin) {
        return (int)Math.round(kelvin - 273.15);
    }

    /**
     * Method that converts Kelvin (in String format) to Celcius
     * @param kelvin    Kelvin
     * @return      Celcius
     */
    private static int kelvinToCelcius(String kelvin) {
        return kelvinToCelcius(Float.parseFloat(kelvin));
    }

    /**
     * Method that converts 24 hour time to 12 hour
     * @param _24_hour  24 hour time
     * @return  12 hour time
     */
    private static String _24HourTo12Hour(String _24_hour) {

        // convert formats using SimpleDateFormat

        SimpleDateFormat _24_hour_format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12_hour_format = new SimpleDateFormat("hh:mm aa");
        try {
            return _12_hour_format.format(_24_hour_format.parse(_24_hour));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method that converts 24 hour time to 12 hour time, eliminating minutes
     * @param _24_hour  24 hour time
     * @return  12 hour time, no minutes
     */
    private static String _24HourTo12HourForForecast(String _24_hour) {

        // convert formats using SimpleDateFormat

        SimpleDateFormat _24_hour_format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12_hour_format = new SimpleDateFormat("h aa");
        try {
            return _12_hour_format.format(_24_hour_format.parse(_24_hour));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method that updates variables using API XML files
     */
    private static void getWeatherDataFromAPI() {

        Console.printGeneralMessage("Weather daemon, updating weather data");

        temperature = kelvinToCelcius(getDataFromXML(latest_weather_from_api, XPATH_EXPR_TEMP));
        high_temp = kelvinToCelcius(getDataFromXML(latest_weather_from_api, XPATH_EXPR_HIGH));
        low_temp = kelvinToCelcius(getDataFromXML(latest_weather_from_api, XPATH_EXPR_LOW));

        humidity = Integer.parseInt(getDataFromXML(latest_weather_from_api, XPATH_EXPR_HUM));
        pressure = Integer.parseInt(getDataFromXML(latest_weather_from_api, XPATH_EXPR_PSR));

        sun_rise = _24HourTo12Hour(getDataFromXML(latest_weather_from_api, XPATH_EXPR_RISE).substring(11, 16));
        sun_set = _24HourTo12Hour(getDataFromXML(latest_weather_from_api, XPATH_EXPR_SET).substring(11, 16));
    }

    /**
     * Method that updates forecast variables using API XML files
     */
    private static void getForecastDataFromAPI() {

        Console.printGeneralMessage("Weather daemon, updating forecast data");

        forecast_times = new String[3];
        forecast_temperatures = new int[3];
        forecast_precipitations = new int[3];

        for (int i = 0; i < 3; i ++) {
            forecast_times[i] = _24HourTo12HourForForecast(getDataFromXML(latest_forecast_from_api, XPATH_EXPR_FORE_TIME[i]).substring(11, 16));
            forecast_temperatures[i] = kelvinToCelcius(getDataFromXML(latest_forecast_from_api, XPATH_EXPR_FORE_TEMP[i]));
            forecast_precipitations[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
    }

}
