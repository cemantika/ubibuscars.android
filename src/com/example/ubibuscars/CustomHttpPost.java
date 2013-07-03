package com.example.ubibuscars;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


public class CustomHttpPost {
	
	public static String postData(String url, List<NameValuePair> nameValuePairs) {
	    
		//Create new default HTTPClient
		HttpClient httpclient;
	    
	    //Use HTTP POST method
	    HttpPost httppost;
	
	    
	    //Create a HTTP Response and HTTP Entity
	    HttpResponse response;
	    HttpEntity entity;
	    
	    String resposta = null;
		
		
		httpclient = new DefaultHttpClient();
		
		//Create new HTTP POST with URL to php file as parameter
		httppost = new HttpPost(url);

		//Next block of code needs to be surrounded by try/catch block for it to work
		try {
			
			//Add array list to http post
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
			
			//assign executed form container to response
			response = httpclient.execute(httppost);
			
			resposta = response.toString();
			entity = response.getEntity();
			resposta = EntityUtils.toString(entity);

			
		} catch(Exception e){
			e.printStackTrace();
		}
		return resposta;
		
	}
	
	public static String readJson(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
          HttpResponse response = client.execute(httpGet);
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
              builder.append(line);
            }
          } else {
            
          }
        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return builder.toString();
      }
	
	
	public static Bitmap getImagem(String img_url) {
        URL url = null;
		try {
			url = new URL(img_url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Bitmap bmp = null; 
        try {
			bmp=BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bmp;
		
	}	
	
	
}