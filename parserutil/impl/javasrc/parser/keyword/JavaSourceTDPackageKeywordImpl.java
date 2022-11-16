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
package parserutil.impl.javasrc.parser.keyword;

import parserutil.impl.javasrc.parser.JavaSourceTDFixedBase;
import parserutil.impl.javasrc.parser.JavaSourceTokenDesignation;
import parserutil.impl.javasrc.parser.JavaSourceTokenType;

/**
 * <p>Implementation of a token descriptor that identifies a boolean 'true' or 'false' value.
 * 
 * @author James David Foster jdfoster73@gmail.com
 *
 */
public class JavaSourceTDPackageKeywordImpl extends JavaSourceTDFixedBase
{
 
  private static final char[] FIXWD = {'p', 'a', 'c', 'k', 'a', 'g', 'e'};
  
  public JavaSourceTDPackageKeywordImpl()
  {
    super(FIXWD);
  }

  /**
   * <p>These are identifiers.
   */
  @Override
  public JavaSourceTokenType getType()
  {
    return JavaSourceTokenType.IDENTIFIER;
  }

  /**
   * <p>The designation (tells the parser receiver what to do with the token).
   */
  @Override
  public JavaSourceTokenDesignation getDesignation()
  {
    return JavaSourceTokenDesignation.PACKAGE_SPEC;
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
