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
package parserutil.impl.textreplace.parser;

/**
 * <p>Implementation of a token descriptor that identifies an identifier (quoted).
 * <p>These serve as object name, field name and field value identifiers.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class TokenDescriptorMarkupTextImpl implements TextReplaceTokenDescriptor
{ 
  boolean end = false;
  
  boolean escape = false;
  
  /**
   * <p>Markup starts (and ends) with the tilde ~ character.
   */
  @Override
  public boolean isTokenStartChar(char ch)
  {
    //Make sure bools are false.
    end = escape = false;
    
    //Return.
    return ch == '~';
  }

  /**
   * <p>The newline character is, but the next character afterwards is not.
   */
  @Override
  public boolean isTokenContentChar(char ch)
  {
    //Escape state?
    if(escape)
    {
      escape = false;
      return true;
    }
    
    //Escape char?
    if(ch == '\\')
    {
      escape = true;
      return true;
    }
    
    //At end?
    if(end) return false;
    
    if(ch == '~') end = true;
    
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init()
  {
    end = false;
    escape = false;
  }

  @Override
  public boolean isReplaceMarkupToken()
  {
    return true;
  }  
 
}
