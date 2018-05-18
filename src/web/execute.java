package web;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class execute {

	public static void main(String[] args) throws MalformedURLException, IOException {
		String url = "https://no1s.biz/";
		
		ArrayList<aTagVo> urlList = getFisrtUrl(url);
		for(aTagVo no1Url:urlList){
			getUrl(no1Url.getUrl());
		}
	}
	
	public static ArrayList<aTagVo> getFisrtUrl(String url) throws MalformedURLException, IOException{
		System.out.println("<"+url+">");
		ArrayList<aTagVo> voList = new ArrayList<aTagVo>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		String line;
		String regex = "^<a.*?href=\"(.*?)\".*?>(.*?)</a>";
		while ((line = br.readLine()) != null) {
			aTagVo vo = new aTagVo();
			Pattern aTagPattern = Pattern.compile(regex);
			Matcher matcherTag = aTagPattern.matcher(line);
			//System.out.println(line);
			if (matcherTag.find()) {
				String getUrl = matcherTag.group(1).replaceAll("\\s", ""); 
				String text = matcherTag.group(2).replaceAll("\\s", "");
				if(getUrl.contains("http")){
					vo.setUrl(getUrl);
					vo.setText(text);
					voList.add(vo);
					System.out.println("url1:"+getUrl+",text1:"+text);
				}
			}
		}
		
		return voList;
	}
	
	public static void getUrl(String url) throws MalformedURLException, IOException{
		System.out.println("<"+url+">");
		aTagVo vo = new aTagVo();
		BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		String line;
		String regex = "^<a.*?href=\"(.*?)\".*?>(.*?)</a>";
		while ((line = br.readLine()) != null) {
			Pattern aTagPattern = Pattern.compile(regex);
			Matcher matcherTag = aTagPattern.matcher(line);
			//System.out.println(line);
			if (matcherTag.find()) {
				String getUrl = matcherTag.group(1).replaceAll("\\s", ""); 
				String text = matcherTag.group(2).replaceAll("\\s", "");
				vo.setUrl(getUrl);
				vo.setText(text);
				System.out.println("url:"+getUrl+",text:"+text);
			}
		}
	}
}
