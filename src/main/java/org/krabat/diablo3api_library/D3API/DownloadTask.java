package org.krabat.diablo3api_library.D3API;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.krabat.diablo3api_library.Types.Diablo3APIRequestEnum;
import org.krabat.diablo3api_library.Types.IDiablo3APIType;

import android.os.AsyncTask;

public class DownloadTask extends AsyncTask<URL, Void, IDiablo3APIType> implements IDownloadTask
{
    private final String battletagName;
    private final int battletagCode;
    private final Diablo3APIRequestEnum requestEnum;
    private final IDownloadListener downloadListener;

    public DownloadTask(final String battletagName, final int battletagCode, final Diablo3APIRequestEnum requestEnum, final IDownloadListener downloadListener)
    {
        this.battletagName = battletagName;
        this.battletagCode = battletagCode;
        this.requestEnum = requestEnum;
        this.downloadListener = downloadListener;
    }

	@Override
	public void getRemoteData(URL url)
	{
		// "Anonymous" method (inner class), that calls an URL and returns the call in the downloadListener. 
//		AsyncTask<URL, Void, IDiablo3APIType> asyncTask = new AsyncTask<URL, Void, IDiablo3APIType>()
//		asyncTask.execute(url);
        this.execute(url);
	}

	private IDiablo3APIType ConvertJSONtoDiablo3APIType(String battletagName, int battletagCode, Diablo3APIRequestEnum requestEnum, String json)
	{
		switch(requestEnum)
		{
			case CareerProfile:
				return Converter.GetCareerProfileFromJSON(battletagName, battletagCode, json);

			case HeroProfile:
				return Converter.GetHeroProfileFromJSON(json);
				
			case Item:
				break;
				
			default:
				break;
		}
		
		return null;
	}

	@Override
	protected IDiablo3APIType doInBackground(URL... urls)
	{
		HttpURLConnection httpURLConnection = null;
		try
		{
			if(urls.length > 0)
			{
				URL url = urls[0];
				httpURLConnection = (HttpURLConnection)url.openConnection();
			}
			else
			{
				throw new IllegalArgumentException("URL address not supplied.", new Throwable());
			}
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
			return Converter.GetError(ioex);
		}

		try
		{
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			String json = ReadStream(bufferedInputStream);

			IDiablo3APIType diablo3APIType = ConvertJSONtoDiablo3APIType(battletagName, battletagCode, requestEnum, json);
			return diablo3APIType;
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
			return Converter.GetError(ioex);
		}
		finally
		{
			httpURLConnection.disconnect();
		}
	}

	@Override
	protected void onPostExecute(IDiablo3APIType diablo3APIType)
	{
		super.onPostExecute(diablo3APIType);
		downloadListener.onDownloadCompleted(requestEnum, diablo3APIType);
	}

    /*
    @Override
    protected void onProgressUpdate(Integer... progress)
    {
        //setProgressPercent(progress[0]);
    }
    */

	private String ReadStream(BufferedInputStream stream) throws IOException
	{
		InputStreamReader inputStreamReader = new InputStreamReader(stream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null)
		{
			sb.append(line);
		}

        return sb.toString();
	}
}