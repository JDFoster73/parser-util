package parserutil.impl.json;

import parserutil.impl.json.parser.JSONTokenDescriptor;
import parserutil.main.GeneralParserToken;

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
 * <p>This class defines the JSON value implementation.  Values are stored as strings, 
 * and contain an additional {@link VALTYPE} which describes the actual type of the
 * value.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONValueImpl implements JSONValue
{
  /**
   * <p>Value type.  Valid types are STR (string), NUM (number; either fixed or floating point) and BOOL (true|false).
   * 
   * @author James David Foster jdfoster73@gmail.com
   *
   */
  public enum VALTYPE
  {
    STR,
    NUM,
    BOOL
  }
  
  /**
   * <p>The string value.
   */
  private final String fieldValue;
  
  /**
   * <p>The type of value.
   */
  private final VALTYPE type;

  /**
   * <p>Construct the value implementation.
   * 
   * @param generalParserToken
   */
  public JSONValueImpl(GeneralParserToken<JSONTokenDescriptor> generalParserToken)
  {
    //Condition the string - make sure any quotes are stripped.
    this.fieldValue = generalParserToken.tokenValue;
    
    //Store the value type.
    switch(generalParserToken.descriptor.getDesignation())
    {
      case ID_BOOL:
        this.type = VALTYPE.BOOL;
        break;
      case ID_NUM:
        this.type = VALTYPE.NUM;
        break;
      case ID_STR:
        this.type = VALTYPE.STR;
        break;
        default:
          throw new IllegalArgumentException();
    }
  }

  /**
   * <p>Type of JSON object is {@link JSONInstanceType#VALUE}.
   */
  @Override
  public JSONInstanceType getType()
  {
    return JSONInstanceType.VALUE;
  }

  /**
   * <p>Return the value type (str, num, bool, ...).
   * 
   * @return
   */
  public VALTYPE getValueType()
  {
    return type;
  }
  
  /**
   * <p>The value is simply the unquoted string present in the input.
   */
  public String getValue()
  {
    //If STR then make sure this is given without quotes.
    if(type == VALTYPE.STR)
    {
      return fieldValue.substring(1, fieldValue.length() - 1);
    }

    //Just return the value otherwise.
    return fieldValue;
  }
  
  /**
   * <p>This returns the actual text as it is in the input.
   */
  public String toString()
  {
    return fieldValue;
  }

  /**
   * <p>Copy method.  Instances are fully immutable so we can simply return this instance. 
   */
  @Override
  public JSONValue copy()
  {
    return this;
  }
}
