package com.scrontch.flags.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FlagServiceAsync {

	void getRandomFlag(AsyncCallback<String> callback);

}
