/**
 * Copyright 2021 James David Foster jdfoster73@gmail.com
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package parserutil.impl.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * <p>Implementation of a JSON object.  This contains JSON fields which are name:value pairs.  Values can be simple values, arrays or objects.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONObjectImpl implements JSONValue
{
  /**
   * <p>List of JSON fields contained by this object.
   */
  private final List<JSONField> fieldList;
  
  /**
   * <p>Create empty JSON object.
   */
  public JSONObjectImpl()
  {
    fieldList = new ArrayList<>();
  }
  
  /**
   * <p>This {@link JSONValue} is an {@link JSONInstanceType#OBJECT}.
   */
  @Override
  public JSONInstanceType getType()
  {
    return JSONInstanceType.OBJECT;
  }
  
  @Override
  public String getValue()
  {
    return toString();
  }

  /**
   * <p>Return a deep copy of this object.
   * 
   * @return
   */
  @Override
  public JSONObjectImpl copy()
  {
    JSONObjectImpl copy = new JSONObjectImpl();
    //Deep copy of the fields.
    for(int i = 0; i < fieldList.size(); i++)
    {
      //Get next field.
      JSONField jsonField = fieldList.get(i);
      
      //Copy it.
      copy.addField(new JSONField(jsonField.getFieldName(), jsonField.getFieldValue().copy()));
    }
    
    //Return.
    return copy;
  }
  
  /**
   * <p>The array value which is a JSON-formatted string of the object contents bounded by the '{' ... '}' characters.
   */
  public void addField(JSONField jsonObjectImpl)
  {
    //
    fieldList.add(jsonObjectImpl);
  }
  
  /**
   * <p>JSON-formatted string of the object contents.
   */
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("{ ");
    for(int i = 0; i < fieldList.size(); i++)
    {
      if(i > 0) sb.append(" , ");
      sb.append(fieldList.get(i));
    }
    sb.append(" }");
    return sb.toString();
  }

  /**
   * <p>Iterate each field in this object instance.
   * 
   * @param object
   */
  public void forEachField(Consumer<JSONField> object)
  {
    fieldList.forEach(object);
  }

  /**
   * <p>Get the requested field by name.  This throws a runtime exception if the field doesn't exist.
   * 
   * @param string
   * @return
   */
  public JSONField getField(String string)
  {
    //Find the field.
    for(int i = 0; i < fieldList.size(); i++)
    {
      JSONField retFld;
      if( (retFld = fieldList.get(i)).getFieldName().equals(string)) return retFld;
    }
    
    throw new IllegalArgumentException(string);
  }
  
  /**
   * <p>Get the field for the given index.
   * 
   * @param ix
   * @return
   */
  public JSONField getField(int ix)
  {
    return fieldList.get(ix);
  }
  
  /**
   * <p>Look for the field; returning true if there is at least one match.
   * 
   * @param string
   * @return
   */
  public boolean containsField(String string)
  {
    //Find the field.
    for(int i = 0; i < fieldList.size(); i++)
    {
      JSONField retFld = fieldList.get(i);
      if( retFld.getFieldName().equals(string)) return true;
    }
    
    //Not found.
    return false;
  }
}
