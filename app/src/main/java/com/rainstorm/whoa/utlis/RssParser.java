package com.rainstorm.whoa.utlis;

import android.text.TextUtils;

import com.rainstorm.whoa.bean.RssBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liys on 2019-01-25.
 */

public class RssParser {
    
    public static ArrayList<RssBean> parse(String rssStr) {
        ArrayList<RssBean> rssBeans = new ArrayList<>();
        
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=factory.newPullParser();
            parser.setInput(new StringReader(rssStr));

            String title = "";
            String description = "";
            String imageLink = "";
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if ("title".equals(name)) {
                            title = parser.nextText();
                        } else if ("description".equals(name)) {
                            description = parser.nextText();
                        } else if ("enclosure".equals(name)) {
                            imageLink = parser.getAttributeValue(0);
                            if (!TextUtils.isEmpty(imageLink)) {
                                RssBean rssBean = new RssBean();
                                rssBean.title = title;
                                rssBean.description = description;
                                rssBean.imageLink = imageLink;
                                rssBeans.add(rssBean);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rssBeans;
    }
}
