package com.spring.controller;

import javax.servlet.annotation.WebFilter;

import com.googlecode.objectify.ObjectifyFilter;

@WebFilter(urlPatterns = {"/*"})
public class ObjectifyWebFilter extends ObjectifyFilter {}