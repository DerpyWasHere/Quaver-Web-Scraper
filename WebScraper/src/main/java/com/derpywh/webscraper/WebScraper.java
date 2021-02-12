/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.derpywh.webscraper;

import java.util.Scanner;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.Arrays;
/**
 *
 * @author DER-PC
 */
public class WebScraper 
{
    String[] notSplitScoreArray = new String[30];
    // Each value in ArrayList songNames and scoreValues corresponds to eachother
    // ex: songNames[0] ≙ scoreValues[0]
    ArrayList<String> songNames = new ArrayList<String>();
    ArrayList<String> scoreValues = new ArrayList<String>();
    
    public WebScraper(String url) throws IOException
    {
        populateNotSplitArray(url);
        populateSplitArrays();
    }
    
    public String[][] returnProfileData()
    {
        String[][] profileData = new String[13][];
        for(int i = 0; i < profileData.length; i++)
        {
            profileData[i] = returnProfileData(i); 
            //System.out.println(Arrays.deepToString(profileData));
        }
        return profileData;
    }
    
    private String[] returnProfileData(int index)
    {
        String[] profileData = new String[13];
        
        profileData[0] = getSongURL(index);
        profileData[1] = getSongArtist(index);
        profileData[2] = getSongTitle(index);
        profileData[3] = getSongDifficultyName(index);
        profileData[4] = getGrade(index);
        profileData[5] = getPerformanceRating(index) + "";
        profileData[6] = getAccuracy(index);
        profileData[7] = getRate(index);
        profileData[8] = getMods(index);
        profileData[9] = getScore(index);
        profileData[10] = getScrollSpeed(index) + "";
        profileData[11] = getMaxCombo(index);
        profileData[12] = getRatio(index) + "";
        
        return profileData;
    }
    
    private void printScoreInfo(int index)
    {
        System.out.println(getSongURL(index));
        System.out.println(getSongArtist(index));
        System.out.println(getSongTitle(index));
        System.out.println(getSongDifficultyName(index));
        System.out.println(getGrade(index));
        System.out.println(getPerformanceRating(index));
        System.out.println(getAccuracy(index));
        System.out.println(getRate(index));
        System.out.println(getMods(index));
        System.out.println(getScore(index));
        System.out.println(getScrollSpeed(index));
        System.out.println(getMaxCombo(index));
        System.out.println(getRatio(index));
    }
    
    private String getSongURL(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element name = doc.select("a").first();
        return name.attr("href");
    }
    
    private String getSongName(int index)
    {
        // Method used in development - not used
        Document doc = Jsoup.parse(songNames.get(index));
        Element name = doc.select("a").first();
        return name.text();
    }
    
    private String getSongArtist(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element name = doc.select("a").first();
        String[] str = name.text().split(" - ");
        return str[0];
    }
    
    private String getSongTitle(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element name = doc.select("a").first();
        String[] str = name.text().split(" - ");
        String[] str2 = str[1].split("\\[[^\\]]*\\]");
        return str2[0];
    }
    
    private String getSongDifficultyName(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element name = doc.select("a").first();
        String[] str = name.text().split(" - ");
        String[] str2 = str[1].split("\\[");
        return str2[1].split("\\]")[0];
    }
    
    private String getGrade(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element grade = doc.select("img").first();
        return grade.attr("title");
    }
    
    private double getPerformanceRating(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element score = doc.select("body").first();
        String s = score.text();
        String[] s2 = s.split(" ");
        if(s2[s2.length - 1].endsWith("Mirror"))
            return Double.parseDouble(s2[s2.length - 4]);
        
        else if(s2[s2.length - 2].endsWith("Mirror,"))
            return Double.parseDouble(s2[s2.length - 4]);
        
        else return Double.parseDouble(s2[s2.length - 3]);
    }
    
    private String getAccuracy(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element score = doc.select("body").first();
        String s = score.text();
        String[] s2 = s.split(" ");
        if(s2[s2.length - 1].endsWith("Mirror"))
            return s2[s2.length - 3];
        
        else if(s2[s2.length - 2].endsWith("Mirror,"))
            return s2[s2.length - 3];
        
        else return s2[s2.length - 2];
    }
    
    private String getRate(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element score = doc.select("body").first();
        String s = score.text();
        String[] s2 = s.split(" ");
        if(s2[s2.length - 1].endsWith("Mirror"))
             {
            String[] temp = s2[s2.length - 2].split(",");
            return temp[0];
        }
        else if(s2[s2.length - 2].endsWith("Mirror,"))
            return s2[s2.length - 1];
        
        else return s2[s2.length - 1];
    }
    
    private String getMods(int index)
    {
        Document doc = Jsoup.parse(songNames.get(index));
        Element score = doc.select("body").first();
        String s = score.text();
        String[] s2 = s.split(" ");
        if(s2[s2.length - 1].endsWith("Mirror"))
            return s2[s2.length - 1];
        else if(s2[s2.length - 2].endsWith("Mirror,"))
        {
            String[] temp = s2[s2.length - 2].split(",");
            return temp[0];
        }
        return "None";
    }
    
    private String getScore(int index)
    {
        Document d = Jsoup.parse(scoreValues.get(index));
        Elements e = d.select("span").eq(0);
        return e.text();
    }
    
    private double getScrollSpeed(int index)
    {
        Document d = Jsoup.parse(scoreValues.get(index));
        Elements e = d.select("span").eq(1);
        return Double.parseDouble(e.text());
    }
    
    private String getMaxCombo(int index)
    {
        Document d = Jsoup.parse(scoreValues.get(index));
        Elements e = d.select("span").eq(2);
        return e.text();
    }
    
    private Double getRatio(int index)
    {
        Document d = Jsoup.parse(scoreValues.get(index));
        Elements e = d.select("span").eq(3);
        return Double.parseDouble(e.text());
    }
    
    private Document getWebPage(String url) throws IOException
    {
        Validate.notEmpty(url, "url is empty");
        Document doc = Jsoup.connect(url).get();
        Element username = doc.select("a.text-white").first();
        System.out.printf("Fetching %s...\n", username.text());
        return doc;
        //return "https://quavergame.com/user/22989"; // Temp for testing
        //return "https://quavergame.com/user/12446"; // Temp for testing
    }
    
    private void populateNotSplitArray(String url) throws IOException
    {
        Document doc = getWebPage(url);
        Element scoreHTML = doc.select("#scores.row.mg-bottom").first();
        
        Elements e2 = scoreHTML.getElementsByTag("tr");
        String[] tempScoreHTMLArray = e2.toString().split("</tr>");
        
        for(int n = 0; n < 30; n++)
        {
            notSplitScoreArray[n] = tempScoreHTMLArray[n + 1];
            Validate.notNull(notSplitScoreArray[n], "null");
        }
    }
    
    private void populateSplitArrays()
    {
        for(int n = 0; n < 30; n++)
        {
            if(n % 2 == 0)
                songNames.add(notSplitScoreArray[n]);
            if(n % 2 != 0)
                scoreValues.add(notSplitScoreArray[n]);
        }
    }
}
