package com.plasticMobile.cragslist.querySearch;

//import org.apache.logging.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@ComponentScan("com.plasticMobile.cragslist.services")
@RestController


@RequestMapping("/")
public class qController {
	

	 
	    public static final Logger logger = LoggerFactory.getLogger(QuerySearchApplication.class);
	 
	    @Autowired
	    restConsumer craigslistService; //Service which will do all data retrieval/manipulation work
	 
	    // ------------------- Retrieve Listings---------------------------------------------
	 
	    @RequestMapping(value = "/", method = RequestMethod.GET)
	    public ResponseEntity<String> homePage() {
	        String info = "The search parameter can be embedded in the URL. Please try the below URL for testing the program once it has been run \n http://localhost:8080/cragslist/listing/{searchParament}";
	        return new ResponseEntity<String>(info, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/cragslist", method = RequestMethod.GET)
	    public ResponseEntity<String> cPage() {
	        String info = "The search parameter can be embedded in the URL. Please try the below URL for testing the program once it has been run \n http://localhost:8080/cragslist/listing/{searchParament}";
	        return new ResponseEntity<String>(info, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/cragslist/listing/", method = RequestMethod.GET)
	    public ResponseEntity<String> listAllFinds() {
	        String searchListing = craigslistService.getListings("apartment");
	        if (searchListing.isEmpty()) {
	        	System.out.println("Nothing returned");
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	            // You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<String>(searchListing, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/cragslist/listing/{searchWord}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<String> listSearch(@PathVariable("searchWord") String s) {
	        String searchListing = craigslistService.getListings(s);
	        if (searchListing.isEmpty()) {
	        	System.out.println("Nothing returned");
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	            // You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<String>(searchListing, HttpStatus.OK);
	    }
	

}
