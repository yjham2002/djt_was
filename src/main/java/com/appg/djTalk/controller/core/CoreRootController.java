package com.appg.djTalk.controller.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appg.djTalk.controller.BaseController;

@Controller
@RequestMapping("/admin")
public class CoreRootController extends BaseController{

	private final static Log	logger	= LogFactory.getLog(CoreRootController.class);
	
}
