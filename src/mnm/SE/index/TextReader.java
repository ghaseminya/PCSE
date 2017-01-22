/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mnm.SE.index;

/**
 *
 * @author mnm
 */
/*
    This file defines the Class TextReader as a subclass of FilterReader..
    The TextReader provides methods for reading data expressed in human-readable
    ASCII text format.

    This file also defines three public subclasses of RuntimeException to represent
    errors that can occur during input.  These classes are static nested inside the
    TextReader class.

    This is a modest variation on an earlier class called AsciiInputStream, which served
    a similar function for Java 1.0, where InputStreams are used instead of Readers
    for character input.

    David Eck
    August 18, 1998

*/


import java.io.*;


public class TextReader extends FilterReader {

    // ************************** Exception Classes *******************************

		// First, define the exceptions that can be thrown by this class.  These exceptions
		// are subclasses of RuntimeException, so they do not require mandatory error-handling.
		// Also, if you prefer, you can turn off excpetions by calling the IOCheck() method.
		// In that case, when an exception occurs during processing by one of the methods
		// of the TextReader class, an errorFlag is set but no exception is thrown.
		// If you use this alternative error-handling strategy, then you should call the
		// checkError() method after each input operation to see whether the operation
		// completed successfully.

		public static class Error extends RuntimeException {
		      // Represents any excpetion that occurs in the TextReader Class.
		      // In fact, only general IOExceptions are translated directly into
		      // TextReader.Error.  Other exceptions throw objects belonging
		      // to one of the following subclasses of TextReader.Error.
		   Error(String errorMessage) {
		      super(errorMessage);
		   }
		}

		public static class FormatError extends Error {
		      // Illegal data: an illegal number or an illegal boolean value
		   FormatError(String errorMessage) {
		      super(errorMessage);
		   }
		}

		public static class EOFError extends Error {
		     // attempt to read past end of stream
		   EOFError(String errorMessage) {
		      super(errorMessage);
		   }
		}


   // ***************************** Constructors ********************************

   public TextReader(BufferedReader s) {
      super(s);
   }

   public TextReader(Reader s) {
      super(new BufferedReader(s));
   }

   public TextReader(InputStream s) {
      super( new BufferedReader(new InputStreamReader(s)) );
   }

   // ***************************** Error Checking *****************************

   public void IOCheck(boolean throwExceptions) {  // call IOCheck(false) to turn
      throwExceptionOnError = throwExceptions;     //   off exceptions, then check
   }                                               //   for errors by calling checkError()

   public boolean error() {   // returns true if the most recent input operation on
      return errorFlag;       // this TextReader produced an error.  An error
   }                          // message can be retrieved by calling getErrorMessage()

   public String getErrorMessage() {            // if the most recent operation on
      return errorFlag ? errorMessage : null;   // this TextReader produced an error, this
   }                                            // gets an error message for that error

   
   public char peek()          { errorFlag = false; return lookChar(); }

   public char getAnyChar()    { errorFlag = false; return readChar(); }

   public char getChar()       { errorFlag = false; skipWhiteSpace(); return readChar(); }
   public byte getByte()       { errorFlag = false; return (byte)readInteger(-128L,127L); }
   public short getShort()     { errorFlag = false; return (short)readInteger(-32768L,32767L); }
   public int getInt()         { errorFlag = false; return (int)readInteger((long)Integer.MIN_VALUE, (long)Integer.MAX_VALUE); }
   public long getLong()       { errorFlag = false; return readInteger(Long.MIN_VALUE, Long.MAX_VALUE); }
   public float getFloat()     { errorFlag = false; return readFloat(); }
   public double getDouble()   { errorFlag = false; return readDouble(); }
   public boolean getBoolean() { errorFlag = false; return readBoolean(); }
   public String getWord()     { errorFlag = false; return readWord(); }
   public String getAlpha()    { errorFlag = false; return readAlpha(); }

   public String getln()         { errorFlag = false; return readLine(); }

   public char getlnChar()       { char x=getChar();       dropLine();  return x; }
   public byte getlnByte()       { byte x=getByte();       dropLine();  return x; }
   public short getlnShort()     { short x=getShort();     dropLine();  return x; }
   public int getlnInt()         { int x=getInt();         dropLine();  return x; }
   public long getlnLong()       { long x=getLong();       dropLine();  return x; }
   public float getlnFloat()     { float x=getFloat();     dropLine();  return x; }
   public double getlnDouble()   { double x=getDouble();   dropLine();  return x; }
   public boolean getlnBoolean() { boolean x=getBoolean(); dropLine();  return x; }
   public String getlnWord()     { String x=getWord();     dropLine();  return x; }
   public String getlnAlpha()    { String x=getAlpha();    dropLine();  return x; }

