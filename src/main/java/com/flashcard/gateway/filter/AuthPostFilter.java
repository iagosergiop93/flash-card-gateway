package com.flashcard.gateway.filter;

import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flashcard.gateway.entities.Principal;
import com.flashcard.gateway.security.PrincipalEncoder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AuthPostFilter extends ZuulFilter {
	
	private PrincipalEncoder principalEnc;
	
	@Autowired
	public AuthPostFilter(PrincipalEncoder pe) {
		this.principalEnc = pe;
	}
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		String uri = req.getRequestURI();
		
		return uri.matches(".*/login.*|.*/auth.*");
	}

	@Override
	public Object run() throws ZuulException {
		
		HttpServletResponse res = RequestContext.getCurrentContext().getResponse();
		InputStream is = RequestContext.getCurrentContext().getResponseDataStream();
		Principal principal = readPrincipalInputStream(is);
		if(principal != null) {
			String token = principalEnc.encodePrincipal(principal);
			res.addHeader("authorization", token);
		}
		
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
	public Principal readPrincipalInputStream(InputStream is) {
		Principal principal = null;
		
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			principal = (Principal) ois.readObject();
		} catch(Exception e) {
			System.out.println(e.getCause());
		}
		
		return principal;
	}

}
