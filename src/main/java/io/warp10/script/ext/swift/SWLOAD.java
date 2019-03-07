package io.warp10.script.ext.swift;

import org.javaswift.joss.exception.CommandException;
import org.javaswift.joss.exception.CommandExceptionError;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class SWLOAD extends NamedWarpScriptFunction implements WarpScriptStackFunction {  
  public SWLOAD(String name) {
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
    
    try {
      Container container = account.getContainer(containerName);

      StoredObject obj = container.getObject(objectName);

      byte[] content = obj.downloadObject();

      stack.push(content);
    } catch (Exception e) {
      boolean rethrow = true;
      if (e instanceof CommandException) {
        CommandException ce = (CommandException) e;
        if (CommandExceptionError.ENTITY_DOES_NOT_EXIST.equals(ce.getError())) {
          stack.push(null);
          rethrow = false;
        }
      }
      if (rethrow) {
        throw new WarpScriptException(getName() + " error while accessing " + containerName + "/" + objectName, e);
      }
    }
    
    return stack;
  }
}
