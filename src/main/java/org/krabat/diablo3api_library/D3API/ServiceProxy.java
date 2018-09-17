package org.krabat.diablo3api_library.D3API;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

import android.util.Log;

import org.krabat.diablo3api_library.Types.Diablo3APIRequestEnum;

public class ServiceProxy
{
	private static final String PATH = "d3/profile";
	
    private String host;
    private String apiKey;

	public ServiceProxy(String host, String apiKey)
    {
        this.host = host;
        this.apiKey = apiKey;
    }

    private void AsyncServiceCall(String address, String battletagName, int battletagCode, Diablo3APIRequestEnum requestEnum, IDownloadListener downloadListener) throws IOException
	{
		Log.d("", "");

		URL url = new URL(address);
	    DownloadTask dl = new DownloadTask(battletagName, battletagCode, requestEnum, downloadListener);
	    dl.getRemoteData(url);
	}

	public void GetCareerProfile(String battletagName, int battletagCode, IDownloadListener downloadListener) throws UnsupportedEncodingException {
        //https://dev.battle.net/io-docs
        //https://eu.api.battle.net/d3/profile/Krabat%232146/?locale=en_GB&apikey=dfhu2jbc9ktsjttbr58b6abhsqc7wyk9
        String urlAddress = String.format(Locale.US,"%s/%s/%s%s%d/?apikey=%s", host, PATH, battletagName, URLEncoder.encode("#", "UTF-8"), battletagCode, apiKey);

		try
		{
			AsyncServiceCall(urlAddress, battletagName, battletagCode, Diablo3APIRequestEnum.CareerProfile, downloadListener);
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}
	
	public void GetHeroProfile(String battletagName, int battletagCode, int heroId, IDownloadListener downloadListener)
	{
		//OLD: From http://blizzard.github.io/d3-api-docs/ :
		//battletag-name ::= <regional battletag allowed characters>
		//battletag-code ::= <integer>
		//hero-id ::= <integer>
		//url ::= <host> "/api/d3/profile/" <battletag-name> "-" <battletag-code> "/hero/" <hero-id>
		String address = String.format(Locale.US, "%s/%s/%s-%d/hero/%d", host, PATH, battletagName, battletagCode, heroId);

		//NEW: //https://dev.battle.net/io-docs
		// https://eu.api.battle.net/d3/profile/krabat%232146/hero/65549824?locale=en_GB&apikey=dfhu2jbc9ktsjttbr58b6abhsqc7wyk9

		try
		{
			AsyncServiceCall(address, battletagName, battletagCode, Diablo3APIRequestEnum.HeroProfile, downloadListener);
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	public void GetFollower(String battletagName, int battletagCode, int heroId, IDownloadListener downloadListener)
	{
		//"https://us.api.battle.net/d3/data/follower/templar?apikey=dfhu2jbc9ktsjttbr58b6abhsqc7wyk9"
	}
}