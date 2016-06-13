package com.ibm.iotapp.data;

import java.util.ArrayList;
import java.util.List;

public class IOTApiResponse {

	private String bookmark;
	private List<IOTEvent> events;
	private long eventTimeFilter;
	
	public IOTApiResponse()
	{
		super();
		this.events = new ArrayList<>(1);
	}
	public IOTApiResponse(String bookmark)
	{
		super();
		this.bookmark = bookmark;
		this.events = new ArrayList<>(1);
	}
	/**
	 * Add a event to the response object
	 * @param event
	 */
	public void addEvent(IOTEvent event)
	{
		if(this.eventTimeFilter< event.getTimestamp().getTime())
		{
			this.events.add(event);
		}
		
	}
	/**
	 * @return the bookmark
	 */
	public String getBookmark() {
		return bookmark;
	}
	/**
	 * @param bookmark the bookmark to set
	 */
	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	/**
	 * @return the events
	 */
	public List<IOTEvent> getEvents() {
		return events;
	}
	/**
	 * @param events the events to set
	 */
	public void setEvents(List<IOTEvent> events) {
		this.events = events;
	}
	/**
	 * @return the eventFilter
	 */
	public long getEventTimeFilter() {
		return eventTimeFilter;
	}
	/**
	 * @param eventFilter the eventFilter to set
	 */
	public void setEventTimeFilter(long eventFilter) {
		this.eventTimeFilter = eventFilter;
	}
	
	
}
