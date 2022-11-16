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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import parserutil.impl.textreplace.TextNormalValueImpl;
import parserutil.impl.textreplace.TextReplaceValue;
import parserutil.impl.textreplace.TextReplaceValueImpl;
import parserutil.main.GeneralParser;
import parserutil.main.GeneralParserException;
import parserutil.main.GeneralParserToken;
import parserutil.main.TokenLocation;

/**
 * <p>
 * The {@link TextReplaceParser} extends the functionality of the
 * {@link GeneralParser} for Unit Configuration Format files. This format very simple.  It allows markup-delimited strings
 * to be parsed into normal text and marked-up text.  The ~ char is used to start and finish a markup-delimited string.  If a ~ char
 * is required in normal text then it should be prefixed with the \ char.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class TextReplaceParser extends GeneralParser<TextReplaceTokenDescriptor>
{
  /**
   * <p>
   * 
   * @return
   */
  private static List<TextReplaceTokenDescriptor> getParserTokenList()
  {
    // Create all token descriptors used in JSON schema.
    List<TextReplaceTokenDescriptor> cp = new ArrayList<>();
    cp.add(new TokenDescriptorMarkupTextImpl());    
    cp.add(new TokenDescriptorNormalTextImpl());
    
    return cp;
  }
  
//  private final Reader content;
//  
//  private final Map<String, String> replacementMap;
//
  /**
   * <p>
   * Create an instance of a unit configuration file parser.
   */
  public TextReplaceParser()
  {
    // Create the general parser instance with the lexical elements of unit config
    // files.
    super(getParserTokenList(), new TextReplaceParseStateMachine<>());

//    //Content.
//    this.content = content;
//    
//    //Replacement map.
//    this.replacementMap = replacementMap;
    
    // Initialise the parser.
    init();
  }

  public String doReplacement(Reader content, Map<String, String> replacementMap) throws IOException, GeneralParserException
  {
    //Initialise underlying parser.
    init();
    
    //String builder.
    StringBuilder sb = new StringBuilder();
    
    //Get tokens.
    GeneralParserToken<TextReplaceTokenDescriptor> nextToken;
    while( (nextToken = getNextToken(content)) != null)
    {
      //Replacement?
      if(nextToken.descriptor.isReplaceMarkupToken())
      {
        sb.append(replacementMap.get(new TextReplaceValueImpl(nextToken.tokenValue).getValue().trim()));
        sb.append(" ");
      }
      else
      {
        sb.append(new TextNormalValueImpl(nextToken.tokenValue).getValue().trim());
        sb.append(" ");
      }
    }
    
    //Return string builder content.
    return sb.toString();
  }
//  public TextReplaceValue getNext() throws IOException, GeneralParserException
//  {
//    //Get next token.
//    GeneralParserToken<TextReplaceTokenDescriptor> nextToken = getNextToken(content);
//    
//    //Null token specifies end of stream.
//    if(nextToken == null) return null;
//    
//    return (nextToken.descriptor.isReplaceMarkupToken()) ? new TextReplaceValueImpl(nextToken.tokenValue) : new TextNormalValueImpl(nextToken.tokenValue);
//  }

  /**
   * <p>Handle condition where next token is not recognised as being a particular type.
   */
  @Override
  protected void handleNoProcessingToken(TokenLocation location) throws GeneralParserException
  {
    throw new GeneralParserException("No token element for location: ", location);
  }
}
