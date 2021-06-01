package parserutil.impl.json.parser;

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
 * <p>Implementation of a token descriptor that identifies an identifier (literal, quoted is handled in the {@link JSONTokenDescriptorStringLiteralImpl}).
 * <p>These serve as object name, field name and field value identifiers.  These identifiers can't contain whitespace.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONTokenDescriptorIdentifierImpl implements JSONTokenDescriptor
{
  /**
   * <p>Any unicode identifier start is valid.
   */
  @Override
  public boolean isTokenStartChar(char ch)
  {
    //Return.
    return Character.isUnicodeIdentifierPart(ch);
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
    return JSONTokenDesignation.ID_VAR;
  }
  /**
   * <p>The token continues as long as the characters are valid identifier chars.
   */
  @Override
  public boolean isTokenContentChar(char ch)
  {
    return (Character.isUnicodeIdentifierPart(ch));
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
