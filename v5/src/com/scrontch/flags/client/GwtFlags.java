package com.scrontch.flags.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
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

	// Widgets...
	private Button sendButton = new Button("Generate Random Flag");
	private Button downloadButton = new Button("Open as standalone [.svg]");
	private DialogBox dialogBox = new DialogBox();
	private Button closeButton = new Button("Close");
	private HTML serverResponseLabel = new HTML();
	
	private HTML flagWidget = new HTML("<p>[This App requires a browser which supports inline SVG]</p>");
	//private HTML flagLink = new HTML("");
	private FlagInfo flagInfo = new FlagInfo();
	private List<ToggleButton> divisionButtons = new ArrayList<ToggleButton>();
	private List<ToggleButton> color1Buttons = new ArrayList<ToggleButton>();
	private List<ToggleButton> color2Buttons = new ArrayList<ToggleButton>();
	private List<ToggleButton> color3Buttons = new ArrayList<ToggleButton>();
	private List<ToggleButton> overlayButtons = new ArrayList<ToggleButton>();
	private List<ToggleButton> color4Buttons = new ArrayList<ToggleButton>();
	private List<ToggleButton> symbolButtons = new ArrayList<ToggleButton>();
	private List<ToggleButton> color5Buttons = new ArrayList<ToggleButton>();

	
	/**
	 * Update the user interface.
	 */
	private void updateButtons() {
		for (int i=0; i<divisionButtons.size(); i++)
			divisionButtons.get(i).setDown(i == flagInfo.divIdx);
		for (int i=0; i<color1Buttons.size(); i++)
			color1Buttons.get(i).setDown(i == flagInfo.col1);
		for (int i=0; i<color2Buttons.size(); i++)
			color2Buttons.get(i).setDown(i == flagInfo.col2);
		for (int i=0; i<color3Buttons.size(); i++)
			color3Buttons.get(i).setDown(i == flagInfo.col3);
		for (int i=0; i<overlayButtons.size(); i++)
			overlayButtons.get(i).setDown(i == flagInfo.ovlIdx);
		for (int i=0; i<color4Buttons.size(); i++)
			color4Buttons.get(i).setDown(i == flagInfo.col4);
		for (int i=0; i<symbolButtons.size(); i++)
			symbolButtons.get(i).setDown(i == flagInfo.symIdx);
		for (int i=0; i<color5Buttons.size(); i++)
			color5Buttons.get(i).setDown(i == flagInfo.col5);
	}

	/**
	 * Send the request for the flag given by flagInfo to the server and wait for a response.
	 */
	public void requestFlag(final boolean createHistoryItem) {
		sendButton.setEnabled(false);
		serverResponseLabel.setText("");
		flagService.getFlagData( flagInfo,
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
					//updateButtons();
       				flagWidget.setHTML(flagData.svgString);
/*       				
       				flagLink.setHTML("<a href=\"/gwtflags/SvgFileService?"
       						 + flagData.flagInfo.getQueryString()
       						 + "\">Download as [.svg]</a>"
       						);
*/       						
       				//System.out.println("sucess.");
    				sendButton.setEnabled(true);
    				sendButton.setFocus(true);
    				if (createHistoryItem)
    					History.newItem(flagInfo.getQueryString());
       			}
			});
	}

	/**
	 * Send the request a random flag to the server and wait for a response.
	 */
	public void requestRandomFlag() {
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
					updateButtons();
       				flagWidget.setHTML(flagData.svgString);
/*       				
       				flagLink.setHTML("<a href=\"/gwtflags/SvgFileService?"
       						 + flagData.flagInfo.getQueryString()
       						 + "\">Download as [.svg]</a>"
       						);
*/       						
       				//System.out.println("sucess.");
    				sendButton.setEnabled(true);
    				sendButton.setFocus(true);
					History.newItem(flagInfo.getQueryString());
       			}
			});
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

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
			"images/sym7.png",
			"images/sym8.png",
			"images/sym9.png"
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
		
		// We can add style names to widgets
		//sendButton.addStyleName("sendButton");
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("flagContainer").add(flagWidget);
//		RootPanel.get("flagLink").add(flagLink);
		RootPanel.get("flagLink").add(downloadButton);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		RootPanel.get("leftPanel").add(verticalPanel);
		verticalPanel.setSize("100px", "100px");

		// Set up history management
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				
				// Parse the history token
				String[] params = historyToken.split("&");  
				Map<String, String> map = new HashMap<String, String>();  
				for (String param : params) {  
					String name = param.split("=")[0];  
					String value = param.split("=")[1];  
					map.put(name, value);
				}
				flagInfo.divIdx = Integer.parseInt(map.get("d"));
				flagInfo.col1 = Integer.parseInt(map.get("c1"));
				flagInfo.col2 = Integer.parseInt(map.get("c2"));
				flagInfo.col3 = Integer.parseInt(map.get("c3"));
				flagInfo.ovlIdx = Integer.parseInt(map.get("o"));
				flagInfo.col4 = Integer.parseInt(map.get("c4"));
				flagInfo.symIdx = Integer.parseInt(map.get("s"));
				flagInfo.col5 = Integer.parseInt(map.get("c5"));
				requestFlag(false);
			}
		});

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
				// Reload the flag.
				requestFlag(true);
				
				Iterator<ToggleButton> iterator = group.iterator();
				while (iterator.hasNext()) {
					iterator.next().setDown(false);
				}
				button.setDown(true);
			}
		}

		verticalPanel.add(new HTML("<div class=\"tool-header\">Division</div>"));
		//--------------------------------------------------
		// Divisions
		{
			Grid grid = new Grid(2, 6);
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
		
		{
			Grid grid = new Grid(1, color_codes.length);
			verticalPanel.add(grid);
		
			for (int i=0; i<color_codes.length; i++) {
				ToggleButton button = new ToggleButton();
				button.setStyleName("color-button");
				button.getElement().getStyle().setProperty("backgroundColor", color_codes[i]);
				grid.setWidget(0, i, button);
				button.addClickHandler(
					new RadioGroupHandler(button, color1Buttons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.col1 = value;
							}
						}
					) 
				);
			}
		}

		{
			Grid grid = new Grid(1, color_codes.length);
			verticalPanel.add(grid);
		
			for (int i=0; i<color_codes.length; i++) {
				ToggleButton button = new ToggleButton();
				button.setStyleName("color-button");
				button.getElement().getStyle().setProperty("backgroundColor", color_codes[i]);
				grid.setWidget(0, i, button);
				button.addClickHandler(
					new RadioGroupHandler(button, color2Buttons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.col2 = value;
							}
						}
					) 
				);
			}
		}

		{
			Grid grid = new Grid(1, color_codes.length);
			verticalPanel.add(grid);
		
			for (int i=0; i<color_codes.length; i++) {
				ToggleButton button = new ToggleButton();
				button.setStyleName("color-button");
				button.getElement().getStyle().setProperty("backgroundColor", color_codes[i]);
				grid.setWidget(0, i, button);
				button.addClickHandler(
					new RadioGroupHandler(button, color3Buttons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.col3 = value;
							}
						}
					) 
				);
			}
		}

		verticalPanel.add(new HTML("<div class=\"tool-header\">Overlay</div>"));
		//--------------------------------------------------
		// Overlays
		{
			Grid grid = new Grid(2, 6);
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
		
		{
			Grid grid = new Grid(1, color_codes.length);
			verticalPanel.add(grid);
		
			for (int i=0; i<color_codes.length; i++) {
				ToggleButton button = new ToggleButton();
				button.setStyleName("color-button");
				button.getElement().getStyle().setProperty("backgroundColor", color_codes[i]);
				grid.setWidget(0, i, button);
				button.addClickHandler(
					new RadioGroupHandler(button, color4Buttons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.col4 = value;
							}
						}
					) 
				);
			}
		}

		verticalPanel.add(new HTML("<div class=\"tool-header\">Symbol</div>"));
		//--------------------------------------------------
		// Symbols
		{
			Grid grid = new Grid(2, 6);
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
		
		{
			Grid grid = new Grid(1, color_codes.length);
			verticalPanel.add(grid);
		
			for (int i=0; i<color_codes.length; i++) {
				ToggleButton button = new ToggleButton();
				button.setStyleName("color-button");
				button.getElement().getStyle().setProperty("backgroundColor", color_codes[i]);
				grid.setWidget(0, i, button);
				button.addClickHandler(
					new RadioGroupHandler(button, color5Buttons,
						new IntegerSetter() {
							public void setInteger(int value) {
								flagInfo.col5 = value;
							}
						}
					) 
				);
			}
		}

		// Create the popup dialog box
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
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

		// Create a handler for the sendButton
		class SendButtonHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				requestRandomFlag();
			}
		}
		
		// Create a handler for the downloadButton
		class DownloadButtonHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the downloadButton.
			 */
			public void onClick(ClickEvent event) {
				com.google.gwt.user.client.Window.open(
						"/gwtflags/SvgFileService?" + flagInfo.getQueryString(),
						"_blank", "");
			}
		}

		// Add the handlers
		SendButtonHandler sendButtonHandler = new SendButtonHandler();
		sendButton.addClickHandler(sendButtonHandler);
		DownloadButtonHandler downloadButtonHandler = new DownloadButtonHandler();
		downloadButton.addClickHandler(downloadButtonHandler);
		
		// Start with a random flag
		requestRandomFlag();
	}
}
