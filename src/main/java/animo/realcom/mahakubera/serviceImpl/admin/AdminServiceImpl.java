package animo.realcom.mahakubera.serviceImpl.admin;

import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.service.admin.AdminService;
import animo.realcom.mahakubera.util.ApplicationUtil;

@Service
public class AdminServiceImpl implements AdminService {

	public AdminServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String fetchingDuration(String hours) {

		int hour = ApplicationUtil.stringInToInt(hours);

		String freeze = "15";
		if (hour >= 9 && hour < 15) {
			freeze = "15";
		} else if (hour >= 15 && hour < 23) {
			freeze = "20";
		} else if (hour >= 23 || hour < 9) {
			freeze = "30";
		}

		return freeze;
	}

}
