package io.warp10.script.ext.swift;

import java.util.Map;

import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class SWAUTH extends NamedWarpScriptFunction implements WarpScriptStackFunction {  
  public SWAUTH(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    
    Object top = stack.pop();
    
    if (!(top instanceof Map)) {
      throw new WarpScriptException(getName() + " expects a map of parameters on top of the stack.");
    }
    
    Map<Object,Object> params = (Map<Object,Object>) top;
    
    AccountConfig config = new AccountConfig();
    
    if (params.containsKey(SwiftWarpScriptExtension.PARAM_USERNAME)) {
      config.setUsername(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_USERNAME)));
    }
    
    if (params.containsKey(SwiftWarpScriptExtension.PARAM_PASSWORD)) {
      config.setPassword(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_PASSWORD)));
    }

    if (params.containsKey(SwiftWarpScriptExtension.PARAM_AUTHURL)) {
      config.setAuthUrl(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_AUTHURL)));
    }

    if (params.containsKey(SwiftWarpScriptExtension.PARAM_TENANTID)) {
      config.setTenantId(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_TENANTID)));
    }

    if (params.containsKey(SwiftWarpScriptExtension.PARAM_TENANTNAME)) {
      config.setTenantName(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_TENANTNAME)));
    }

    if (params.containsKey(SwiftWarpScriptExtension.PARAM_REGION)) {
      config.setPreferredRegion(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_REGION)));
    }

    if (params.containsKey(SwiftWarpScriptExtension.PARAM_DOMAIN)) {
      config.setDomain(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_DOMAIN)));
    }
    
    if (params.containsKey(SwiftWarpScriptExtension.PARAM_AUTHMETHOD)) {
      config.setAuthenticationMethod(AuthenticationMethod.valueOf(String.valueOf(params.get(SwiftWarpScriptExtension.PARAM_AUTHMETHOD))));
    }

    try {
      AccountFactory af = new AccountFactory(config);      
      Account account = af.createAccount();

      stack.push(account);      
    } catch (Exception e) {
      throw new WarpScriptException(getName() + " encountered an error while authenticating.", e);
    }    
    
    return stack;
  }
}
