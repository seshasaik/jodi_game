package animo.realcom.mahakubera.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.entity.JodiResult;
import animo.realcom.mahakubera.repository.JodiResultRepository;
import animo.realcom.mahakubera.service.GameOpenCloseService;

@Service
public class GameOpenCloseServiceImpl implements GameOpenCloseService {

	@Autowired
	JodiResultRepository jodiResultRepository;

	public GameOpenCloseServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JodiResult> getRecentGameResulst() {
		// TODO Auto-generated method stub
		return jodiResultRepository.findRecentGameDrawResult();
	}

	
}
