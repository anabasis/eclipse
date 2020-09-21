package com.slug.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Collection;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

/**
 * A class which takes a MIME multipart message in an InputStream, and returns
 * several InputStreams and offers their header values.
 * <p>
 * For details see <a
 * href="http://www.w3.org/Protocols/rfc1341/7_2_Multipart.html">RFC 1341</a>
 */
public class ExtMultipartMessage {
	private static String boundaryMarker = "boundary=";

	private PushbackInputStream messageStream = null;
	private String enc;
	private String boundaryString = "";
	byte[] boundaryStringBytes = null;
	byte[] boundaryTest = null;
	byte hyphen = (byte) '-';
	byte[] twoHyphenTest = new byte[2];
	boolean messagePartAvailable = false;
	private Hashtable<String, MessagePart> parts = new Hashtable<String, MessagePart>();
	private MessagePart currentStream = null;
	private boolean firstMessagePart = true;
	private String fileSeparator;

	/** Mainline left in for testing purposes. */
	public static void main(String[] args) {
		doTest(test);
		doTest(badTest);
	}

	private static void doTest(byte[] testBytes) {
		System.out.println("testing message\r\n" + new String(testBytes));
		try {
			ExtMultipartMessage m = new ExtMultipartMessage(new ByteArrayInputStream(
					testBytes), "outer");
			for (MessagePart p : m.getParts()) {
				System.out.println("Found a message part:");
				System.out.print(new String(p.getBytes()));
				System.out.println("End of message part");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construct a MultipartMessage from an InputStream. The resulting object
	 * then offers Hashtable of MessageParts containing the data of each
	 * seperate message part. Each MessagePart returns any headers found in each
	 * message part.
	 */
	public ExtMultipartMessage(InputStream messageStream, String boundaryString)
			throws IOException {
		fileSeparator = System.getProperty("file.separator");
		this.messageStream = new PushbackInputStream(messageStream, 512);
		this.boundaryString = "--" + boundaryString;
		boundaryStringBytes = this.boundaryString.getBytes();
		boundaryTest = new byte[this.boundaryString.length()];
		try {
			while ((currentStream = findNextStream()) != null)
				parts.put(currentStream.getName(), currentStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construct a MultipartMessage from an HttpServletRequest. The resulting
	 * object then offers Hashtable of MessageParts containing the data of each
	 * seperate message part. Each MessagePart returns any headers found in each
	 * message part.
	 */
	public ExtMultipartMessage(HttpServletRequest request) throws IOException {
		String requestEnc = request.getCharacterEncoding();
		if (requestEnc != null) {
			enc = requestEnc;
		} else {
			enc = "UTF-8";
		}
		fileSeparator = System.getProperty("file.separator");
		String contentType = request.getContentType();

		// Make sure the request is multipart/form-data
		if ((contentType == null)
				|| !contentType.startsWith("multipart/form-data")) {
			throw new IOException("Unexpected Content-Type: " + contentType);
		}
		this.messageStream = new PushbackInputStream(request.getInputStream(),
				512);

		// Get the boundary marker
		boundaryString = getBoundaryString(contentType);
		boundaryStringBytes = this.boundaryString.getBytes();
		boundaryTest = new byte[this.boundaryString.length()];
		try {
			while ((currentStream = findNextStream()) != null)
				parts.put(currentStream.getName(), currentStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Collection<MessagePart> getParts() {
		return parts.values();
	}

	/**
	 * Returns a list of Part names in the message. These correspond to
	 * parameter names.
	 * 
	 * @return A Strnig array of available Part names.
	 */
	public String[] getPartNames() {
		return (String[]) (parts.keySet().toArray(new String[parts.size()]));
	}

	public boolean hasPart(String name) {
		return parts.containsKey(name);
	}

	/**
	 * Returns a message Part from the multipart message that this object
	 * encapsulates.
	 * 
	 * @param name
	 *            The parameter name of the Part to return.
	 * @return The {@link Part} named in the parameter
	 */
	public MessagePart getPart(String name) {
		return (MessagePart) parts.get(name);
	}

	/**
	 * Returns the raw byte content of the named Part.
	 * 
	 * @param name
	 *            The parameter name of the Part to return.
	 * @return The data content of the Part.
	 */
	public byte[] getPartBytes(String name) {
		MessagePart p = (MessagePart) parts.get(name);
		if (p == null)
			return null;
		return p.getBytes();
	}

	/**
	 * Returns the content of the named Part decoded into a String according to
	 * the request's content encoding.
	 * 
	 * @param name
	 *            The parameter name of the Part to return.
	 * @return The data content of the Part.
	 */
	public String getPartString(String name) {
		MessagePart p = (MessagePart) parts.get(name);
		if (p == null)
			return null;
		try {
			return new String(p.getBytes(), enc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the boundary marker from the Content-Type header.
	 * 
	 * @param contentType
	 *            The value passed in the Content-Type header
	 * @return String containing the boundary marker.
	 */
	private String getBoundaryString(String contentType) {
		int boundaryIndex = contentType.indexOf(boundaryMarker)
				+ boundaryMarker.length();

		// the boundary in the Content-Type lacks a leading "--"
		return "--" + contentType.substring(boundaryIndex);
	}

	/**
	 * Returns true if there is another message in this multipart message. The
	 * flag <strong>messagePartAvailable</strong> is cleared when a terminating
	 * boundary is found, that is a boundary followed by 2 hyphens.
	 */
	public boolean hasMoreMessages() {
		return messagePartAvailable;
	}

	private MessagePart findNextStream() throws IOException {
		return new MessagePart(messageStream);
	}

	private String readLine(PushbackInputStream i) throws IOException {
		int length = 0;
		byte[] result = new byte[1024];
		for (int b = i.read(); !isLineTerminator(b); b = i.read()) {
			result[length] = (byte) b;
			length++;
		}

		/* Skip any second line terminator character */
		int b = i.read();
		if ((b != -1) && !isLineTerminator(b)) {
			i.unread(b);
		}
		return new String(result, 0, length);
	}

	private boolean isLineTerminator(int b) {
		return (b == 13) || (b == 10);
	}

	/**
	 * Class which implements a message part stream given the whole multipart
	 * stream.
	 */
	public class MessagePart {
		private Hashtable<String, String> headers = new Hashtable<String, String>();
		private String fileName = "";
		private String paramName = "";
		private ByteArrayOutputStream partBytes = new ByteArrayOutputStream();
		private PushbackInputStream in = null;

		public MessagePart(PushbackInputStream in) throws IOException {
			this.in = in;

			/*
			 * Skip to the first boundary only if it is the first time in.
			 * Subsequent times, we will already be positioned exactly at the
			 * start of the datastream after the boundary by the atBoundary()
			 * method which is called by the read() method.
			 */
			if (firstMessagePart) {
				if (!atBoundary()) {
					while (read() != -1)
						;
				}
				firstMessagePart = false;
			}
			if (in.available() == 0)
				throw new IOException("No more parts");

			String h = "";
			for (h = readLine(in); h.length() != 0; h = readLine(in)) {
				int c = h.indexOf(":");
				if (c != -1) {
					String k = h.substring(0, c).trim().toLowerCase();
					String v = h.substring(c + 1).trim();
					headers.put(k, v);
					if (k.equals("content-disposition")) {
						fileName = getDispositionFilename(v);
						paramName = getParamName(v);
					}
				}
			}

			// Collect the bytes in the current part
			for (int b = read(); b != -1; b = read())
				partBytes.write(b);
		}

		/** Return the name from this message part's content-disposition header. */
		public String getName() {
			return paramName;
		}

		/**
		 * Return the filename from this message part's content-disposition
		 * header.
		 */
		public String getFileName() {
			return fileName;
		}

		/** Return any of the headers found in this message part */
		public String getHeader(String k) {
			return (String) headers.get(k.toLowerCase());
		}

		/** Get the byte content of this message part. */
		public byte[] getBytes() {
			return partBytes.toByteArray();
		}

		/* This must signal EOF by returning -1 if we are at a boundary */
		private int read() throws java.io.IOException {
			int b1 = in.read();
			if (isLineTerminator(b1)) {
				int b2 = in.read();
				boolean needsPushback = true;
				if (!isLineTerminator(b2)) {
					((PushbackInputStream) in).unread(b2);
					needsPushback = false;
				}
				if (atBoundary()) {
					return -1;
				}
				if (needsPushback) {
					((PushbackInputStream) in).unread(b2);
				}
			}
			return b1;
		}

		/**
		 * Override default FilterInputStream behaviour which is to delegate
		 * this to the contained InputStream.
		 */
		private int read(byte b[], int off, int len) throws IOException {
			if (b == null) {
				throw new NullPointerException();
			} else if ((off < 0) || (off > b.length) || (len < 0)
					|| ((off + len) > b.length) || ((off + len) < 0)) {
				throw new IndexOutOfBoundsException();
			} else if (len == 0) {
				return 0;
			}

			int c = read();
			if (c == -1) {
				return -1;
			}
			b[off] = (byte) c;

			int i = 1;
			try {
				for (; i < len; i++) {
					c = read();
					if (c == -1) {
						break;
					}
					if (b != null) {
						b[off + i] = (byte) c;
					}
				}
			} catch (IOException ee) {
			}
			return i;
		}

		/**
		 * See if the next byte to be read is the beginning of the boundary
		 * string.
		 * <p>
		 * If it is, then it will be skipped so that the next byte read is the
		 * first byte after the boundary.
		 */
		private boolean atBoundary() throws java.io.IOException {
			for (int i = 0; i < boundaryStringBytes.length; i++) {
				boundaryTest[i] = (byte) in.read();
				if (boundaryTest[i] != boundaryStringBytes[i]) {
					in.unread(boundaryTest, 0, i + 1);
					return false;
				}
			}

			/*
			 * We are at a boundary if we get here. So see if we are at the
			 * final one terminated by two hyphens
			 */
			messagePartAvailable = false;
			for (int i = 0; i < 2; i++) {
				twoHyphenTest[i] = (byte) in.read();
				if (twoHyphenTest[i] != hyphen) {
					in.unread(twoHyphenTest, 0, i + 1);
					messagePartAvailable = true;
				}
			}

			/* A boundary has line terminators, so skip them */
			if (messagePartAvailable) {
				skipLineTerminators();
			}
			return true;
		}

		/** Skip line terminator characters */
		private void skipLineTerminators() {
			try {
				int b = 0;
				for (b = in.read(); isLineTerminator(b); b = in.read())
					;
				in.unread(b);
			} catch (Exception e) {
			}
		}

		/**
		 * Returns the filename from the Content-Disposition header.
		 * 
		 * @param line
		 *            String containing the Content-Disposition header
		 * @return String containing the filename if it exists
		 */
		private String getDispositionFilename(String line) {
			String search = "filename=";
			int filenameStart = line.toLowerCase().indexOf(search);
			int filenameEnd = 0;
			if (filenameStart == -1) {
			}

			filenameStart += search.length();
			char q = line.charAt(filenameStart);
			if ((q == '"') || (q == '\'')) {
				filenameStart++;
				filenameEnd = line.indexOf(q, filenameStart);
			} else {
				filenameEnd = line.length();
			}
			String filename = line.substring(filenameStart, filenameEnd);
			filename = filename
					.substring(filename.lastIndexOf(fileSeparator) + 1);
			return filename;
		}

		/**
		 * Returns the filename from the Content-Disposition header.
		 * 
		 * @param line
		 *            String containing the Content-Disposition header
		 * @return String containing the filename if it exists.
		 */
		private String getParamName(String line) {
			String search = "name=";

			int nameStart = line.toLowerCase().indexOf(search);
			int nameEnd = 0;
			if (nameStart == -1) {
				return "";
			}

			nameStart += search.length();
			char q = line.charAt(nameStart);
			if ((q == '"') || (q == '\'')) {
				nameStart++;
				nameEnd = line.indexOf(q, nameStart);
			} else {
				nameEnd = line.length();
			}
			return line.substring(nameStart, nameEnd);
		}
	}

	/**
	 * This is test input used by the main() method to test the class.
	 * <p>
	 * Note that the line-end characters are inconsistent. The class tolerates
	 * this in keeping with W3C recommendations about leniency.
	 */
	private static byte[] test = ("\r\n" + "--outer\r\n"
			+ "Content-Type: text/plain\r\n" + "Content-Disposition: inline\r"
			+ "Content-Description: text-part-1\n" + "\r\n"
			+ "Some text goes here\r\n" + "\r\n" + "--outer\r\n"
			+ "Content-Type: text/plain\n" + "Content-Disposition: inline\r"
			+ "Content-Description: text-part-2\r\n" + "\r\n"
			+ "Some more text here.\r\n" + "\r\n" + "--outer--").getBytes();

	private static byte[] badTest = ("\r\n").getBytes();
}
