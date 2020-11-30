package com.lds.frmwk.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil extends File {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private FileSize fileSize = null;
	private static final long serialVersionUID = -6245186655660350846L;

	/**
	 * super?´?˜?Š¤?˜ ?˜•?ƒœë¡? ?ƒ?„±?ë¥? ? •?˜...
	 * 
	 * @param pathname
	 * @param create   ë¶?ëª? ?Œ¨?Š¤ë¥? ?ƒ?„±?• ì§? ?—¬ë¶?
	 */

	/**
	 * ?´ ?ƒ?„±?ë¡? ?ŒŒ?¼?„ ? •?˜ ?• ?•Œ ê²½ë¡œ?ƒ?˜ ?ƒ?œ„ ?˜¤ë¸Œì ?Š¸(?””? ‰?† ë¦?)ê°? ì¡´ì¬?•˜ì§? ?•Š?œ¼ë©? ??™?œ¼ë¡? ?ƒ?„±?•˜ê³? ?•´?‹¹ ?˜¤ë¸Œì ?Š¸ë¥? ?ƒ?„±?•œ?‹¤.
	 */
	public FileUtil(String pathname, boolean create) throws IOException {
		super(pathname);
		if (create)
			createParentPath();
	}

	public FileUtil(String parent, String child, boolean create) throws IOException {
		super(parent, child);
		if (create)
			createParentPath();
	}

	public FileUtil(File parent, String child, boolean create) throws IOException {
		super(parent, child);
		if (create)
			createParentPath();
	}

	public FileUtil(URI uri) {
		super(uri);
	}

	/**
	 * file ?ƒ?„±
	 * 
	 * @throws IOException
	 */

	public int makeFile(String file, String filePath) {
		int res = 0;
		try {

			BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
			String s = file;
			out.write(s);
			// out.newLine();
			out.close();
			res = 1;
		} catch (IOException e) {
			// System.err.println(e); // ?—?Ÿ¬ê°? ?ˆ?‹¤ë©? ë©”ì‹œì§? ì¶œë ¥
			// System.exit(1);
		}

		return res;

	}

	/**
	 * file ?ƒ?„±
	 * 
	 * @throws IOException
	 */

	public int makeLargeFile(String file, String filePath) throws Exception {

		int res = 0;
		InputStreamReader reader = null;
		InputStream is = null;
		int filedata;
		long start_time;
		BufferedWriter out = null;

		try {

			start_time = System.currentTimeMillis();
			//logger.debug("start_time : " + start_time);

			is = new ByteArrayInputStream(file.getBytes());
			reader = new InputStreamReader(is, "utf-8");
			out = new BufferedWriter(new FileWriter(filePath), 16384);
			do {
				filedata = reader.read();
				if (filedata != -1) {
					out.write(filedata);
				}
			} while (filedata != -1);

			res = 1;
			is.close();
			reader.close();
			out.close();
			//logger.debug("end_time : " + System.currentTimeMillis());
		} catch (IOException e) {
			e.printStackTrace();
			res = 0;
			is.close();
			reader.close();
			out.close();
		} finally {
			try {
				is.close();
				reader.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return res;

	}

	/**
	 * file ?ƒ?„±
	 * 
	 * @throws IOException
	 */

	public int makeLargeFile(StringBuffer file, String filePath) {
		int res = 0;
		InputStream is = null;
		BufferedWriter out = null;
		int filedata;
		long start_time;

		try {

			start_time = System.currentTimeMillis();
			//logger.debug("start_time : " + start_time);

			is = new ByteArrayInputStream(file.toString().getBytes());

			out = new BufferedWriter(new FileWriter(filePath), 16384);

			do {
				filedata = is.read();
				if (filedata != -1) {
					out.write(filedata);
				}
			} while (filedata != -1);

			is.close();
			out.close();
			res = 1;
			//logger.debug("end_time : " + System.currentTimeMillis());
		} catch (IOException e) {
			// System.err.println(e); // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Ö´Ù¸ï¿½ ï¿½Ş½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿?
			// System.exit(1);
			e.printStackTrace();
			try {
				is.close();
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return res;

	}

	/**
	 * ?´ fileê°ì²´?˜ ?ƒ?œ„ ?´?”?“¤?´ ì¡´ì¬ ?•˜ì§? ?•Š?„?•Œ ê·? ì¡´ì¬?•˜ì§? ?•Š?Š” ?ƒ?œ„ ?´?”?“¤?„ ?ƒ?„±?•´ì¤??‹¤.
	 * 
	 * @throws IOException
	 */
	public void createParentPath() throws IOException {
		ArrayList<File> p = new ArrayList<File>();
		File parent = getParentFile();
		COMPLETE: while (parent != null) {
			if (!parent.exists())
				p.add(parent);
			else
				break COMPLETE;
			parent = parent.getParentFile();
		}
		for (int i = p.size() - 1; i >= 0; i--)
			((File) p.get(i)).mkdir();
	}

	/**
	 * ?ŒŒ?¼?„ ?ƒ?„±?• ?•Œ ?ƒ?œ„ ?´?”ê°? ì¡´ì¬ ?•˜ì§? ?•Š?œ¼ë©? ì¡´ì¬?•˜ì§??•Š?Š” ?´?”?“¤?„ ?ƒ?„±?•˜ê³? ?ŒŒ?¼?„ ?ƒ?„±?•œ?‹¤.
	 */
	public boolean createNewCommonFileUtil() throws IOException {
		// ?ƒ?œ„ ?””? ‰?† ë¦¬ë“¤?„ ?ƒ?„±?•œ?‹¤...
		createParentPath();
		// ?ƒ?„±?•˜ê³ ì ?•˜?Š” ?ŒŒ?¼ ?ƒ?„±...
		return super.createNewFile();
	}

	/**
	 * file?„ ?¬?•¨?•˜ê³? ê·? ?„œë¸? ?””? ‰?† ë¦? ?ŒŒ?¼?“¤?„ ëª¨ë‘ ?‚­? œ ?•˜?Š” ?•¨?ˆ˜... ì§??š°ê³ ì ?•˜?Š” ?ŒŒ?¼?´?‚˜ ?””? ‰?† ë¦¬ê? file?¼?•Œ ?´ ?•¨?ˆ˜ë¥? ?˜¸ì¶œí•˜ë©?
	 * ?„œë¸? ?ŒŒ?¼ ?””? ‰?† ë¦¬ë?? ëª¨ë‘ ì§??›Œì¤??‹¤.
	 * 
	 * @param file
	 */
	public static boolean deleteWidthSubThings(File file) {
		boolean isDel = false;
		if (file.isDirectory()) {
			if (file.listFiles().length != 0) {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {

					// ?””? ‰?† ë¦¬ì´ê³? ?„œë¸? ?””? ‰?† ë¦¬ê? ?ˆ?„ ê²½ìš° ë¦¬ì»¤? ¼?„ ?•œ?‹¤...
					deleteWidthSubThings(fileList[i]);
					isDel = file.delete();

				}
			} else {

				// ?ŒŒ?¼ ?Š¸ë¦¬ì˜ ë¦¬í”„ê¹Œì? ?„?‹¬?–ˆ?„?•Œ ?‚­? œ...
				isDel = file.delete();
			}
		} else {

			// ?ŒŒ?¼ ?¼ ê²½ìš° ë¦¬ì»¤? ¼ ?—†?´ ?‚­? œ...
			isDel = file.delete();
		}

		return isDel;
	}

	/**
	 * ?˜¸ì¶œí•˜ë©? ?„œë¸? ?ŒŒ?¼ ?””? ‰?† ë¦¬ë?? ëª¨ë‘ ì§??›Œì¤??‹¤.
	 * 
	 * @param file
	 */
	public static boolean deleteWidthLastDir(File file) {
		boolean isDel = false;
		if (file.isDirectory()) {
			if (file.listFiles().length != 0) {
				File[] fileList = file.listFiles();
				// for (int i = 0; i < fileList.length; i++) {

				// ?””? ‰?† ë¦¬ì´ê³? ?„œë¸? ?””? ‰?† ë¦¬ê? ?ˆ?„ ê²½ìš° ë¦¬ì»¤? ¼?„ ?•œ?‹¤...
				deleteWidthSubThings(fileList[file.listFiles().length - 1]);
				isDel = file.delete();
				// }
			} else {

				// ?ŒŒ?¼ ?Š¸ë¦¬ì˜ ë¦¬í”„ê¹Œì? ?„?‹¬?–ˆ?„?•Œ ?‚­? œ...
				isDel = file.delete();
			}
		} else {

			// ?ŒŒ?¼ ?¼ ê²½ìš° ë¦¬ì»¤? ¼ ?—†?´ ?‚­? œ...
			isDel = file.delete();

		}
		return isDel;
	}

	public static boolean deleteAll(File dir) {
		if (!dir.exists()) {
			return true;
		}
		boolean res = true;
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				res &= deleteAll(files[i]);
			}
		} else {
			res = dir.delete();
		}
		return res;
	}

	/**
	 * ?ŒŒ?¼ ?‚¬?´ì¦? ?˜¤ë¸Œì ?Š¸?˜ ?¸?Š¤?„´?Š¤ë¥? ?ƒ?„±?•´?„œ ë¦¬í„´?•œ?‹¤.
	 * 
	 * @return
	 */
	public FileSize getFileSizeInstance() {
		if (fileSize == null) {
			fileSize = new FileSize();
		}
		return fileSize;
	}

	public class FileSize {
		/**
		 * ?ŒŒ?¼ ?‚¬?´ì¦ˆë?? ê³„ì‚° ?•˜ê¸? ?œ„?•´?„œ ë°”ì´?Š¸ ?‹¨?œ„ë¥? ?ƒ?ˆ˜?™” ?•´?‘”?‹¤.
		 */
		private static final long KBYTE = 1000L;

		private static final long MBYTE = KBYTE * 1000;

		private static final long GBYTE = MBYTE * 1000;

		private static final long TBYTE = GBYTE * 1000;

		private FileSize() {
		}

		/**
		 * getTByte, getGByte, getMByte, getKByte, getByte?Š” ? „ì²? ?‚¬?´ì¦ˆì—?„œ ?•´?‹¹?•˜?Š” ?‹¨?œ„?˜ ?‚¬?´ì¦ˆë§Œ?„
		 * ë¦¬í„´?•œ?‹¤.
		 * 
		 * @return
		 */
		public long getTByte() {
			return length() / TBYTE;
		}

		public long getGByte() {
			return (length() - (getTByte() * TBYTE)) / GBYTE;
		}

		public long getMByte() {
			return (length() - (getTByte() * TBYTE) - (getGByte() * GBYTE)) / MBYTE;
		}

		public long getKByte() {
			return (length() - (getTByte() * TBYTE) - (getGByte() * GBYTE) - (getMByte() * MBYTE)) / KBYTE;
		}

		public long getByte() {
			return length() - (getTByte() * TBYTE) - (getGByte() * GBYTE) - (getMByte() * MBYTE) - (getKByte() * KBYTE);
		}

		/**
		 * ?‚¬?´ì¦ˆë?? ?•´?‹¹ ?‹¨?œ„ë¡? ë³??™˜?•´?„œ float?˜•?œ¼ë¡? ?Œ? ¤ì¤??‹¤.
		 * 
		 * @return
		 */
		public float getTByteLength() {
			return length() / (float) TBYTE;
		}

		public float getGByteLength() {
			return length() / (float) GBYTE;
		}

		public float getMByteLength() {
			return length() / (float) MBYTE;
		}

		public float getKByteLength() {
			return length() / (float) KBYTE;
		}

		public float getByteLength() {
			return length();
		}

		private long length() {
			return size(FileUtil.this);
		}

		/**
		 * CommonFileUtilê°ì²´ê°? ?””? ‰?† ë¦? ?¼ ê²½ìš° ?„œë¸Œë””? ‰?† ë¦¬ì˜ ëª¨ë“  ?ŒŒ?¼?“¤?˜ ?‚¬?´ì¦ˆë?? ?”?•œ ê²°ê³¼ê°?ë¥? ?—°?‚°...
		 * 
		 * @param o
		 * @return
		 */
		private long size(File o) {
			long s = 0;
			if (o.isDirectory()) {
				File[] list = o.listFiles();
				if (list.length > 0) {
					for (int i = 0; i < list.length; i++) {
						s += size(list[i]);
					}
				}
			} else {
				s = o.length();
			}
			return s;
		}
	}

	public static void longLineSkip(String fileFullPath, String encoding, int start, int end) {
		String file = fileFullPath;
		StringBuffer sb = new StringBuffer();

		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rws");
			String oneLine = null;
			long lastFilePointer = 0;
			String eol = System.getProperty("line.separator");
			int eolLength = eol.getBytes().length;
			int count = 0;
			while (null != (oneLine = raf.readLine())) {
				count++;
				if (count >= start && count <= end) {

					if (oneLine.startsWith("CREATE")) {
						long position = raf.getFilePointer();
						raf.seek(lastFilePointer);
						raf.writeBytes(oneLine);
						raf.writeBytes(eol);
						lastFilePointer += oneLine.getBytes().length + eolLength;
						raf.seek(position);
					}

					sb.append(oneLine + " <br> ");
				}

			}
			raf.setLength(lastFilePointer);
			raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List getFileTextList(String fileFullPath, String encoding, int start, int end)
			throws IOException, IllegalArgumentException {
		List result = new ArrayList();
		FileReader freader = null;
		LineNumberReader lnreader = null;
		StringBuffer sb = new StringBuffer();
		String title = null;
		try {

			File file = new File(fileFullPath);
			if (!file.isFile() || !file.exists()) {
				throw new IllegalArgumentException("Source file '" + file.getAbsolutePath() + "' not found!");
			}

			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader, 16384);
			String line = "";
			String space = Integer.toString(end);
			int count = 0;
			for (int i = 0; i < space.length() - 1; i++) {
				sb.append(" ");
			}
			sb.append("NO" + lnreader.readLine() + "\n");
			// title = "NO"+lnreader.readLine();
			HashMap tHs = new HashMap();
			// tHs.put("title", title);
			// result.add(tHs);

			// if(start == 1){ start = 2; end++;} //first row is title

			while ((line = lnreader.readLine()) != null) {
				count++;

				if (count >= start && count <= end) {
					HashMap hs = new HashMap();
					hs.put("no", (lnreader.getLineNumber() - 1));
					hs.put("row_data", line);
					result.add(hs);
					sb.append((lnreader.getLineNumber() - 1) + " " + line + "\n");
				}
			}

			//logger.debug(sb.toString());

		} finally {
			freader.close();
			lnreader.close();
		}

		return result;
	}

	public List getFileTextOut(String fileFullPath, String encoding, int start, int end, String pagerHtml,
			String delimeter, List attrList, String delim, List epidGateList)
			throws IOException, IllegalArgumentException {
		List result = new ArrayList();
		FileReader freader = null;
		LineNumberReader lnreader = null;
		StringBuffer sb = new StringBuffer();
		String title = null;
		String EpioutName = null; // ?ŒŒ?¼?´ë¦?
		String epidGate = null; // snp epidgate ?—°ê²°ì˜µ?…˜
		String tableName = null; // ì£¼ì„ ?…Œ?´ë¸”ì´ë¦?

		String EpiType = null; // ':' êµ¬ë¶„?ë¡? ?¬ì§??…˜ êµ¬ë¶„
		int positionSnp = 0;

		if (epidGateList.size() > 0) {
			for (int k = 0; k < epidGateList.size(); k++) {
				HashMap hs = (HashMap) epidGateList.get(k);
				EpioutName = ((String) hs.get("epidout_name")).replaceAll("-PLINK", "").toLowerCase();
				if (fileFullPath.toLowerCase().endsWith(EpioutName)) {
					epidGate = (String) hs.get("epid_option");
					tableName = (String) hs.get("table_name");
				}
			}
		}

		if (epidGate != null && !epidGate.equals("")) {
			if (epidGate.startsWith("SNP")) {
				String[] epid = epidGate.split(":");
				if (epid.length > 1) {
					EpiType = epid[0];
					positionSnp = Integer.parseInt(epid[1]);
				}
			}
		}
		try {

			File file = new File(fileFullPath);
			if (!file.isFile() || !file.exists()) {
				throw new IllegalArgumentException("Source file '" + file.getAbsolutePath() + "' not found!");
			}

			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader, 16384);
			String line = "";
			String space = Integer.toString(end);
			String addSpace = "";
			int count = 0;
			for (int i = 0; i < space.length() - 1; i++) {
				if (i < 5) {
					addSpace += "&nbsp;";
				}
			}

			if (!delimeter.equals("ped")) {
				if (delim.equals("") || delim.equals("040")) {
					sb.append(addSpace + "NO" + addSpace + lnreader.readLine() + " <br> ");
				} else {
					sb.append(
							"<table id='outer' width='100%' border='0' cellpadding='0' cellspacing='1'  class='table01' >");
					sb.append("<tr>");
				}
			}

			String seperator = " ";
			if (delim.equals("010")) { // tab
				seperator = "\t";
			} else if (delim.equals("020")) { // space
				seperator = " ";
			} else if (delim.equals("030")) { // comma
				seperator = ",";
			}

			if (delim.equals("010") || delim.equals("020") || delim.equals("030")) { // space ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
				int column_cnt = 0;

				while ((line = lnreader.readLine()) != null) {
					if (lnreader.getLineNumber() == 1) {
						String[] lineT = line.split(seperator);

						sb.append("<td class='t01_center'>NO</td>");
						// logger.debug("lineTlength: "+lineT.length);

						for (int j = 0; j < lineT.length; j++) {
							if (lineT[j].length() > 0) {
								if (!lineT[j].equals("")) {
									sb.append("<td class='t01_center'>" + lineT[j] + "</td>");
									// logger.debug("title:"+lineT[j]);
									column_cnt++;
								}
							}
						}
						sb.append("</tr>");

					} else {

						count++;

						if (count >= start && count <= end) {

							if (line.getBytes().length > 4096) {
								String subStringLine = line.substring(0, 500);

							} else {

								if (lnreader.getLineNumber() > 1) {
									// logger.debug("column_cnt: "+column_cnt);

									sb.append("<tr>");

									String[] lineT = line.split(seperator);
									int contLineSplit = 0;
									sb.append("<td class='t01_w'>" + (lnreader.getLineNumber() - 1) + "</td>");
									// logger.debug("lineT.length : "+lineT.length);
									for (int k = 0; k < lineT.length; k++) {
										if (lineT[k].length() > 0) {
											// if(lineT[k].startsWith("SNP")){
											if (contLineSplit == positionSnp - 1) {
												// sb.append("<td class='t01_w' style='cursor:pointer;cursor:hand;'
												// onClick=epidGate('"+rescDao.rescGetEpidGateRsNum(lineT[k],tableName)+"');>"+lineT[k]+"</td>");
												// //epidgate connect
												sb.append(
														"<td class='t01_w'><a style='cursor:pointer;cursor:hand;' onClick=epidGate('"
																+ lineT[k] + "');>" + lineT[k] + "</a></td>"); // epidgate
																												// connect
											} else {
												sb.append("<td class='t01_w'>" + lineT[k] + "</td>");
											}
											contLineSplit++;
										}

									}
									// logger.debug("contLineSplit : "+contLineSplit);
									sb.append("</tr>");
								}
							}
						}
					}
				}

				sb.append("</table>");

			} else {
				while ((line = lnreader.readLine()) != null) {
					count++;
					if (count >= start && count <= end) {

						if (line.getBytes().length > 4096) {
							String subStringLine = line.substring(0, 500);
							if (count < 10) {
								sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (lnreader.getLineNumber())
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + subStringLine + " <br> ");
							} else if (count < 100) {
								sb.append("&nbsp;&nbsp;&nbsp;" + (lnreader.getLineNumber())
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + subStringLine + " <br> ");
							} else if (count < 1000) {
								sb.append("&nbsp;&nbsp;" + (lnreader.getLineNumber())
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + subStringLine + " <br> ");
							} else if (count < 10000) {
								sb.append("&nbsp;" + (lnreader.getLineNumber()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
										+ subStringLine + " <br> ");
							} else {
								sb.append((lnreader.getLineNumber()) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
										+ subStringLine + " <br> ");
							}
						} else {

							String[] lineT = line.split(seperator);
							int contLineSplit = 0;

							if (count < 10) {
								sb.append("&nbsp;&nbsp;&nbsp;&nbsp;" + (lnreader.getLineNumber() - 1)
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
							} else if (count < 100) {
								sb.append("&nbsp;&nbsp;&nbsp;" + (lnreader.getLineNumber() - 1)
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ");
							} else if (count < 1000) {
								sb.append("&nbsp;&nbsp;" + (lnreader.getLineNumber() - 1)
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							} else if (count < 10000) {
								sb.append(
										"&nbsp;" + (lnreader.getLineNumber() - 1) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ");
							} else {
								sb.append((lnreader.getLineNumber() - 1) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
							}

							for (int k = 0; k < lineT.length; k++) {
								if (lineT[k].length() > 0) {
									if (contLineSplit == positionSnp - 1) {
										// sb.append("&nbsp;&nbsp; <a style='cursor:pointer;cursor:hand;'
										// onClick=epidGate('"+rescDao.rescGetEpidGateRsNum(lineT[k],tableName)+"');>"+lineT[k]+"</a>
										// "); //epidgate connect
										sb.append(
												"&nbsp;&nbsp; <a style='cursor:pointer;cursor:hand;' onClick=epidGate('"
														+ lineT[k] + "');>" + lineT[k] + "</a> "); // epidgate connect
									} else {
										sb.append(" &nbsp;&nbsp;" + lineT[k] + "");
									}
									contLineSplit++;
								}
							}
							sb.append("<br>");

						}
					}
				}
			}

			HashMap hs = new HashMap();
			hs.put("row_data", sb.toString());
			hs.put("pagerHtml", pagerHtml);
			hs.put("delimeter", delim);
			hs.put("attr_list", attrList);

			result.add(hs);

		} finally {
			freader.close();
			lnreader.close();
		}
		return result;
	}

	public String getFileText(String fileFullPath, String encoding, int start, int end)
			throws IOException, IllegalArgumentException {

		FileReader freader = null;
		LineNumberReader lnreader = null;
		StringBuffer sb = new StringBuffer();
		try {

			File file = new File(fileFullPath);
			if (!file.isFile() || !file.exists()) {
				throw new IllegalArgumentException("Source file '" + file.getAbsolutePath() + "' not found!");
			}

			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader, 16384);
			String line = "";
			String space = Integer.toString(end);
			int count = 0;
			for (int i = 0; i < space.length() - 1; i++) {
				sb.append(" ");
			}

			if (start == 1) {
				start = 2;
				end++;
			} // first row is title

			sb.append("NO" + lnreader.readLine() + "\n");
			while ((line = lnreader.readLine()) != null) {
				count++;
				if (count >= start && count <= end) {
					sb.append((lnreader.getLineNumber() - 1) + " " + line + "\n");
				}
			}
			// logger.debug(sb.toString());

		} finally {
			freader.close();
			lnreader.close();
		}

		return sb.toString();
	}

	public String getFileTextlogger(String fileFullPath, String encoding) throws IOException, IllegalArgumentException {

		FileReader freader = null;
		LineNumberReader lnreader = null;
		StringBuffer sb = new StringBuffer();
		try {

			File file = new File(fileFullPath);
			if (!file.isFile() || !file.exists()) {
				throw new IllegalArgumentException("Source file '" + file.getAbsolutePath() + "' not found!");
			}

			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader, 16384);
			String line = "";
			int count = 0;
			sb.append(lnreader.readLine() + " ");
			while ((line = lnreader.readLine()) != null) {
				if (line.startsWith("**") || line.startsWith("ERROR")) {
					sb.append(line + " ");
				}
			}
		} finally {
			freader.close();
			lnreader.close();
		}

		return sb.toString();
	}

	public int getTotalLine(String fileFullPath) throws IOException, IllegalArgumentException {

		FileReader freader = null;
		LineNumberReader lnreader = null;
		int count = -1; // Å¸ï¿½ï¿½Æ² ï¿½ï¿½ï¿½ï¿½

		try {
			File file = new File(fileFullPath);
			if (!file.isFile() || !file.exists()) {
				throw new IllegalArgumentException("Source file '" + file.getAbsolutePath() + "' not found!");
			}

			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader, 16384);
			String line = "";

			while ((line = lnreader.readLine()) != null) {
				count++;
			}

			//logger.debug("  count :     " + count);

		} finally {
			freader.close();
			lnreader.close();
		}

		return count;
	}

	/**
	 * file ?“°ê¸?
	 * 
	 * @throws IOException
	 */
	public int writeFile(String file, BufferedWriter out) throws Exception {

		InputStream is = null;
		InputStreamReader isreader = null;
		int filedata;
		int res = 0;
		try {
			is = new ByteArrayInputStream(file.getBytes());
			isreader = new InputStreamReader(is, "utf-8");
			do {
				filedata = isreader.read();
				if (filedata != -1) {
					out.write(filedata);
				}
			} while (filedata != -1);
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				isreader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
