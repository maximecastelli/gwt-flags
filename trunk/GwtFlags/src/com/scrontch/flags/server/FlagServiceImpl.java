package com.scrontch.flags.server;

import com.scrontch.flags.client.FlagInfo;
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

	@Override
	public FlagData getFlagData(FlagInfo flagInfo) {
		Flag flag = new Flag();
		flag.division = Flag.divisions.get(flagInfo.divIdx);
		flag.color1 = Flag.colors.get(flagInfo.col1);
		flag.color2 = Flag.colors.get(flagInfo.col2);
		flag.color3 = Flag.colors.get(flagInfo.col3);
		flag.overlay = Flag.overlays.get(flagInfo.ovlIdx);
		flag.color4 = Flag.colors.get(flagInfo.col4);
		flag.symbol = Flag.symbols.get(flagInfo.symIdx);
		flag.color5 = Flag.colors.get(flagInfo.col5);
		
		return new FlagData(
			flag.getSvgString(),
			flag.getFlagInfo()
		);
	}
}
