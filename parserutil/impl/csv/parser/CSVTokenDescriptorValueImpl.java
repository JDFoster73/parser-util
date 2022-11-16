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
package parserutil.impl.csv.parser;

import parserutil.impl.csv.CSVValue;
import parserutil.impl.csv.CSVValueImpl;

/**
 * <p>Implementation of a token descriptor that identifies a boolean 'true' or 'false' value.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class CSVTokenDescriptorValueImpl implements CSVTokenDescriptor
{
  /**
   * <p>Is the whole token quoted?  This will be the case if it contains quotes or commas.
   */
  private boolean isTokenQuoted;
  
  /**
   * <p>Any non-comma and non-newline char is the start of a csv field value.
   */
  @Override
  public boolean isTokenStartChar(char ch)
  {
    //Initialise - new token.
    init();
    
    //Set the token quoted status depending on whether the first char is a '"'
    isTokenQuoted = ('"' == ch);

    //Return.
    return ( !(',' == ch) && !('\n' == ch) && !('\r' == ch));
  }


  /**
   * <p>The token continues as long as the characters are valid identifier chars.
   */
  @Override
  public boolean isTokenContentChar(char ch)
  {
    //Is the char a ','?  It is content if we are still quoted.
    if( (',' == ch) )
    {
      return isTokenQuoted;
    }
    if( ('\n' == ch) ||  ('\r' == ch)) 
    {
      return isTokenQuoted;
    }
    
    //Handle quotes.
    if('"' == ch)
    {
      //If the last char was a quote then reset the was last quote status and set the token quoted status.
      isTokenQuoted = !isTokenQuoted;
    }
    
    //Default - is a token content char.
    return true;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init()
  {
    //Reset the quote active status.
    isTokenQuoted = false;
  }


  @Override
  public CSVTokenType getTokenType()
  {
    return CSVTokenType.VALUE;
  }


  @Override
  public CSVValue getValue(String val)
  {
    return new CSVValueImpl(val);
  }

  
}
