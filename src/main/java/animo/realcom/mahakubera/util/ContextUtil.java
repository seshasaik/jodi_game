package animo.realcom.mahakubera.util;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class ContextUtil {

	private final ThreadLocal<Context> threadContext = new ThreadLocal<>();

	public ContextUtil() {
		// TODO Auto-generated constructor stub

	}

	public void init() {
		Context context = new Context();
		threadContext.set(context);
	}

	public Context getContext() {
		if (threadContext.get() == null)
			init();
		return threadContext.get();
	}

	public void clearContext() {
		threadContext.remove();
	}

	@Getter
	@Setter
	public static class Context {
		private UserType userType;

	}

}
