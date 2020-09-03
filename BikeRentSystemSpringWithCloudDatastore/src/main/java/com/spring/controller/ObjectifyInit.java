package com.spring.controller;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.spring.model.UserImpl;
import com.spring.pojo.User;

public class ObjectifyInit {
	
	public void initReg(User u)
	{
		ObjectifyService.init();
	    //registering POJO entity classes.
	    ObjectifyService.register(User.class);
		ObjectifyService.run(new VoidWork() {
		    public void vrun() {
		    	UserImpl ui=new UserImpl();
		    	ui.save(u);
		    }
		});
	}
	
	public void init1(String id)
	{
		ObjectifyService.init();
	    //registering POJO entity classes.
	    ObjectifyService.register(User.class);
		ObjectifyService.run(new VoidWork() {
		    public void vrun() {
		    	UserImpl ui=new UserImpl();
		    	ui.deleteUserdata(id);
		    }
		});
	}

}
