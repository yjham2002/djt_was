package com.appg.djTalk.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.appg.djTalk.common.constants.Constants;

public class WebUtil
{
	
	/**
	 * 자바스크립트 메세지 출력하기.
	 * @param script
	 * @return
	 */
	public static ModelAndView scriptMessageModelAndView(String script)
	{
		ModelAndView mav = new ModelAndView(Constants.JSSCRIPT_URL);
		mav.addObject("script", script);
		return mav;
	}
	/**
	 * 메세지 출력 후 페이지 이동하기.
	 * @param request
	 * @param fullURL
	 * @param refreshSeconds
	 * @param message
	 * @return
	 */
	public static ModelAndView redirectMessageModelAndView(HttpServletRequest request,String fullURL,float refreshSeconds,String message)
	{
		ModelAndView mav = new ModelAndView(Constants.REDIRECT_URL);
		mav.addObject("represhSecond", refreshSeconds);
		mav.addObject("message", message);
		mav.addObject("redirectUrl", fullURL);
		return mav;
	}	
	
	/**
	 * 성공 시 페이지 이동 필요할때 사용
	 * @param msg
	 * @param url
	 * @return
	 */
	public static String jsAlertNRedirect (String msg,String url)
	{	
		StringBuffer _t = new StringBuffer();
		
		_t.append("<script language=\"javascript\">");
		_t.append(" alert(\""+ msg + "\"); ");
		_t.append(" window.location.href='" + url + "'; ");
		_t.append("</script>");
		
		return _t.toString();
	}
	
	/**
	 * 실패시 return 시키기 위한 script
	 * @param msg
	 * @return
	 */
	public static String jsAlertNBack (String msg)
	{	
		StringBuffer _t = new StringBuffer();
		
		_t.append("<script language=\"javascript\">");
		_t.append(" alert(\""+ msg + "\"); ");
		_t.append(" window.history.back(-1); ");
		_t.append("</script>");
		
		return _t.toString();
	}
	
	// 16진수를 원래대로 복원
    public static String deHex(String input)
    {

        StringBuffer returnStr = new StringBuffer("");

        String str = null;
        for (int i = 0; i < input.length(); i = i + 2)
        {
            str = input.substring(i, i + 2);
            char c = (char) Integer.parseInt(str, 16);
            returnStr.append(c);
        }
        return returnStr.toString();
    }

    // 16진수로 변환
    public static String enHex(String input)
    {

        input = (input == null) ? "" : input;

        StringBuffer returnStr = new StringBuffer("");

        for (int i = 0; i < input.length(); i++)
        {
            int k = input.charAt(i);
            String str = Integer.toHexString(k);
            returnStr.append(str);
        }

        return returnStr.toString();
    }

    
}
