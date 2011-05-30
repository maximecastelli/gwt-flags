package com.scrontch.flags.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.scrontch.flags.client.FlagService.FlagInfo;

public interface FlagServiceAsync {

	void getRandomFlagInfo(AsyncCallback<FlagInfo> callback);
}
