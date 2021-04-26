package com.example.newsapp;

public class News {
    private String mtitle;
    private String msubtitle;
    private String mdate;
    private String mlink;
    News(String title,String subtitle,String date ,String link)
    {
        mtitle=title;
        msubtitle=subtitle;
        mdate=date;
        mlink=link;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getMsubtitle() {
        return msubtitle;
    }

    public String getMdate() {
        return mdate;
    }

    public String getMlink() {
        return mlink;
    }
}
