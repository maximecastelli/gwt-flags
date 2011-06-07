package com.scrontch.flags.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.scrontch.flags.client.FlagService.FlagData;

public interface FlagServiceAsync {

	void getRandomFlagData(AsyncCallback<FlagData> callback);

	void getFlagData(FlagInfo flagInfo, AsyncCallback<FlagData> callback);
}
