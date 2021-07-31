package animo.realcom.mahakubera.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.security.Principal;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import animo.realcom.mahakubera.modal.CustomUserDetails;

public class ApplicationUtil {

	public static String generateCode() {
		String password = "";
		SecureRandom random = new SecureRandom();

		for (int i = 0; i < 8; ++i) {
			password = password + (char) (random.nextInt(26) + 97);
		}

		return password;
	}

	public static String encodeInBCryptPasswordEncoder(String passwordString) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.encode(passwordString);
	}

	public static String dateFormatLong() {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(1, 1);
		return dateFormat.format(date);
	}

	public static String addUserName(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		return principal.getName() != "" && principal.getName() != null ? principal.getName() : null;
	}

	public static CustomUserDetails getAuthenticationDetails() {
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}

	public static int stringInToInt(String string) {
		return !string.equals("") && string != null ? Integer.valueOf(string) : 0;
	}
	
	public static Double stringToDouble(String string) {
		return Double.parseDouble(string);
	}
	
	public static String doubleToString(Double  doubleObj) {
		return doubleObj.toString();
	}

	public static Date stringToDateFormat(String dateInString) {
		Date date;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse(dateInString);
		} catch (ParseException var3) {
			date = null;
			var3.printStackTrace();
		}

		return date;
	}

	public static String pubUnPubDateFormat(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static Map<String, String> getClientDetails(HttpServletRequest request) {
		String browserDetails = request.getHeader("User-Agent");
		if (browserDetails == null) {
			return new HashMap();
		} else {
			String user = browserDetails.toLowerCase();
			String ip = request.getRemoteAddr();
			String os = "";
			String browser = "";
			if (browserDetails.toLowerCase().indexOf("windows") >= 0) {
				os = "Windows";
			} else if (browserDetails.toLowerCase().indexOf("mac") >= 0) {
				os = "Mac";
			} else if (browserDetails.toLowerCase().indexOf("x11") >= 0) {
				os = "Unix";
			} else if (browserDetails.toLowerCase().indexOf("android") >= 0) {
				os = "Android";
			} else if (browserDetails.toLowerCase().indexOf("iphone") >= 0) {
				os = "IPhone";
			} else {
				os = "UnKnown, More-Info: " + browserDetails;
			}

			if (user.contains("msie")) {
				String substring = browserDetails.substring(browserDetails.indexOf("MSIE")).split(";")[0];
				browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
			} else if (user.contains("safari") && user.contains("version")) {
				browser = browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0].split("/")[0] + "-"
						+ browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0].split("/")[1];
			} else if (!user.contains("opr") && !user.contains("opera")) {
				if (user.contains("chrome")) {
					browser = browserDetails.substring(browserDetails.indexOf("Chrome")).split(" ")[0].replace("/",
							"-");
				} else if (user.indexOf("mozilla/7.0") <= -1 && user.indexOf("netscape6") == -1
						&& user.indexOf("mozilla/4.7") == -1 && user.indexOf("mozilla/4.78") == -1
						&& user.indexOf("mozilla/4.08") == -1 && user.indexOf("mozilla/3") == -1) {
					if (user.contains("firefox")) {
						browser = browserDetails.substring(browserDetails.indexOf("Firefox")).split(" ")[0].replace("/",
								"-");
					} else if (user.contains("rv")) {
						browser = "IE";
					} else {
						browser = "UnKnown, More-Info: " + browserDetails;
					}
				} else {
					browser = "Netscape-?";
				}
			} else if (user.contains("opera")) {
				browser = browserDetails.substring(browserDetails.indexOf("Opera")).split(" ")[0].split("/")[0] + "-"
						+ browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0].split("/")[1];
			} else if (user.contains("opr")) {
				browser = browserDetails.substring(browserDetails.indexOf("OPR")).split(" ")[0].replace("/", "-")
						.replace("OPR", "Opera");
			}

			Map<String, String> result = new HashMap();
			result.put("os", os);
			result.put("browser", browser);
			result.put("ip", ip);
			result.put("agent", browserDetails);
			return result;
		}
	}

	public static String getBrowserInfo(String Information) {
		String browsername = "";
		String browserversion = "";
		String subsString;
		String[] info;
		if (Information.contains("MSIE")) {
			subsString = Information.substring(Information.indexOf("MSIE"));
			info = subsString.split(";")[0].split(" ");
			browsername = info[0];
			browserversion = info[1];
		} else if (Information.contains("Firefox")) {
			subsString = Information.substring(Information.indexOf("Firefox"));
			info = subsString.split(" ")[0].split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (Information.contains("Chrome")) {
			subsString = Information.substring(Information.indexOf("Chrome"));
			info = subsString.split(" ")[0].split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (Information.contains("Opera")) {
			subsString = Information.substring(Information.indexOf("Opera"));
			info = subsString.split(" ")[0].split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (Information.contains("Safari")) {
			subsString = Information.substring(Information.indexOf("Safari"));
			info = subsString.split(" ")[0].split("/");
			browsername = info[0];
			browserversion = info[1];
		}

		return browsername + "-" + browserversion;
	}

	public static String getMacAddress(String ipAddress) {
		System.out.println("MAC ADDRESS  AND IPADDREDD" + ipAddress);
		String str = "";
		String macAddress = "";

		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ipAddress);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (int i = 1; i < 100; ++i) {
				str = input.readLine();
				if (str != null && str.indexOf("MAC Address") > 1) {
					macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
					break;
				}
			}
		} catch (Exception var7) {
			var7.printStackTrace(System.out);
		}

		return macAddress;
	}

	public static String encodingString(String plainString) {
		return Base64.encodeBase64(plainString.getBytes()).toString();
	}

	public static String decodedString(String encryptedString) {
		return Base64.decodeBase64(encryptedString.getBytes()).toString();
	}

	public static Date generateUTCTimer() {
		long ts = System.currentTimeMillis();
		Date localTime = new Date(ts);
		String format = "yyyy/MM/dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date gmtTime = new Date(sdf.format(localTime));
		return gmtTime;
	}

	public static Instant getUTCTime() {
		return Instant.now();
	}

	public static String utcToAsiaTimeZone(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return format.format(nyDateTime);
	}

	public static String utcToAsiaTimeZoneOneDayBack(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		nyDateTime = nyDateTime.minus(1, ChronoUnit.DAYS);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return format.format(nyDateTime);
	}

	public static String utcToAsiaTimeZoneSevenDayBack(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		nyDateTime = nyDateTime.minus(7, ChronoUnit.DAYS);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeDateOnly(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeHours(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("hh");
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeMinutes(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("mm");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeSeconds(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("ss");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeAndDate(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeAndDate24hrsformat(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeAndDate24hoursonlyformat(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String dateandtimeforfilters(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeDate(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyy");
		return format.format(nyDateTime);
	}

	public static String instantToZoneTimeDateSaving(Instant instant) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String drawtimegame() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer drawtime = new StringBuffer();
		if (minutes < 15) {
			drawtime.append(Integer.toString(hours) + ":");
			drawtime.append("15");
		} else if (minutes > 14 && minutes < 30) {
			drawtime.append(Integer.toString(hours) + ":");
			drawtime.append("30");
		} else if (minutes > 29 && minutes < 45) {
			drawtime.append(Integer.toString(hours) + ":");
			drawtime.append("45");
		}

		if (minutes >= 45) {
			drawtime.append(Integer.toString(hours + 1) + ":");
			drawtime.append("00");
		}

		String draw = drawtime.toString();
		return draw;
	}

	public static String advGameid(String starttime, String drawtime) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		System.out.println(nyDateTime);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		String stringdate = formatdate.format(nyDateTime);
		return stringdate + starttime + drawtime;
	}

	public static String startdrawtime() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		System.out.println(nyDateTime);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer newTime1 = new StringBuffer();
		if (minutes < 15) {
			newTime1.append(Integer.toString(hours) + ":");
			newTime1.append("00");
		} else if (minutes > 14 && minutes < 30) {
			newTime1.append(Integer.toString(hours) + ":");
			newTime1.append("15");
		} else if (minutes > 29 && minutes < 45) {
			newTime1.append(Integer.toString(hours) + ":");
			newTime1.append("30");
		}

		if (minutes >= 45) {
			newTime1.append(Integer.toString(hours) + ":");
			newTime1.append("45");
		}

		String starttime = newTime1.toString();
		return starttime;
	}

	public static String morelesstime() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		System.out.println(nyDateTime);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer sttime = new StringBuffer();
		if (minutes < 15) {
			sttime.append(Integer.toString(hours - 1) + ":");
			sttime.append("45");
		} else if (minutes > 14 && minutes < 30) {
			sttime.append(Integer.toString(hours) + ":");
			sttime.append("00");
		} else if (minutes > 29 && minutes < 45) {
			sttime.append(Integer.toString(hours) + ":");
			sttime.append("15");
		}

		if (minutes >= 45) {
			sttime.append(Integer.toString(hours) + ":");
			sttime.append("30");
		}

		String morelesstime = sttime.toString();
		return morelesstime;
	}

	public static String gameid() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringdate = formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer newTime1 = new StringBuffer();
		if (minutes < 15) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("00");
		} else if (minutes > 14 && minutes < 30) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("15");
		} else if (minutes > 29 && minutes < 45) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("30");
		}

		if (minutes >= 45) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("45");
		}

		StringBuffer newTime = new StringBuffer();
		if (minutes < 15) {
			newTime.append(Integer.toString(hours));
			newTime.append("15");
		} else if (minutes > 14 && minutes < 30) {
			newTime.append(Integer.toString(hours));
			newTime.append("30");
		} else if (minutes > 29 && minutes < 45) {
			newTime.append(Integer.toString(hours));
			newTime.append("45");
		}

		if (minutes >= 45) {
			newTime.append(Integer.toString(hours + 1));
			newTime.append("00");
		}

		StringBuffer gameid = new StringBuffer(stringdate);
		String str = newTime1.toString();
		String str1 = newTime.toString();
		gameid.append(str);
		gameid.append(str1);
		String s = gameid.toString();
		return s;
	}

	public static String pregameid() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		System.out.println(nyDateTime);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringdate = formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer lesstime = new StringBuffer();
		if (minutes < 15) {
			lesstime.append(Integer.toString(hours - 1));
			lesstime.append("45");
		} else if (minutes > 14 && minutes < 30) {
			lesstime.append(Integer.toString(hours));
			lesstime.append("00");
		} else if (minutes > 29 && minutes < 45) {
			lesstime.append(Integer.toString(hours));
			lesstime.append("15");
		}

		if (minutes >= 45) {
			lesstime.append(Integer.toString(hours));
			lesstime.append("30");
		}

		StringBuffer newTime1 = new StringBuffer();
		if (minutes < 15) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("00");
		} else if (minutes > 14 && minutes < 30) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("15");
		} else if (minutes > 29 && minutes < 45) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("30");
		}

		if (minutes >= 45) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("45");
		}

		StringBuffer game = new StringBuffer(stringdate);
		game.append(lesstime.toString());
		String str = newTime1.toString();
		game.append(str);
		String pregameid = game.toString();
		return pregameid;
	}

	public static String receiptId(Integer autoIncrementId) {
		String id = String.format("%09d", autoIncrementId);
		DateFormat df = new SimpleDateFormat("yy");
		String formattedDate = df.format(Calendar.getInstance().getTime());
		return "SY" + formattedDate + id;
	}

	public static String getDate(Calendar cal) {
		return cal.get(1) + "-" + (cal.get(2) + 1) + "-" + cal.get(5);
	}

	public static String datetimeFrom(String dateFrom) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.add(Calendar.DAY_OF_MONTH, -3);

		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (dateFrom == "") {
			dateFrom = dateFormat.format(date) + " 00:00:00";
		}

		return dateFrom;
	}

	public static String datetimeEnd(String dateEnd) {
		if (dateEnd == "") {
			dateEnd = utcToAsiaTimeZone(getUTCTime());
		}

		return dateEnd;
	}

	public static String searchEmpty(String search, Integer id) {
		if (search == "") {
			search = "" + id;
		}

		return search;
	}

	public static String starttimeSecondGame() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		System.out.println(nyDateTime);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer newTime1 = new StringBuffer();
		if (minutes < 20) {
			newTime1.append(Integer.toString(hours) + ":");
			newTime1.append("00");
		} else if (minutes >= 20 && minutes < 40) {
			newTime1.append(Integer.toString(hours) + ":");
			newTime1.append("20");
		} else if (minutes >= 40) {
			newTime1.append(Integer.toString(hours) + ":");
			newTime1.append("40");
		}

		String starttime = newTime1.toString();
		return starttime;
	}

	public static String drawTimeSecondGame() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		System.out.println(nyDateTime);
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer drawtime = new StringBuffer();
		if (minutes < 20) {
			drawtime.append(Integer.toString(hours) + ":");
			drawtime.append("20");
		} else if (minutes >= 20 && minutes < 40) {
			drawtime.append(Integer.toString(hours) + ":");
			drawtime.append("40");
		} else if (minutes >= 40) {
			drawtime.append(Integer.toString(hours + 1) + ":");
			drawtime.append("00");
		}

		String draw = drawtime.toString();
		return draw;
	}

	public static String gameidSecondGame() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringdate = formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer newTime1 = new StringBuffer();
		if (minutes < 20) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("00");
		} else if (minutes >= 20 && minutes < 40) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("20");
		} else if (minutes >= 40) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("40");
		}

		StringBuffer newTime = new StringBuffer();
		if (minutes < 20) {
			newTime.append(Integer.toString(hours));
			newTime.append("20");
		} else if (minutes >= 20 && minutes < 40) {
			newTime.append(Integer.toString(hours));
			newTime.append("40");
		} else if (minutes >= 40) {
			newTime.append(Integer.toString(hours + 1));
			newTime.append("00");
		}

		StringBuffer gameid = new StringBuffer(stringdate);
		String str = newTime1.toString();
		String str1 = newTime.toString();
		gameid.append(str);
		gameid.append(str1);
		String s = gameid.toString();
		return s;
	}

	public static String previousGameidSecondGame() {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringdate = formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		StringBuffer newTime1 = new StringBuffer();
		if (minutes < 20) {
			Integer num = hours - 1;
			newTime1.append(Integer.toString(num));
			newTime1.append("40");
		} else if (minutes >= 20 && minutes < 40) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("00");
		} else if (minutes >= 40) {
			newTime1.append(Integer.toString(hours));
			newTime1.append("20");
		}

		StringBuffer newTime = new StringBuffer();
		if (minutes < 20) {
			newTime.append(Integer.toString(hours));
			newTime.append("00");
		} else if (minutes >= 20 && minutes < 40) {
			newTime.append(Integer.toString(hours));
			newTime.append("20");
		} else if (minutes >= 40) {
			newTime.append(Integer.toString(hours));
			newTime.append("40");
		}

		StringBuffer gameid = new StringBuffer(stringdate);
		String str = newTime1.toString();
		String str1 = newTime.toString();
		gameid.append(str);
		gameid.append(str1);
		String s = gameid.toString();
		return s;
	}

	public static String IntToLettersIncrement(Integer value) {
//      String result;
//      for(result = ""; value = value - 1 >= 0; value = value / 26) {
//         result = (char)(65 + value % 26) + result;
//      }
//
//      return result;
		String result = "";
		while (--value >= 0) {
			result = (char) ('A' + value % 26) + result;
			value /= 26;
		}
		return result;
	}

	public static String getgameid(String duration) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringdate = formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int conduration = Integer.parseInt(duration);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		String s = null;
		StringBuffer newTime1;
		StringBuffer newTime;
		StringBuffer gameid;
		String str;
		String str1;
		if (conduration == 15) {
			newTime1 = new StringBuffer();
			if (minutes < 15) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("00");
			} else if (minutes > 14 && minutes < 30) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("15");
			} else if (minutes > 29 && minutes < 45) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("30");
			}

			if (minutes >= 45) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("45");
			}

			newTime = new StringBuffer();
			if (minutes < 15) {
				newTime.append(Integer.toString(hours));
				newTime.append("15");
			} else if (minutes > 14 && minutes < 30) {
				newTime.append(Integer.toString(hours));
				newTime.append("30");
			} else if (minutes > 29 && minutes < 45) {
				newTime.append(Integer.toString(hours));
				newTime.append("45");
			}

			if (minutes >= 45) {
				newTime.append(Integer.toString(hours + 1));
				newTime.append("00");
			}

			gameid = new StringBuffer(stringdate);
			str = newTime1.toString();
			str1 = newTime.toString();
			gameid.append(str);
			gameid.append(str1);
			s = gameid.toString();
		} else if (conduration == 20) {
			newTime1 = new StringBuffer();
			if (minutes < 20) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("00");
			} else if (minutes >= 20 && minutes < 40) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("20");
			} else if (minutes >= 40) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("40");
			}

			newTime = new StringBuffer();
			if (minutes < 20) {
				newTime.append(Integer.toString(hours));
				newTime.append("20");
			} else if (minutes >= 20 && minutes < 40) {
				newTime.append(Integer.toString(hours));
				newTime.append("40");
			} else if (minutes >= 40) {
				newTime.append(Integer.toString(hours + 1));
				newTime.append("00");
			}

			gameid = new StringBuffer(stringdate);
			str = newTime1.toString();
			str1 = newTime.toString();
			gameid.append(str);
			gameid.append(str1);
			s = gameid.toString();
		} else {
			newTime1 = new StringBuffer();
			if (minutes < 30) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("00");
			} else if (minutes >= 30 && minutes <= 59) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("30");
			}

			newTime = new StringBuffer();
			if (minutes < 30) {
				newTime.append(Integer.toString(hours));
				newTime.append("30");
			} else if (minutes >= 30 && minutes <= 59) {
				newTime.append(Integer.toString(hours + 1));
				newTime.append("00");
			}

			gameid = new StringBuffer(stringdate);
			str = newTime1.toString();
			str1 = newTime.toString();
			gameid.append(str);
			gameid.append(str1);
			s = gameid.toString();
		}

		return s;
	}

	public static String getstarttime(String duration) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter formatdate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		formatdate.format(nyDateTime);
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		int conduration = Integer.parseInt(duration);
		String starttime = null;
		StringBuffer newTime1;
		if (conduration == 15) {
			newTime1 = new StringBuffer();
			if (minutes < 15) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("00");
			} else if (minutes > 14 && minutes < 30) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("15");
			} else if (minutes > 29 && minutes < 45) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("30");
			}

			if (minutes >= 45) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("45");
			}

			starttime = newTime1.toString();
		} else if (conduration == 20) {
			newTime1 = new StringBuffer();
			if (minutes < 20) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("00");
			} else if (minutes >= 20 && minutes < 40) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("20");
			} else if (minutes >= 40) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("40");
			}

			starttime = newTime1.toString();
		} else {
			newTime1 = new StringBuffer();
			if (minutes < 30) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("00");
			} else if (minutes >= 30 && minutes <= 59) {
				newTime1.append(Integer.toString(hours) + ":");
				newTime1.append("30");
			}

			starttime = newTime1.toString();
		}

		return starttime;
	}

	public static String getdrawtime(String duration) {
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter formathours = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter formatminutes = DateTimeFormatter.ofPattern("mm");
		String stringhours = formathours.format(nyDateTime);
		String stringminutes = formatminutes.format(nyDateTime);
		int hours = Integer.parseInt(stringhours);
		int minutes = Integer.parseInt(stringminutes);
		int conduration = Integer.parseInt(duration);
		String draw = null;
		StringBuffer drawtime;
		if (conduration == 15) {
			drawtime = new StringBuffer();
			if (minutes < 15) {
				drawtime.append(Integer.toString(hours) + ":");
				drawtime.append("15");
			} else if (minutes > 14 && minutes < 30) {
				drawtime.append(Integer.toString(hours) + ":");
				drawtime.append("30");
			} else if (minutes > 29 && minutes < 45) {
				drawtime.append(Integer.toString(hours) + ":");
				drawtime.append("45");
			}

			if (minutes >= 45) {
				drawtime.append(Integer.toString(hours + 1) + ":");
				drawtime.append("00");
			}

			draw = drawtime.toString();
		} else if (conduration == 20) {
			drawtime = new StringBuffer();
			if (minutes < 20) {
				drawtime.append(Integer.toString(hours) + ":");
				drawtime.append("20");
			} else if (minutes >= 20 && minutes < 40) {
				drawtime.append(Integer.toString(hours) + ":");
				drawtime.append("40");
			} else if (minutes >= 40) {
				drawtime.append(Integer.toString(hours + 1) + ":");
				drawtime.append("00");
			}

			draw = drawtime.toString();
		} else {
			drawtime = new StringBuffer();
			if (minutes < 30) {
				drawtime.append(Integer.toString(hours) + ":");
				drawtime.append("30");
			} else {
				drawtime.append(Integer.toString(hours + 1) + ":");
				drawtime.append("00");
			}

			draw = drawtime.toString();
		}

		return draw;
	}

	public static String Teencategory(String number) {
		String WinningPrice = "";
		String x = number.substring(0, 1);
		String y = number.substring(1, 2);
		String z = number.substring(2, 3);
		Integer xx = stringInToInt(x);
		Integer xy = stringInToInt(y) - 1;
		Integer xz = stringInToInt(z) - 2;
		if (x.equals(y) && x.equals(z)) {
			System.out.println("BOX NUMBER" + number);
			WinningPrice = "TNBOX";
		} else if (xx == xy && xx == xz) {
			System.out.println("SERIES NUMBER" + number);
			WinningPrice = "TNSERIES";
		} else if (!x.equals(y) && !y.equals(z)) {
			System.out.println("RANDOM NUMBER" + number);
			WinningPrice = "TNRANDOM";
		} else {
			System.out.println("DOUBLE NUMBER" + number);
			WinningPrice = "TNDOUBLE";
		}

		return WinningPrice;
	}

	public static Integer TeencategoryAmount(String number) {
		String x = number.substring(0, 1);
		String y = number.substring(1, 2);
		String z = number.substring(2, 3);
		Integer xx = stringInToInt(x);
		Integer xy = stringInToInt(y) - 1;
		Integer xz = stringInToInt(z) - 2;
		Integer xxx = stringInToInt(x);
		Integer xxy = stringInToInt(y) + 1;
		Integer xxz = stringInToInt(z) + 2;
		Integer WinningPrice;
		if (x.equals(y) && x.equals(z)) {
			WinningPrice = 4000;
		} else if (xx == xy && xx == xz) {
			WinningPrice = 3000;
		} else if (xxx == xxy && xxx == xxz) {
			WinningPrice = 3000;
		} else if (!x.equals(y) && !y.equals(z)) {
			WinningPrice = 1000;
		} else {
			WinningPrice = 2000;
		}

		return WinningPrice;
	}

	public static String getfreezetime(Instant instant) {
		String hours = instantToZoneTimeAndDate24hoursonlyformat(getUTCTime());
		ZoneId newYokZoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime nyDateTime = getUTCTime().atZone(newYokZoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH");
		format.format(nyDateTime);
		return format.format(nyDateTime);
	}

	public static String getOpenDouble(String number) {
		String openDouble = number.substring(0, 2);
		return openDouble;
	}

	public static String getCloseDouble(String number) {
		String closeDouble = number.substring(1, 3);
		return closeDouble;
	}

	public static int getSingle(int n) {
		int sum;
		for (sum = 0; n != 0; n /= 10) {
			sum += n % 10;
		}

		if (sum < 10) {
			sum = sum;
		} else {
			sum = getSingle(sum);
		}

		return sum;
	}

	public static String gpgetgameid(String duration, String uhour, String uminute, String udate) {
		int conduration = Integer.parseInt(duration);
		int hours = Integer.parseInt(uhour);
		int minutes = Integer.parseInt(uminute);
		String s = null;
		StringBuffer newTime1;
		StringBuffer newTime;
		StringBuffer gameid;
		String str;
		String str1;
		if (conduration == 15) {
			newTime1 = new StringBuffer();
			if (minutes < 15) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("00");
			} else if (minutes > 14 && minutes < 30) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("15");
			} else if (minutes > 29 && minutes < 45) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("30");
			}

			if (minutes >= 45) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("45");
			}

			newTime = new StringBuffer();
			if (minutes < 15) {
				newTime.append(Integer.toString(hours));
				newTime.append("15");
			} else if (minutes > 14 && minutes < 30) {
				newTime.append(Integer.toString(hours));
				newTime.append("30");
			} else if (minutes > 29 && minutes < 45) {
				newTime.append(Integer.toString(hours));
				newTime.append("45");
			}

			if (minutes >= 45) {
				newTime.append(Integer.toString(hours + 1));
				newTime.append("00");
			}

			gameid = new StringBuffer(udate);
			str = newTime1.toString();
			str1 = newTime.toString();
			gameid.append(str);
			gameid.append(str1);
			s = gameid.toString();
		} else if (conduration == 20) {
			newTime1 = new StringBuffer();
			if (minutes < 20) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("00");
			} else if (minutes >= 20 && minutes < 40) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("20");
			} else if (minutes >= 40) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("40");
			}

			newTime = new StringBuffer();
			if (minutes < 20) {
				newTime.append(Integer.toString(hours));
				newTime.append("20");
			} else if (minutes >= 20 && minutes < 40) {
				newTime.append(Integer.toString(hours));
				newTime.append("40");
			} else if (minutes >= 40) {
				newTime.append(Integer.toString(hours + 1));
				newTime.append("00");
			}

			gameid = new StringBuffer(udate);
			str = newTime1.toString();
			str1 = newTime.toString();
			gameid.append(str);
			gameid.append(str1);
			s = gameid.toString();
		} else {
			newTime1 = new StringBuffer();
			if (minutes < 30) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("00");
			} else if (minutes >= 30 && minutes <= 59) {
				newTime1.append(Integer.toString(hours));
				newTime1.append("30");
			}

			newTime = new StringBuffer();
			if (minutes < 30) {
				newTime.append(Integer.toString(hours));
				newTime.append("30");
			} else if (minutes >= 30 && minutes <= 59) {
				newTime.append(Integer.toString(hours + 1));
				newTime.append("00");
			}

			gameid = new StringBuffer(udate);
			str = newTime1.toString();
			str1 = newTime.toString();
			gameid.append(str);
			gameid.append(str1);
			s = gameid.toString();
		}

		return s;
	}

	public static List<Integer> pickNRandomElements(List<Integer> list, int n, Random r) {
		int length = list.size();

		if (length < n)
			return null;

		// We don't need to shuffle the whole list
		for (int i = length - 1; i >= length - n; --i) {
			Collections.swap(list, i, r.nextInt(i + 1));
		}
		return list.subList(length - n, length);
	}

	public static List<Integer> pickNRandomElements(List<Integer> list, int n) {
		return pickNRandomElements(list, n, ThreadLocalRandom.current());
	}

	public static String getUserName() {
		return null;
	}

}
