package io.warp10.script.ext.swift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class SWLIST extends NamedWarpScriptFunction implements WarpScriptStackFunction {  
  public SWLIST(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    
    Object top = stack.pop();
    
    if (!(top instanceof Account)) {
      throw new WarpScriptException(getName() + " expects a swift authentication object on top of the stack.");
    }
    
    Account account = (Account) top;

    top = stack.pop();
    
    if (!(top instanceof String)) {
      throw new WarpScriptException(getName() + " expects a container name below the auth.");
    }

    String containerName = top.toString();
    
    try {
      Container container = account.getContainer(containerName);

      Collection<StoredObject> objects = container.list();
    
      List<Object> results = new ArrayList<Object>();
      
      for (StoredObject obj: objects) {
        // Full name
        results.add(obj.getName());
      }
      
      stack.push(results);
    } catch (Exception e) {
      throw new WarpScriptException(getName() + " error while accessing " + containerName, e);
    }
    
    return stack;
  }
}
