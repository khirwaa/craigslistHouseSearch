package com.plasticMobile.cragslist.querySearch;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class restConsumer {

	
	@Autowired
    private RestTemplate restTemplate;
	
	
	@Value("${craigslist.url}")
	private String url;
	
	// Gets the listing from Craigslist
	public String getListings(String sWord) {
		String searchUrl = url+sWord;
		String jData = xmlParser2JSON(restTemplate.getForObject(searchUrl, String.class));
		
		//restTemplate.getForObject(url, String.class);
		return jData;
	}
	
	// For Converting xml data to the Listing Object
	public ArrayList<ListingDataObject>  xml2Object(String s){
	
		ArrayList<ListingDataObject> listingData = new ArrayList<ListingDataObject>();
		 try {

			
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(s));
				Document doc = dBuilder.parse(is);
			
				doc.getDocumentElement().normalize();
				
				NodeList nList = doc.getElementsByTagName("item");
						
				System.out.println("----------------------------");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);
							
					//System.out.println("\nCurrent Element :" + nNode.getNodeName());
							
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						ListingDataObject lObject = new ListingDataObject();
						Element eElement = (Element) nNode;
						lObject.setListingTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
						lObject.setListingDate(eElement.getElementsByTagName("dc:date").item(0).getTextContent());
						//System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
						//System.out.println("Date : " + eElement.getElementsByTagName("dc:date").item(0).getTextContent());
						listingData.add(lObject);
					}
				
				}
				System.out.println("Size of Listing: " + listingData.size());
			    
		 	} catch (Exception e) {
				e.printStackTrace();
		 }
		 return listingData;
}
	// For Converting Listing Object to Json
	public String object2JSON(ArrayList<ListingDataObject> l){
		String jsonText="";
		
		try {
			for(ListingDataObject nObject:l){
				//ObjectMapper mapper = new ObjectMapper();
				//mapper.writeValue((System.out),nObject);
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(nObject);
				if(jsonText.isEmpty()){
					jsonText = jsonText + json ;
				}
				else{
					jsonText = jsonText + "," + json ;
				}
			//	jsonText = jsonText + json ;
			}
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(jsonText);
		return jsonText;
	}

	
	// Converts the xml data from Craigslist to JSON data
	public String xmlParser2JSON(String xmlData){
		String jsonData="";
		jsonData = object2JSON(xml2Object(xmlData));
		return "[" +jsonData+ "]";
	}
}
