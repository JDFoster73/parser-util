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
package parserutil.impl.javasrc.parser;

/**
 * <p>Implementation of a token descriptor that identifies a comment.
 * <p>Comments start with a '#' character and extend to the end of the line (\n' character).  They can occur anywhere as long as they don't split another token.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JavaSourceTDMultiCommentImpl implements JavaSourceTokenDescriptor
{
  /**
   * <p>Finished status - reset to false on {@link #isTokenContentChar(char)} and set on newline.
   */
  private STAGE stage;
  
  private enum STAGE
  {
    START,
    CONTENT,
    STAR,
    FINISH
  }
  
  /**
   * <p>Comments start with a '/*' and end with &#42;&#47;
   */
  @Override
  public boolean isTokenStartChar(char ch)
  {
    //Comment start?
    boolean ret = (ch == '/');
    //If this is a comment start, reset the finished flag.  Do nothing if not.
    if(ret) stage = STAGE.START;
    //Return.
    return ret;
  }
  
  /**
   * <p>The newline character is a content char, but the next character afterwards is not.  Set the finished
   * flag on the newline character.  The character afterwards, whatever it is, is not part of the comment.
   */
  @Override
  public boolean isTokenContentChar(char ch)
  {
    switch(stage)
    {
      case START:
        //Char MUST be the * following the / at the start of the comment.
        if(ch == '*')
        {
          //Move stage to CONTENT.
          stage = STAGE.CONTENT;
          return true;
        }
        break;
      case CONTENT:
        // Content can be literally anything (except for '*/' - these must be written as entities in the comment code.
        if(ch == '*')
        {
          //Move to stage STAR.  We either close the comment with a subsequent '/' or move back to CONTENT with anything else.
          stage = STAGE.STAR;
        }
        return true;
      case STAR:
        // If we have '/' the move to FINISHED.
        if(ch == '/')
        {
          stage = STAGE.FINISH;
        }
        return true;
      case FINISH:
        //The comment is finished at this point.
        return false;
    }
    
    //Still here - return false.
    return false;
  }

  /**
   * <p>Get the general token type.
   */
  @Override
  public JavaSourceTokenType getType()
  {
    return JavaSourceTokenType.COMMENT;
  }

  /**
   * <p>The designation (tells the parser receiver what to do with the token).
   */
  @Override
  public JavaSourceTokenDesignation getDesignation()
  {
    return JavaSourceTokenDesignation.COMMENT;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void init()
  {
    //Reset the finished flag.
    stage = STAGE.START;
  }
}
