package com.flashcard.gateway.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashcard.gateway.entities.Principal;
import com.flashcard.gateway.security.PrincipalEncoder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class AuthPostFilter extends ZuulFilter {
	
	private Logger logger = LoggerFactory.getLogger(AuthPostFilter.class);
	private PrincipalEncoder principalEnc;
	
	public AuthPostFilter(PrincipalEncoder pe) {
		this.principalEnc = pe;
	}
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		String uri = req.getRequestURI();
		
		logger.info("URI: " + uri);
		
		return uri.matches(".*/users/login.*|.*/users/auth.*|.*/users/user.*");
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext context = RequestContext.getCurrentContext();
	    ObjectMapper mapper = new ObjectMapper();
	    	
	    Principal principal = readPrincipalInputStream(context, mapper);
    	addToken(context, principal);
    	try {
    		context.setResponseBody(mapper.writeValueAsString(principal));
    	} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	    
		return null;
	}

	@Override
	public String filterType() {
		return POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return 10;
	}
	
	public Principal readPrincipalInputStream(RequestContext context, ObjectMapper mapper) {
		Principal principal = null;
		
	    try (final InputStream responseDataStream = context.getResponseDataStream()) {	
	    	principal = mapper.readValue(responseDataStream, Principal.class);
		} catch(Exception e) {
			System.out.println(e.getCause());
		}
		
		return principal;
	}
	
	public void addToken(RequestContext context, Principal principal) {
		String token = "";
    	logger.info("Principal value: " + principal.toString());
    	
    	if(principal != null) {
    		token = principalEnc.encodePrincipal(principal);
    		context.addZuulResponseHeader("authorization", token);
    	}
	}

}
