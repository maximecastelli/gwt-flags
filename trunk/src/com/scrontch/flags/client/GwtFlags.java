package com.scrontch.flags.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtFlags implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Flag service.
	 */
	private final FlagServiceAsync flagService = GWT.create(FlagService.class);

	private HTML flagWidget = new HTML("[Press button]");
	private HTML flagLink = new HTML("");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		FlagService.FlagInfo flagInfo = new FlagService.FlagInfo();
		
		final Button sendButton = new Button("Generate New Flag");

		// We can add style names to widgets
		//sendButton.addStyleName("sendButton");

		FlagResources resources = GWT.create(FlagResources.class);
		final String[] division_image_urls = new String[] {
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png",
			"images/flag-div0.png"
		};
		
		final String[] color_codes = new String[] {
		        "#000000",
		        "#ffffff",
		        "#ce1126",
		        "#00335b",
		        "#006b3f",
		        "#fcd116",
		        "#ff8400",
		        "#3a75c4"
			};
			
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("sendButtonContainer");
		rootPanel.add(sendButton);
		RootPanel.get("flagContainer").add(flagWidget);
		RootPanel.get("flagLink").add(flagLink);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		rootPanel.add(verticalPanel, 22, 66);
		verticalPanel.setSize("100px", "100px");

		final List<ToggleButton> divisionButtons = new ArrayList<ToggleButton>();
		
		class DivisionRadio implements ClickHandler {
			ToggleButton button;
			public DivisionRadio(ToggleButton button) {
				this.button = button;
				divisionButtons.add(button);
			}
			public void onClick(ClickEvent event) {
				int i = divisionButtons.indexOf(button);
				//TODO
				
				//iterator loop
				Iterator<ToggleButton> iterator = divisionButtons.iterator();
				while ( iterator.hasNext() ){
					iterator.next().setDown(false);
				}
				button.setDown(true);
			}
		}

		// divisions
		{
			Grid grid = new Grid((division_image_urls.length / 6)+1, 6);
			//grid.setSize("100px", "100px");
			verticalPanel.add(grid);
		
			for (int i=0; i<division_image_urls.length; i++) {
				Image image = new Image(division_image_urls[i]);
				image.setSize("36px", "24px");
				ToggleButton button = new ToggleButton(image);
				button.addStyleName("flag-icon");
				grid.setWidget(i/6, i%6, button);
				DivisionRadio radio = new DivisionRadio(button); 
				button.addClickHandler(radio);
			}
		}
	
		
		// division-colors 1
		{
			Grid grid = new Grid(1, color_codes.length);
			//grid.setSize("100px", "100px");
			verticalPanel.add(grid);
		
			for (int i=0; i<color_codes.length; i++) {
				SimplePanel simplePanel = new SimplePanel();
				simplePanel.addStyleName("color-icon");
				grid.setWidget(0, i, simplePanel);
				simplePanel.getElement().getStyle().setProperty("backgroundColor", color_codes[i]);
//				simplePanel.addClickHandler(colorClickHandler);

			}
		}
	
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				requestFlag();
			}

			/**
			 * Send the request to the server and wait for a response.
			 */
			private void requestFlag() {
				sendButton.setEnabled(false);
				serverResponseLabel.setText("");
				flagService.getRandomFlagInfo(
						new AsyncCallback<FlagService.FlagInfo>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(FlagService.FlagInfo flagInfo) {
		           				flagWidget.setHTML(flagInfo.svgString);
		           				flagLink.setHTML("<a href=\"/gwtflags/SvgFileService?"
		           						 + flagInfo.queryString
		           						 + "\">Download as [.svg]</a>"
		           						);
		           				//System.out.println("sucess.");
		        				sendButton.setEnabled(true);
		        				sendButton.setFocus(true);
		           			}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
	}
}
