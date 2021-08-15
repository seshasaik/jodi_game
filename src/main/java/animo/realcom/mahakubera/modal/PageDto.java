package animo.realcom.mahakubera.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageDto {

	private int size;
	private int page;
	private long total;
	private long filtered;
	private Object data;

}