   public boolean eoln() {
      char ch = lookChar();
      while (!EOF && !errorFlag && (ch == ' ' || ch == '\t')) {
         readChar();
         ch = lookChar();
      }
      return (ch == '\n' || EOF);
   }

   public boolean eof() {
      char ch = lookChar();
      while (!EOF && !errorFlag && (ch == ' ' || ch == '\n' || ch == '\t')) {
         readChar();
         ch = lookChar();
      }
      return EOF;
   }

   public void skipWhiteSpace() {
      char ch = lookChar();
      while (!errorFlag && (ch == ' ' || ch == '\n' || ch == '\t')) {
         readChar();
         ch = lookChar();
      }
   }

   public void skipNonLetters() {
      char ch = lookChar();
      while (!errorFlag && !EOF && !Character.isLetter(ch)) {
         readChar();
         ch = lookChar();
      }
   }

   public void close() {
      errorFlag = false;
      try {
         in.close();
      }
      catch (IOException e) {
         errorFlag = true;
         errorMessage = e.toString();
      }
   }

   private int lookAhead = -1;          // one-character buffer; -1 indicates the buffer is empty
   private boolean errorFlag = false;    // set to true if most recent operation produced an error
   private String errorMessage = "";     // error message for the most recent error
   private boolean EOF = false;          // has the end-of-stream been encountered?
   private boolean throwExceptionOnError = true;  // determines which type of error-handling is used

   // Three utility routines for throwing exceptions.

   private void doError(String message) {
      errorFlag = true;
      errorMessage = message;
      if (throwExceptionOnError)
         throw new Error(message);
   }

   private void doFormatError(String message) {
      errorFlag = true;
      errorMessage = message;
      if (throwExceptionOnError)
         throw new FormatError(message);
   }

   private void doEOFError(String message) {
      errorFlag = true;
      errorMessage = message;
      if (throwExceptionOnError)
         throw new FormatError(message);
   }

   // The remaining methods are basic methods for reading the various types of
   // data from the stream

   private char readChar() {
      char ch = lookChar();
      if (EOF)
         doEOFError("Attempt to read past end-of-data in input stream.");
      lookAhead = -1;
      return ch;
   }

   private boolean possibleLineFeedPending = false;  // for dealing with \r\n pairs

   private char lookChar() {
      if (lookAhead != -1) {
         if (lookAhead == '\r')
            return '\n';
         else
           return (char)lookAhead;
      }
      if (EOF)
         return '\0';
      try {
         int n = in.read();
         if (n == '\n' && possibleLineFeedPending) {  // ignore \n of \r\n pair
            n = in.read();
         }
         possibleLineFeedPending = (n == '\r');
         lookAhead = n;
         if (lookAhead == -1) {
            EOF = true;
            return '\0';
         }
      }
      catch (IOException e) {
         doError(e.getMessage());
      }
      if (lookAhead == '\r')  // represent all eoln's with \n
         lookAhead = '\n';
      return (char)lookAhead;
   }

   private void dropLine() {
      while (!errorFlag) {
         if (lookChar() == '\0')
            return;
         if (readChar() == '\n')
            return;
      }
   }

   private String readLine() {
      StringBuffer s = new StringBuffer(100);
      char ch = readChar();
      while (!errorFlag && ch != '\n') {
         s.append(ch);
         ch = lookChar();
         if (ch == '\0')
            break;
         ch = readChar();
      }
      return s.toString();
   }

   private String readWord() {
      skipWhiteSpace();
      if (errorFlag)
         return null;
      StringBuffer s = new StringBuffer(50);
      char ch = lookChar();
      if (EOF) {
         doEOFError("Attempt to read past end-of-data");
         return null;
      }
      while (!errorFlag && !EOF && ch != '\n' && ch != ' ' && ch != '\t') {
         s.append(readChar());
         ch = lookChar();
      }
      return s.toString();
   }


   private String readAlpha() {
      skipNonLetters();
      if (errorFlag)
         return null;
      StringBuffer s = new StringBuffer(50);
      char ch = lookChar();
      if (EOF) {
         doEOFError("Attempt to read past end-of-data");
         return null;
      }
      while (!errorFlag && Character.isLetter(ch)) {
         s.append(readChar());
         ch = lookChar();
      }
      return s.toString();
   }


   public float readFloat() {
      double d = readDouble();
      if (errorFlag)
         return Float.NaN;
      if (Math.abs(d) > Float.MAX_VALUE)
         doFormatError("Input number outside of legal range for values of type float");
      return (float)d;
   }

