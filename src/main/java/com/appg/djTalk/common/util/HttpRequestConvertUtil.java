package com.appg.djTalk.common.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpRequestConvertUtil extends HttpServletRequestWrapper
{
    HashMap params;
    
    /**
     * @param request
     */
    public HttpRequestConvertUtil(HttpServletRequest request) {
        super(request);
        this.params = new HashMap(request.getParameterMap());
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
     */
    public String getParameter(String name) {
        String returnValue = null;
        String[] paramArray = getParameterValues(name);
        if (paramArray != null && paramArray.length > 0){
          returnValue = paramArray[0];   
        }
        return returnValue;
    }
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterMap()
     */
    public Map getParameterMap() {
        return Collections.unmodifiableMap(params);
    }
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterNames()
     */
    public Enumeration getParameterNames() {
        return Collections.enumeration(params.keySet());        
    }
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String name) {
    	String[] result = null;
    	String[] temp = (String[])params.get(name);
    	if (temp != null){
    		result = new String[temp.length];
    		System.arraycopy(temp, 0, result, 0, temp.length);    		
    	}
        return result;
    }
       
    
    /**
     * Sets the a single value for the parameter.  Overwrites any current values.
     * @param name Name of the parameter to set
     * @param value Value of the parameter.
     */
    public void setParameter(String name, String value){
      String[] oneParam = {value};
      setParameter(name, oneParam);
    }
    
    /**
     * Sets multiple values for a parameter.
     * Overwrites any current values.
     * @param name Name of the parameter to set
     * @param values String[] of values.
     */
    public void setParameter(String name, String[] values){
      params.put(name, values);   
    }
}
