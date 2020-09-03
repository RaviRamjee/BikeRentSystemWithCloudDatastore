package com.spring.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.spring.pojo.User;
import com.spring.model.UserImpl;

@Controller
public class UserController {

	@RequestMapping(value = "/regUser", method = RequestMethod.POST)
	protected void regUser(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("phoneNo") String phoneNo,@RequestParam("address") String address)
			throws ServletException, IOException {

		Datastore datastore = DatastoreOptions.newBuilder().setProjectId("bike-rent-system").build().getService();

		UserImpl uImpl = new UserImpl();

		String afterHashing = "";
		afterHashing = uImpl.passwordHashing(password); // converting password into cryptographic form

		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setUserName(name);
		user.setUserEmail(email);
		user.setUserPassword(afterHashing);
		user.setUserPhoneNo(phoneNo);
		user.setUserAddress(address);
		
		ObjectifyInit oi = new ObjectifyInit(); // taking object for intialization and registering class for objectify
		oi.initReg(user);

	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	protected ModelAndView loginUser(@RequestParam("id") String id,@RequestParam("password") String password,HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		UserImpl uImpl = new UserImpl();

		String myHash = "";
		myHash = uImpl.passwordHashing(password); // converting password into cryptographic form for comparing

		// getting object of ModelAndView
		ModelAndView mv = new ModelAndView();
		
		/**
		 * initializing OnjectifyService registering User class calling run method
		 */
		ObjectifyService.init();
		ObjectifyService.register(User.class);
		ObjectifyService.run(new VoidWork() {
			public void vrun() {
				User getUserDetails = ofy().load().type(User.class).id(id).now();
				
				
				String afterHashing = "";
				afterHashing = uImpl.passwordHashing(password);

				// comparing email and password for login
				if (getUserDetails.getUserId().equals(id)
						&& getUserDetails.getUserPassword().equals(afterHashing)) {

					mv.setViewName("WelcomeHomepage"); // setting view name
					mv.addObject("userName", getUserDetails.getUserName());
					
					HttpSession session=request.getSession();
					session.setAttribute("email",getUserDetails.getUserEmail());

				} else {
					mv.setViewName("Login");
				}
			}
		});
		return mv;
	}

}
