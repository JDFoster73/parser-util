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
package parserutil.impl.csv;

import parserutil.impl.csv.parser.CSVTokenType;

/**
 * <p>'Normal' text value - one that is not wrapped in ~ chars.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class CSVValueImpl implements CSVValue
{
  
  private String val;

  /**
   * <p>Construct the value implementation.
   * 
   * @param generalParserToken
   */
  public CSVValueImpl(String val)
  {
    val = val.replaceAll("\\\"\\\"", "\"");
    if(val.startsWith("\""))
    {
      this.val = val.substring(1, val.length() - 1);
    }
    else
    {
      this.val = val;
    }
  }

  @Override
  public String getValue()
  {
    return val;
  }

  @Override
  public CSVTokenType getType()
  {
    return CSVTokenType.VALUE;
  }

  @Override
  public String toString()
  {
    return "VALUE: " + val;
  }
  
  
}
