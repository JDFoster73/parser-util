# parser-util

This Java library is for very simple parsers.  It contains a functional JSON parser as an example.  The target audience is assumed to be programmers which need very simple and lightweight
parsing in a small-footprint library.  There are no metadata required; parsers are implemented by providing a list of TokenDescriptor instances to a GeneralParser
instance, and a solid implementation of GeneralParserStateMachine.  The TokenDescriptors resolve the input into tokens, and the GeneralParserStateMachine implementation
checks that the input conforms to a set of rules.

If you need a full-on language parser then you'd probably be better off with something a little more heavyweight.
