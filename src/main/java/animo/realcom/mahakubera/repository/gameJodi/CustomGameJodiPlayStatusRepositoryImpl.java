package animo.realcom.mahakubera.repository.gameJodi;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatus;

public class CustomGameJodiPlayStatusRepositoryImpl implements CustomGameJodiPlayStatusRepository {

	public CustomGameJodiPlayStatusRepositoryImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	EntityManager entityManger;

	@Override
	public Optional<GameJodiPlayStatus> findRecentGameByPlayDate(LocalDate date) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
