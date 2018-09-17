package org.krabat.diablo3api_library.D3API;

import org.krabat.diablo3api_library.Types.Diablo3APIRequestEnum;
import org.krabat.diablo3api_library.Types.IDiablo3APIType;

public interface IDownloadListener 
{
	void onDownloadCompleted(Diablo3APIRequestEnum requestEnum, IDiablo3APIType diablo3APIType);
}