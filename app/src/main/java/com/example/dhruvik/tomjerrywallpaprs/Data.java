package com.example.dhruvik.tomjerrywallpaprs;

import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {

    static  String htmlData;
    static ArrayList<String> photoUrl= new ArrayList<String>();


    static void getPhotoUrl() {
        Pattern p = Pattern.compile("src=\"http://www.backgroundimageshd.com/wp-content/uploads/2018/02/(.*?).jpg");
        Matcher m = p.matcher(htmlData);

        while(m.find()){
            String fi = "http://www.backgroundimageshd.com/wp-content/uploads/2018/02/"+  m.group(1) +".jpg";
            if(fi.length()<200) {
                Log.i("URL", fi);
                photoUrl.add(fi);
            }
        }
    }
}
