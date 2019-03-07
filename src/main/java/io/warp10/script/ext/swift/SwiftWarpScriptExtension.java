package io.warp10.script.ext.swift;

import java.util.HashMap;
import java.util.Map;

import io.warp10.warp.sdk.WarpScriptExtension;

public class SwiftWarpScriptExtension extends WarpScriptExtension {
  
  private static Map<String,Object> functions = new HashMap<String,Object>();
    
  public static final String PARAM_USERNAME = "username";
  public static final String PARAM_PASSWORD = "password";
  public static final String PARAM_AUTHURL = "authurl";
  public static final String PARAM_TENANTID = "tenant.id";
  public static final String PARAM_TENANTNAME = "tenant.name";
  public static final String PARAM_REGION = "region";
  public static final String PARAM_AUTHMETHOD = "authmethod";
  public static final String PARAM_DOMAIN = "domain";
  
  static {
    functions.put("SWAUTH", new SWAUTH("SWAUTH"));
    functions.put("SWSTORE", new SWSTORE("SWSTORE"));
    functions.put("SWLIST", new SWLIST("SWLIST"));
    functions.put("SWLOAD", new SWLOAD("SWLOAD"));
  }
  
  @Override
  public Map<String, Object> getFunctions() {
    return functions;
  }
}
