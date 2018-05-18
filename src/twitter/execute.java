package twitter;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class execute {
	static final String[] EXTENSION_LIST = {".jpg",".png",".gif"};
	static final String CUSTOMER_KEY = "UhuBsj54DbRnnHGQYaPywHmAi";
	static final String CUSTOMER_SECRET = "e1zSaGoQorVKzyW5W7U8LaAmkUsf3nnjdIHCxh45Y03bnnBj86";
	static final String ACCESS_TOKEN = "42566916-5CI2BDtmiUQdWFKtTY6ajgxmLlYjURI4ftW2K88h3";
	static final String ACCESS_TOKEN_SECRET = "YHfdFB1RJxIcHQ6XOriX0GZhmZmFpNmI5wegaBIRDflIU";
	static final String SEARCH_KEYWORD = "JustinBieber"; 
	
	public static void main(String[] args) throws TwitterException {
		//twitterコンストラクタ
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		//アクセス設定
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(CUSTOMER_KEY)
				.setOAuthConsumerSecret(CUSTOMER_SECRET)
				.setOAuthAccessToken(ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Query query = new Query(SEARCH_KEYWORD);
		QueryResult result = null;
		
		//検索
		result = twitter.search(query);
		result.getTweets().forEach(status->{
			MediaEntity[] arrMedia = status.getMediaEntities();
			for(MediaEntity media : arrMedia){
				for(String extension : EXTENSION_LIST){
					if(media.getMediaURL().endsWith(extension)){
						System.out.println(media.getMediaURL());
						try{							
							DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
							String name =  df.format(status.getCreatedAt())+extension;
							InputStream in = new URL(media.getMediaURL()).openStream();
							Files.copy(in,Paths.get(name),StandardCopyOption.REPLACE_EXISTING);
						}catch(Exception ex){
							ex.printStackTrace();
						}				
					}					
				}
			}
		});
	}
}
