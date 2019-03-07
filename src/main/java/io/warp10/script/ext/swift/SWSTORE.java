package io.warp10.script.ext.swift;

import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;

import com.google.common.base.Charsets;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class SWSTORE extends NamedWarpScriptFunction implements WarpScriptStackFunction {  
  public SWSTORE(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    
    Object top = stack.pop();
        
    if (!(top instanceof String)) {
      throw new WarpScriptException(getName() + " expects an object name on top of the stack.");
    }

    String objectName = top.toString();
    
    top = stack.pop();
        
    if (!(top instanceof String)) {
      throw new WarpScriptException(getName() + " expects a container name below the object name.");
    }

    String containerName = top.toString();
    
    top = stack.pop();
    
    if (!(top instanceof Account)) {
      throw new WarpScriptException(getName() + " expects a swift authentication object below the container name.");
    }
    
    Account account = (Account) top;

    top = stack.pop();

    if (!(top instanceof byte[]) && !(top instanceof String)) {
      throw new WarpScriptException(getName() + " can only store byte arrays or strings.");
    }
    
    byte[] content;
    
    if (top instanceof byte[]) {
      content = (byte[]) top;
    } else {
      content = top.toString().getBytes(Charsets.UTF_8);
    }
    
    try {
      Container container = account.getContainer(containerName);

      StoredObject obj = container.getObject(objectName);
      
      obj.uploadObject(content);      
    } catch (Exception e) {
      throw new WarpScriptException(getName() + " error while accessing " + containerName + "/" + objectName, e);
    }
    
    return stack;
  }
}
