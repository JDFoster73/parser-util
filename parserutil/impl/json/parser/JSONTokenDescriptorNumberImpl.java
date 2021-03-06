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
package parserutil.impl.json.parser;

/**
 * <p>Implementation of a token descriptor that identifies a JSON number identifier, num[.frac]['e'|'E'['-'|'+']num]
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONTokenDescriptorNumberImpl implements JSONTokenDescriptor
{
  /**
   * <p>Stage - <br>
   * 0 = start digit: '-' or number.
   * 1 = '.' separator in the case of the mantissa being a floating-point number.
   * 2 = 'e' or 'E' in the case that there is an exponent.  Check for '-', '+' or digit.
   * 3 = exponent digit(s).
   */
  private int stage = 0;
  
  /**
   * <p>Valid number token?
   */
  private boolean valid = false;
  /**
   * <p>Any unicode identifier start is valid.
   */
  @Override
  public boolean isTokenStartChar(char ch)
  {
    //Set the stage to 0.
    stage = 0;
    
    //Valid?
    valid = (Character.isDigit(ch) || ('-' == ch) ); 
    
    //Return.
    return valid;
  }

  /**
   * <p>These are identifiers.
   */
  @Override
  public JSONTokenType getType()
  {
    return JSONTokenType.IDENTIFIER;
  }

  /**
   * <p>The designation (tells the parser receiver what to do with the token).
   */
  @Override
  public JSONTokenDesignation getDesignation()
  {
    return (stage == 0) ? JSONTokenDesignation.ID_NUM_INT : JSONTokenDesignation.ID_NUM_REA;
  }
  /**
   * <p>The token continues as long as the characters are valid identifier chars.
   */
  @Override
  public boolean isTokenContentChar(char ch)
  {
    //
    switch(stage)
    {
      case 0:
        //Number is OK.
        if(Character.isDigit(ch)) return true;
        //Can end in '.' or 'e'|'E' only.
        if('.' == ch)
        {
          stage = 1;
          return true;
        }
        if( ('e' == ch) || ('E' == ch) )
        {
          stage = 2;
          return true;
        }
        //Nothing else is ok.
        return false;
      case 1:
        //Number is OK.
        if(Character.isDigit(ch)) return true;
        if( ('e' == ch) || ('E' == ch) )
        {
          stage = 2;
          return true;
        }
        //Nothing else is ok.
        return false;
      case 2:
        //Pos/neg exponent.
        if( ('+' == ch) || ('-' == ch) )
        {
          stage = 3;
          return true;
        }
        //Digit ok.
        if(Character.isDigit(ch))
        {
          stage = 3;
          return true;
        }
        //Nothing else is ok.
        return false;
      case 3:
        if(Character.isDigit(ch))
        {
          return true;
        }
        //Nothing else is ok.
        return false;
    }
    return false;
  }

  /**
   * Stringify.
   */
  @Override
  public String toString()
  {
    return getType().name();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void init()
  {
    //Set stage and validity flag to initial state.
    stage = 0;
    valid = false;
  }  
}
