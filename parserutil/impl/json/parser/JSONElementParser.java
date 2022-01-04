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

import java.io.IOException;
import java.io.Reader;
import java.util.ResourceBundle;

import parserutil.main.GeneralParser;
import parserutil.main.GeneralParserException;
import parserutil.main.GeneralParserToken;
import parserutil.main.TokenLocation;

/**
 * <p>
 * The {@link JSONElementParser} extends the functionality of the
 * {@link GeneralParser} for Unit Configuration Format files. This format is
 * similar to JSON and provides a simple way of including field name:field value
 * entries.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JSONElementParser extends JSONAbstractParser
{
 
  /**
   * <p>
   * Create an instance of a unit configuration file parser.
   */
  public JSONElementParser()
  {
  }
  
  /**
   * <p>
   * Parse the given content.  Send elements to the token receiver specified.
   * 
   * @param content
   * @throws IOException
   * @throws GeneralParserException
   */
  public void parse(Reader content, JSONTokenReceiver receiver) throws IOException, GeneralParserException
  {
    try
    {
      //Initialise the parser.
      init();
      
      // Get the first non-comment token.
      GeneralParserToken<JSONTokenDescriptor> nextToken;
      
      // Check the next token. Null implies the end of stream.
      while( (nextToken = getNextJSONToken(content)) != null)
      {
        // Check status.
//        if (!"".equals(nextToken.machineStatus))
//          throw new GeneralParserException(nextToken.machineStatus, nextToken.getLocation());

        //Call the receiver.
        receiver.receiveJSONValue(nextToken);
      }
    }
    catch (NullPointerException npe)
    {
      throw new GeneralParserException(ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("general"), null, npe);
    }
  }  
}
