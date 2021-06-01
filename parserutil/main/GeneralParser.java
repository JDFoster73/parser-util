package parserutil.main;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
 * <p>General parser.  This takes the rules given it in the constructor and parses a stream of characters.  Every new token
 * found on the stream will be dealt with by calling the owner-supplied token listener.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class GeneralParser<T extends TokenDescriptor>
{
  /**
   * <p>These token descriptors are set by the owning class.  They are the way that the general parser
   * decides the types of tokens and the boundaries between them.
   */
  private final List<T> configuredParserTokenList = new ArrayList<>();

  /**
   * <p>These token descriptors are candidates for the token currently being processed.  Tokens may share characters
   * at the start.  These descriptors are loaded as candidates on the initial character and whittled down until only
   * one possible token type remains.  We then know what the type of token is.
   */
  private final List<T> parserTokenProcessingList = new ArrayList<>();

  /**
   * <p>Builder for the current token.
   */
  private final StringBuilder currentTokenBuilder = new StringBuilder();
  
  /**
   * <p>The current character.
   */
  private char currentChar;

  /**
   * <p>The line number of the current input character.
   */
  private int line = 0;
  
  /**
   * <p>The column number of the current input character.
   */
  private int column = 0;
  
  /**
   * <p>The line number of the previous input character.
   */
  private int prevLine = 0;
  
  /**
   * <p>The column number of the previous input character.
   */
  private int prevColumn = 0;

  /**
   * <p>Current token start line.
   */
  private int startLine = 0;
  
  /**
   * <p>Current token start column.
   */
  private int startColumn = 0;
  
  /**
   * <p>The token validation machine.
   */
  private GeneralParserStateMachine<T> validationMachine;
  
  /**
   * <p>Create the general parser with the token types and listener provided.  The token types are used to render
   * flat text input into a series of tokens for processing.
   * 
   * @param tokenTypes
   * @param listener
   */
  public GeneralParser(List<T> tokenTypes)
  {
    for(T t : tokenTypes) configuredParserTokenList.add(t);
  }
  
  /**
   * <p>Parse each available token until the end of stream.
   * 
   * @param content
   * @throws IOException
   * @throws GeneralParserException
   */
  public void init(GeneralParserStateMachine<T> validationMachine)
  {
    //Set the line and column numbers.  Start on line 0 but column is -1.  We want it to be 0 when the first char is read.
    line = 0;
    column = -1;
    
    //Set the current char to 0.
    currentChar = 0;
    
    //Set validation machine.
    this.validationMachine = validationMachine;
  }

  /**
   * <p>Read characters from the stream until the next token has been found, built and dealt with (sent to the token listener).
   * 
   * @param content
   * @return
   * @throws IOException
   * @throws GeneralParserException
   */
  public GeneralParserToken<T> getNextToken(Reader content) throws IOException, GeneralParserException
  {
    //Check the end of stream hasn't already been reached.
    if(currentChar == 0xffff) return null;
    
    //Check the current char for whitespace.  We don't process WS between tokens.
    if(currentChar == 0 || Character.isWhitespace(currentChar))
    {
      //Move to first non-whitespace character.
      while(true)
      {
        //Read the next char.
        currentChar = readNext(content);
        
        //If end of stream then 
        if(currentChar == 0xFFFF) return null;

        //If not whitespace then break and continue processing;
        if(!Character.isWhitespace(currentChar)) break;
      }
    }
    
    //Initialise the current token type to null.
    T current = null;
        
    //First char - let's find the token.
    loadTokensForStartChar();
    
    //If there's only one option then set that as current.
    if(parserTokenProcessingList.size() == 1) current = parserTokenProcessingList.get(0);
    
    //Token type found flag - initially false.
    boolean found = false;
    
    //Load characters until we've whittled down the options to 1.
    while( (currentChar = readNext(content)) != 0xFFFF)
    {
      //Keep processing types in the candidate type list until type is found.
      if(!found)
      {
        int ix = 0;
        while(ix < parserTokenProcessingList.size())
        {
          //Remove any tokens that aren't applicable.
          if(!parserTokenProcessingList.get(ix).isTokenContentChar(currentChar)) 
          {
            parserTokenProcessingList.remove(ix);
          }
          else
          {
            ix++;
          }
        }

        //If we have zero left then we are dealing with a 1-char token.
        if(parserTokenProcessingList.size() == 0)
        {
          //Break out of the loop.  Current token set above.
          break;
        }
        //We need to be left with 1 token type.
        if(parserTokenProcessingList.size() == 1)
        {
          //Found the only option.
          found = true;
          //Set the current token.
          current = parserTokenProcessingList.get(0);
        }

        currentTokenBuilder.append(currentChar);
      }
      else
      {
        //We have the only type it can be.  Proceed to get the rest of the token.
        if(!current.isTokenContentChar(currentChar)) 
        {
          //Break out of the loop - finished.
          break;
        }
        else
        {
          currentTokenBuilder.append(currentChar);
        }
      }
    }
        
    //Got a token - check and return it.
    String stat = validationMachine.check(current);
    //Throw exception if state machine check failed.
    if(!"".equals(stat)) throw new GeneralParserException(stat, new TokenLocation(startLine, startColumn, prevLine, prevColumn));
    //OK - return the token.
    return new GeneralParserToken<>(current, currentTokenBuilder.toString(), startLine, startColumn, prevLine, prevColumn, "");
  }

  /**
   * <p>Load the possible tokens types for the given token start character.
   * @throws GeneralParserException 
   * 
   */
  private void loadTokensForStartChar() throws GeneralParserException
  {
    //Clear out the possible tokens list.
    parserTokenProcessingList.clear();
    
    configuredParserTokenList.forEach((v) -> {
      if(v.isTokenStartChar(currentChar)) parserTokenProcessingList.add(v);
    });
    
    //We need at least one candidate.
    if(parserTokenProcessingList.size() == 0) 
    {
      throw new GeneralParserException(ResourceBundle.getBundle("parser.strings").getString("0001"), new TokenLocation(line, column, line, column));
    }
    
    //Start the next token in the string builder.
    currentTokenBuilder.setLength(0);
    currentTokenBuilder.append(currentChar);
    
    //Set up the start line and column.
    startColumn = column;
    startLine = line;
  }
  
  /**
   * <P>Read next char.  Examine the char before returning it and update the line and column numbers accordingly.
   * 
   * @param content
   * @return
   * @throws IOException
   */
  private char readNext(Reader content) throws IOException
  {
    //Read the next character from the stream.
    char ret = (char) (content.read());
    
    //Set the previous.
    prevLine = line;
    prevColumn = column;
    
    //Test for newline.
    if(ret == '\n') 
    {
      line++;
      column = -1;
    }
    else
    {
      column++;
    }
    
    //Return the read character.
    return ret;
  }
}
