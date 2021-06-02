# parser-util

This Java library is for very simple parsers.  It contains a functional JSON parser as an example.  The target audience is assumed to be programmers who need very simple and lightweight parsing in a small-footprint library.  There are no metadata required; parsers are implemented by providing a list of TokenDescriptor instances and a solid implementation of GeneralParserStateMachine to a GeneralParser instance.  The TokenDescriptors resolve the input text into tokens, and the GeneralParserStateMachine implementation checks that the input tokens conform to a set of rules.

The JSON implementation is a little bit naughty in that it includes comments.  Comments are prefixed with '#' and continue until a newline character.  Using comments for preprocessing is very much a bad idea but I really like to have comments in my configuration files.

If you need a full-on language parser then you'd probably be better off with something a little more heavyweight.