   public double readDouble() {
      double x = Double.NaN;
      StringBuffer s=new StringBuffer(50);
      skipWhiteSpace();
      char ch = lookChar();
      if (ch == '-' || ch == '+') {
          s.append(readChar());
          skipWhiteSpace();
          ch = lookChar();
      }
      if ( (ch < '0' || ch > '9') && (ch != '.') ) {
         if (EOF)
            doEOFError("Expecting a floating-point number and found end-of-data");
         else
            doFormatError("Expecting a floating-point number and found \""  + ch + "\"" );
         return Double.NaN;
      }
      boolean digits = false;
      while (ch >= '0' && ch <= '9') {
          s.append(readChar());
          ch = lookChar();
          digits = true;
      }
      if (ch == '.') {
         s.append(readChar());
         ch = lookChar();
         while (ch >= '0' && ch <= '9') {
             s.append(readChar());
             ch = lookChar();
             digits = true;
         }
      }
      if (!digits) {
         doFormatError("No digits found in floating-point input.");
         return Double.NaN;
      }
      if (ch == 'E' || ch == 'e') {
         s.append(readChar());
         ch = lookChar();
         if (ch == '-' || ch == '+') {
             s.append(readChar());
             ch = lookChar();
         }
         if ( (ch < '0' || ch > '9') && (ch != '.') ) {
            if (EOF)
               doEOFError("Expecting exponent for a floating-point number and found end-of-data");
            else
               doFormatError("Expecting exponent for a floating-point number and found \""  + ch + "\"");
            return Double.NaN;
         }
         while (ch >= '0' && ch <= '9') {
             s.append(readChar());
             ch = lookChar();
         }
      }
      String str = s.toString();
      Double d;
      try {
         d = new Double(str);
         x = d.doubleValue();
      }
      catch (NumberFormatException e) {
         x = Double.NaN;
      }
      if (Double.isNaN(x) || Double.isInfinite(x)) {
         doFormatError("Illegal floating point number");
         return Double.NaN;
      }
      return x;
   }

   public boolean readBoolean() {
      boolean ans = false;
      String s = getWord();
      if (errorFlag)
         return false;
      if ( s.equalsIgnoreCase("true") || s.equalsIgnoreCase("t") ||
                 s.equalsIgnoreCase("yes")  || s.equalsIgnoreCase("y") ||
                 s.equals("1") ) {
           ans = true;
       }
       else if ( s.equalsIgnoreCase("false") || s.equalsIgnoreCase("f") ||
                 s.equalsIgnoreCase("no")  || s.equalsIgnoreCase("n") ||
                 s.equals("0") ) {
           ans = false;
       }
       else
          doFormatError("Illegal input for value of type boolean: \"" + s + "\"");
       return ans;
   }


   private long readInteger(long min, long max) {  // read long integer, limited to specified range
      skipWhiteSpace();
      if (errorFlag)
         return 0;
      char sign = '+';
      if (lookChar() == '-' || lookChar() == '+') {
         sign = getChar();
         skipWhiteSpace();
      }
      long n = 0;
      char ch = lookChar();
      if (ch < '0' || ch > '9') {
         if (EOF)
            doEOFError("Expecting an integer and found end-of-data");
         else
            doFormatError("Expecting an integer and found \""  + ch + "\"");
         return 0;
      }
      while (!errorFlag && ch >= '0' && ch <= '9') {
         readChar();
         n = 10*n + (int)ch - (int)'0';
         if (n < 0) {
            doFormatError("Integer value outside of legal range");
            return 0;
         }
         ch = lookChar();
      }
      if (sign == '-')
         n = -n;
      if (n < min || n > max) {
         doFormatError("Integer value outside of legal range");
         return 0;
      }
      return n;
   }

   // Override the read() methods from FilterReader so that they will work if a
   // TextReader is wrapped inside another file.  (This is necessary to take
   // lookAhead into account.)

   public int read() throws IOException {
      if (lookAhead == -1)
         return super.read();
      else {
         int x = lookAhead;
         lookAhead = -1;
         return x;
      }
   }

   public int read(char[] buffer, int offset, int count) throws IOException {
      if (lookAhead == -1 || count <= 0)
         return super.read(buffer,offset,count);
      else if (count == 1) {
         buffer[offset] = (char)lookAhead;
         lookAhead = -1;
         return 1;
      }
      else {
         buffer[offset] = (char)lookAhead;
         lookAhead = -1;
         int ct = super.read(buffer,offset+1,count-1);
         return ct + 1;
      }
   }

} // end of class TextReader

