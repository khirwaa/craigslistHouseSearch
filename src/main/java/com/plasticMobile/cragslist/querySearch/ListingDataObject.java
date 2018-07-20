package com.plasticMobile.cragslist.querySearch;

public class ListingDataObject {

	
	private String title;
	private String date;
	
	
	public ListingDataObject(){
		
	}
	
	public String getListingTitle() {
		return title;
	}
	public void setListingTitle(String listingTitle) {
		this.title = listingTitle;
	}
	public String getListingDate() {
		return date;
	}
	public void setListingDate(String listingDate) {
		this.date = listingDate;
	}
}
