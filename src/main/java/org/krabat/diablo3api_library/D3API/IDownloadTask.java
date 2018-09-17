package org.krabat.diablo3api_library.D3API;

import java.net.URL;

import org.krabat.diablo3api_library.Types.Diablo3APIRequestEnum;

public interface IDownloadTask
{
	// This method runs an AsyncTask and calls supplied Callback object's methods when done
	//public void getRemoteData(URL url, IDownloadListener downloadListener);
	//public void getRemoteData(URL url, Diablo3APIRequestEnum requestEnum, final IDownloadListener downloadListener);
	void getRemoteData(URL url);
}
