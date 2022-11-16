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

/**
 * <p>
 * A JSON field is a constituent of a JSON object. This represents a single
 * name:value pair entry in a JSON object.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONField
{
  /**
   * <p>
   * The field name of the name:value pair.
   */
  private final String fieldName;
  
  /**
   * <p>
   * The field value of the name:value pair.
   */
  private final JSONValueHolder val;
  
  /**
   * <p>
   * Create the JSON field.
   * 
   * @param fieldName
   * @param val
   */
  public JSONField(String fieldName, JSONValueHolder val)
  {
    // Make sure is trimmed.
    // this.fieldName = fieldName.trim();
    // Check that the field name is quoted. If partially quoted then illegal
    // argument. If fully unquoted then quote.
    boolean startQ = fieldName.startsWith("\"");
    boolean endQ = fieldName.endsWith("\"");
    if (startQ ^ endQ)
      throw new IllegalArgumentException();
    if (!startQ)
      this.fieldName = "\"" + fieldName + "\"";
    else
      this.fieldName = fieldName;
    this.val = val;
  }
  
  /**
   * <p>
   * Get the field name.
   * 
   * @return
   */
  public String getFieldName()
  {
    return fieldName.trim().substring(1, fieldName.length() - 1);// fieldName;
  }
  
  /**
   * <p>Get JSON value.  The underlying {@link JSONValue} contained MUST return
   * {@link JSONInstanceType#VALUE} when its {@link JSONValue#getType()} method is called.
   *  
   * @return
   */
  public JSONValueImpl getValueType()
  {
    return val.getValueType();
  }

  /**
   * <p>Get JSON object.  The underlying {@link JSONValue} contained MUST return
   * {@link JSONInstanceType#OBJECT} when its {@link JSONValue#getType()} method is called.
   *  
   * @return
   */
  public JSONObjectImpl getObjectType()
  {
    return val.getObjectType();
  }

  /**
   * <p>Get JSON array.  The underlying {@link JSONValue} contained MUST return
   * {@link JSONInstanceType#ARRAY} when its {@link JSONValue#getType()} method is called.
   *  
   * @return
   */
  public JSONArrayImpl getArrayType()
  {
    return val.getArrayType();
  }

  /**
   * <p>Get the JSON field type of the value - object, array or val.
   * 
   * @return the field's JSON type.
   */
  public JSONInstanceType getFieldType()
  {
    return val.getType();
  }
  
  /**
   * <p>Perform a deep copy of the object.
   */
  public JSONValueHolder copy()
  {
    return new JSONValueHolder(val.copy());
  }

//  /**
//   * <p>
//   * Get the field value.
//   * 
//   * @return
//   */
//  public JSONValueHolder getFieldValue()
//  {
//    return val;
//  }
  
  /**
   * <p>
   * Return a JSON-formatted string of the name:value of the field.
   */
  public String toString()
  {
    return fieldName + ":" + val;
  }
}
