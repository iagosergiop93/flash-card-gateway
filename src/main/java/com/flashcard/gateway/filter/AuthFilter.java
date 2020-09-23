package com.flashcard.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import com.flashcard.gateway.entities.Principal;
import com.flashcard.gateway.security.PrincipalEncoder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


public class AuthFilter extends ZuulFilter {
	
	private PrincipalEncoder principalEnc;
	
	public AuthFilter(PrincipalEncoder principalEncoder) {
		this.principalEnc = principalEncoder;
	}
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        String token = req.getHeader("Authorization");
        if (token != null && !token.equals("")) {
        	try {
        		Principal principal = principalEnc.decodePrincipal(token);
        		req.setAttribute("principal", principal);
        	} catch(Exception e) {
        		System.out.println("Failed to authenticate");
        	}
        }
        
        return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
