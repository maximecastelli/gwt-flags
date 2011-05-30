package com.scrontch.flags.client;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("FlagService")
public interface FlagService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static FlagServiceAsync instance;
		public static FlagServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(FlagService.class);
			}
			return instance;
		}
	}

	@SuppressWarnings("serial")
	public class FlagInfo implements Serializable {
		String svgString;
		String queryString;

		public FlagInfo() {
		}

		public FlagInfo(String svgString, String queryString) {
			this.svgString = svgString;
			this.queryString = queryString;
		}
	}
	
	FlagInfo getRandomFlagInfo();

}
