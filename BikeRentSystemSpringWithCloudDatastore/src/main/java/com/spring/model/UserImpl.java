package com.spring.model;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.spring.pojo.User;

public class UserImpl {

	// saving user data
	public void save(User u) {
		
		ofy().save().entity(u).now();
	}

	// extracting user data
	public User getUserData(String id) {
		User getUserDetails=new User();
		    	getUserDetails = ofy().load().type(User.class).id(id).now();
		   
		return getUserDetails;
	}
	
	public void getUserByFilter(User u)
	{
		//User filter = ofy().load().type(User.class).filter("userId =",u.getUserId()).first().now();
		//System.out.println(filter);
	}

	// updating some data
	public void upadateUserData(User u) {
		
		ofy().save().entity(u).now();
	}

	// deleting user data
	public void deleteUserdata(String id) {
		User u = ofy().load().type(User.class).id(id).now();
		ofy().delete().entity(u).now();
	}
	
	
	public String passwordHashing(String password)
	{
		String myHash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
			System.out.println(myHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return myHash;
	}

}
