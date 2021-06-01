package parserutil.impl.json;

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
 * 
 * <p>
 * <p>Holder for a JSON value.  The instance contained can be one of the valid JSON types.
 * As every JSON file may be different, it is up to the programmer to enforce the 
 * structure in the program.  If an object is the expected value of a JSON field,
 * for example, then {@link #getObjectType()} will return an instance that is capable
 * of interacting with the object fields. 
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONValueHolder
{
  /**
   * <p>The JSON value.
   */
  private JSONValue val;
  
  /**
   * <p>Construct the value holder.
   * 
   * @param val
   */
  public JSONValueHolder(JSONValue val)
  {
    this.val = val;
  }
  
  /**
   * <p>Get JSON value.  The underlying {@link JSONValue} contained MUST return
   * {@link JSONInstanceType#VALUE} when its {@link JSONValue#getType()} method is called.
   *  
   * @return
   */
  public JSONValueImpl getValueType()
  {
    return (JSONValueImpl) val;
  }

  /**
   * <p>Get JSON object.  The underlying {@link JSONValue} contained MUST return
   * {@link JSONInstanceType#OBJECT} when its {@link JSONValue#getType()} method is called.
   *  
   * @return
   */
  public JSONObjectImpl getObjectType()
  {
    return (JSONObjectImpl) val;
  }

  /**
   * <p>Get JSON array.  The underlying {@link JSONValue} contained MUST return
   * {@link JSONInstanceType#ARRAY} when its {@link JSONValue#getType()} method is called.
   *  
   * @return
   */
  public JSONArrayImpl getArrayType()
  {
    return (JSONArrayImpl) val;
  }
  
  /**
   * <p>Return the string representation of the object.
   */
  public String toString()
  {
    return val.toString();
  }

  /**
   * <p>Perform a deep copy of the object.
   */
  public JSONValueHolder copy()
  {
    return new JSONValueHolder(val.copy());
  }
}
