package com.flashcard.gateway.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashcard.gateway.entities.Principal;
import com.flashcard.gateway.security.PrincipalEncoder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class AuthPreFilter extends ZuulFilter {
	
	private Logger logger = LoggerFactory.getLogger(AuthPreFilter.class);
	private PrincipalEncoder principalEnc;
	
	public AuthPreFilter(PrincipalEncoder principalEncoder) {
		this.principalEnc = principalEncoder;
	}
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest req = context.getRequest();
        String token = req.getHeader("Authorization");
        if (token != null && !token.equals("")) {
        	try {
        		Principal principal = principalEnc.decodePrincipal(token);
        		ObjectMapper mapper = new ObjectMapper();
        		context.addZuulRequestHeader("principal", mapper.writeValueAsString(principal));
        	} catch (Exception e) {
    			logger.debug(e.getMessage());
    		}
        }
        
        return null;
	}

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
