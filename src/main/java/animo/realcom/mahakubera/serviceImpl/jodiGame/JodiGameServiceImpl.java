package animo.realcom.mahakubera.serviceImpl.jodiGame;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import animo.realcom.mahakubera.entity.JodiTicket;
import animo.realcom.mahakubera.repository.JodiTicketRepository;
import animo.realcom.mahakubera.service.jodiGame.JodiGameService;

@Service
@Transactional
public class JodiGameServiceImpl implements JodiGameService {

	@Autowired
	JodiTicketRepository jodiTicketRepository;
	
	
	public JodiGameServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JodiTicket> saveJodiTicket(List<JodiTicket> jodiTickets) {
		// TODO Auto-generated method stub
		return jodiTicketRepository.saveAll(jodiTickets);
	}

}
