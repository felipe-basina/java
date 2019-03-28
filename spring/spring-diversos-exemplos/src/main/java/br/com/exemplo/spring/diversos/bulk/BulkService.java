package br.com.exemplo.spring.diversos.bulk;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BulkService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private EntityManagerFactory emf;

	private EntityManager em;

	@Autowired
	public BulkService(EntityManagerFactory emf, EntityManager em) {
		super();
		this.emf = emf;
		this.em = em;
	}

	public void bulkInsert() {
		List<BulkEntity> bulkEntities = this.createBulkEntityList();
		logger.info("[BULK] Init {}", OffsetDateTime.now());
		EntityManager entityManager = this.emf.createEntityManager();
		entityManager.getTransaction().begin();
		bulkEntities.forEach(bulk -> entityManager.persist(bulk));
		entityManager.getTransaction().commit();
		entityManager.close();
		logger.info("[BULK] End {}", OffsetDateTime.now());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void regularInsert() {
		List<BulkEntity> bulkEntities = this.createBulkEntityList();
		logger.info("[REGULAR] Init {}", OffsetDateTime.now());
		bulkEntities.forEach(bulk -> this.em.persist(bulk));
		logger.info("[REGULAR] End {}", OffsetDateTime.now());
	}

	private List<BulkEntity> createBulkEntityList() {
		List<BulkEntity> bulkEntities = new ArrayList<>();
		for (int index = 0; index < 500; index++) {
			bulkEntities.add(new BulkEntity(this.generateUniqueId(), "description" + index, OffsetDateTime.now()));
		}
		return bulkEntities;
	}

	private Long generateUniqueId() {
		long val = -1;
		do {
			final UUID uid = UUID.randomUUID();
			final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
			buffer.putLong(uid.getLeastSignificantBits());
			buffer.putLong(uid.getMostSignificantBits());
			final BigInteger bi = new BigInteger(buffer.array());
			val = bi.longValue();
		} while (val < 0);
		return val;
	}

}
