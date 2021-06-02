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
 * <p>Implementation of a token descriptor that identifies a boolean 'true' or 'false' value.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONTokenDescriptorBoolImpl implements JSONTokenDescriptor
{
  /**
   * <p>Stage - <br>
   * 0 = start (require 'f' or 't')<br>
   * 1 = start (require 'a' or 'r')<br>
   * 2 = start (require 'l' or 'u')<br>
   * 3 = start (require 's' or 'e')<br>
   * 4 = start (require 'e')<br>
   * 
   */
  private int stage = 0;
  
  /**
   * <p>Any unicode identifier start is valid.
   */
  @Override
  public boolean isTokenStartChar(char ch)
  {
    //Set the stage to 0.
    stage = 0;
    
    //Return.
    return ( ('t' == ch) || ('f' == ch));
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
    return JSONTokenDesignation.ID_BOOL;
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
        stage = 1;
        return (('a' == ch) || ('r' == ch));
      case 1:
        stage = 2;
        return (('l' == ch) || ('u' == ch));
      case 2:
        stage = 3;
        return (('s' == ch) || ('e' == ch));
      case 3:
        stage = 4;
        return (('e' == ch));
    }

    //Default - not a token content char.
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
}
