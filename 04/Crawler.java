package project1;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;
import java.security.*;
import java.security.cert.*;

public class Crawler {
	public static void main(String[] args) {
		int depth=0;
		
		if (args.length !=2){
			System.out.println("write format : java Crawler <URL> <depth>");
			System.exit(1);
		}
		
		else {
			try {
				depth = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				System.out.println("write depth as intger format");
				System.exit(1);
			}
		}
		//want to process list9
		List <UrlDepthPair> pendingURLs = new LinkedList<>();
		UrlDepthPair new_url = null;
		try {
			new_url = new UrlDepthPair(args[0],0); 
		}
		catch (MalformedURLException e) {
			System.out.println("from must be http...");
			System.exit(1);
		}
		
		pendingURLs.add(new_url);
		try {
			 TrustManager[] trustAllCerts = 
					 new TrustManager[]{ new X509TrustManager() {
						 public X509Certificate[] getAcceptedIssuers() { return null; }
						 public void checkClientTrusted(X509Certificate[] certs, String authType) {}
						 public void checkServerTrusted(X509Certificate[] certs, String authType) {}
			 }};
			 SSLContext sc = SSLContext.getInstance("SSL");
			 sc.init(null, trustAllCerts, new SecureRandom());
			 HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) { System.exit(1); }		
		//want to save already seen URL never want to see again
		LinkedList <String> alreadyURL = new LinkedList<>();
		alreadyURL.add(new_url.getURLString());
		
		//want to see all URL
		LinkedList <UrlDepthPair> resultURL = new LinkedList<>();
		// Set up SSL context for HTTPS support


		while (!pendingURLs.isEmpty()) {
			UrlDepthPair delList = pendingURLs.remove(0);
			
			resultURL.add(delList);
			
			int this_depth = delList.getDepth();
			try {
				
				

				HttpURLConnection conn = (HttpURLConnection)delList.url.openConnection();
				conn.setConnectTimeout(1000);
				conn.setReadTimeout(3000);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				System.out.println(">>>"+delList.getURLString());
				String URL_finder = "<a href=\"http";
				int BeginIndex=0;
				int EndIndex=0;
				
				for (String line; (line = in.readLine()) != null;) {
					BeginIndex = line.indexOf(URL_finder,BeginIndex);
					//can't find "<a href=" the next line
					while (BeginIndex!=-1) {
						
						BeginIndex = BeginIndex + 9;
						EndIndex=line.indexOf("\"",BeginIndex);
						//can't find "> then next line(which means seperate in two sentence
						if (EndIndex==-1) {
							break;
						}
						//extract "https://~"
						String newlink = line.substring(BeginIndex,EndIndex);
						
						//to find proper depth and to know already seen url
						if (this_depth+1 <= depth) {
							if (!alreadyURL.contains(newlink)) {
								System.out.println("!!!"+newlink);
								alreadyURL.add(newlink);
								try {
									pendingURLs.add(new UrlDepthPair(newlink, this_depth+1));							
								}	
								catch(Exception e) {
									continue;
								}
							}
							
							BeginIndex = line.indexOf(URL_finder,EndIndex+1);
				
						}
						else {
							break;
						}
						
						//System.out.println("");	
					}
				}
				in.close();
			}
			catch (Exception e) {
				continue;
			}
			
			
		}
		Iterator<UrlDepthPair> iter = resultURL.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toString());
        }
        System.out.println(resultURL.size());
	
	
	}
	
	
	
}

