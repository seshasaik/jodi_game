package animo.realcom.mahakubera.util;

public class URIConstants {

	public static final String VERSION = "/V1";
	public static final String LOGIN = "/login";
	public static final String PROFILE = "/profile";
	
	public static final String COMMON_BASE_PATH = "/common";
	
	public static final String COUNTRY = "/country";
	public static final String COUNTRY_REGION = COUNTRY + "/{countryId}/region";
	public static final String ROLES = "/role";
	
	public static final String USER_BASE_PATH = "/user";
	public static final String USER= "/{userId}";
		
	public static final String GAME_JODI_BASE_PATH = "/game-jodi";
	
	public static final String GAME_JODI_COMAPANY_LIST = "/companies";
	public static final String GAME_JODI_NEXT_GAME = "/next-game";
	public static final String GAME_JODI_FUTURE_GAME = "/future-game";
	public static final String GAME_GODI_SAVE_TRANSACTION = "/save-transaction";
	public static final String GAME_GODI_CANCEL_TRANSACTION = "/cancel-transaction/{transactionId}";
	public static final String GAME_GODI_CLAIM_TRANSACTION = "/claim-transaction/{transactionId}";
	public static final String JODI_GAME_RECENT_DRAW_RESULT = "/recent-result";
	public static final String JODI_GAME_CURRENT_DRAW_RESULT_STATUS = "/{gameJodiTicketId}/status";
	public static final String JODI_GAME_TIMING_LIST = "/global-timing-list";
	
	public static final String GAME_GODI_TRANSACTION_LIST = "/transactions";
	public static final String GAME_GODI_TRANSACTION_DETAIL = "/transaction/{transactionId}";
	public static final String GAME_GODI_CANCELED_TRANSACTION_LIST = "/cancel-transactions";	
	public static final String GAME_GODI_WINING_TRANSACTION_LIST = "/win-transactions";
	public static final String JODI_GAME_JOURNAL_ENTRY = "/journal-entry";
	public static final String JODI_GAME_JOURNAL_ENTRY_SUMMARY = "/journal-entry-summary";
	
	public static final String JODI_GAME_RESULTS = "/result";
	public static final String JODI_GAME_WALLET = "/wallet";
}
