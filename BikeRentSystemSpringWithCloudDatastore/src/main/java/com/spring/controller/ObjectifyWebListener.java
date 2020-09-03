package com.spring.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.spring.pojo.User;

@WebListener
public class ObjectifyWebListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent event) {
    //ObjectifyService.init();
    //registering POJO entity classes.
   
	  
	  ObjectifyService.init(new ObjectifyFactory(
			    DatastoreOptions.newBuilder()
			        .setProjectId("bike-rent-system")
			        .build()
			        .getService()
			    ));
	  ObjectifyService.register(User.class);
    
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
  }
}