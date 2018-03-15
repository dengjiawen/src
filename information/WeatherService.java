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
 *-----------------------------------------------------------------------------
 * WeatherService.java
 *-----------------------------------------------------------------------------
 * This is a specialized java class designed to parse content from
 * property list files (.plist files) in XML format.
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

    public static int temperature;
    public static int high_temp;
    public static int low_temp;

    public static int humidity;
    public static int pressure;

    public static String sun_rise;
    public static String sun_set;

    public static String[] forecast_times;
    public static int[] forecast_temperatures;
    public static int[] forecast_precipitations;

    private static Timer weather_information_service;

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

    public static void init () {

        weather_information_service = new Timer(60 * 1000, e -> {
            requestDocumentsFromAPI();
            getWeatherDataFromAPI();
            getForecastDataFromAPI();

            RenderingService.invokeRepaint();
        });
        weather_information_service.setInitialDelay(60 * 1000);
        weather_information_service.start();

        requestDocumentsFromAPI();
        getWeatherDataFromAPI();
        getForecastDataFromAPI();
    }

    public static void requestDocumentsFromAPI () {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            latest_weather_from_api = builder.parse(BASE_URL + WEATHER + CITY_ID + API_KEY + SUFFIX);
            latest_weather_from_api.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            latest_forecast_from_api = builder.parse(BASE_URL + FORECAST + CITY_ID + API_KEY + SUFFIX);
            latest_forecast_from_api.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDataFromXML (Document document, String XPATH_EXPR) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
            XPathExpression expr = xpath.compile(XPATH_EXPR);
            return expr.evaluate(document);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int kelvinToCelcius (Float kelvin) {
        return (int)Math.round(kelvin - 273.15);
    }

    public static int kelvinToCelcius (String kelvin) {
        return kelvinToCelcius(Float.parseFloat(kelvin));
    }

    public static String _24HourTo12Hour (String _24_hour) {
        SimpleDateFormat _24_hour_format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12_hour_format = new SimpleDateFormat("hh:mm aa");
        try {
            return _12_hour_format.format(_24_hour_format.parse(_24_hour));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String _24HourTo12HourForForecast (String _24_hour) {
        SimpleDateFormat _24_hour_format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12_hour_format = new SimpleDateFormat("h aa");
        try {
            return _12_hour_format.format(_24_hour_format.parse(_24_hour));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getWeatherDataFromAPI () {
        temperature = kelvinToCelcius(getDataFromXML(latest_weather_from_api, XPATH_EXPR_TEMP));
        high_temp = kelvinToCelcius(getDataFromXML(latest_weather_from_api, XPATH_EXPR_HIGH));
        low_temp = kelvinToCelcius(getDataFromXML(latest_weather_from_api, XPATH_EXPR_LOW));

        humidity = Integer.parseInt(getDataFromXML(latest_weather_from_api, XPATH_EXPR_HUM));
        pressure = Integer.parseInt(getDataFromXML(latest_weather_from_api, XPATH_EXPR_PSR));

        sun_rise = _24HourTo12Hour(getDataFromXML(latest_weather_from_api, XPATH_EXPR_RISE).substring(11, 16));
        sun_set = _24HourTo12Hour(getDataFromXML(latest_weather_from_api, XPATH_EXPR_SET).substring(11, 16));
    }

    public static void getForecastDataFromAPI () {
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
