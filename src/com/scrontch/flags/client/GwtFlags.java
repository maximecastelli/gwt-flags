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


interface IntegerSetter {
	public void setInteger(int value);
}

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

	private HTML flagWidget = new HTML("<p>[This App requires a browser which supports inline SVG]</p>");
	private HTML flagLink = new HTML("");
	private FlagInfo flagInfo = new FlagInfo();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Generate New Flag");

		// We can add style names to widgets
		//sendButton.addStyleName("sendButton");

//		FlagResources resources = GWT.create(FlagResources.class);
		final String[] division_icon_urls = new String[] {
			"images/div0.png",
			"images/div1.png",
			"images/div2.png",
			"images/div3.png",
			"images/div4.png",
			"images/div5.png",
			"images/div6.png",
			"images/div7.png",
			"images/div8.png",
			"images/div9.png",
			"images/div10.png",
			"images/div11.png"
		};
		
		final String[] overlay_icon_urls = new String[] {
			"images/ovl0.png",
			"images/ovl1.png",
			"images/ovl2.png",
			"images/ovl3.png",
			"images/ovl4.png",
			"images/ovl5.png",
			"images/ovl6.png",
			"images/ovl7.png",
			"images/ovl8.png",
			"images/ovl9.png",
			"images/ovl10.png",
			"images/ovl11.png"
		};
		
		final String[] symbol_icon_urls = new String[] {
			"images/sym0.png",
			"images/sym1.png",
			"images/sym2.png",
			"images/sym3.png",
			"images/sym4.png",
			"images/sym5.png",
			"images/sym6.png",
			"images/sym7.png"
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


		class RadioGroupHandler implements ClickHandler {
			ToggleButton button;
			List<ToggleButton> group;
			IntegerSetter intSetter;
			
			public RadioGroupHandler(ToggleButton button, List<ToggleButton> group, IntegerSetter intSetter) {
				this.button = button;
				this.group = group;
				this.intSetter = intSetter;
				this.group.add(button);
			}
			
			public void onClick(ClickEvent event) {
				intSetter.setInteger(group.indexOf(button));
				//TODO: reload flag
				
				//iterator loop
				Iterator<ToggleButton> iterator = group.iterator();
				while (iterator.hasNext()) {
					iterator.next().setDown(false);
				}
				button.setDown(true);
			}
		}

		//--------------------------------------------------
		// Divisions
		//--------------------------------------------------
		final List<ToggleButton> divisionButtons = new ArrayList<ToggleButton>();
		
		{
			Grid grid = new Grid((division_icon_urls.length / 6)+1, 6);
			verticalPanel.add(grid);
		
			for (int i=0; i<division_icon_urls.length; i++) {
				Image image = new Image(division_icon_urls[i]);
				image.setSize("36px", "24px");
				ToggleButton button = new ToggleButton(image);
				button.addStyleName("flag-icon");
				grid.setWidget(i/6, i%6, button);
				button.addClickHandler(
					new RadioGroupHandler(button, divisionButtons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.divIdx = value;
							}
						}
					) 
				);
			}
		}
		
		// Division colors 1
		final List<ToggleButton> divColor1Buttons = new ArrayList<ToggleButton>();
		
		{
			Grid grid = new Grid(1, color_codes.length);
			verticalPanel.add(grid);
		
			for (int i=0; i<color_codes.length; i++) {
				ToggleButton button = new ToggleButton();
				button.addStyleName("color-icon");
				button.getElement().getStyle().setProperty("backgroundColor", color_codes[i]);
				grid.setWidget(0, i, button);
				button.addClickHandler(
					new RadioGroupHandler(button, divColor1Buttons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.col1 = value;
							}
						}
					) 
				);
			}
		}

		//--------------------------------------------------
		// Overlays
		//--------------------------------------------------
		final List<ToggleButton> overlayButtons = new ArrayList<ToggleButton>();
		
		{
			Grid grid = new Grid((overlay_icon_urls.length / 6)+1, 6);
			verticalPanel.add(grid);
		
			for (int i=0; i<overlay_icon_urls.length; i++) {
				Image image = new Image(overlay_icon_urls[i]);
				image.setSize("36px", "24px");
				ToggleButton button = new ToggleButton(image);
				button.addStyleName("flag-icon");
				grid.setWidget(i/6, i%6, button);
				button.addClickHandler(
					new RadioGroupHandler(button, overlayButtons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.ovlIdx = value;
							}
						}
					) 
				);
			}
		}
		
		//--------------------------------------------------
		// Symbols
		//--------------------------------------------------
		final List<ToggleButton> symbolButtons = new ArrayList<ToggleButton>();
		
		{
			Grid grid = new Grid((symbol_icon_urls.length / 6)+1, 6);
			verticalPanel.add(grid);
		
			for (int i=0; i<symbol_icon_urls.length; i++) {
				Image image = new Image(symbol_icon_urls[i]);
				image.setSize("36px", "24px");
				ToggleButton button = new ToggleButton(image);
				button.addStyleName("flag-icon");
				grid.setWidget(i/6, i%6, button);
				button.addClickHandler(
					new RadioGroupHandler(button, symbolButtons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.symIdx = value;
							}
						}
					) 
				);
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
			public void requestFlag() {
				sendButton.setEnabled(false);
				serverResponseLabel.setText("");
				flagService.getRandomFlagData(
						new AsyncCallback<FlagService.FlagData>() {
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

							public void onSuccess(FlagService.FlagData flagData) {
								flagInfo = flagData.flagInfo;

								//Update the UI
								for (int i=0; i<divisionButtons.size(); i++)
									divisionButtons.get(i).setDown(i == flagInfo.divIdx);
								for (int i=0; i<overlayButtons.size(); i++)
									overlayButtons.get(i).setDown(i == flagInfo.ovlIdx);
								for (int i=0; i<symbolButtons.size(); i++)
									symbolButtons.get(i).setDown(i == flagInfo.symIdx);
								
		           				flagWidget.setHTML(flagData.svgString);
		           				flagLink.setHTML("<a href=\"/gwtflags/SvgFileService?"
		           						 + flagData.flagInfo.getQueryString()
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
		handler.requestFlag();
	}
}
