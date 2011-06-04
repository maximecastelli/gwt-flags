package com.scrontch.flags.server;

import com.scrontch.flags.client.FlagService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FlagServiceImpl extends RemoteServiceServlet implements FlagService {
	
	@Override
	public FlagData getRandomFlagData() {
		Flag flag = Flag.getRandomFlag();
		return new FlagData(
			flag.getSvgString(),
			flag.getFlagInfo()
		);
	}
}
