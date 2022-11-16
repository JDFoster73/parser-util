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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import parserutil.main.GeneralParser;
import parserutil.main.GeneralParserException;
import parserutil.main.GeneralParserToken;
import parserutil.main.TokenLocation;

/**
 * <p>
 * The {@link JSONAbstractParser} extends the functionality of the
 * {@link GeneralParser} for Unit Configuration Format files. This format is
 * similar to JSON and provides a simple way of including field name:field value
 * entries.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public abstract class JSONAbstractParser extends GeneralParser<JSONTokenDescriptor>
{

  /**
   * <p>Provide a list of JSON parser tokens for a given instance.  This should be thread safe as only local references
   * are made in this method.
   * <p>A separate list of token instances is required for each parser instance as the token instances can hold state of their own
   * and hence must not be shared between different instances of parser.
   * 
   * @return
   */
  private static List<JSONTokenDescriptor> getParserTokenList()
  {
    // Create all token descriptors used in JSON schema.
    List<JSONTokenDescriptor> cp = new ArrayList<>();
    cp.add(new JSONTokenDescriptorCommentImpl());
    cp.add(new JSONTokenDescriptorWhitespaceImpl());
    cp.add(new JSONTokenDescriptorNumberImpl());
    cp.add(new JSONTokenDescriptorBoolImpl());
    cp.add(new JSONTokenDescriptorNullImpl());
    cp.add(new JSONTokenDescriptorFieldSeparatorImpl());
    cp.add(new JSONTokenDescriptorEntrySeparatorImpl());
    cp.add(new JSONTokenDescriptorStringLiteralImpl());
    cp.add(new JSONTokenDescriptorStartObjectImpl());
    cp.add(new JSONTokenDescriptorEndObjectImpl());
    cp.add(new JSONTokenDescriptorStartArrayImpl());
    cp.add(new JSONTokenDescriptorEndArrayImpl());
    
    return cp;
  }
  
  /**
   * <p>
   * Create an instance of a unit configuration file parser.
   */
  public JSONAbstractParser()
  {
    
    // Create the general parser instance with the lexical elements of unit config
    // files.
    super(getParserTokenList(), new JSONParseStateMachine<>());
    // Initialise the parser.
    init();
  }
  
  /**
   * <p>
   * Get the next JSON token that isn't a comment. Return null if the end of input
   * has been reached.
   * 
   * @param content
   * @return
   * @throws IOException
   * @throws GeneralParserException
   */
  protected GeneralParserToken<JSONTokenDescriptor> getNextJSONToken(Reader content) throws IOException, GeneralParserException
  {
    // Token object to return.
    GeneralParserToken<JSONTokenDescriptor> nextToken = null;
    
    // Cycle through tokens until the first found that is not a comment.
    while ((nextToken = getNextToken(content)) != null)
    {
      // Ignore comments.
      // Ignore comments.
      // Check it's not a comment.
      if (nextToken.descriptor.getDesignation() != JSONTokenDesignation.COMMENT)
      {
        break;
      }
    }
    
    // Nothing found that isn't null to return. Return null.
    return nextToken;
  }  

  /**
   * <p>Handle condition where next token is not recognised as being a particular type.
   */
  @Override
  protected void handleNoProcessingToken(TokenLocation location) throws GeneralParserException
  {
    throw new GeneralParserException(ResourceBundle.getBundle("parserutil.impl.json.parser.strings").getString("notok"), location);
  }
}
